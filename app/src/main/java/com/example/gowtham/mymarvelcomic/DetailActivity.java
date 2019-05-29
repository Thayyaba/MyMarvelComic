package com.example.gowtham.mymarvelcomic;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    TextView modi,desc,name;
    String modif,descr,title,thumbnail,cid;
    ImageView poster;
    RecyclerView recyclerView_series,recycleerView_stories;
    List<MoviesModel> datamodel=new ArrayList<>();
    List<MoviesModel> data=datamodel;
    LikeButton likeButton;
    MoviesViewModel moviesViewModel;
    ListActivity1 listActivity;
    SharedPreferences  preferences;
    SharedPreferences.Editor pref_editor;
    final String SHARED_KEY="pavani";
    private static final String STATE_KEY="detailState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data=new ArrayList<>();
        listActivity=new ListActivity1();
        modi=findViewById(R.id.modified_text);
        desc=findViewById(R.id.desc_text);
        poster=findViewById(R.id.thumb);
        name=findViewById(R.id.name_text);
        likeButton=findViewById(R.id.star_but);
        moviesViewModel=ViewModelProviders.of(this).get(MoviesViewModel.class);
        Intent intent=getIntent();
        cid=intent.getStringExtra("cid");
        modif=intent.getStringExtra("modi");
        descr=intent.getStringExtra("desc");
        modi.setText(modif);
        desc.setText(descr);
        thumbnail=intent.getStringExtra("image");
        //Toast.makeText(this, thumbnail, Toast.LENGTH_SHORT).show();
        Log.e("url",thumbnail+".jpg");
        Picasso.with(this).load(thumbnail+".jpg").into(poster);
        title=intent.getStringExtra("Name");
        name.setText(title);

        List<String> comics=intent.getStringArrayListExtra("comics");
        recyclerView_series=findViewById(R.id.Recycler_Comics);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_series.setLayoutManager(layoutManager);
        recyclerView_series.setAdapter(new SeriesRecyclerAdapter(comics, this));
        List<String> stories=intent.getStringArrayListExtra("stories");
        recycleerView_stories=findViewById(R.id.recycler_stories);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleerView_stories.setLayoutManager(linearLayoutManager);
        recycleerView_stories.setAdapter(new StoriesRecyclerAdapter(stories, this));
        favouritecheck();

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                FavouriteMovies favouriteMovies=new FavouriteMovies(cid,title,thumbnail,modif,descr);
                moviesViewModel.insertFromViewModel(favouriteMovies);
                Snackbar.make(likeButton,"Added to Favourites",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                FavouriteMovies favouriteMovies=new FavouriteMovies(cid,title,thumbnail,modif,descr);
                moviesViewModel.deleteFromViewModel(favouriteMovies);
                Snackbar.make(likeButton,"Removed from Favourites",Snackbar.LENGTH_SHORT).show();


            }
        });

        preferences=getSharedPreferences(SHARED_KEY,MODE_PRIVATE);
        pref_editor=preferences.edit();
        pref_editor.putString(listActivity.SHARED_KEY_deatil_activity,"");
        pref_editor.commit();

    }

    private void favouritecheck(){
        moviesViewModel.getview_data().observe(this, new Observer<List<FavouriteMovies>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteMovies> favouriteMovies) {
                for (int i=0;i<favouriteMovies.size();i++){
                    String st=favouriteMovies.get(i).getMid();
                    if (st.equalsIgnoreCase(cid)){
                        likeButton.setLiked(true);
                    }
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_KEY,null);
        preferences=getPreferences(MODE_PRIVATE);
        pref_editor=preferences.edit();
        pref_editor.putString(listActivity.SHARED_KEY_deatil_activity,"");
        pref_editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
