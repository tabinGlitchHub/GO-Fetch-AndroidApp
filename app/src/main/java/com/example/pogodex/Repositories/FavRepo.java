package com.example.pogodex.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.pogodex.ModelClasses.FavoritePokemon;

import java.util.List;

public class FavRepo {
    private FavMonDAO favMonDAO;
    private LiveData<List<FavoritePokemon>> allFavMons;

    public FavRepo(Application application){
        DataBase dataBase = DataBase.getInstance(application);
        favMonDAO = dataBase.favMonDAO();
        allFavMons = favMonDAO.getAllFavMons();
    }

    public void insert(FavoritePokemon favoritePokemon){
        new InsertFavAsyncTask(favMonDAO).execute(favoritePokemon);
    }

    public void remove(FavoritePokemon favoritePokemon){
        new RemoveFavAsyncTask(favMonDAO).execute(favoritePokemon);
    }

    public void removeAll(){
        new RemoveAllFavAsyncTask(favMonDAO).execute();
    }

    public LiveData<List<FavoritePokemon>> getAllFavMons(){
        return allFavMons;
    }

    private static class InsertFavAsyncTask extends AsyncTask<FavoritePokemon, Void, Void>{
        private FavMonDAO favMonDAO;

        public InsertFavAsyncTask(FavMonDAO favMonDAO) {
            this.favMonDAO = favMonDAO;
        }

        @Override
        protected Void doInBackground(FavoritePokemon... favoritePokemons) {
            favMonDAO.insertFavMon(favoritePokemons[0]);
            return null;
        }
    }

    private static class RemoveFavAsyncTask extends AsyncTask<FavoritePokemon, Void, Void>{
        private FavMonDAO favMonDAO;

        public RemoveFavAsyncTask(FavMonDAO favMonDAO) {
            this.favMonDAO = favMonDAO;
        }

        @Override
        protected Void doInBackground(FavoritePokemon... favoritePokemons) {
            favMonDAO.deleteFavMon(favoritePokemons[0]);
            return null;
        }
    }

    private static class RemoveAllFavAsyncTask extends AsyncTask<FavoritePokemon, Void, Void>{
        private FavMonDAO favMonDAO;

        public RemoveAllFavAsyncTask(FavMonDAO favMonDAO) {
            this.favMonDAO = favMonDAO;
        }

        @Override
        protected Void doInBackground(FavoritePokemon... favoritePokemons) {
            favMonDAO.deleteAll();
            return null;
        }
    }
}
