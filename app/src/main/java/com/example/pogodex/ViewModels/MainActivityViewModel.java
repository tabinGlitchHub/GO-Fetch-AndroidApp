package com.example.pogodex.ViewModels;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pogodex.Activities.MainActivity;
import com.example.pogodex.ModelClasses.FavoritePokemon;
import com.example.pogodex.ModelClasses.PokemonChargedMoves;
import com.example.pogodex.ModelClasses.PokemonFastMoves;
import com.example.pogodex.RetroInterfaces.RequestInterface;
import com.example.pogodex.ModelClasses.PokemonGeneralData;
import com.example.pogodex.Repositories.RetroInstance;
import com.example.pogodex.RetroInterfaces.RequestInterfaceChargedMoves;
import com.example.pogodex.RetroInterfaces.RequestInterfaceFastMoves;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<List<PokemonGeneralData>> pkmnGenDataList;
    private final MutableLiveData<List<PokemonFastMoves>> pkmnFastMovesList;
    private final MutableLiveData<List<PokemonChargedMoves>> pkmnChargedMovesList;
    private final MutableLiveData<List<FavoritePokemon>> favMonsList;

    public MainActivityViewModel() {
        pkmnGenDataList = new MutableLiveData<>();
        pkmnFastMovesList = new MutableLiveData<>();
        pkmnChargedMovesList = new MutableLiveData<>();
        favMonsList = new MutableLiveData<>();
    }

    public LiveData<List<PokemonGeneralData>> getPkmnGenDataList() {
        return pkmnGenDataList;
    }

    public LiveData<List<PokemonFastMoves>> getPkmnFastMovesList() {
        return pkmnFastMovesList;
    }

    public LiveData<List<PokemonChargedMoves>> getPkmnChargedMovesList() {
        return pkmnChargedMovesList;
    }

    public LiveData<List<FavoritePokemon>> getFavMonsList() {
        return favMonsList;
    }

    public void init() {
        fetchGeneralData();
        fetchFastMoves();
        fetchChargedMoves();
    }

    public void test(){
        System.out.println("ping");
    }

    public void fetchGeneralData() {
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
                            || pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Galarian") || pkmnGenDataList.getValue().get(i).get_pokemonForm().equals("Alola")
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

    public void fetchFastMoves() {
        RequestInterfaceFastMoves requestInterfaceFM = RetroInstance.getRetrofitClient().create(RequestInterfaceFastMoves.class);
        Call<List<PokemonFastMoves>> call2 = requestInterfaceFM.getFastMoveJSON();

        call2.enqueue(new Callback<List<PokemonFastMoves>>() {
            @Override
            public void onResponse(@NotNull Call<List<PokemonFastMoves>> call, @NotNull Response<List<PokemonFastMoves>> response) {
                pkmnFastMovesList.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<PokemonFastMoves>> call, Throwable t) {
                MainActivity.getInstance().DisplayErrorToast();
            }
        });
    }

    private void fetchChargedMoves() {
        RequestInterfaceChargedMoves requestInterfaceCM = RetroInstance.getRetrofitClient().create(RequestInterfaceChargedMoves.class);
        Call<List<PokemonChargedMoves>> call3 = requestInterfaceCM.getChargedMoveJSON();

        call3.enqueue(new Callback<List<PokemonChargedMoves>>() {
            @Override
            public void onResponse(@NotNull Call<List<PokemonChargedMoves>> call, @NotNull Response<List<PokemonChargedMoves>> response) {
                pkmnChargedMovesList.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<PokemonChargedMoves>> call, Throwable t) {
                MainActivity.getInstance().DisplayErrorToast();
            }
        });
    }
}
