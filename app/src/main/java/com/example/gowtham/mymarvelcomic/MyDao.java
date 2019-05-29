package com.example.gowtham.mymarvelcomic;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyDao {
    @Query("select * from favourite_movies")
    LiveData<List<FavouriteMovies>> getAllData();
    @Insert
    void insertData(FavouriteMovies favouriteMovies);
    @Delete
    void deleteData(FavouriteMovies favouriteMovies);
}
