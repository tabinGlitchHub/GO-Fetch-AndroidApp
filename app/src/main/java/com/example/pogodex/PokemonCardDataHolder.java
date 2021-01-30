package com.example.pogodex;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.ArrayList;
import java.util.List;

public class PokemonCardDataHolder extends RecyclerView.Adapter<PokemonCardDataHolder.ViewHolder> implements Filterable {

    private ArrayList<PokemonData> pokemonDataList;
    private ArrayList<PokemonData> pokemonDataListCopy;
    public static ArrayList<PokemonData> favoritePkmnList = new ArrayList<>(20);
    private Context context;
    int curveRadius = 25;

    public PokemonCardDataHolder(Context context, ArrayList<PokemonData> pokemonDataList) {
        this.context = context;
        this.pokemonDataList = pokemonDataList;
        pokemonDataListCopy = new ArrayList<>(pokemonDataList);
    }

    public PokemonCardDataHolder() {
    }

    public ArrayList<PokemonData> getFavoritePkmnList() {
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
        holder.favBtn.setTag(position);
        int colorid = pokemonDataList.get(position).get_background();
        Drawable color = holder.parent.getContext().getDrawable(colorid);
        holder.parent.setBackground(color);
        holder.pokemonName.setText(pokemonDataList.get(position).get_pokemonName());
        holder.pokemonID.setText(pokemonDataList.get(position).get_pokemonID());

        Glide.with(context)
                .asBitmap()
                .load(pokemonDataList.get(position).get_pokemonImage())
                .into(holder.pokemonImg);

        Glide.with(context)
                .asBitmap()
                .load(pokemonDataList.get(position).get_pokemonType())
                .transform(new RoundedCorners(curveRadius))
                .into(holder.pokemonBG);

        Glide.with(context)
                .asBitmap()
                .load(pokemonDataList.get(position).get_pokemonType1())
                .into(holder.typeOne);

        Glide.with(context)
                .asBitmap()
                .load(pokemonDataList.get(position).get_pokemonType2())
                .into(holder.typeTwo);

        refreshFavList(holder, position);
    }

    public void refreshFavList(ViewHolder viewHolder, int position) {
        if (!pokemonDataList.get(position).isFavorite()) {
            viewHolder.favBtn.setBackgroundResource(R.mipmap.ic_fav_pb_btn_deselected_foreground);
        }
    }


    @Override
    public int getItemCount() {
        return pokemonDataList.size();
    }

    public void setPokemonDataList(ArrayList<PokemonData> pokemonDataList) {
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
            List<PokemonData> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(pokemonDataListCopy);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PokemonData data : pokemonDataListCopy) {
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

    private boolean containmentCheck(String toThis) {
        for (int i = 0; i < favoritePkmnList.size(); i++) {
            if (favoritePkmnList.get(i).get_pokemonID().equals(toThis)) {
                return true;
            }
        }
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView pokemonName;
        private final TextView pokemonID;
        private ImageView pokemonImg;
        private final ImageView pokemonBG;
        private final ImageView typeOne;
        private final ImageView typeTwo;
        private RelativeLayout parent;
        public Button favBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemonName = itemView.findViewById(R.id.pkmnName);
            pokemonID = itemView.findViewById(R.id.pkmnID);
            pokemonImg = itemView.findViewById(R.id.pkmnImage);
            pokemonBG = itemView.findViewById(R.id.pkmnBackG);
            typeOne = itemView.findViewById(R.id.type1Icon);
            typeTwo = itemView.findViewById(R.id.type2Icon);
            favBtn = itemView.findViewById(R.id.favoriteBTN);
            parent = itemView.findViewById(R.id.pkmnCard);

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) favBtn.getTag();
                    PokemonData pkData = pokemonDataList.get(position);
                    if (!pkData.isFavorite() || !containmentCheck(pkData.get_pokemonID())) {
                        pkData.setFavorite(true);
                        favoritePkmnList.add(pkData);
                        favBtn.setBackgroundResource(R.mipmap.ic_fav_pb_btn_selected_foreground);
                    } else {
                        pkData.setFavorite(false);
                        favoritePkmnList.remove(pkData);
                        favBtn.setBackgroundResource(R.mipmap.ic_fav_pb_btn_deselected_foreground);
                    }
                }
            });
        }
    }
}
