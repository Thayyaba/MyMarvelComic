package com.example.gowtham.mymarvelcomic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StoriesRecyclerAdapter extends RecyclerView.Adapter<StoriesRecyclerAdapter.MyViewHolder> {
    List<String> Stories=new ArrayList<String>();
    Context context;

    public StoriesRecyclerAdapter(List<String> stories, Context context) {
        Stories = stories;
        this.context = context;
    }

    @NonNull
    @Override
    public StoriesRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.storieslist,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesRecyclerAdapter.MyViewHolder holder, int position) {
        holder.stories_name.setText(Stories.get(position));

    }

    @Override
    public int getItemCount() {
        return Stories.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView stories_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            stories_name=itemView.findViewById(R.id.stories_text);
        }
    }
}
