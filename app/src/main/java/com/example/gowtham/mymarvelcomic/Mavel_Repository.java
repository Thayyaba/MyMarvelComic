package com.example.gowtham.mymarvelcomic;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class Mavel_Repository {
    private MyDao Dao;
    private LiveData<List<FavouriteMovies>> get_data;
    public Mavel_Repository(Application application) {
        MyDataBase dataBase=MyDataBase.getDatabase(application);
        Dao=dataBase.myDaoDb();
        get_data=Dao.getAllData();
    }
    public LiveData<List<FavouriteMovies>> getrepo(){
        return get_data;
    }
    public void insert(FavouriteMovies favouriteMovies){
        new AsynctaskInsert(Dao).execute(favouriteMovies);
    }
    private class AsynctaskInsert extends AsyncTask<FavouriteMovies,Void,Void>{

        MyDao Dao;

        public AsynctaskInsert(MyDao dao) {
            Dao = dao;
        }

        @Override
        protected Void doInBackground(FavouriteMovies... favouriteMovies) {
            Dao.insertData(favouriteMovies[0]);
            return null;
        }
    }
    public void delete(FavouriteMovies favouriteMovies){
        new DeleteAsynctask(Dao).execute(favouriteMovies);
    }
    private class DeleteAsynctask extends AsyncTask<FavouriteMovies,Void,Void>{
        MyDao Dao;

        public DeleteAsynctask(MyDao dao) {
            Dao = dao;
        }

        @Override
        protected Void doInBackground(FavouriteMovies... favouriteMovies) {
            Dao.deleteData(favouriteMovies[0]);
            return null;
        }
    }
}
