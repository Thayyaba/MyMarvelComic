package com.example.gowtham.mymarvelcomic;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavouriteMovies.class},version = 1,exportSchema =false )
public  abstract class MyDataBase extends RoomDatabase {
    public abstract MyDao myDaoDb();
    private static MyDataBase INSTANCE;
    public static MyDataBase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (MyDataBase.class){
                if (INSTANCE==null){
                    INSTANCE=Room.databaseBuilder(context,MyDataBase.class,"marveldb")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
