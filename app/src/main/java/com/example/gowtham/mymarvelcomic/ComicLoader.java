package com.example.gowtham.mymarvelcomic;

import android.content.Context;
import android.content.Loader;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.example.gowtham.mymarvelcomic.Key.BASE_URL;

public class ComicLoader extends AsyncTaskLoader {
    public ComicLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public Object loadInBackground() {
        Uri uri;
        uri=Uri.parse(BASE_URL).buildUpon().build();
        try {
            URL url=new URL(uri.toString());
            HttpsURLConnection httpsURLConnection= (HttpsURLConnection) url.openConnection();
            InputStream inputStream=httpsURLConnection.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader br=new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder=new StringBuilder();
            String line;
            while ((line=br.readLine())!=null){
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
