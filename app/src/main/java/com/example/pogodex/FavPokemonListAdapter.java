package com.example.pogodex;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.pogodex.Activities.MainActivity;
import com.example.pogodex.ModelClasses.FavoritePokemon;
import com.example.pogodex.ModelClasses.PokemonGeneralData;
import com.example.pogodex.ViewModels.FavActivityViewModel;
import com.example.pogodex.ViewModels.MainActivityViewModel;

import java.util.ArrayList;

import static com.example.pogodex.MainPokemonListAdapter.favoritePkmnList;

public class FavPokemonListAdapter extends RecyclerView.Adapter<FavPokemonListAdapter.ViewHolder> {

    private static ArrayList<FavoritePokemon> favpkmnList;
    private final Context context;

    FavPokemonListAdapter(Context context, ArrayList<FavoritePokemon> favpkmn) {
        this.context = context;
        this.favpkmnList = favpkmn;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_favpkmn_layout, parent, false);
        FavPokemonListAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.favMonName.setText(favpkmnList.get(position).get_favPokemonName());

        Glide.with(context)
                .asBitmap()
                .load(favpkmnList.get(position).get_favPokemonImage())
                .into(holder.favMonImg);

        Glide.with(context)
                .asBitmap()
                .load(favpkmnList.get(position).get_favPokemonType1())
                .into(holder.typeOne);

        Glide.with(context)
                .asBitmap()
                .load(favpkmnList.get(position).get_favPokemonType2())
                .into(holder.typeTwo);
    }

    @Override
    public int getItemCount() {
        if (favpkmnList != null) {
            return favpkmnList.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView favMonName;
        private final ImageView favMonImg;
        private final ImageView typeOne;
        private final ImageView typeTwo;
        private final Button favBTN;
        FavoriteMonActivity favoriteMonActivity = FavoriteMonActivity.getInstance();
        FavActivityViewModel favActivityViewModel = favoriteMonActivity.favActivityViewModel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            favMonName = itemView.findViewById(R.id.favMonTxt);
            favMonImg = itemView.findViewById(R.id.favMonImg);
            typeOne = itemView.findViewById(R.id.favMonType1);
            typeTwo = itemView.findViewById(R.id.favMonType2);
            favBTN = itemView.findViewById(R.id.favoriteBTN2);

            favBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    FavoritePokemon favoritePokemon = favpkmnList.get(position);
                    favActivityViewModel.removeFav(favoritePokemon);
                }
            });

        }
    }
}