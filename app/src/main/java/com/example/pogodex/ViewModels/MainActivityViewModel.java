package com.example.pogodex.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pogodex.Activities.MainActivity;
import com.example.pogodex.Interfaces.RequestInterface;
import com.example.pogodex.ModelClasses.PokemonGeneralData;
import com.example.pogodex.Repositories.RetroInstance;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<PokemonGeneralData>> pkmnGenDataList;

    public MainActivityViewModel() {
        pkmnGenDataList = new MutableLiveData<>();
    }

    public LiveData<List<PokemonGeneralData>> getPkmnGenDataList() {
        return pkmnGenDataList;
    }

    public void init() {
        makeAPICall();
    }

    public void makeAPICall() {
        RequestInterface requestInterface = RetroInstance.getRetrofitClient().create(RequestInterface.class);
        Call<List<PokemonGeneralData>> call = requestInterface.getPokeJSON();

        call.enqueue(new Callback<List<PokemonGeneralData>>() {
            @Override
            public void onResponse(@NotNull Call<List<PokemonGeneralData>> call, @NotNull Response<List<PokemonGeneralData>> response) {
                pkmnGenDataList.setValue(response.body());
                //Filter to display only Normal forms
                for (int i = 0; i < pkmnGenDataList.getValue().size(); ) {
                    if (pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Normal") || pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Incarnate")
                            || pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Ordinary") || pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Aria")
                            || pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Galarian")|| pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Alola")
                            || pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Altered") || pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Land")
                            || pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Overcast") || pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("East_sea")
                            || pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Purified") || pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Shadow")) {
                        i++;
                    } else {
                        pkmnGenDataList.getValue().remove(i);
                    }
                }
                MainActivity.getInstance().PostLoadEffects();
            }

            @Override
            public void onFailure(Call<List<PokemonGeneralData>> call, Throwable t) {
                MainActivity.getInstance().DisplayErrorToast();
            }
        });
    }
}
