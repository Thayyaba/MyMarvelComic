package com.example.gowtham.mymarvelcomic;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class StatefullRecyclerView extends RecyclerView {
    private static final String SAVED_SUPER_STATE="state";
    private static final String SAVED_LAYOUT_MANAGER="layout state";
    private Parcelable parcelable;
    public StatefullRecyclerView(Context context) {
        super(context);
    }

    public StatefullRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatefullRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle=new Bundle();
        bundle.putParcelable(SAVED_SUPER_STATE,super.onSaveInstanceState());
        bundle.putParcelable(SAVED_LAYOUT_MANAGER,this.getLayoutManager().onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle= (Bundle) state;
            parcelable=bundle.getParcelable(SAVED_LAYOUT_MANAGER);
            state=bundle.getParcelable(SAVED_SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }
    private void restorePosition(){
        if (parcelable!=null){
            this.getLayoutManager().onRestoreInstanceState(parcelable);
            parcelable=null;
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        restorePosition();
    }
}
