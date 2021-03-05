package com.example.pogodex.Repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.pogodex.Interfaces.RequestInterface;
import com.example.pogodex.ModelClasses.PokemonGeneralData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeneralDataRepo {

    private static GeneralDataRepo instance;
    private ArrayList<PokemonGeneralData> pkmnGenDataList = new ArrayList<>();
    private static String JSON_Url = "https://pokemon-go1.p.rapidapi.com/";

    //Singleton Instance
    public static GeneralDataRepo getInstance() {
        if (instance == null) {
            instance = new GeneralDataRepo();
        }
        return instance;
    }

    //use it to get data fetched from API Calls
    public MutableLiveData<ArrayList<PokemonGeneralData>> getGenData() {
        getData();

        MutableLiveData<ArrayList<PokemonGeneralData>> data = new MutableLiveData<>();
        data.setValue(pkmnGenDataList);
        System.out.println(pkmnGenDataList.get(0).get_pokemonName());
        return data;
    }

    //get data from Api
    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JSON_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<PokemonGeneralData>> call = requestInterface.getPokeJSON();

        call.enqueue(new Callback<List<PokemonGeneralData>>() {
            @Override
            public void onResponse(@NotNull Call<List<PokemonGeneralData>> call, @NotNull Response<List<PokemonGeneralData>> response) {
                if (response != null) {
                    pkmnGenDataList = new ArrayList<>(response.body());

                    //Filter to display only Normal forms
                    for (int i = 0; i < pkmnGenDataList.size(); ) {
                        if (pkmnGenDataList.get(i).get_pokemonForm().equals("Normal") || pkmnGenDataList.get(i).get_pokemonForm().equals("Incarnate")
                                || pkmnGenDataList.get(i).get_pokemonForm().equals("Ordinary") || pkmnGenDataList.get(i).get_pokemonForm().equals("Aria")
                                || pkmnGenDataList.get(i).get_pokemonForm().equals("Galarian") || pkmnGenDataList.get(i).get_pokemonForm().equals("Altered")
                                || pkmnGenDataList.get(i).get_pokemonForm().equals("Land") || pkmnGenDataList.get(i).get_pokemonForm().equals("Overcast")
                                || pkmnGenDataList.get(i).get_pokemonForm().equals("East_sea") || pkmnGenDataList.get(i).get_pokemonForm().equals("Purified")
                                || pkmnGenDataList.get(i).get_pokemonForm().equals("Shadow")) {
                            i++;
                        } else {
                            pkmnGenDataList.remove(i);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PokemonGeneralData>> call, Throwable t) {
            }
        });
    }

}
