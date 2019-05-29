package com.example.gowtham.mymarvelcomic;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {
    private Mavel_Repository mavelRepository;
    private LiveData<List<FavouriteMovies>> get_data;
    public MoviesViewModel(@NonNull Application application) {
        super(application);
        mavelRepository=new Mavel_Repository(application);
        get_data=mavelRepository.getrepo();
    }
    public LiveData<List<FavouriteMovies>> getview_data(){
        return get_data;
    }
    public void insertFromViewModel(FavouriteMovies favouriteMovies){
        mavelRepository.insert(favouriteMovies);
    }
    public void deleteFromViewModel(FavouriteMovies favouriteMovies){
        mavelRepository.delete(favouriteMovies);
    }
}
