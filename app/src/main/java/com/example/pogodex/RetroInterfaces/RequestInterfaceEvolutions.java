package com.example.pogodex.RetroInterfaces;

import com.example.pogodex.ModelClasses.PokemonEvolutionsPar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RequestInterfaceEvolutions {
    @Headers("X-RapidAPI-Key: 999d37ce4dmsh30f9c80d8f151ebp1059f6jsnc6f9662d9293")
    @GET("pokemon_evolutions.json")
    Call<List<PokemonEvolutionsPar>> getPokeEvosJSON();
}
