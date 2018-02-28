package com.example.godaa.movieapplacation.helper;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.godaa.movieapplacation.Interface.ServiceCalback;
import com.example.godaa.movieapplacation.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pc1 on 27/02/2018.
 */

public class favService extends IntentService {
    static List<Movie> movies;
    public static ServiceCalback servicecallback;
    static Context context;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param //name Used to name the worker thread, important only for debugging.
     */
    public favService() {
        super("favService");
        //context = this;
    }




    public  void loadFavouriteMovies() {

        Cursor cursor =this.getContentResolver().query(Dbcotract.TableInfo.CONTENT_URI,
                null, Dbcotract.TableInfo.Favourite + "=?", new String[]{"1"}, null);
        if (cursor.moveToFirst()) {
            movies = new ArrayList<>();
            do {
                Movie movie = new Movie();

                movie.setId(cursor.getInt(0));
                movie.setOriginalTitle(cursor.getString(1));
                movie.setOriginalLanguage(cursor.getString(2));
                movie.setTitle(cursor.getString(3));
                movie.setPosterPath(cursor.getString(4));
                movie.setPopularity(cursor.getDouble(5));
                movie.setVoteCount(cursor.getInt(6));
                movie.setVideo(cursor.getString(7));
                movie.setVoteAverage(cursor.getDouble(8));
                movie.setAdult(cursor.getString(9));
                movie.setOverview(cursor.getString(10));
                movie.setReleaseDate(cursor.getString(11));

                movies.add(movie);

            } while (cursor.moveToNext());
            cursor.close();
            servicecallback.loadUi(movies);

        }
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
            //  }else {
            loadFavouriteMovies();

            //  }

        //loadFavouriteMovies();
        context = this;

    }
}
