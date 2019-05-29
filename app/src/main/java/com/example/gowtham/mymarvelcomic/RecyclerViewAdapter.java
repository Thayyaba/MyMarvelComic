package com.example.gowtham.mymarvelcomic;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewInfo> {
    private static final String SHARED_WIDGET="movie";
    private static final String IMAGE_ID="img_wid";
    private static final String NAME_KEY="name_wid";
    Context context;
    List<MoviesModel> dataModel;

    public RecyclerViewAdapter(Context context, List<MoviesModel> dataModel) {
        this.context = context;
        this.dataModel = dataModel;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.itemlist,parent,false);
        return new ViewInfo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewInfo holder, int position) {
        Picasso.with(context).load(dataModel.get(position).getThumbNail()+".jpg")
                .placeholder(R.drawable.imgnot)
                .into(holder.comic_Img);
        holder.title.setText(String.valueOf(dataModel.get(position).getName()));

    }

    @Override
    public int getItemCount() {
        return dataModel.size();
    }

    public class ViewInfo extends RecyclerView.ViewHolder {
        ImageView comic_Img;
        TextView title;
        public ViewInfo(View itemView) {
            super(itemView);
            comic_Img=itemView.findViewById(R.id.comic_image);
            title=itemView.findViewById(R.id.title_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Intent intent=new Intent(context,DetailActivity.class);
                    intent.putExtra("cid",dataModel.get(position).comic_id);
                    intent.putExtra("desc",dataModel.get(position).getDescription());
                    intent.putExtra("modi",dataModel.get(position).getModified());
                    intent.putExtra("image",dataModel.get(position).getThumbNail());
                    intent.putExtra("Name",dataModel.get(position).getName());
                    intent.putStringArrayListExtra("stories", (ArrayList<String>) dataModel.get(position).getStoriesList());
                    intent.putStringArrayListExtra("comics", (ArrayList<String>) dataModel.get(position).getComicList());
                    SharedPreferences preferences=context.getSharedPreferences(SHARED_WIDGET,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();

                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(dataModel.get(position).getName()+"\n"+dataModel.get(position).getModified());
                    editor.putString(IMAGE_ID,dataModel.get(position).getThumbNail()+".jpg");
                    editor.putString(NAME_KEY,stringBuilder.toString());
                    editor.commit();
                    Intent wid_intent=new Intent(context,MarvelAppWidget.class);
                    wid_intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                    int widget[]=AppWidgetManager.getInstance(context.getApplicationContext())
                            .getAppWidgetIds(new ComponentName(context.getApplicationContext(),MarvelAppWidget.class));
                    wid_intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,widget);
                    context.sendBroadcast(wid_intent);
                    context.startActivity(intent);
                }
            });

        }

    }
}
