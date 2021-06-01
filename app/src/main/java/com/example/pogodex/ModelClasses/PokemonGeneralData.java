package com.example.pogodex.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

import com.example.pogodex.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PokemonGeneralData implements Parcelable {

    @SerializedName("pokemon_name")
    @Expose
    private String _pokemonName;

    @SerializedName("pokemon_id")
    @Expose
    private String _pokemonID;

    @SerializedName("form")
    @Expose
    private String _pokemonForm;

    @SerializedName("type")
    @Expose
    private String[] _pokemonTypes;

    private boolean isFavorite;

//    private String[] typeList = {
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_bug.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_dark.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_dragon.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_electric.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_fairy.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_fighting.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_fire.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_flying.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_ghost.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_grass.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_ground.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_ice.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_normal.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_poison.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_psychic.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_rock.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_steel.png",
//            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Type%20Backgrounds/details_type_bg_water.png"
//    };

    private String[] ofForm = {
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Rocket/ic_shadow.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Rocket/ic_purified.png"
    };

    private String[] typeIconList = {
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_BUG.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_DARK.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_DRAGON.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_ELECTRIC.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_FAIRY.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_FIGHTING.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_FIRE.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_FLYING.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_GHOST.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_GRASS.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_GROUND.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_ICE.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_NORMAL.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_POISON.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_PSYCHIC.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_ROCK.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_STEEL.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Types/POKEMON_TYPE_WATER.png"
    };


    private String[] typeOf = {"Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting", "Fire", "Flying",
            "Ghost", "Grass", "Ground", "Ice", "Normal", "Poison", "Psychic", "Rock", "Steel", "Water"};

    private String pkmnSprite = new String();

//    private Integer[] colorsForBG = {
//            R.drawable.card_gradient_bug, R.drawable.card_gradient_dark, R.drawable.card_gradient_dragon,
//            R.drawable.card_gradient_electric, R.drawable.card_gradient_fairy, R.drawable.card_gradient_fightng,
//            R.drawable.card_gradient_fire, R.drawable.card_gradient_flyng, R.drawable.card_gradient_ghost,
//            R.drawable.card_gradient_grass, R.drawable.card_gradient_ground, R.drawable.card_gradient_ice,
//            R.drawable.card_gradient_normal, R.drawable.card_gradient_poison, R.drawable.card_gradient_psychic,
//            R.drawable.card_gradient_rock, R.drawable.card_gradient_steel, R.drawable.card_gradient_water,
//            R.drawable.card_gradient_purified, R.drawable.card_gradient_shadow
//    };

    public String get_form() {
        switch (_pokemonForm) {
            case "Purified":
                return ofForm[1];
            case "Shadow":
                return ofForm[0];
            default:
                return null;
        }
    }

    public String get_pokemonImage() {
        return pkmnSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vii/ultra-sun-ultra-moon/" + get_pokemonID() + ".png";
    }

//    public Integer get_background() {
//        for (int i = 0; i <= 19; i++) {
//            if (_pokemonForm.equals("Purified")) {
//                return colorsForBG[18];
//            } else if (_pokemonForm.equals("Shadow")) {
//                return colorsForBG[19];
//            } else if (_pokemonTypes[0].equals(typeOf[i])) {
//                return colorsForBG[i];
//            }
//        }
//        return null;
//    }

    //get the primary type of mon to draw Background with Glide
//    public String get_pokemonType() {
//        for (int i = 0; i <= 19; i++) {
//            if (_pokemonForm.equals("Purified")) {
//                return null;
//            } else if (_pokemonForm.equals("Shadow")) {
//                return null;
//            } else if (_pokemonTypes[0].equals(typeOf[i])) {
//                return typeList[i];
//            }
//        }
//        return null;
//    }

    public String get_pokemonTypeString() {
        for (int i = 0; i <= 18; i++) {
            if (_pokemonTypes[0].equals(typeOf[i])) {
                return typeOf[i];
            }
        }
        return null;
    }

    //get the primary type of mon to draw ICON with Glide
    public String get_pokemonType1() {
        for (int i = 0; i <= 18; i++) {
            if (_pokemonTypes[0].equals(typeOf[i])) {
                return typeIconList[i];
            }
        }
        return null;
    }

    //get the secondary type of mon to draw ICON with Glide
    public String get_pokemonType2() {
        if (_pokemonTypes.length == 2) {
            for (int i = 0; i <= 18; i++) {
                if (_pokemonTypes[1].equals(typeOf[i])) {
                    return typeIconList[i];
                }
            }
        }
        return null;
    }


    //get pokemon types array
    public String[] get_pokemonTypes() {
        return _pokemonTypes;
    }

    public void set_pokemonTypes(String[] _pokemonTypes) {
        this._pokemonTypes = _pokemonTypes;
    }

    //getter for isFavorite boolean
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String get_pokemonForm() {
        return _pokemonForm;
    }

    public void set_pokemonForm(String _pokemonForm) {
        this._pokemonForm = _pokemonForm;
    }

    public String get_pokemonName() {
        return _pokemonName;
    }

    public void set_pokemonName(String _pokemonName) {
        this._pokemonName = _pokemonName;
    }

    public PokemonGeneralData() {
    }

    public String get_pokemonID() {
        return _pokemonID;
    }

    public void set_pokemonID(String _pokemonID) {
        this._pokemonID = _pokemonID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_pokemonName);
        dest.writeString(_pokemonID);
        dest.writeString(_pokemonForm);
        dest.writeStringArray(_pokemonTypes);
    }

    public PokemonGeneralData(Parcel in) {
        _pokemonName = in.readString();
        _pokemonID = in.readString();
        _pokemonForm = in.readString();
        _pokemonTypes = in.createStringArray();
    }

    public static final Parcelable.Creator<PokemonGeneralData> CREATOR = new Parcelable.Creator<PokemonGeneralData>() {
        public PokemonGeneralData createFromParcel(Parcel in) {
            return new PokemonGeneralData(in);
        }

        public PokemonGeneralData[] newArray(int size) {
            return new PokemonGeneralData[size];
        }
    };
}
