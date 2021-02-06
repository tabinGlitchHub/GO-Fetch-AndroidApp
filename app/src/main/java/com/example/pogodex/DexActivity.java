package com.example.pogodex;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DexActivity extends AppCompatActivity {

    CollapsingToolbarLayout toolBar;
    TextView pokeIDTxt, maxCpTxt, fastMove1Txt, fastMove2Txt,
                chargeMove1Txt, chargeMove2Txt , chargeMove3Txt;
    ImageView pokeImage, type1DexIcon, type2DexIcon;
    CoordinatorLayout parentLay;

    String pokeId = "";
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
    PokemonData data;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex);

        pokeIDTxt = findViewById(R.id.pokeID);
        maxCpTxt = findViewById(R.id.maxCPtxt);
        pokeImage = findViewById(R.id.pokemonDexImage);
        toolBar = findViewById(R.id.collapsingBar);
        parentLay = findViewById(R.id.dexParent);
        type1DexIcon = findViewById(R.id.type1dexIcon);
        type2DexIcon = findViewById(R.id.type2dexIcon);

        //TODO check branch before working
        //TODO Complete UI


        data = (PokemonData) getIntent().getSerializableExtra("id");
        pokeIDTxt.setText("#" + data.get_pokemonID());
        toolBar.setTitle(data.get_pokemonName());

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

        for (int i = 0; i < typeOf.length; i++) {
            if(data.get_pokemonTypeString().equalsIgnoreCase(typeOf[i])){
                int colorid = gradientBG[i];
                Drawable color = toolBar.getContext().getDrawable(colorid);
                toolBar.setBackground(color);
                parentLay.setBackgroundResource(gradientBG[i]);
            }
        }
    }
}