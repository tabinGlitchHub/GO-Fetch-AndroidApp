package com.example.pogodex.Activities;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.bumptech.glide.Glide;
import com.example.pogodex.Interfaces.RequestInterfaceMaxCP;
import com.example.pogodex.Interfaces.RequestInterfaceStats;
import com.example.pogodex.ModelClasses.PokemonGeneralData;
import com.example.pogodex.ModelClasses.PokemonMaxCP;
import com.example.pogodex.ModelClasses.PokemonStats;
import com.example.pogodex.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DexActivity extends AppCompatActivity {

    //Declare All Views
    CollapsingToolbarLayout toolBar;
    TextView pokeIDTxt, maxCpTxt, fastMove1Txt, fastMove2Txt,
            chargeMove1Txt, chargeMove2Txt, chargeMove3Txt;
    ImageView pokeImage, type1DexIcon, type2DexIcon;
    CoordinatorLayout parentLay;
    HorizontalBarChart barChart;

    private static String JSON_Url = "https://pokemon-go1.p.rapidapi.com/";

    private Integer[] gradientBG = {
            R.drawable.dex_gradient_bug, R.drawable.dex_gradient_dark, R.drawable.dex_gradient_dragon,
            R.drawable.dex_gradient_electric, R.drawable.dex_gradient_fairy, R.drawable.dex_gradient_fighting,
            R.drawable.dex_gradient_fire, R.drawable.dex_gradient_flyng, R.drawable.dex_gradient_ghost,
            R.drawable.dex_gradient_grass, R.drawable.dex_gradient_ground, R.drawable.dex_gradient_ice,
            R.drawable.dex_gradient_normal, R.drawable.dex_gradient_poison, R.drawable.dex_gradient_psychic,
            R.drawable.dex_gradient_rock, R.drawable.dex_gradient_steel, R.drawable.dex_gradient_water
    };
    private String[] typeOf = {"Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting", "Fire", "Flying",
            "Ghost", "Grass", "Ground", "Ice", "Normal", "Poison", "Psychic", "Rock", "Steel", "Water"};

    private PokemonGeneralData data;
    private List<PokemonMaxCP> pkmnListMaxCP;
    private List<PokemonStats> pkmnListStats;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex);

        //Grab Reference to Views
        pokeIDTxt = findViewById(R.id.pokeID);
        maxCpTxt = findViewById(R.id.maxCPtxt);
        pokeImage = findViewById(R.id.pokemonDexImage);
        toolBar = findViewById(R.id.collapsingBar);
        parentLay = findViewById(R.id.dexParent);
        type1DexIcon = findViewById(R.id.type1dexIcon);
        type2DexIcon = findViewById(R.id.type2dexIcon);
        barChart = findViewById(R.id.barChartStats);

        //TODO check branch before working
        //TODO Complete UI


        data = (PokemonGeneralData) getIntent().getSerializableExtra("id");
        pokeIDTxt.setText("#" + data.get_pokemonID());
        toolBar.setTitle(data.get_pokemonName());

        //Load BG and Image Assets into Glide
        setLoadImageAssets();

        //Calculate the BG
        setDexBG();

        MakeAPICall();
    }

    private void MakeAPICall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JSON_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterfaceMaxCP requestInterfaceMCP = retrofit.create(RequestInterfaceMaxCP.class);
        Call<List<PokemonMaxCP>> call2 = requestInterfaceMCP.getMaxCPJSON();

        call2.enqueue(new Callback<List<PokemonMaxCP>>() {
            @Override
            public void onResponse(Call<List<PokemonMaxCP>> call, Response<List<PokemonMaxCP>> response) {
                pkmnListMaxCP = response.body();
                for (int i = 0; i < pkmnListMaxCP.size(); ) {
                    if (pkmnListMaxCP.get(i).get_pokemonID().equals(data.get_pokemonID())
                            && pkmnListMaxCP.get(i).get_pokemonForm().equals(data.get_pokemonForm())) {
                        maxCpTxt.setText(pkmnListMaxCP.get(i).get_maxCP());
                        i++;
                    } else {
                        pkmnListMaxCP.remove(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PokemonMaxCP>> call, Throwable t) {
                Toast.makeText(DexActivity.this, "Network Error! Try Again Later.", Toast.LENGTH_LONG).show();
            }
        });

        RequestInterfaceStats requestInterfaceStats = retrofit.create(RequestInterfaceStats.class);
        Call<List<PokemonStats>> call3 = requestInterfaceStats.getPokeStatsJSON();

        call3.enqueue(new Callback<List<PokemonStats>>() {
            @Override
            public void onResponse(Call<List<PokemonStats>> call, Response<List<PokemonStats>> response) {
                pkmnListStats = response.body();
                for (int i = 0; i < pkmnListStats.size(); ) {
                    if (pkmnListStats.get(i).get_pokemonID().equals(data.get_pokemonID())
                            && pkmnListStats.get(i).get_pokemonForm().equals(data.get_pokemonForm())) {
                        String[] labels = {"Attack","Defence","Stamina"};
                        ArrayList<BarEntry> statEntry = new ArrayList<>();
                        statEntry.add(new BarEntry(1, pkmnListStats.get(i).get_baseStamina()));
                        statEntry.add(new BarEntry(2, pkmnListStats.get(i).get_baseDefence()));
                        statEntry.add(new BarEntry(3, pkmnListStats.get(i).get_baseAttack()));
                        BarDataSet barDataSet = new BarDataSet(statEntry, "Stats");
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet.setDrawValues(false);
                        BarData barData = new BarData(barDataSet);
                        barChart.setFitBars(true);
                        barChart.getAxisLeft().setAxisMinimum(0);
                        barChart.getAxisLeft().setAxisMaximum(550);
                        barChart.getAxisRight().setDrawLabels(false);
//                        barChart.getXAxis().setDrawLabels(false);
                        barChart.setData(barData);
                        barChart.animateY(500);
                        barChart.setDescription(null);
                        barChart.getAxisRight().setDrawGridLines(false);
                        barChart.getAxisLeft().setDrawGridLines(false);
                        barChart.getXAxis().setDrawGridLines(false);
                        barChart.getAxisRight().setDrawAxisLine(false);
                        barChart.getAxisLeft().setDrawAxisLine(false);
                        barChart.getXAxis().setDrawAxisLine(false);
                        barChart.setTouchEnabled(false);
                        barChart.setClickable(false);
                        barChart.setDoubleTapToZoomEnabled(false);
                        barChart.setDoubleTapToZoomEnabled(false);
                        barChart.getLegend().setEnabled(false);
                        barChart.getBarData().setDrawValues(false);
                        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                        barChart.invalidate();

                        i++;
                    } else {
                        pkmnListStats.remove(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PokemonStats>> call, Throwable t) {
                Toast.makeText(DexActivity.this, "Network Error! Try Again Later.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setDexBG() {
        for (int i = 0; i < typeOf.length; i++) {
            if (data.get_pokemonTypeString().equalsIgnoreCase(typeOf[i])) {
                int colorid = gradientBG[i];
                Drawable color = toolBar.getContext().getDrawable(colorid);
                toolBar.setBackground(color);
                parentLay.setBackgroundResource(gradientBG[i]);
            }
        }
    }

    public void setLoadImageAssets() {
        Glide.with(this)
                .asBitmap()
                .load(data.get_pokemonImage())
                .into(pokeImage);

        Glide.with(this)
                .asBitmap()
                .load(data.get_pokemonType1())
                .into(type1DexIcon);

        Glide.with(this)
                .asBitmap()
                .load(data.get_pokemonType2())
                .into(type2DexIcon);
    }
}