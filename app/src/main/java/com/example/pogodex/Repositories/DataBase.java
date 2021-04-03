package com.example.pogodex.Repositories;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pogodex.ModelClasses.FavoritePokemon;

@Database(entities = {FavoritePokemon.class}, version = 2)
public abstract class DataBase extends RoomDatabase {

    private static DataBase instance;

    public abstract FavMonDAO favMonDAO();

    public static synchronized DataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), DataBase.class, "database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
