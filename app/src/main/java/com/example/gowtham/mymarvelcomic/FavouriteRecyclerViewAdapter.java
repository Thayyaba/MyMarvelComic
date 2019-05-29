package com.example.gowtham.mymarvelcomic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavouriteRecyclerViewAdapter extends RecyclerView.Adapter<FavouriteRecyclerViewAdapter.MyViewHolder> {
    Context context_marvel;
    List<FavouriteMovies> favouriteMoviesList;
    List<MoviesModel> moviesModels;

    public FavouriteRecyclerViewAdapter(Context context_marvel, List<FavouriteMovies> favouriteMoviesList, List<MoviesModel> moviesModels) {
        this.context_marvel = context_marvel;
        this.favouriteMoviesList = favouriteMoviesList;
        this.moviesModels = moviesModels;
    }

    @NonNull
    @Override
    public FavouriteRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context_marvel).inflate(R.layout.favourite_movie_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tv.setText(favouriteMoviesList.get(position).getName());
        Picasso.with(context_marvel).load(favouriteMoviesList.get(position)
        .getThumbNail()+".jpg").placeholder(R.drawable.imgnot).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return favouriteMoviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.fav_movieimg);
            tv=itemView.findViewById(R.id.fav_marvel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            Intent intent=new Intent(context_marvel,DetailActivity.class);
            intent.putExtra("cid",favouriteMoviesList.get(pos).getMid());
            intent.putExtra("desc",favouriteMoviesList.get(pos).getDescription());
            intent.putExtra("modi",favouriteMoviesList.get(pos).getModified());
            intent.putExtra("image",favouriteMoviesList.get(pos).getThumbNail());
            intent.putExtra("Name",favouriteMoviesList.get(pos).getName());
            intent.putStringArrayListExtra("stories", (ArrayList<String>) moviesModels.get(pos).getStoriesList());
            intent.putStringArrayListExtra("comics", (ArrayList<String>) moviesModels.get(pos).getComicList());
            context_marvel.startActivity(intent);
        }
    }
}
