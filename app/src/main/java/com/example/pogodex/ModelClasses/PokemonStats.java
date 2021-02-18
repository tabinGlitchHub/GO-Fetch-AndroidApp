package com.example.pogodex.ModelClasses;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PokemonStats {
    @SerializedName("pokemon_name")
    @Expose
    private String _pokemonName;

    @SerializedName("pokemon_id")
    @Expose
    private String _pokemonID;

    @SerializedName("form")
    @Expose
    private String _pokemonForm;

    @SerializedName("base_attack")
    @Expose
    private Integer _baseAttack;

    @SerializedName("base_defense")
    @Expose
    private Integer _baseDefence;

    @SerializedName("base_stamina")
    @Expose
    private Integer _baseStamina;

    public Integer get_baseAttack() {
        return _baseAttack;
    }

    public void set_baseAttack(Integer _baseAttack) {
        this._baseAttack = _baseAttack;
    }

    public Integer get_baseDefence() {
        return _baseDefence;
    }

    public void set_baseDefence(Integer _baseDefence) {
        this._baseDefence = _baseDefence;
    }

    public Integer get_baseStamina() {
        return _baseStamina;
    }

    public void set_baseStamina(Integer _baseStamina) {
        this._baseStamina = _baseStamina;
    }

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
}
