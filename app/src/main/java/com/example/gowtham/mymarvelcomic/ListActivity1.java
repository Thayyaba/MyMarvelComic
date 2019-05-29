package com.example.gowtham.mymarvelcomic;

import android.app.AlertDialog;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ListActivity1 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerView;
    @InjectView(R.id.progress)
    ProgressBar progressBar;
    List<MoviesModel> moviesModels;
    public static List<FavouriteMovies> list;
    RecyclerViewAdapter viewAdapter;
    MoviesViewModel moviesViewModel;
    final String marvel = "all_movies";
    public static final String SHARED_KEY_deatil_activity = "chenna";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    FirebaseAuth firebaseAuth;
    private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listrecycler);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        sp = getPreferences(Context.MODE_PRIVATE);
        MobileAds.initialize(this, "ca-app-pub-4417180280217549~4364352580");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        if (firebaseUser != null) {
            moviesModels = new ArrayList<>();
            ButterKnife.inject(this);
            moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
            String data = sp.getString("mykey", "marvel");

            if (data.equalsIgnoreCase("marvel")) {
                marvelMovies();
            } else if (data.equalsIgnoreCase("fav")) {
                favouriteMovies();
            }

            marvelMovies();
        } else {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    private void marvelMovies() {
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new ComicLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<String> loader, String data) {
        progressBar.setVisibility(View.GONE);
        List<String> comicList = new ArrayList<>();
        List<String> storiesList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(data);
            JSONObject dataObject = object.getJSONObject("data");
            JSONArray array = dataObject.getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject resultObject = array.getJSONObject(i);
                String dataid = resultObject.getString("id");
                String name = resultObject.optString("name");
                JSONObject thumbnailObj = resultObject.getJSONObject("thumbnail");
                String path = thumbnailObj.getString("path");
                String description = resultObject.getString("description");
                String modifiedDate = resultObject.getString("modified");
                JSONObject comicObject = resultObject.getJSONObject("comics");
                JSONArray comicArray = comicObject.getJSONArray("items");
                for (int j = 0; j < comicArray.length(); j++) {
                    JSONObject itemsObj = comicArray.getJSONObject(j);
                    String comicName = itemsObj.getString("name");
                    comicList.add(comicName);
                }
                JSONObject storiesObj = resultObject.getJSONObject("stories");
                JSONArray storiesArray = storiesObj.getJSONArray("items");
                for (int k = 0; k < storiesArray.length(); k++) {
                    JSONObject itemobject1 = storiesArray.getJSONObject(k);
                    String storyName = itemobject1.getString("name");
                    storiesList.add(storyName);
                }
                MoviesModel model = new MoviesModel(dataid, name, path, description, comicList, storiesList, modifiedDate);
                moviesModels.add(model);
            }

            viewAdapter = new RecyclerViewAdapter(this, moviesModels);
            int orentation = this.getResources().getConfiguration().orientation;
            if (orentation == Configuration.ORIENTATION_LANDSCAPE) {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            }
            recyclerView.setAdapter(viewAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<String> loader) {

    }

    private void favouriteMovies() {
        progressBar.setVisibility(View.INVISIBLE);
        moviesViewModel.getview_data().observe(this, new Observer<List<FavouriteMovies>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteMovies> favouriteMovies) {
                if (favouriteMovies.isEmpty()) {
                    Toast.makeText(ListActivity1.this, "No Favourite Movies", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity1.this);
                    builder.setTitle("Do you want to go for ONLINE..!");
                    builder.setMessage("Not yet selected Favourites let's go for online");
                    builder.setPositiveButton("Go to web", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String link = "https://www.marvel.com/comics/characters";
                            Uri addressUri = Uri.parse(link);
                            Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                } else {
                    FavouriteRecyclerViewAdapter adapter = new FavouriteRecyclerViewAdapter(ListActivity1.this, favouriteMovies, moviesModels);
                    recyclerView.setAdapter(adapter);
                    int orientation = ListActivity1.this.getResources().getConfiguration().orientation;
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        recyclerView.setLayoutManager(new GridLayoutManager(ListActivity1.this, 4));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(ListActivity1.this, 2));
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.marvel:
                editor = sp.edit();
                editor.putString("mykey", "marvel");
                editor.commit();
                marvelMovies();
                break;
            case R.id.web:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Do you want to go to web...!");
                builder.setMessage("Browse Online");
                builder.setPositiveButton("Go to web", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String link = "https://www.marvel.com/comics/characters";
                        Uri addressUri = Uri.parse(link);
                        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.fav_movie:
                editor = sp.edit();
                editor.putString("mykey", "fav");
                editor.commit();
                favouriteMovies();
                break;
            case R.id.signout:
                finish();
                firebaseAuth.signOut();
                startActivity(new Intent(ListActivity1.this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


}
