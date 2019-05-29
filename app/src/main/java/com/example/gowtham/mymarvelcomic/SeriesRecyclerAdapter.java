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

public class SeriesRecyclerAdapter extends RecyclerView.Adapter<SeriesRecyclerAdapter.SeriesInfo> {
    List<String> data=new ArrayList<String>();
    Context context;

    public SeriesRecyclerAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public SeriesRecyclerAdapter.SeriesInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.serieslist,parent,false);
        return new SeriesInfo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesRecyclerAdapter.SeriesInfo holder, int position) {
        holder.series_name.setText(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SeriesInfo extends RecyclerView.ViewHolder {
        TextView series_name;
        public SeriesInfo(View itemView) {
            super(itemView);
            series_name=itemView.findViewById(R.id.series_text);
        }
    }
}
