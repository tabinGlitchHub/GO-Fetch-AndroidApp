package com.example.pogodex.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pogodex.Activities.MainActivity;
import com.example.pogodex.ModelClasses.PokemonGeneralData;
import com.example.pogodex.Repositories.GeneralDataRepo;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<ArrayList<PokemonGeneralData>> pkmnGenDataList;
    private GeneralDataRepo generalDataRepo;

    public void init(){
        if(pkmnGenDataList!=null){
            return;
        }
        generalDataRepo = GeneralDataRepo.getInstance();
        pkmnGenDataList = generalDataRepo.getGenData();
    }

    public LiveData<ArrayList<PokemonGeneralData>> getGenData(){
        return pkmnGenDataList;
    }
}
