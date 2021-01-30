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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.pogodex.PokemonCardDataHolder.favoritePkmnList;

public class FavPokemonCardDataHolder extends RecyclerView.Adapter<FavPokemonCardDataHolder.ViewHolder> {

    ArrayList<PokemonData> favpkmnList;
    private Context context;

    FavPokemonCardDataHolder(Context context, ArrayList<PokemonData> favpkmn) {
        this.context = context;
        this.favpkmnList = favoritePkmnList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_pkmn_layout, parent, false);
        FavPokemonCardDataHolder.ViewHolder holder = new FavPokemonCardDataHolder.ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.favBtn.setTag(position);
        int colorid = favpkmnList.get(position).get_background();
        Drawable color = holder.parent.getContext().getDrawable(colorid);
        holder.parent.setBackground(color);
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
        private RelativeLayout parent;
        private Button favBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemonName = itemView.findViewById(R.id.pkmnName);
            pokemonID = itemView.findViewById(R.id.pkmnID);
            pokemonImg = itemView.findViewById(R.id.pkmnImage);
            pokemonBG = itemView.findViewById(R.id.pkmnBackG);
            typeOne = itemView.findViewById(R.id.type1Icon);
            typeTwo = itemView.findViewById(R.id.type2Icon);
            parent = itemView.findViewById(R.id.pkmnCard);
            favBtn = itemView.findViewById(R.id.favoriteBTN);

            favBtn.setBackgroundResource(R.mipmap.ic_fav_pb_btn_selected_foreground);

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) favBtn.getTag();
                    PokemonData pkData = favpkmnList.get(position);
                    pkData.setFavorite(false);
                    favpkmnList.remove(pkData);
                    notifyDataSetChanged();
                }
            });
        }
    }
}