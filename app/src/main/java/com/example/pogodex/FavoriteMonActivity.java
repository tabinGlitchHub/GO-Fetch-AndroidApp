package com.example.pogodex;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pogodex.ModelClasses.FavoritePokemon;
import com.example.pogodex.ModelClasses.PokemonGeneralData;
import com.example.pogodex.ViewModels.FavActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMonActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView emptyWarnTxt;
    private static FavoriteMonActivity favoriteMonActivity;

    private FavPokemonListAdapter favholder;
    public FavActivityViewModel favActivityViewModel;
    private List<FavoritePokemon> favoritePokemonList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_mon);
        favoriteMonActivity = this;

        emptyWarnTxt = findViewById(R.id.emptyWarningTxt);
        recyclerView = findViewById(R.id.favMonsRecyclerV);

        Bundle bundle = getIntent().getExtras();
        favoritePokemonList = bundle.getParcelableArrayList("favList");

        favActivityViewModel = ViewModelProviders.of(this).get(FavActivityViewModel.class);
        favActivityViewModel.getFavPokmeonList().observe(this, new Observer<List<FavoritePokemon>>() {
            @Override
            public void onChanged(List<FavoritePokemon> favoritePokemons) {
                if (favoritePokemons != null) {
                    favoritePokemonList = new ArrayList<>(favoritePokemons);
                    setRecyclerView((ArrayList<FavoritePokemon>) favoritePokemonList);
                } else {
                    checkShowEmptyListTxt();
                }
            }
        });

        if (favoritePokemonList != null) {
            for (int i = 0; i < favoritePokemonList.size(); i++) {
                favActivityViewModel.insertFav(favoritePokemonList.get(i));
            }
        }

        checkShowEmptyListTxt();
    }

    public void checkShowEmptyListTxt() {
        if (favoritePokemonList != null) {
            emptyWarnTxt.setVisibility(View.GONE);
        } else {
            emptyWarnTxt.setVisibility(View.VISIBLE);
        }
    }

    public static FavoriteMonActivity getInstance() {
        return favoriteMonActivity;
    }

    public void setRecyclerView(ArrayList<FavoritePokemon> list) {
        favholder = new FavPokemonListAdapter(FavoriteMonActivity.this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(favholder);
    }
}
