package com.example.pogodex.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PokemonEvolutionsChld {
    @SerializedName("pokemon_name")
    @Expose
    private String _pokemonName;

    @SerializedName("pokemon_id")
    @Expose
    private String _pokemonID;

    @SerializedName("form")
    @Expose
    private String _pokemonForm;

    @SerializedName("candy_required")
    @Expose
    private String _candyRequired;

    @SerializedName("item_required")
    @Expose
    private String _itemRequired;

    @SerializedName("lure_required")
    @Expose
    private String _lureRequired;

    @SerializedName("buddy_distance_required")
    @Expose
    private String _buddyDistanceRequired;

    @SerializedName("must_be_buddy_to_evolve")
    @Expose
    private Boolean _beBuddyToEvolve;

    @SerializedName("only_evolves_in_daytime")
    @Expose
    private Boolean _onlyEvolveDayTime;

    @SerializedName("only_evolves_in_nighttime")
    @Expose
    private Boolean _onlyEvolveNightTime;


    @SerializedName("no_candy_cost_if_traded")
    @Expose
    private Boolean _noCostIfTraded;

    public String get_buddyDistanceRequired() {
        return _buddyDistanceRequired;
    }

    public void set_buddyDistanceRequired(String _buddyDistanceRequired) {
        this._buddyDistanceRequired = _buddyDistanceRequired;
    }

    public Boolean get_beBuddyToEvolve() {
        return _beBuddyToEvolve;
    }

    public void set_beBuddyToEvolve(Boolean _beBuddyToEvolve) {
        this._beBuddyToEvolve = _beBuddyToEvolve;
    }

    public Boolean get_onlyEvolveDayTime() {
        return _onlyEvolveDayTime;
    }

    public void set_onlyEvolveDayTime(Boolean _onlyEvolveDayTime) {
        this._onlyEvolveDayTime = _onlyEvolveDayTime;
    }

    public Boolean get_onlyEvolveNightTime() {
        return _onlyEvolveNightTime;
    }

    public void set_onlyEvolveNightTime(Boolean _onlyEvolveNightTime) {
        this._onlyEvolveNightTime = _onlyEvolveNightTime;
    }

    public String get_lureRequired() {
        return _lureRequired;
    }

    public void set_lureRequired(String _lureRequired) {
        this._lureRequired = _lureRequired;
    }

    public Boolean get_noCostIfTraded() {
        return _noCostIfTraded;
    }

    public void set_noCostIfTraded(Boolean _noCostIfTraded) {
        this._noCostIfTraded = _noCostIfTraded;
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

    public String get_candyRequired() {
        return _candyRequired;
    }

    public void set_candyRequired(String _candyRequired) {
        this._candyRequired = _candyRequired;
    }

    public String get_itemRequired() {
        return _itemRequired;
    }

    public void set_itemRequired(String _itemRequired) {
        this._itemRequired = _itemRequired;
    }
}
