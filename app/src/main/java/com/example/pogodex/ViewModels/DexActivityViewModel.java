package com.example.pogodex.ViewModels;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pogodex.Activities.DexActivity;
import com.example.pogodex.ChargeMovesAdapter;
import com.example.pogodex.EvolutionsAdapter;
import com.example.pogodex.FastMovesAdapter;
import com.example.pogodex.ModelClasses.PokemonChargedMoves;
import com.example.pogodex.ModelClasses.PokemonEvolutionsChld;
import com.example.pogodex.ModelClasses.PokemonEvolutionsPar;
import com.example.pogodex.ModelClasses.PokemonFastMoves;
import com.example.pogodex.ModelClasses.PokemonGeneralData;
import com.example.pogodex.ModelClasses.PokemonMaxCP;
import com.example.pogodex.ModelClasses.PokemonStats;
import com.example.pogodex.ModelClasses.SelectedPokemonMoves;
import com.example.pogodex.Repositories.RetroInstance;
import com.example.pogodex.RetroInterfaces.RequestInterfaceEvolutions;
import com.example.pogodex.RetroInterfaces.RequestInterfaceMaxCP;
import com.example.pogodex.RetroInterfaces.RequestInterfaceMovePool;
import com.example.pogodex.RetroInterfaces.RequestInterfaceStats;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("StaticFieldLeak")
public class DexActivityViewModel extends ViewModel {

    private final MutableLiveData<List<PokemonMaxCP>> pkmnMaxCP;
    private final MutableLiveData<List<PokemonStats>> pkmnStats;
    private final MutableLiveData<List<PokemonEvolutionsPar>> pkmnEvoParent;
    private final MutableLiveData<ArrayList<PokemonEvolutionsChld>> pkmnEvoChild;
    private final MutableLiveData<ArrayList<SelectedPokemonMoves>> thisPkmnMoves;
    private final MutableLiveData<ArrayList<PokemonFastMoves>> pkmnFastMoves;
    private final MutableLiveData<ArrayList<PokemonChargedMoves>> pkmnChargedMoves;
    private ArrayList<PokemonMaxCP> maxcp = new ArrayList<>();
    private ArrayList<PokemonStats> stats = new ArrayList<>();
    private ArrayList<PokemonEvolutionsPar> ecoPar = new ArrayList<>();
    private ArrayList<PokemonEvolutionsChld> evoChild = new ArrayList<>();
    private ArrayList<SelectedPokemonMoves> selMoves = new ArrayList<>();
    private ArrayList<PokemonFastMoves> pfm = new ArrayList<>();
    private ArrayList<PokemonChargedMoves> pcm = new ArrayList<>();

    private final DexActivity dexActivity = DexActivity.getInstance();

    public DexActivityViewModel() {
        this.pkmnMaxCP = new MutableLiveData<>();
        this.pkmnStats = new MutableLiveData<>();
        this.pkmnEvoParent = new MutableLiveData<>();
        this.pkmnEvoChild = new MutableLiveData<>();
        this.thisPkmnMoves = new MutableLiveData<>();
        this.pkmnFastMoves = new MutableLiveData<>();
        this.pkmnChargedMoves = new MutableLiveData<>();
    }

    public LiveData<List<PokemonMaxCP>> getPkmnMaxCP() {
        return pkmnMaxCP;
    }

    public LiveData<List<PokemonStats>> getPkmnStats() {
        return pkmnStats;
    }

    public LiveData<List<PokemonEvolutionsPar>> getPkmnEvoParent() {
        return pkmnEvoParent;
    }

    public LiveData<ArrayList<PokemonEvolutionsChld>> getPkmnEvoChild() {
        return pkmnEvoChild;
    }

    public LiveData<ArrayList<SelectedPokemonMoves>> getThisPkmnMoves() {
        return thisPkmnMoves;
    }

    public LiveData<ArrayList<PokemonFastMoves>> getPkmnFastMoves() {
        return pkmnFastMoves;
    }

    public LiveData<ArrayList<PokemonChargedMoves>> getPkmnChargedMoves() {
        return pkmnChargedMoves;
    }

    public void Init(PokemonGeneralData data, ArrayList<PokemonChargedMoves> allChargedMoves, ArrayList<PokemonFastMoves> allFastMoves) {
        fetchMaxCP(data);
        fetchStats(data);
        fetchPokemonEvolutions(data);
        fetchMoves(data, allChargedMoves, allFastMoves);
    }

    private void fetchMaxCP(PokemonGeneralData data) {
        RequestInterfaceMaxCP requestInterfaceMCP = RetroInstance.getRetrofitClient().create(RequestInterfaceMaxCP.class);
        Call<List<PokemonMaxCP>> call1 = requestInterfaceMCP.getMaxCPJSON();

        call1.enqueue(new Callback<List<PokemonMaxCP>>() {
            @Override
            public void onResponse(Call<List<PokemonMaxCP>> call, Response<List<PokemonMaxCP>> response) {
                maxcp = new ArrayList<>(response.body());
                for (int i = 0; i < maxcp.size(); ) {
                    if (maxcp.get(i).get_pokemonID().equals(data.get_pokemonID())
                            && maxcp.get(i).get_pokemonForm().equals(data.get_pokemonForm())) {
                        dexActivity.setMaxCpTxt(maxcp.get(i).get_maxCP());
                        i++;
                    } else {
                        maxcp.remove(i);
                    }
                }
                pkmnMaxCP.setValue(maxcp);
            }

            @Override
            public void onFailure(Call<List<PokemonMaxCP>> call, Throwable t) {
                dexActivity.DisplayErrorToast();
            }
        });
    }

    private void fetchStats(PokemonGeneralData data) {
        RequestInterfaceStats requestInterfaceStats = RetroInstance.getRetrofitClient().create(RequestInterfaceStats.class);
        Call<List<PokemonStats>> call2 = requestInterfaceStats.getPokeStatsJSON();

        call2.enqueue(new Callback<List<PokemonStats>>() {
            @Override
            public void onResponse(Call<List<PokemonStats>> call, Response<List<PokemonStats>> response) {
                stats = new ArrayList<>(response.body());
                for (int i = 0; i < stats.size(); ) {
                    if (stats.get(i).get_pokemonID().equals(data.get_pokemonID())
                            && stats.get(i).get_pokemonForm().equals(data.get_pokemonForm())) {

                        //clear Bar before setting
                        dexActivity.clearBar();

                        //Load it in BarGraph
                        dexActivity.setBarGraph(stats.get(i));

                        i++;
                    } else {
                        stats.remove(i);
                    }
                }
                pkmnStats.setValue(stats);
            }

            @Override
            public void onFailure(Call<List<PokemonStats>> call, Throwable t) {
                dexActivity.DisplayErrorToast();
            }
        });
    }

    private void fetchPokemonEvolutions(PokemonGeneralData data) {
        RequestInterfaceEvolutions requestInterfaceEvolutions = RetroInstance.getRetrofitClient().create(RequestInterfaceEvolutions.class);
        Call<List<PokemonEvolutionsPar>> call4 = requestInterfaceEvolutions.getPokeEvosJSON();

        call4.enqueue(new Callback<List<PokemonEvolutionsPar>>() {
            @Override
            public void onResponse(Call<List<PokemonEvolutionsPar>> call, Response<List<PokemonEvolutionsPar>> response) {
                pkmnEvoParent.setValue(response.body());

                for (int i = 0; i < pkmnEvoParent.getValue().size(); ) {
                    if (pkmnEvoParent.getValue().get(i).get_pokemonID().equals(data.get_pokemonID())
                            && pkmnEvoParent.getValue().get(i).get_pokemonForm().equals(data.get_pokemonForm())) {
                        pkmnEvoChild.setValue((ArrayList<PokemonEvolutionsChld>) pkmnEvoParent.getValue().get(i).getEvolutions());
                        dexActivity.afterEvoLoadEffects();
                        i++;
                    } else {
                        pkmnEvoParent.getValue().remove(i);
                    }
                }
                if (pkmnEvoParent.getValue().size() == 0) {
                    dexActivity.displayNoEvolutions();
                }
            }

            @Override
            public void onFailure(Call<List<PokemonEvolutionsPar>> call, Throwable t) {
                dexActivity.DisplayErrorToast();
            }
        });
    }

    private void fetchMoves(PokemonGeneralData data, ArrayList<PokemonChargedMoves> allChargedMoves, ArrayList<PokemonFastMoves> allFastMoves) {
        RequestInterfaceMovePool requestInterfaceMovePool = RetroInstance.getRetrofitClient().create(RequestInterfaceMovePool.class);
        Call<List<SelectedPokemonMoves>> call5 = requestInterfaceMovePool.getAllMoveJSON();

        call5.enqueue(new Callback<List<SelectedPokemonMoves>>() {
            @Override
            public void onResponse(Call<List<SelectedPokemonMoves>> call, Response<List<SelectedPokemonMoves>> response) {
                thisPkmnMoves.setValue((ArrayList<SelectedPokemonMoves>) response.body());
                int numOfFM = 0;
                int numOfCM = 0;
                for (int i = 0; i < thisPkmnMoves.getValue().size(); ) {
                    if (data.get_pokemonName().equals(thisPkmnMoves.getValue().get(i).get_pokemonName())
                            && data.get_pokemonForm().equals(thisPkmnMoves.getValue().get(i).get_pokemonForm())) {
                        //append all elite moves to normal move list since only single list can be passed to recyclerview
                        thisPkmnMoves.getValue().get(i).get_fastMoves().addAll(thisPkmnMoves.getValue().get(i).get_eliteFastMoves());
                        thisPkmnMoves.getValue().get(i).get_chargedMoves().addAll(thisPkmnMoves.getValue().get(i).get_eliteChargedMoves());

                        //compare the current pkmn fast moves to the list of all fast moves and get details
                        for (int j = 0; j < allFastMoves.size() && numOfFM < thisPkmnMoves.getValue().get(i).get_fastMoves().size(); ) {
                            if (thisPkmnMoves.getValue().get(i).get_fastMoves().get(numOfFM).equals(allFastMoves.get(j).get_fMoveName())) {
                                pfm.add(allFastMoves.get(j));
                                numOfFM++;
                                j = 0;
                            } else {
                                j++;
                            }
                        }
                        pkmnFastMoves.setValue(pfm);

                        //compare the current pkmn charged moves to the list of all charged moves and get details
                        for (int j = 0; j < allChargedMoves.size() && numOfCM < thisPkmnMoves.getValue().get(i).get_chargedMoves().size(); ) {
                            if (thisPkmnMoves.getValue().get(i).get_chargedMoves().get(numOfCM).equals(allChargedMoves.get(j).get_cMoveName())) {
                                pcm.add(allChargedMoves.get(j));
                                numOfCM++;
                                j = 0;
                            } else {
                                j++;
                            }
                        }
                        i++;
                    } else {
                        thisPkmnMoves.getValue().remove(i);
                    }
                    pkmnChargedMoves.setValue(pcm);
                }
            }

            @Override
            public void onFailure(Call<List<SelectedPokemonMoves>> call, Throwable t) {
                dexActivity.DisplayErrorToast();
            }
        });
    }
}
