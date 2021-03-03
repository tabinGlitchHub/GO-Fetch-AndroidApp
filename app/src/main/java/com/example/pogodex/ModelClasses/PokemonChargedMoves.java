package com.example.pogodex.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PokemonChargedMoves implements Parcelable {

    @SerializedName("energy_delta")
    @Expose
    private int cEnergyDelta;

    @SerializedName("move_id")
    @Expose
    private String _moveId;

    @SerializedName("name")
    @Expose
    private String _cMoveName;

    @SerializedName("power")
    @Expose
    private String _cMovePower;

    @SerializedName("type")
    @Expose
    private String _cMoveType;

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


    public String get_cmoveType(){
        for (int i = 0; i <= typeOf.length; i++) {
            if (_cMoveType.equals(typeOf[i])) {
                return typeIconList[i];
            }
        }
        return null;
    }


    public Integer getcEnergyDelta() {
        return cEnergyDelta;
    }

    public void setcEnergyDelta(Integer cEnergyDelta) {
        this.cEnergyDelta = cEnergyDelta;
    }

    public String get_moveId() {
        return _moveId;
    }

    public void set_moveId(String _moveId) {
        this._moveId = _moveId;
    }

    public String get_cMoveName() {
        return _cMoveName;
    }

    public void set_cMoveName(String _cMoveName) {
        this._cMoveName = _cMoveName;
    }

    public String get_cMovePower() {
        return _cMovePower;
    }

    public void set_cMovePower(String _cMovePower) {
        this._cMovePower = _cMovePower;
    }

    public void set_cMoveType(String _cMoveType) {
        this._cMoveType = _cMoveType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PokemonChargedMoves(Parcel in) {
        _cMoveName = in.readString();
        _cMovePower = in.readString();
        cEnergyDelta = in.readInt();
        _cMoveType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_cMoveName);
        dest.writeString(_cMovePower);
        dest.writeInt(cEnergyDelta);
        dest.writeString(_cMoveType);
    }
    public static final Parcelable.Creator<PokemonChargedMoves> CREATOR = new Parcelable.Creator<PokemonChargedMoves>() {
        public PokemonChargedMoves createFromParcel(Parcel in) {
            return new PokemonChargedMoves(in);
        }

        public PokemonChargedMoves[] newArray(int size) {
            return new PokemonChargedMoves[size];
        }
    };
}
