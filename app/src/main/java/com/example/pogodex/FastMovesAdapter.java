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
import com.example.pogodex.ModelClasses.PokemonFastMoves;

import java.util.ArrayList;

public class FastMovesAdapter extends RecyclerView.Adapter<FastMovesAdapter.ViewHolder>  {

    private ArrayList<PokemonFastMoves> fmList;
    private Context context;

    public FastMovesAdapter(ArrayList<PokemonFastMoves> fmList, Context context) {
        this.fmList = fmList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fast_move, parent, false);
        FastMovesAdapter.ViewHolder holder = new FastMovesAdapter.ViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.moveName.setText(fmList.get(position).get_fMoveName());
        holder.moveDamage.setText(fmList.get(position).get_fMovePower());
        holder.moveNRG.setText(String.valueOf(fmList.get(position).get_fEnergyDelta()));

        Glide.with(this.context)
                .asBitmap()
                .load(fmList.get(position).get_fmoveType())
                .into(holder.moveTypeIcon);
    }

    @Override
    public int getItemCount() {
        return fmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView moveTypeIcon;
        TextView moveName, moveDamage, moveNRG;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            moveTypeIcon = itemView.findViewById(R.id.fmoveTypeIcon);
            moveName = itemView.findViewById(R.id.fmoveNameTxt);
            moveDamage = itemView.findViewById(R.id.fmoveDmgTxt);
            moveNRG = itemView.findViewById(R.id.fmoveEnergyTxt);
        }
    }
}
