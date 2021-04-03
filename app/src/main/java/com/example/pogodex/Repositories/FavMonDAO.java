package com.example.pogodex.Repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pogodex.ModelClasses.FavoritePokemon;

import java.util.List;

@Dao
public interface FavMonDAO {

    @Insert
    void insertFavMon(FavoritePokemon fm);

    @Delete
    void deleteFavMon(FavoritePokemon fm);

    @Query("SELECT * FROM fav_mons")
    LiveData<List<FavoritePokemon>> getAllFavMons();

    @Query("DELETE FROM fav_mons")
    void deleteAll();
}
