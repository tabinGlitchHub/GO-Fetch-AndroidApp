package com.example.pogodex.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonEvolutionsPar {

    @SerializedName("pokemon_name")
    @Expose
    private String _pokemonName;

    @SerializedName("pokemon_id")
    @Expose
    private String _pokemonID;

    @SerializedName("form")
    @Expose
    private String _pokemonForm;

    @SerializedName("evolutions")
    @Expose
    private List<PokemonEvolutionsChld> Evolutions;

    public String get_pokemonName() {
        return _pokemonName;
    }

    public void set_pokemonName(String _pokemonName) {
        this._pokemonName = _pokemonName;
    }

    public String get_pokemonID() {
        return _pokemonID;
    }

    public void set_pokemonID(String _pokemonID) {
        this._pokemonID = _pokemonID;
    }

    public String get_pokemonForm() {
        return _pokemonForm;
    }

    public void set_pokemonForm(String _pokemonForm) {
        this._pokemonForm = _pokemonForm;
    }

    public List<PokemonEvolutionsChld> getEvolutions() {
        return Evolutions;
    }

    public void setEvolutions(List<PokemonEvolutionsChld> evolutions) {
        Evolutions = evolutions;
    }
}
