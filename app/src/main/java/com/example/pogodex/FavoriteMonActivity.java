package com.example.pogodex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoriteMonActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView emptyWarnTxt;
    PokemonCardDataHolder pkmnHolder = new PokemonCardDataHolder();
    ArrayList<PokemonData> favListedMons = new ArrayList<>();
    FavPokemonCardDataHolder favholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_mon);

        emptyWarnTxt = findViewById(R.id.emptyWarningTxt);
        recyclerView = findViewById(R.id.favMonsRecyclerV);

        Intent intent = getIntent();
        favListedMons = (ArrayList<PokemonData>) intent.getSerializableExtra("key");

        System.out.println(favListedMons.size());

        if(favListedMons.size() == 0){
            emptyWarnTxt.setVisibility(View.VISIBLE);
        }else{
            emptyWarnTxt.setVisibility(View.INVISIBLE);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            favholder = new FavPokemonCardDataHolder(FavoriteMonActivity.this, favListedMons);
            recyclerView.setAdapter(favholder);
        }
    }
}
