package com.example.pogodex;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.pogodex.Activities.DexActivity;
import com.example.pogodex.Activities.MainActivity;
import com.example.pogodex.ModelClasses.FavoritePokemon;
import com.example.pogodex.ModelClasses.PokemonChargedMoves;
import com.example.pogodex.ModelClasses.PokemonFastMoves;
import com.example.pogodex.ModelClasses.PokemonGeneralData;
import com.example.pogodex.ViewModels.FavActivityViewModel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainPokemonListAdapter extends RecyclerView.Adapter<MainPokemonListAdapter.ViewHolder> implements Filterable {

    private ArrayList<PokemonGeneralData> pokemonDataList;
    private ArrayList<PokemonGeneralData> pokemonDataListCopy;
    private ArrayList<PokemonFastMoves> pkmnFastMoves = new ArrayList<>();
    private ArrayList<PokemonChargedMoves> pkmnChargedMoves = new ArrayList<>();
    private final MainActivity mainActivity = MainActivity.getInstance();
    private List<FavoritePokemon> favList = mainActivity.favoritePokemonList;

    public static ArrayList<PokemonGeneralData> favoritePkmnList = new ArrayList<>(20);
    private Context context;
    int curveRadius = 25;

    public MainPokemonListAdapter(Context context, ArrayList<PokemonGeneralData> pokemonDataList, ArrayList<PokemonFastMoves> FastMoves
            , ArrayList<PokemonChargedMoves> ChargedMoves) {
        this.context = context;
        this.pokemonDataList = pokemonDataList;
        pokemonDataListCopy = new ArrayList<>(pokemonDataList);
        this.pkmnFastMoves = FastMoves;
        this.pkmnChargedMoves = ChargedMoves;
    }

    public MainPokemonListAdapter() {
    }

    public ArrayList<PokemonGeneralData> getFavoritePkmnList() {
        return favoritePkmnList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_pkmn_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PokemonGeneralData thisMon = pokemonDataList.get(position);
        holder.pokemonName.setText(thisMon.get_pokemonName());
        holder.pokemonID.setText("#" + thisMon.get_pokemonID());

        Glide.with(context)
                .asBitmap()
                .load(thisMon.get_pokemonImage())
                .into(holder.pokemonImg);

        Glide.with(context)
                .asBitmap()
                .load(thisMon.get_pokemonType1())
                .into(holder.typeOne);

        Glide.with(context)
                .asBitmap()
                .load(thisMon.get_pokemonType2())
                .into(holder.typeTwo);

        Glide.with(context)
                .asBitmap()
                .load(thisMon.get_form())
                .into(holder.formIcon);
    }


    @Override
    public int getItemCount() {
        if (pokemonDataList != null) {
            return pokemonDataList.size();
        }
        return 0;
    }

    public void setPokemonDataList(ArrayList<PokemonGeneralData> pokemonDataList) {
        this.pokemonDataList = pokemonDataList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return Filtered;
    }

    private Filter Filtered = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PokemonGeneralData> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(pokemonDataListCopy);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PokemonGeneralData data : pokemonDataListCopy) {
                    if (data.get_pokemonName().toLowerCase().contains(filterPattern)
                            || data.get_pokemonID().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pokemonDataList.clear();
            pokemonDataList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void removeFavorite(FavoritePokemon fp) {
        for (int i = 0; i < favList.size(); i++) {
            if (fp.get_favPokemonID().equals(favList.get(i).get_favPokemonID())
                    && fp.get_favPokemonForm().equals(favList.get(i).get_favPokemonForm())) {
                favList.remove(i);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView pokemonName, pokemonID;
        private final ImageView pokemonImg /* ,pokemonBG*/, typeOne, typeTwo, formIcon;
        private RelativeLayout parent;
        public Button favBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemonName = itemView.findViewById(R.id.pkmnName);
            pokemonID = itemView.findViewById(R.id.pkmnID);
            pokemonImg = itemView.findViewById(R.id.pkmnImage);
//            pokemonBG = itemView.findViewById(R.id.pkmnBackG);
            typeOne = itemView.findViewById(R.id.type1Icon);
            typeTwo = itemView.findViewById(R.id.type2Icon);
            favBtn = itemView.findViewById(R.id.favoriteBTN);
            parent = itemView.findViewById(R.id.pkmnCard);
            formIcon = itemView.findViewById(R.id.formIcon);

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    PokemonGeneralData pkData = pokemonDataList.get(position);
                    FavoritePokemon favoritePokemon = new FavoritePokemon();
                    favoritePokemon.set_favPokemonName(pkData.get_pokemonName());
                    favoritePokemon.set_favPokemonID(pkData.get_pokemonID());
                    favoritePokemon.set_favPokemonForm(pkData.get_pokemonForm());
                    favoritePokemon.set_favPokemonType1(pkData.get_pokemonType1());
                    favoritePokemon.set_favPokemonType2(pkData.get_pokemonType2());

                    if (!pkData.isFavorite()) {
                        pkData.setFavorite(true);
                        favList.add(favoritePokemon);
                        favBtn.setBackgroundResource(R.mipmap.ic_fav_pb_btn_selected_foreground);
                    } else {
                        pkData.setFavorite(false);
                        removeFavorite(favoritePokemon);
                        favBtn.setBackgroundResource(R.mipmap.ic_fav_pb_btn_deselected_foreground);
                    }
                }
            });

            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent.getContext(), DexActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("fm", pkmnFastMoves);
                    bundle.putParcelableArrayList("cm", pkmnChargedMoves);
                    bundle.putParcelable("id", pokemonDataList.get(getAdapterPosition()));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
