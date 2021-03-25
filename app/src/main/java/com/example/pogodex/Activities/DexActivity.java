package com.example.pogodex.Activities;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pogodex.ChargeMovesAdapter;
import com.example.pogodex.EvolutionsAdapter;
import com.example.pogodex.FastMovesAdapter;
import com.example.pogodex.ModelClasses.PokemonChargedMoves;
import com.example.pogodex.ModelClasses.PokemonEvolutionsChld;
import com.example.pogodex.ModelClasses.PokemonEvolutionsPar;
import com.example.pogodex.ModelClasses.PokemonFastMoves;
import com.example.pogodex.ModelClasses.PokemonGeneralData;
import com.example.pogodex.ModelClasses.PokemonMaxCP;
import com.example.pogodex.ModelClasses.PokemonStats;
import com.example.pogodex.ModelClasses.SelectedPokemonMoves;
import com.example.pogodex.R;
import com.example.pogodex.RetroInterfaces.RequestInterfaceEvolutions;
import com.example.pogodex.RetroInterfaces.RequestInterfaceMovePool;
import com.example.pogodex.RetroInterfaces.RequestInterfaceStats;
import com.example.pogodex.ViewModels.DexActivityViewModel;
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
    private static DexActivity dexActivity;

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
    private PokemonMaxCP pkmnMaxCP;
    private PokemonStats pkmnStats;
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
    private DexActivityViewModel dexActivityViewModel;


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex);
        dexActivity = this;

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

        fastMoveRV.setLayoutManager(new LinearLayoutManager(this));
        chargedMoveRV.setLayoutManager(new LinearLayoutManager(this));
        downArrow.setVisibility(View.INVISIBLE);

        //get Data from Main Activity from received intent
        Bundle bundle = getIntent().getExtras();
        data = (PokemonGeneralData) bundle.getParcelable("id");
        allFastMoves = bundle.getParcelableArrayList("fm");
        allChargedMoves = bundle.getParcelableArrayList("cm");

        InitializeBarChart(barChart);

        dexActivityViewModel = ViewModelProviders.of(this).get(DexActivityViewModel.class);

        if (dexActivityViewModel.getPkmnChargedMoves().getValue() == null) {
            noEvoTxt.setText("Loading...");
            dexActivityViewModel.Init(data, allChargedMoves, allFastMoves);
        }

        //Observer for MaxCP
        dexActivityViewModel.getPkmnMaxCP().observe(this, new Observer<List<PokemonMaxCP>>() {
            @Override
            public void onChanged(List<PokemonMaxCP> pokemonMaxCPS) {
                if (pokemonMaxCPS != null) {
                    pkmnMaxCP = pokemonMaxCPS.get(0);
                    setMaxCpTxt(pkmnMaxCP.get_maxCP());
                }
            }
        });

        //Observer for Stats
        dexActivityViewModel.getPkmnStats().observe(this, new Observer<List<PokemonStats>>() {
            @Override
            public void onChanged(List<PokemonStats> pokemonStats) {
                if (pokemonStats != null) {
                    pkmnStats = pokemonStats.get(0);
                    setBarGraph(pkmnStats);
                }
            }
        });

        //Observer for EvolutionsList
        dexActivityViewModel.getPkmnEvoChild().observe(this, new Observer<ArrayList<PokemonEvolutionsChld>>() {
            @Override
            public void onChanged(ArrayList<PokemonEvolutionsChld> pokemonEvolutionsChlds) {
                if (pokemonEvolutionsChlds != null) {
                    pkmnListEvosChild = new ArrayList<>(pokemonEvolutionsChlds);
                    if (pkmnListEvosChild.size() > 0) {
                        noEvoTxt.setVisibility(View.GONE);
                    }
                    setEvolutionAdapter(pkmnListEvosChild);
                }
            }
        });

        //Observer for Fast Moves List
        dexActivityViewModel.getPkmnFastMoves().observe(dexActivity, new Observer<ArrayList<PokemonFastMoves>>() {
            @Override
            public void onChanged(ArrayList<PokemonFastMoves> pokemonFastMoves) {
                if (pokemonFastMoves != null) {
                    finalFastMoves = new ArrayList<>(pokemonFastMoves);
                    setMovesAdapter(finalFastMoves, finalChargedMoves);
                }
            }
        });

        //Observer for Charge Moves list
        dexActivityViewModel.getPkmnChargedMoves().observe(dexActivity, new Observer<ArrayList<PokemonChargedMoves>>() {
            @Override
            public void onChanged(ArrayList<PokemonChargedMoves> pokemonChargedMoves) {
                if (pokemonChargedMoves != null) {
                    finalChargedMoves = new ArrayList<>(pokemonChargedMoves);
                    setMovesAdapter(finalFastMoves, finalChargedMoves);
                }
            }
        });

        dexActivityViewModel.getThisPkmnMoves().observe(this, new Observer<ArrayList<SelectedPokemonMoves>>() {
            @Override
            public void onChanged(ArrayList<SelectedPokemonMoves> selectedPokemonMoves) {
                if (selectedPokemonMoves != null) {
                    selectedPkmn = new ArrayList<>(selectedPokemonMoves);
                }
            }
        });


        pokeIDTxt.setText("#" + data.get_pokemonID());
        toolBar.setTitle(data.get_pokemonName());

        //Load BG and Image Assets into Glide
        setLoadImageAssets();

        //Calculate the BG
        setDexBG();

    }

    //getInstance of this activity to call methods (only to be called from ViewModel of this activity)
    public static DexActivity getInstance() {
        return dexActivity;
    }

    //To display error toast on network issue
    public void DisplayErrorToast() {
        Toast.makeText(DexActivity.this, "Something went wrong! Try again later.", Toast.LENGTH_SHORT).show();
    }

    //Set Text for Max CP
    public void setMaxCpTxt(String text) {
        maxCpTxt.setText(text);
    }

    public void clearBar() {
        statEntry.clear();
        barDataSet.clear();
        barData.clearValues();
    }

    public void setBarGraph(PokemonStats stats) {
        //Load it in BarGraph
        statEntry.add(new BarEntry(1, stats.get_baseStamina()));
        statEntry.add(new BarEntry(2, stats.get_baseDefence()));
        statEntry.add(new BarEntry(3, stats.get_baseAttack()));
        barDataSet = new BarDataSet(statEntry, "Stats");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setDrawValues(false);
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();
    }

    public void afterEvoLoadEffects() {
        noEvoTxt.setVisibility(View.GONE);
        evoScrollView.getViewTreeObserver().addOnScrollChangedListener(DexActivity.this);
    }

    public void displayNoEvolutions() {
        noEvoTxt.setText("Doesn't Evolve.");
        evoScrollView.setVisibility(View.GONE);
    }

    public void setEvolutionAdapter(ArrayList<PokemonEvolutionsChld> pec) {
        evoAdapter = new EvolutionsAdapter(pec, DexActivity.this);
        evolutionRV.setLayoutManager(new LinearLayoutManager(this));
        evolutionRV.setAdapter(evoAdapter);
    }

    public void setMovesAdapter(ArrayList<PokemonFastMoves> pfm, ArrayList<PokemonChargedMoves> pcm) {
        //assign object to adapter
        fmAdapter = new FastMovesAdapter(pfm, DexActivity.this);
        cmAdapter = new ChargeMovesAdapter(pcm, DexActivity.this);
        //set adapter
        fastMoveRV.setAdapter(fmAdapter);
        chargedMoveRV.setAdapter(cmAdapter);
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

        if (dexActivityViewModel.getPkmnEvoChild().getValue().size() > 1) {
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