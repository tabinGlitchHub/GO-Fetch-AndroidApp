package com.example.pogodex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pogodex.ModelClasses.PokemonEvolutionsChld;

import java.util.ArrayList;

public class EvolutionsAdapter extends RecyclerView.Adapter<EvolutionsAdapter.ViewHolder> {
    private ArrayList<PokemonEvolutionsChld> pkmnEvosList;
    private Context context;

    public EvolutionsAdapter(ArrayList<PokemonEvolutionsChld> pkmnEvosList, Context context) {
        this.pkmnEvosList = pkmnEvosList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evolution_model, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PokemonEvolutionsChld data = pkmnEvosList.get(position);
        //set number of candies to evolve
        holder.candyReqTxt.setText(data.get_candyRequired() + " candies.");


        //set item Required TextView only if parameter exists
        try {
            if (!data.get_itemRequired().equals(null)) {
                holder.itemReqTxt.setText(pkmnEvosList.get(position).get_itemRequired());
            }
        } catch (Exception e) {
            holder.itemReqTxt.setVisibility(View.GONE);
        }

        //set Lure Required TextView only if parameter exists
        try {
            if (!data.get_lureRequired().equals(null)) {
                holder.lureReqTxt.setText(data.get_lureRequired());
            }
        } catch (Exception e) {
            holder.lureReqTxt.setVisibility(View.GONE);
        }

        //set distance required TextView only if parameter exists
        try {
            if (!data.get_buddyDistanceRequired().equals(null)) {
                holder.distanceReqTxt.setText(data.get_buddyDistanceRequired() + " km as Buddy.");
            }
        } catch (Exception e) {
            holder.distanceReqTxt.setVisibility(View.GONE);
        }

        //set TextView only if paramter is true and not null
        try {
            if (!data.get_beBuddyToEvolve().equals(null)) {
                holder.beBuddyTxt.setText("Keep as Buddy to Evolve!");
            }
        } catch (Exception e) {
            holder.beBuddyTxt.setVisibility(View.GONE);
        }

        //set TextView only if paramter is true and not null
        try {
            if (!data.get_onlyEvolveDayTime().equals(null)) {
                holder.beDayTxt.setText("Evolve Day time.");
            }
        } catch (Exception e) {
            holder.beDayTxt.setVisibility(View.GONE);
        }

        //set TextView only if paramter is true
        try {
            if (data.get_onlyEvolveNightTime().equals(null)) {
                holder.beNightTxt.setText("Evolve Night Time.");
            }
        } catch (Exception e) {
            holder.beNightTxt.setVisibility(View.GONE);
        }

        //set TextView only if paramter is true
        try {
            if (data.get_noCostIfTraded().equals(null)) {
                holder.noCandyTradedTxt.setText("No Candy required After trade.");
            }
        } catch (Exception e) {
            holder.noCandyTradedTxt.setVisibility(View.GONE);
        }

        Glide.with(context)
                .asBitmap()
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-viii/icons/" + data.get_pokemonID() + ".png")
                .into(holder.pkmnImg);
    }

    @Override
    public int getItemCount() {
        if (pkmnEvosList != null) {
            return pkmnEvosList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pkmnImg;
        TextView candyReqTxt, itemReqTxt, distanceReqTxt, lureReqTxt, beBuddyTxt,
                beDayTxt, beNightTxt, noCandyTradedTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pkmnImg = itemView.findViewById(R.id.pkmnSpriteEvo);
            candyReqTxt = itemView.findViewById(R.id.candiesReqTxt);
            itemReqTxt = itemView.findViewById(R.id.itemReqTxt);
            distanceReqTxt = itemView.findViewById(R.id.distanceReqTxt);
            lureReqTxt = itemView.findViewById(R.id.lureReqTxt);
            beBuddyTxt = itemView.findViewById(R.id.beBuddyTxt);
            beDayTxt = itemView.findViewById(R.id.beDayTxt);
            beNightTxt = itemView.findViewById(R.id.beNightTxt);
            noCandyTradedTxt = itemView.findViewById(R.id.noCandyTxt);
        }
    }
}
