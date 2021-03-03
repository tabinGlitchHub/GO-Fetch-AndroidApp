package com.example.pogodex.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SelectedPokemonMoves {
    @SerializedName("pokemon_name")
    @Expose
    private String _pokemonName;

    @SerializedName("pokemon_id")
    @Expose
    private String _pokemonID;

    @SerializedName("form")
    @Expose
    private String _pokemonForm;

    @SerializedName("fast_moves")
    @Expose
    private List<String> _fastMoves;

    @SerializedName("elite_fast_moves")
    @Expose
    private List<String> _eliteFastMoves;

    @SerializedName("charged_moves")
    @Expose
    private List<String> _chargedMoves;

    @SerializedName("elite_charged_moves")
    @Expose
    private List<String> _eliteChargedMoves;


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

    public List<String> get_chargedMoves() {
        return _chargedMoves;
    }

    public void set_chargedMoves(List<String> _chargedMoves) {
        this._chargedMoves = _chargedMoves;
    }

    public List<String> get_eliteChargedMoves() {
        return _eliteChargedMoves;
    }

    public void set_eliteChargedMoves(List<String> _eliteChargedMoves) {
        this._eliteChargedMoves = _eliteChargedMoves;
    }

    public List<String> get_fastMoves() {
        return _fastMoves;
    }

    public void set_fastMoves(List<String> _fastMoves) {
        this._fastMoves = _fastMoves;
    }

    public List<String> get_eliteFastMoves() {
        return _eliteFastMoves;
    }

    public void set_eliteFastMoves(List<String> _eliteFastMoves) {
        this._eliteFastMoves = _eliteFastMoves;
    }

}
