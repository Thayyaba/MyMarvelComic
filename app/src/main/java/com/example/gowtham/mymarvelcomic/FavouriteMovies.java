package com.example.gowtham.mymarvelcomic;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favourite_movies")
public class FavouriteMovies {
    @NonNull
    @PrimaryKey
    String mid;
    String name;
    String thumbNail;
    String description;
    String modified;

    public FavouriteMovies(@NonNull String mid, String name, String thumbNail, String description, String modified) {
        this.mid = mid;
        this.name = name;
        this.thumbNail = thumbNail;
        this.description = description;
        this.modified = modified;
    }

    @NonNull
    public String getMid() {
        return mid;
    }

    public void setMid(@NonNull String mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
