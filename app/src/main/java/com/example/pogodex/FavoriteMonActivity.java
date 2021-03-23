package com.example.pogodex;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pogodex.ModelClasses.PokemonGeneralData;

import java.util.ArrayList;

public class FavoriteMonActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView emptyWarnTxt;
    MainPokemonListAdapter pkmnHolder = new MainPokemonListAdapter();
    ArrayList<PokemonGeneralData> favListedMons = new ArrayList<>();
    FavPokemonListAdapter favholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_mon);

        emptyWarnTxt = findViewById(R.id.emptyWarningTxt);
        recyclerView = findViewById(R.id.favMonsRecyclerV);

        Bundle bundle = getIntent().getExtras();
        favListedMons = bundle.getParcelableArrayList("key");


        if(favListedMons.size() == 0){
            emptyWarnTxt.setVisibility(View.VISIBLE);
        }else{
            emptyWarnTxt.setVisibility(View.INVISIBLE);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            favholder = new FavPokemonListAdapter(FavoriteMonActivity.this, favListedMons);
            recyclerView.setAdapter(favholder);
        }
    }
}
