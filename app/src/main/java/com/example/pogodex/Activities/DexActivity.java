package com.example.pogodex.Activities;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pogodex.ChargeMovesAdapter;
import com.example.pogodex.EvolutionsAdapter;
import com.example.pogodex.FastMovesAdapter;
import com.example.pogodex.RetroInterfaces.RequestInterfaceEvolutions;
import com.example.pogodex.RetroInterfaces.RequestInterfaceMaxCP;
import com.example.pogodex.RetroInterfaces.RequestInterfaceMovePool;
import com.example.pogodex.RetroInterfaces.RequestInterfaceStats;
import com.example.pogodex.ModelClasses.PokemonChargedMoves;
import com.example.pogodex.ModelClasses.PokemonEvolutionsChld;
import com.example.pogodex.ModelClasses.PokemonEvolutionsPar;
import com.example.pogodex.ModelClasses.PokemonFastMoves;
import com.example.pogodex.ModelClasses.PokemonGeneralData;
import com.example.pogodex.ModelClasses.PokemonMaxCP;
import com.example.pogodex.ModelClasses.PokemonStats;
import com.example.pogodex.ModelClasses.SelectedPokemonMoves;
import com.example.pogodex.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DexActivity extends AppCompatActivity implements ViewTreeObserver.OnScrollChangedListener {

    //Declare All Views
    CollapsingToolbarLayout toolBar;
    TextView pokeIDTxt, maxCpTxt, noEvoTxt;
    ImageView pokeImage, type1DexIcon, type2DexIcon, downArrow;
    CoordinatorLayout parentLay;
    HorizontalBarChart barChart;
    RecyclerView evolutionRV, fastMoveRV, chargedMoveRV;
    EvolutionsAdapter evoAdapter;
    NestedScrollView evoScrollView;
    FastMovesAdapter fmAdapter;
    ChargeMovesAdapter cmAdapter;

    private static String JSON_Url = "https://pokemon-go1.p.rapidapi.com/";

    private final Integer[] gradientBG = {
            R.drawable.dex_gradient_bug, R.drawable.dex_gradient_dark, R.drawable.dex_gradient_dragon,
            R.drawable.dex_gradient_electric, R.drawable.dex_gradient_fairy, R.drawable.dex_gradient_fighting,
            R.drawable.dex_gradient_fire, R.drawable.dex_gradient_flyng, R.drawable.dex_gradient_ghost,
            R.drawable.dex_gradient_grass, R.drawable.dex_gradient_ground, R.drawable.dex_gradient_ice,
            R.drawable.dex_gradient_normal, R.drawable.dex_gradient_poison, R.drawable.dex_gradient_psychic,
            R.drawable.dex_gradient_rock, R.drawable.dex_gradient_steel, R.drawable.dex_gradient_water,
            R.drawable.dex_gradient_purified, R.drawable.dex_gradient_shadow
    };
    private String[] typeOf = {"Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting", "Fire", "Flying",
            "Ghost", "Grass", "Ground", "Ice", "Normal", "Poison", "Psychic", "Rock", "Steel", "Water"};

    private PokemonGeneralData data;
    private List<PokemonMaxCP> pkmnListMaxCP;
    private List<PokemonStats> pkmnListStats;
    private List<PokemonEvolutionsPar> pkmnListEvosParent;
    private ArrayList<PokemonEvolutionsChld> pkmnListEvosChild;
    private ArrayList<BarEntry> statEntry = new ArrayList<>();
    private BarDataSet barDataSet;
    private BarData barData;
    private ArrayList<SelectedPokemonMoves> selectedPkmn;
    private ArrayList<PokemonFastMoves> allFastMoves;
    private ArrayList<PokemonChargedMoves> allChargedMoves = new ArrayList<>();
    private ArrayList<PokemonFastMoves> finalFastMoves = new ArrayList<>();
    private ArrayList<PokemonChargedMoves> finalChargedMoves = new ArrayList<>();


    @SuppressLint("SetTextI18n")
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
        evolutionRV = findViewById(R.id.evolutionsRecyclerView);
        noEvoTxt = findViewById(R.id.noEvoTxt);
        downArrow = findViewById(R.id.scrollDownArrow);
        evoScrollView = findViewById(R.id.evoScrollView);
        fastMoveRV = findViewById(R.id.fastMoveRecyclerView);
        chargedMoveRV = findViewById(R.id.chargeMoveRecyclerView);

        evolutionRV.setLayoutManager(new LinearLayoutManager(this));
        fastMoveRV.setLayoutManager(new LinearLayoutManager(this));
        chargedMoveRV.setLayoutManager(new LinearLayoutManager(this));
        noEvoTxt.setText("Loading...");
        downArrow.setVisibility(View.INVISIBLE);

        //get Data from Main Activity from received intent
        Bundle bundle = getIntent().getExtras();
        data = (PokemonGeneralData) bundle.getParcelable("id");

        allFastMoves = bundle.getParcelableArrayList("fm");
        allChargedMoves = bundle.getParcelableArrayList("cm");

        pokeIDTxt.setText("#" + data.get_pokemonID());
        toolBar.setTitle(data.get_pokemonName());

        //Load BG and Image Assets into Glide
        setLoadImageAssets();

        //Calculate the BG
        setDexBG();

        InitializeBarChart(barChart);

        MakeAPICall();

    }

    //Since SetNoDataSet method of Barchart wasn't working, a workaround to avoid appearance of the
    //ugly text was to initialize the barchart with empty values and do the styling beforehand,
    //that is what this function does
    private void InitializeBarChart(HorizontalBarChart barChart) {
        statEntry.add(new BarEntry(1, 0));
        statEntry.add(new BarEntry(2, 0));
        statEntry.add(new BarEntry(3, 0));
        barDataSet = new BarDataSet(statEntry, "Stats");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setDrawValues(false);
        barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisLeft().setAxisMaximum(550);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getXAxis().setDrawLabels(false);
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
        barChart.invalidate();
    }

    private void MakeAPICall() {

        //Build Retrofit Instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JSON_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Make a call for 'MAX CP'
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
                Toast.makeText(DexActivity.this, "Error Loading Data! Try Again Later.", Toast.LENGTH_LONG).show();
            }
        });


        //Make a call for 'Stats'
        RequestInterfaceStats requestInterfaceStats = retrofit.create(RequestInterfaceStats.class);
        Call<List<PokemonStats>> call3 = requestInterfaceStats.getPokeStatsJSON();

        call3.enqueue(new Callback<List<PokemonStats>>() {
            @Override
            public void onResponse(Call<List<PokemonStats>> call, Response<List<PokemonStats>> response) {
                pkmnListStats = response.body();
                for (int i = 0; i < pkmnListStats.size(); ) {
                    if (pkmnListStats.get(i).get_pokemonID().equals(data.get_pokemonID())
                            && pkmnListStats.get(i).get_pokemonForm().equals(data.get_pokemonForm())) {

                        //Clear the dummy empty values when Stats are recieved from API
                        statEntry.clear();
                        barDataSet.clear();
                        barData.clearValues();
                        //Load it in BarGraph
                        statEntry.add(new BarEntry(1, pkmnListStats.get(i).get_baseStamina()));
                        statEntry.add(new BarEntry(2, pkmnListStats.get(i).get_baseDefence()));
                        statEntry.add(new BarEntry(3, pkmnListStats.get(i).get_baseAttack()));
                        barDataSet = new BarDataSet(statEntry, "Stats");
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet.setDrawValues(false);
                        barData = new BarData(barDataSet);
                        barChart.setData(barData);
                        barChart.invalidate();

                        i++;
                    } else {
                        pkmnListStats.remove(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PokemonStats>> call, Throwable t) {
                Toast.makeText(DexActivity.this, "Error Loading Data! Try Again Later.", Toast.LENGTH_LONG).show();
            }
        });


        //Make a call for 'Evolutions'
        RequestInterfaceEvolutions requestInterfaceEvolutions = retrofit.create(RequestInterfaceEvolutions.class);
        Call<List<PokemonEvolutionsPar>> call4 = requestInterfaceEvolutions.getPokeEvosJSON();

        call4.enqueue(new Callback<List<PokemonEvolutionsPar>>() {
            @Override
            public void onResponse(Call<List<PokemonEvolutionsPar>> call, Response<List<PokemonEvolutionsPar>> response) {
                if (response != null) {
                    pkmnListEvosParent = response.body();

                    for (int i = 0; i < pkmnListEvosParent.size(); ) {
                        if (pkmnListEvosParent.get(i).get_pokemonID().equals(data.get_pokemonID())
                                && pkmnListEvosParent.get(i).get_pokemonForm().equals(data.get_pokemonForm())) {
                            pkmnListEvosChild = new ArrayList<PokemonEvolutionsChld>(pkmnListEvosParent.get(i).getEvolutions());
                            noEvoTxt.setVisibility(View.GONE);
                            evoScrollView.getViewTreeObserver().addOnScrollChangedListener(DexActivity.this);
                            i++;
                        } else {
                            pkmnListEvosParent.remove(i);
                        }
                    }
                    if (pkmnListEvosParent.size() == 0) {
                        noEvoTxt.setText("Doesn't Evolve.");
                        evoScrollView.setVisibility(View.GONE);
                    }
                }
                evoAdapter = new EvolutionsAdapter(pkmnListEvosChild, DexActivity.this);
                evolutionRV.setAdapter(evoAdapter);
            }

            @Override
            public void onFailure(Call<List<PokemonEvolutionsPar>> call, Throwable t) {
                Toast.makeText(DexActivity.this, "Error Loading Data! Try Again Later.", Toast.LENGTH_LONG).show();
            }
        });

        //Make a call for 'Moves Lists'
        RequestInterfaceMovePool requestInterfaceMovePool = retrofit.create(RequestInterfaceMovePool.class);
        Call<List<SelectedPokemonMoves>> call5 = requestInterfaceMovePool.getAllMoveJSON();

        call5.enqueue(new Callback<List<SelectedPokemonMoves>>() {
            @Override
            public void onResponse(Call<List<SelectedPokemonMoves>> call, Response<List<SelectedPokemonMoves>> response) {
                selectedPkmn = new ArrayList<>(response.body());
                int numOfFM = 0;
                int numOfCM = 0;
                for (int i = 0; i < selectedPkmn.size(); ) {
                    if (data.get_pokemonName().equals(selectedPkmn.get(i).get_pokemonName())
                            && data.get_pokemonForm().equals(selectedPkmn.get(i).get_pokemonForm())) {
                        //append all elite moves to normal move list since only single list can be passed to recyclerview
                        selectedPkmn.get(i).get_fastMoves().addAll(selectedPkmn.get(i).get_eliteFastMoves());
                        selectedPkmn.get(i).get_chargedMoves().addAll(selectedPkmn.get(i).get_eliteChargedMoves());

                        //compare the current pkmn fast moves to the list of all fast moves and get details
                        for (int j = 0; j < allFastMoves.size() && numOfFM < selectedPkmn.get(i).get_fastMoves().size(); ) {
                            if (selectedPkmn.get(i).get_fastMoves().get(numOfFM).equals(allFastMoves.get(j).get_fMoveName())) {
                                finalFastMoves.add(allFastMoves.get(j));
                                numOfFM++;
                                j = 0;
                            } else {
                                j++;
                            }
                        }

                        //compare the current pkmn charged moves to the list of all charged moves and get details
                        for (int j = 0; j < allChargedMoves.size() && numOfCM < selectedPkmn.get(i).get_chargedMoves().size(); ) {
                            if (selectedPkmn.get(i).get_chargedMoves().get(numOfCM).equals(allChargedMoves.get(j).get_cMoveName())) {
                                finalChargedMoves.add(allChargedMoves.get(j));
                                numOfCM++;
                                j = 0;
                            } else {
                                j++;
                            }
                        }

                        i++;
                    } else {
                        selectedPkmn.remove(i);
                    }
                }
                //assign object to adapter
                fmAdapter = new FastMovesAdapter(finalFastMoves, DexActivity.this);
                cmAdapter = new ChargeMovesAdapter(finalChargedMoves, DexActivity.this);
                //set adapter
                fastMoveRV.setAdapter(fmAdapter);
                chargedMoveRV.setAdapter(cmAdapter);
            }

            @Override
            public void onFailure(Call<List<SelectedPokemonMoves>> call, Throwable t) {
                Toast.makeText(DexActivity.this, "Error Loading Data! Try Again Later.", Toast.LENGTH_LONG).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setDexBG() {
        if (data.get_pokemonForm().equals("Purified")) {
            int colorid = gradientBG[18];
            @SuppressLint("UseCompatLoadingForDrawables") Drawable color = toolBar.getContext().getDrawable(colorid);
            toolBar.setBackground(color);
            parentLay.setBackgroundResource(gradientBG[18]);
        } else if (data.get_pokemonForm().equals("Shadow")) {
            int colorid = gradientBG[19];
            @SuppressLint("UseCompatLoadingForDrawables") Drawable color = toolBar.getContext().getDrawable(colorid);
            toolBar.setBackground(color);
            parentLay.setBackgroundResource(gradientBG[19]);
        } else {
            for (int i = 0; i < typeOf.length; i++) {
                if (data.get_pokemonTypeString().equalsIgnoreCase(typeOf[i])) {
                    int colorid = gradientBG[i];
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable color = toolBar.getContext().getDrawable(colorid);
                    toolBar.setBackground(color);
                    parentLay.setBackgroundResource(gradientBG[i]);
                }
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

    @Override
    public void onScrollChanged() {
        View view = evoScrollView.getChildAt(evoScrollView.getChildCount() - 1);
        int top = evoScrollView.getScrollY();
        int bottom = view.getBottom() - (evoScrollView.getHeight() + evoScrollView.getScrollY());

        if (pkmnListEvosChild.size() > 1) {
            if (top <= 0) {
                downArrow.setVisibility(View.VISIBLE);
                if (downArrow.getRotation() > 0) {
                    downArrow.setRotation(0);
                }
            } else if (bottom <= 0) {
                downArrow.setVisibility(View.INVISIBLE);
            } else {
                downArrow.setRotation(180);
                downArrow.setVisibility(View.VISIBLE);
            }
        } else {
            downArrow.setVisibility(View.INVISIBLE);
        }

    }
}