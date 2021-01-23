package com.example.pogodex;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    private FloatingActionButton fabParent, fabCh1, fabCh2;
    private static String JSON_Url = "https://pokemon-go1.p.rapidapi.com/";
    private Boolean fabisOpen = false;
    private Boolean popUpIsOpen = false;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private AlertDialog.Builder dialogBuilder;
    private Button radioButtonSelected;
    private RadioGroup radioGroupOfSort;
    private RadioGroup radioGroupFilter;

    private final String[] legendIdList = {"144", "145", "146", "150", "151", "243", "244", "245", "249", "250",
            "251", "377", "378", "379", "380", "381", "382", "383", "384", "385", "386", "480", "481", "482", "483",
            "484", "485", "486", "487", "488", "489", "490", "491", "492", "493", "638", "639", "640", "643", "644",
            "645", "646", "647", "648", "649", "716", "717", "718", "808", "809"};

    //Will contain Filtered list without forms
    ArrayList<PokemonData> pkmnList = new ArrayList<>();

    //Will contain unfiltered list with id,name,types and forms
    ArrayList<PokemonData> pkmnListOG = new ArrayList<>();

    //To be used to temporarily store pkmlist original data
    ArrayList<PokemonData> pkmnListTempCopy = new ArrayList<>();

    PokemonCardDataHolder pkmnHolder;

    androidx.appcompat.widget.SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find & Initialize views
        recyclerView = findViewById(R.id.recViewListOfPkmn);
        fabParent = findViewById(R.id.floatParent);
        fabCh1 = findViewById(R.id.floatChild1);
        fabCh2 = findViewById(R.id.floatChild2);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        //Animations initialization
        Animation rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        Animation rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        Animation toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        //Get list from JSON source
        ExtractListOfPKMN();

        searchView = (androidx.appcompat.widget.SearchView) findViewById(R.id.searchView);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //Execute Search from RecyclerView list
        SearchList();

        //Parent FloatButton on click
        fabParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fabisOpen) {
                    fabCh1.setVisibility(View.VISIBLE);
                    fabCh2.setVisibility(View.VISIBLE);
                    fabParent.startAnimation(rotateOpen);
                    fabCh1.startAnimation(fromBottom);
                    fabCh2.startAnimation(fromBottom);
                    fabisOpen = true;
                } else {
                    fabParent.startAnimation(rotateClose);
                    fabCh1.startAnimation(toBottom);
                    fabCh2.startAnimation(toBottom);
                    fabCh1.setVisibility(View.INVISIBLE);
                    fabCh2.setVisibility(View.INVISIBLE);
                    fabisOpen = false;
                }
            }
        });

        //FAB child1(Sort) on Click
        fabCh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabParent.startAnimation(rotateClose);
                fabCh1.startAnimation(toBottom);
                fabCh2.startAnimation(toBottom);
                fabCh1.setVisibility(View.INVISIBLE);
                fabCh2.setVisibility(View.INVISIBLE);
                fabisOpen = false;
                setSortPopUpDialog();
            }
        });

        //FAB child2(Filter) on Click
        fabCh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabParent.startAnimation(rotateClose);
                fabCh1.startAnimation(toBottom);
                fabCh2.startAnimation(toBottom);
                fabCh1.setVisibility(View.INVISIBLE);
                fabCh2.setVisibility(View.INVISIBLE);
                fabisOpen = false;
                setFiltPopUpDialog();
            }
        });

        //TODO complete the filter features
        //TODO complete 3dot ooptions
        //TODO improve design and UI

    }

    public void setSortPopUpDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View popUpView = getLayoutInflater().inflate(R.layout.sort_popup, null);

        Button sortByAscBTN = popUpView.findViewById(R.id.ascendBtn);
        Button sortByDscBTN = popUpView.findViewById(R.id.dscendBtn);
        radioGroupOfSort = popUpView.findViewById(R.id.radGrpSort);

        dialogBuilder.setView(popUpView);
        Dialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        sortByAscBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroupOfSort.getCheckedRadioButtonId();
                RadioButton radioButtonSelected = popUpView.findViewById(radioId);

                if (radioId == R.id.radAlphBtn) {
                    Collections.sort(pkmnList, new Comparator<PokemonData>() {
                        @Override
                        public int compare(PokemonData o1, PokemonData o2) {
                            return o1.get_pokemonName().compareTo(o2.get_pokemonName());
                        }
                    });
                } else {
                    Collections.sort(pkmnList, new Comparator<PokemonData>() {
                        @Override
                        public int compare(PokemonData o1, PokemonData o2) {
                            return Integer.valueOf(o1.get_pokemonID()) - Integer.valueOf(o2.get_pokemonID());
                        }
                    });
                }
                pkmnHolder.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        sortByDscBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroupOfSort.getCheckedRadioButtonId();
                RadioButton radioButtonSelected = popUpView.findViewById(radioId);

                if (radioId == R.id.radAlphBtn) {
                    Collections.sort(pkmnList, new Comparator<PokemonData>() {
                        @Override
                        public int compare(PokemonData o1, PokemonData o2) {
                            return o2.get_pokemonName().compareTo(o1.get_pokemonName());
                        }
                    });
                } else {
                    Collections.sort(pkmnList, new Comparator<PokemonData>() {
                        @Override
                        public int compare(PokemonData o1, PokemonData o2) {
                            return Integer.valueOf(o2.get_pokemonID()) - Integer.valueOf(o1.get_pokemonID());
                        }
                    });
                }
                pkmnHolder.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    public void setFiltPopUpDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View popUpViewFilt = getLayoutInflater().inflate(R.layout.filter_popup, null);

        Button filtApplyBTN = popUpViewFilt.findViewById(R.id.applyFiltBtn);
        radioGroupFilter = popUpViewFilt.findViewById(R.id.radGrpFilter);

        dialogBuilder.setView(popUpViewFilt);
        Dialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        filtApplyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroupFilter.getCheckedRadioButtonId();
                RadioButton radioButtonSelected = popUpViewFilt.findViewById(radioId);

                if (radioId == R.id.radLegBtn) {
                    //Remove everything that's not in legendList
                    for (int i = 0; i < legendIdList.length; ) {
                        if (legendIdList[i].equals(pkmnList.get(i).get_pokemonID())) {
                            i++;
                        } else {
                            pkmnList.remove(i);
                        }
                    }
                    //secondary loop to clear everything that's left
                    for (int i = 0; i < pkmnList.size(); ) {
                        if (i >= legendIdList.length) pkmnList.remove(i);
                        else i++;
                    }
                    pkmnHolder.notifyDataSetChanged();
                    dialog.dismiss();
                }

                if (radioId == R.id.radNormalBtn) {
                    pkmnList.clear();
                    deepCopyArrList(pkmnListTempCopy, pkmnList);

                    pkmnHolder.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
    }

    public void deepCopyArrList(ArrayList<PokemonData> src, ArrayList<PokemonData> des) {
        Iterator<PokemonData> it = src.iterator();
        while (it.hasNext()) {
            PokemonData pd = it.next();
            PokemonData newpd = new PokemonData();
            newpd.set_pokemonName(pd.get_pokemonName());
            newpd.set_pokemonID(pd.get_pokemonID());
            newpd.set_pokemonForm(pd.get_pokemonForm());
            newpd.set_pokemonImage(pd.get_pokemonImage());
            newpd.set_pokemonTypes(pd.get_pokemonTypes());
            newpd.setFavorite(pd.isFavorite());
            des.add(newpd);
        }
    }

    public void checkButton(View v) {
        int radioId = radioGroupOfSort.getCheckedRadioButtonId();
        radioButtonSelected = findViewById(radioId);
    }

    public void checkButton2(View v) {
        int radioId = radioGroupFilter.getCheckedRadioButtonId();
        radioButtonSelected = findViewById(radioId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.three_dots_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.favorites_item) {
            Intent intToGotoFav = new Intent(this, FavoriteMonActivity.class);
            intToGotoFav.putExtra("key", (Serializable) pkmnHolder.favoritePkmnList);
            startActivity(intToGotoFav);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void SearchList() {
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pkmnHolder.getFilter().filter(newText);
                return false;
            }
        });
    }

    //Extract JSON data for Main screen layout
    private void ExtractListOfPKMN() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JSON_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<PokemonData>> call = requestInterface.getPokeJSON();

        call.enqueue(new Callback<List<PokemonData>>() {
            @Override
            public void onResponse(Call<List<PokemonData>> call, Response<List<PokemonData>> response) {
                pkmnList = new ArrayList<>(response.body());

                //Copy Original JSON array to backup List
                deepCopyArrList(pkmnList, pkmnListOG);

                //Filter to display only Normal forms
                for (int i = 0; i < pkmnList.size(); ) {
                    if (pkmnList.get(i).get_pokemonForm().equals("Normal") || pkmnList.get(i).get_pokemonForm().equals("Incarnate")
                            || pkmnList.get(i).get_pokemonForm().equals("Ordinary") || pkmnList.get(i).get_pokemonForm().equals("Aria")
                            || pkmnList.get(i).get_pokemonForm().equals("Galarian") || pkmnList.get(i).get_pokemonForm().equals("Altered")
                            || pkmnList.get(i).get_pokemonForm().equals("Land")) {
                        i++;
                    } else {
                        pkmnList.remove(i);
                    }
                }
                deepCopyArrList(pkmnList, pkmnListTempCopy);
                //Assign to Adapter
                pkmnHolder = new PokemonCardDataHolder(MainActivity.this, pkmnList);
                recyclerView.setAdapter(pkmnHolder);
            }

            @Override
            public void onFailure(Call<List<PokemonData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Check your Internet Connection and try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

}
