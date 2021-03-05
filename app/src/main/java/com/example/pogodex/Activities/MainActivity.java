package com.example.pogodex.Activities;

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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pogodex.FavoriteMonActivity;
import com.example.pogodex.Interfaces.RequestInterfaceChargedMoves;
import com.example.pogodex.Interfaces.RequestInterfaceFastMoves;
import com.example.pogodex.ModelClasses.PokemonChargedMoves;
import com.example.pogodex.ModelClasses.PokemonFastMoves;
import com.example.pogodex.ModelClasses.PokemonGeneralData;
import com.example.pogodex.PokemonCardDataHolder;
import com.example.pogodex.R;
import com.example.pogodex.ViewModels.MainActivityViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

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
    private Button favoBTN;
    private RadioGroup radioGroupOfSort;
    private RadioGroup radioGroupFilter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private boolean legendFilterApplied = false;
    private boolean shinyFilterApplied = false;
    private boolean isDataLoaded = false;
    private PokemonCardDataHolder.ViewHolder viewHolder;
    private MainActivityViewModel mainActivityViewModel;
    private static MainActivity mainActivity;

    //indexed list for Filter purpose.
    private final String[] legendIdList = {"144", "145", "146", "150", "151", "243", "244", "245",
            "249", "250", "251", "377", "378", "379", "380", "381", "382", "383", "384", "385",
            "386", "480", "481", "482", "483", "484", "485", "486", "487", "488", "489", "490",
            "491", "492", "493", "638", "639", "640", "643", "644", "645", "646", "647", "648",
            "649", "716", "717", "718", "808", "809"};
    private final String[] nonShinyList = {"21", "22", "46", "47", "106", "107", "132", "143", "151",
            "163", "164", "167", "168", "187", "188", "189", "203", "214", "218", "219", "222", "223",
            "224", "226", "231", "232", "235", "236", "237", "251", "283", "284", "285", "286", "292",
            "293", "294", "295", "299", "316", "317", "322", "323", "324", "331", "332", "341", "342",
            "352", "357", "358", "363", "364", "365", "369", "385", "396", "397", "398", "399", "400",
            "408", "409", "410", "411", "415", "416", "417", "420", "421", "422", "423", "433", "434",
            "435", "441", "446", "455", "456", "457", "458", "476", "479", "480", "481", "482", "483",
            "484", "486", "489", "490", "492", "493", "494", "498", "499", "500", "501", "502", "503",
            "509", "510", "511", "512", "513", "514", "515", "516", "517", "518", "522", "523", "529",
            "530", "531"};
    private final String[] shinyList = {"532", "533", "534", "557", "558", "562", "563", "572", "573",
            "597", "598", "599", "600", "601", "613", "614", "627", "628", "631", "632", "633", "634",
            "635", "638", "639", "640", "649", "808", "809"};
    private final String[] shinyLegendsList = {"144", "145", "146", "150", "243", "244", "245", "249",
            "250", "377", "378", "379", "380", "381", "382", "383", "384", "385", "485", "487", "488",
            "491", "638", "639", "640", "649", "808", "809"};

    //Will contain Filtered list without forms
    ArrayList<PokemonGeneralData> pkmnList = new ArrayList<>();

    //Will contain unfiltered list with id,name,types and forms
    ArrayList<PokemonGeneralData> pkmnListOG = new ArrayList<>();

    //To be used to temporarily store pkmlist original data
    ArrayList<PokemonGeneralData> pkmnListTempCopy = new ArrayList<>();

    //To store fast and Charged moves
    ArrayList<PokemonFastMoves> pkmnFastMoves = new ArrayList<>();
    ArrayList<PokemonChargedMoves> pkmnChargedMoves = new ArrayList<>();

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
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        searchView = (androidx.appcompat.widget.SearchView) findViewById(R.id.searchView);

        //Animations initialization
        Animation rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        Animation rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        Animation toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        shimmerFrameLayout.startShimmer();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));


        setRecyclerView();
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mainActivityViewModel.init();

        mainActivityViewModel.getPkmnGenDataList().observe(this, new Observer<List<PokemonGeneralData>>() {
            @Override
            public void onChanged(List<PokemonGeneralData> pokemonGeneralData) {
                if(pokemonGeneralData!=null){
                    pkmnList = new ArrayList<>(pokemonGeneralData);
                    pkmnHolder.setPokemonDataList((ArrayList<PokemonGeneralData>) pokemonGeneralData);
                }
            }
        });


        fabParent.setEnabled(false);

        //Get list from JSON source
        ExtractListOfPKMN();

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

        //TODO Look into LiveData and fix the favorite Icon Bug
        //TODO complete 3dot options
    }

    public void setRecyclerView() {
        pkmnHolder = new PokemonCardDataHolder(MainActivity.this, pkmnList, pkmnFastMoves, pkmnChargedMoves);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(pkmnHolder);
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
                    Collections.sort(pkmnList, new Comparator<PokemonGeneralData>() {
                        @Override
                        public int compare(PokemonGeneralData o1, PokemonGeneralData o2) {
                            return o1.get_pokemonName().compareTo(o2.get_pokemonName());
                        }
                    });
                } else {
                    Collections.sort(pkmnList, new Comparator<PokemonGeneralData>() {
                        @Override
                        public int compare(PokemonGeneralData o1, PokemonGeneralData o2) {
                            return Integer.valueOf(o1.get_pokemonID()) - Integer.valueOf(o2.get_pokemonID());
                        }
                    });
                }
                pkmnHolder.notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Sorted in Ascending order!", Toast.LENGTH_SHORT).show();
            }
        });

        sortByDscBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroupOfSort.getCheckedRadioButtonId();
                RadioButton radioButtonSelected = popUpView.findViewById(radioId);

                if (radioId == R.id.radAlphBtn) {
                    Collections.sort(pkmnList, new Comparator<PokemonGeneralData>() {
                        @Override
                        public int compare(PokemonGeneralData o1, PokemonGeneralData o2) {
                            return o2.get_pokemonName().compareTo(o1.get_pokemonName());
                        }
                    });
                } else {
                    Collections.sort(pkmnList, new Comparator<PokemonGeneralData>() {
                        @Override
                        public int compare(PokemonGeneralData o1, PokemonGeneralData o2) {
                            return Integer.valueOf(o2.get_pokemonID()) - Integer.valueOf(o1.get_pokemonID());
                        }
                    });
                }
                pkmnHolder.notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Sorted in Descending order!", Toast.LENGTH_SHORT).show();
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
                    //Remove everything that's not in legendList and haven't been filtered for shinies
                    if (!shinyFilterApplied) {
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
                    } else
                        //if legend filter applied after shiny filter applied.
                        if (shinyFilterApplied) {
                            for (int i = 0; i < shinyLegendsList.length; ) {
                                //remove everything that doesn not match the shinylegendlist,
                                // #385 was an exception as it is not a shiny hence didn't exist,
                                // easy way out was to just mention it in 'OR' and move on.
                                if (shinyLegendsList[i].equals(pkmnList.get(i).get_pokemonID())
                                        || shinyLegendsList[i].equalsIgnoreCase("385")) {
                                    i++;
                                } else {
                                    pkmnList.remove(i);
                                }
                            }
                        }
                    pkmnHolder.notifyDataSetChanged();
                    dialog.dismiss();
                    legendFilterApplied = true;
                    Toast.makeText(MainActivity.this, "Showing only Legendaries!", Toast.LENGTH_SHORT).show();
                }

                if (radioId == R.id.radShinBtn) {
                    //if no filter (legend) has been applied yet.
                    if (!legendFilterApplied) {
                        //since the list of shinies is very dispersed and irregualar after #532
                        //i split the list into non shinies and shinies, for the sake of ease, since
                        //there are more shinies in list before 532 than there are non shinies and
                        //more non shinies than shinies after 532. 2 for loops had to be apploed for this.

                        //this loop applied to remove the nonshinies and keep everything else.
                        int j = 0;
                        for (int i = 0; j < nonShinyList.length; ) {
                            if (nonShinyList[j].equals(pkmnList.get(i).get_pokemonID())) {
                                j++;
                                pkmnList.remove(i);
                            } else {
                                i++;
                            }
                        }
                        //this loop applied to do the same but in a different way and after element 422
                        //in pkmnList since that's where the loop concluded in previous loop and happens
                        //to contain #532.
                        int k = 0;
                        for (int i = 422; k < shinyList.length; ) {
                            if ((shinyList[k].equals(pkmnList.get(i).get_pokemonID()))) {
                                i++;
                                k++;
                            } else {
                                pkmnList.remove(i);
                            }
                        }
                    } else
                        //if legend filter has been applied before shiny filter.
                        if (legendFilterApplied) {
                            //this loop applied to remove the nonshinies and keep everything else.
                            int j = 0;
                            for (int i = 0; j < shinyLegendsList.length; ) {
                                if (shinyLegendsList[j].equals(pkmnList.get(i).get_pokemonID())) {
                                    j++;
                                    i++;
                                } else {
                                    pkmnList.remove(i);
                                }
                            }
                        }
                    pkmnHolder.notifyDataSetChanged();
                    dialog.dismiss();
                    shinyFilterApplied = true;
                    Toast.makeText(MainActivity.this, "Showing only Shinies!", Toast.LENGTH_SHORT).show();
                }

                if (radioId == R.id.radNormalBtn) {
                    pkmnList.clear();
                    deepCopyArrList(pkmnListTempCopy, pkmnList);

                    pkmnHolder.notifyDataSetChanged();
                    dialog.dismiss();
                    legendFilterApplied = false;
                    shinyFilterApplied = false;
                    Toast.makeText(MainActivity.this, "Back to Normal.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deepCopyArrList(List<PokemonGeneralData> src, ArrayList<PokemonGeneralData> des) {
        Iterator<PokemonGeneralData> it = src.iterator();
        while (it.hasNext()) {
            PokemonGeneralData pd = it.next();
            PokemonGeneralData newpd = new PokemonGeneralData();
            newpd.set_pokemonName(pd.get_pokemonName());
            newpd.set_pokemonID(pd.get_pokemonID());
            newpd.set_pokemonForm(pd.get_pokemonForm());
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
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("key", PokemonCardDataHolder.favoritePkmnList);
            intToGotoFav.putExtras(bundle);
//            intToGotoFav.putExtra("key", (Serializable) pkmnHolder.favoritePkmnList);
            startActivity(intToGotoFav);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //if drawer is open on back pressed then close it before going back
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //else if back is pressed upon applying filter then undo the filter
        else if (legendFilterApplied || shinyFilterApplied) {
            pkmnList.clear();
            deepCopyArrList(pkmnListTempCopy, pkmnList);
            pkmnHolder.notifyDataSetChanged();
            //set filter flags to false checking which one was active
            if (legendFilterApplied) {
                legendFilterApplied = false;
            } else {
                shinyFilterApplied = false;
            }
        }
        //when no conditions are met just go back as usual
        else {
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
                if (isDataLoaded) {
                    pkmnHolder.getFilter().filter(newText);
                }
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

        fabParent.setEnabled(true);
        isDataLoaded = true;
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        //Assign to Adapter

        RequestInterfaceFastMoves requestInterfaceFastMoves = retrofit.create(RequestInterfaceFastMoves.class);
        Call<List<PokemonFastMoves>> call2 = requestInterfaceFastMoves.getFastMoveJSON();

        call2.enqueue(new Callback<List<PokemonFastMoves>>() {
            @Override
            public void onResponse(@NotNull Call<List<PokemonFastMoves>> call, @NotNull Response<List<PokemonFastMoves>> response) {
                pkmnFastMoves = new ArrayList<>(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<PokemonFastMoves>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Error!", Toast.LENGTH_LONG).show();
            }
        });

        RequestInterfaceChargedMoves requestInterfaceChargedMoves = retrofit.create(RequestInterfaceChargedMoves.class);
        Call<List<PokemonChargedMoves>> call3 = requestInterfaceChargedMoves.getChargedMoveJSON();

        call3.enqueue(new Callback<List<PokemonChargedMoves>>() {
            @Override
            public void onResponse(@NotNull Call<List<PokemonChargedMoves>> call, @NotNull Response<List<PokemonChargedMoves>> response) {
                pkmnChargedMoves = new ArrayList<>(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<PokemonChargedMoves>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Error!", Toast.LENGTH_LONG).show();
            }
        });
    }

}
