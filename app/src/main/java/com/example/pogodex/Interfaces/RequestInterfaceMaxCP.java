package com.example.pogodex.Interfaces;

import com.example.pogodex.ModelClasses.PokemonGeneralData;
import com.example.pogodex.ModelClasses.PokemonMaxCP;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RequestInterfaceMaxCP {
    @Headers("X-RapidAPI-Key: 999d37ce4dmsh30f9c80d8f151ebp1059f6jsnc6f9662d9293")
    @GET("pokemon_max_cp.json")
    Call<List<PokemonMaxCP>> getMaxCPJSON();
}
