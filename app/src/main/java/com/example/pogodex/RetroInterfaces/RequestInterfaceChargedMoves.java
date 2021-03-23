package com.example.pogodex.RetroInterfaces;

import com.example.pogodex.ModelClasses.PokemonChargedMoves;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RequestInterfaceChargedMoves {
    @Headers("X-RapidAPI-Key: 999d37ce4dmsh30f9c80d8f151ebp1059f6jsnc6f9662d9293")
    @GET("charged_moves.json")
    Call<List<PokemonChargedMoves>> getChargedMoveJSON();
}
