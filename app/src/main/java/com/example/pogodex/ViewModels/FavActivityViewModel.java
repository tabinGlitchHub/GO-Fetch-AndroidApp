package com.example.pogodex.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pogodex.ModelClasses.FavoritePokemon;
import com.example.pogodex.Repositories.FavRepo;

import java.util.List;

public class FavActivityViewModel extends AndroidViewModel {
    private FavRepo favRepo;
    private final LiveData<List<FavoritePokemon>> favPokmeonList;

    public FavActivityViewModel(@NonNull Application application) {
        super(application);
        favRepo = new FavRepo(application);
        favPokmeonList = favRepo.getAllFavMons();
    }

    public void insertFav(FavoritePokemon favoritePokemon){
        favRepo.insert(favoritePokemon);
    }

    public void removeFav(FavoritePokemon favoritePokemon){
        favRepo.remove(favoritePokemon);
    }

    public void removeAllFav(){
        favRepo.removeAll();
    }

    public LiveData<List<FavoritePokemon>> getFavPokmeonList() {
        return favPokmeonList;
    }
}
