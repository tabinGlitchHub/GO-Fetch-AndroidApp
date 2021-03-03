package com.example.pogodex.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PokemonFastMoves implements Parcelable {
    @SerializedName("name")
    @Expose
    private String _fMoveName;

    @SerializedName("power")
    @Expose
    private String _fMovePower;

    @SerializedName("type")
    @Expose
    private String _fMoveType;

    @SerializedName("energy_delta")
    @Expose
    private int _fEnergyDelta;

    @SerializedName("move_id")
    @Expose
    private String _moveId;

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


    public String get_fmoveType(){
        for (int i = 0; i <= typeOf.length; i++) {
            if (_fMoveType.equals(typeOf[i])) {
                return typeIconList[i];
            }
        }
        return null;
    }


    public String get_moveId() {
        return _moveId;
    }

    public void set_moveId(String _moveId) {
        this._moveId = _moveId;
    }

    public String get_fMoveName() {
        return _fMoveName;
    }

    public void set_fMoveName(String _fMoveName) {
        this._fMoveName = _fMoveName;
    }

    public String get_fMovePower() {
        return _fMovePower;
    }

    public void set_fMovePower(String _fMovePower) {
        this._fMovePower = _fMovePower;
    }

    public String get_fMoveType() {
        return _fMoveType;
    }

    public void set_fMoveType(String _fMoveType) {
        this._fMoveType = _fMoveType;
    }

    public int get_fEnergyDelta() {
        return _fEnergyDelta;
    }

    public void set_fEnergyDelta(int _fEnergyDelta) {
        this._fEnergyDelta = _fEnergyDelta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PokemonFastMoves(Parcel in) {
        _fMoveName = in.readString();
        _fMovePower = in.readString();
        _fEnergyDelta = in.readInt();
        _fMoveType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_fMoveName);
        dest.writeString(_fMovePower);
        dest.writeInt(_fEnergyDelta);
        dest.writeString(_fMoveType);
    }
    public static final Parcelable.Creator<PokemonFastMoves> CREATOR = new Parcelable.Creator<PokemonFastMoves>() {
        public PokemonFastMoves createFromParcel(Parcel in) {
            return new PokemonFastMoves(in);
        }

        public PokemonFastMoves[] newArray(int size) {
            return new PokemonFastMoves[size];
        }
    };

}
