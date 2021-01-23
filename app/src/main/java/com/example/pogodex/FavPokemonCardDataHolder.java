package com.example.pogodex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FavPokemonCardDataHolder extends RecyclerView.Adapter<FavPokemonCardDataHolder.ViewHolder> {

    private ArrayList<PokemonData> favpkmnList;
    private Context context;

    FavPokemonCardDataHolder(Context context , ArrayList<PokemonData> favpkmn){
        this.context = context;
        this.favpkmnList = favpkmn;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_pkmn_layout, parent, false);
        FavPokemonCardDataHolder.ViewHolder holder = new FavPokemonCardDataHolder.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pokemonName.setText(favpkmnList.get(position).get_pokemonName());
        holder.pokemonID.setText(favpkmnList.get(position).get_pokemonID());

        Glide.with(context)
                .asBitmap()
                .load(favpkmnList.get(position).get_pokemonImage())
                .into(holder.pokemonImg);

        Glide.with(context)
                .asBitmap()
                .load(favpkmnList.get(position).get_pokemonType())
                .transform(new RoundedCorners(44))
                .into(holder.pokemonBG);

        Glide.with(context)
                .asBitmap()
                .load(favpkmnList.get(position).get_pokemonType1())
                .into(holder.typeOne);

        Glide.with(context)
                .asBitmap()
                .load(favpkmnList.get(position).get_pokemonType2())
                .into(holder.typeTwo);
    }

    @Override
    public int getItemCount() {
        return favpkmnList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pokemonName;
        private TextView pokemonID;
        private ImageView pokemonImg;
        private ImageView pokemonBG;
        private ImageView typeOne;
        private ImageView typeTwo;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemonName = itemView.findViewById(R.id.pkmnName);
            pokemonID = itemView.findViewById(R.id.pkmnID);
            pokemonImg = itemView.findViewById(R.id.pkmnImage);
            pokemonBG = itemView.findViewById(R.id.pkmnBackG);
            typeOne = itemView.findViewById(R.id.type1Icon);
            typeTwo = itemView.findViewById(R.id.type2Icon);
            parent = itemView.findViewById(R.id.pkmnCard);
        }
    }
}