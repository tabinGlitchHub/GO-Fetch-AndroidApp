package com.example.pogodex.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.pogodex.R;

@Entity(tableName = "fav_mons")
public class FavoritePokemon implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int _entryId;

    private String _favPokemonID;

    private String _favPokemonName;

    private String _favPokemonForm;

    private String _favPokemonType1;

    private String _favPokemonType2;

    @Ignore
    private final String[] ofForm = {
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Rocket/ic_shadow.png",
            "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Rocket/ic_purified.png"
    };

    @Ignore
    private final String[] typeIconList = {
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


    @Ignore
    private final String[] typeOf = {"Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting", "Fire", "Flying",
            "Ghost", "Grass", "Ground", "Ice", "Normal", "Poison", "Psychic", "Rock", "Steel", "Water"};

    @Ignore
    private String pkmnSprite = "";

    @Ignore
    private final Integer[] colorsForBG = {
            R.drawable.card_gradient_bug, R.drawable.card_gradient_dark, R.drawable.card_gradient_dragon,
            R.drawable.card_gradient_electric, R.drawable.card_gradient_fairy, R.drawable.card_gradient_fightng,
            R.drawable.card_gradient_fire, R.drawable.card_gradient_flyng, R.drawable.card_gradient_ghost,
            R.drawable.card_gradient_grass, R.drawable.card_gradient_ground, R.drawable.card_gradient_ice,
            R.drawable.card_gradient_normal, R.drawable.card_gradient_poison, R.drawable.card_gradient_psychic,
            R.drawable.card_gradient_rock, R.drawable.card_gradient_steel, R.drawable.card_gradient_water,
            R.drawable.card_gradient_purified, R.drawable.card_gradient_shadow
    };

    public FavoritePokemon() {
    }

    public int get_entryId() {
        return _entryId;
    }

    public void set_entryId(int _entryId) {
        this._entryId = _entryId;
    }

    public String get_favPokemonForm() {
        return _favPokemonForm;
    }

    public String get_favPokemonFormIcon() {
        switch (_favPokemonForm) {
            case "Purified":
                return ofForm[1];
            case "Shadow":
                return ofForm[0];
            default:
                return null;
        }
    }

    public String get_favPokemonImage() {
        return pkmnSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vii/ultra-sun-ultra-moon/" + _favPokemonID + ".png";
    }

    //get the primary type of mon to draw ICON with Glide
    public String get_favPokemonType1() {
        return _favPokemonType1;
    }

    //get the secondary type of mon to draw ICON with Glide
    public String get_favPokemonType2() {
        return _favPokemonType2;
    }

    public FavoritePokemon(String _favPokemonID, String _favPokemonName, String _favPokemonForm, String _favPokemonType1, String _favPokemonType2) {
        this._favPokemonID = _favPokemonID;
        this._favPokemonName = _favPokemonName;
        this._favPokemonForm = _favPokemonForm;
        this._favPokemonType1 = _favPokemonType1;
        this._favPokemonType2 = _favPokemonType2;
    }

    public String get_favPokemonID() {
        return _favPokemonID;
    }

    public void set_favPokemonID(String _favPokemonID) {
        this._favPokemonID = _favPokemonID;
    }

    public String get_favPokemonName() {
        return _favPokemonName;
    }

    public void set_favPokemonName(String _favPokemonName) {
        this._favPokemonName = _favPokemonName;
    }

    public void set_favPokemonForm(String _favPokemonForm) {
        this._favPokemonForm = _favPokemonForm;
    }

    public void set_favPokemonType1(String _favPokemonType1) {
        this._favPokemonType1 = _favPokemonType1;
    }

    public void set_favPokemonType2(String _favPokemonType2) {
        this._favPokemonType2 = _favPokemonType2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_favPokemonName);
        dest.writeString(_favPokemonID);
        dest.writeString(_favPokemonForm);
        dest.writeString(_favPokemonType1);
        dest.writeString(_favPokemonType2);
    }

    public FavoritePokemon(Parcel in) {
        _favPokemonName = in.readString();
        _favPokemonID = in.readString();
        _favPokemonForm = in.readString();
        _favPokemonType1 = in.readString();
        _favPokemonType2 = in.readString();
    }

    public static final Parcelable.Creator<FavoritePokemon> CREATOR = new Parcelable.Creator<FavoritePokemon>() {
        public FavoritePokemon createFromParcel(Parcel in) {
            return new FavoritePokemon(in);
        }

        public FavoritePokemon[] newArray(int size) {
            return new FavoritePokemon[size];
        }
    };
}
