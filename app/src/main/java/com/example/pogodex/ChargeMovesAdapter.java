package com.example.pogodex;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pogodex.ModelClasses.PokemonChargedMoves;

import java.util.ArrayList;

public class ChargeMovesAdapter extends RecyclerView.Adapter<ChargeMovesAdapter.ViewHolder> {

    private ArrayList<PokemonChargedMoves> cmList;
    private Context context;

    public ChargeMovesAdapter(ArrayList<PokemonChargedMoves> cmList, Context context) {
        this.cmList = cmList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.charge_move, parent, false);
        ChargeMovesAdapter.ViewHolder holder = new ChargeMovesAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.moveName.setText(cmList.get(position).get_cMoveName());
        holder.moveDamage.setText(cmList.get(position).get_cMovePower());

        LinearLayout.LayoutParams oneThird = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                4.0f
        );
        LinearLayout.LayoutParams empty = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0f
        );
        LinearLayout.LayoutParams half = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                6.0f
        );
        LinearLayout.LayoutParams full = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                12.0f
        );

        //draw custom energy bars based on energy required
        if(cmList.get(position).getcEnergyDelta().equals(-33)){
            holder.moveEnergyBar1.setLayoutParams(oneThird);
            holder.moveEnergyBar2.setLayoutParams(oneThird);
            holder.moveEnergyBar3.setLayoutParams(oneThird);
        }else if (cmList.get(position).getcEnergyDelta().equals(-50)){
            holder.moveEnergyBar1.setLayoutParams(half);
            holder.moveEnergyBar2.setLayoutParams(half);
            holder.moveEnergyBar3.setLayoutParams(empty);
        }else {
            holder.moveEnergyBar1.setLayoutParams(full);
            holder.moveEnergyBar2.setLayoutParams(empty);
            holder.moveEnergyBar3.setLayoutParams(empty);
        }

        Glide.with(context)
                .asBitmap()
                .load(cmList.get(position).get_cmoveType())
                .into(holder.moveTypeIcon);
    }

    @Override
    public int getItemCount() {
        return cmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView moveTypeIcon , moveEnergyBar1, moveEnergyBar2, moveEnergyBar3;
        TextView moveName, moveDamage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            moveTypeIcon = itemView.findViewById(R.id.cmoveTypeIcon);
            moveEnergyBar1 = itemView.findViewById(R.id.bar1);
            moveEnergyBar2 = itemView.findViewById(R.id.bar2);
            moveEnergyBar3 = itemView.findViewById(R.id.bar3);
            moveName = itemView.findViewById(R.id.cmoveNameTxt);
            moveDamage = itemView.findViewById(R.id.cmoveDmgTxt);
        }
    }

}
