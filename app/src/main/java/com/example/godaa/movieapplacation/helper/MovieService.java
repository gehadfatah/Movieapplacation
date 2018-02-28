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
import com.example.godaa.movieapplacation.model.MoviesData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by Pc1 on 27/02/2018.
 */

public class MovieService extends IntentService {
    static List<Movie> movies;
    public static ServiceCalback servicecallback;
    static Context context;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param //name Used to name the worker thread, important only for debugging.
     */
    public MovieService() {
        super("MovieService");
        //context = this;
    }


    public void insertTodatabase(List<Movie> moviesDatas) {
        int i = 0;
        Cursor cursor = null;


        do {
            Movie movie = moviesDatas.get(i);
            cursor = this.getContentResolver().query(Dbcotract.TableInfo.CONTENT_URI,
                    new String[]{Dbcotract.TableInfo.Id}, Dbcotract.TableInfo.Id + "=?", new String[]{String.valueOf(moviesDatas.get(i).getId())}, Dbcotract.TableInfo.Id);
            // Cursor cursor = moviesDBHelper.get_if_exist(moviesDBHelper, movieDetailData.get(i).getId());
            if (cursor == null || cursor.getCount() <= 0) {
                ContentValues contentValues = new ContentValues();

                contentValues.put(Dbcotract.TableInfo.Id, movie.getId());
                contentValues.put(Dbcotract.TableInfo.PosterPath, movie.getPosterPath());
                contentValues.put(Dbcotract.TableInfo.Adult, movie.getAdult());
                contentValues.put(Dbcotract.TableInfo.Favourite, "0");
                contentValues.put(Dbcotract.TableInfo.OriginalLanguage, movie.getOriginalLanguage());
                contentValues.put(Dbcotract.TableInfo.ReleaseDate, movie.getReleaseDate());
                contentValues.put(Dbcotract.TableInfo.OriginalTitle, movie.getOriginalTitle());
                contentValues.put(Dbcotract.TableInfo.Video, movie.getVideo());
                contentValues.put(Dbcotract.TableInfo.OverView, movie.getOverview());
                contentValues.put(Dbcotract.TableInfo.Popularity, movie.getPopularity());
                contentValues.put(Dbcotract.TableInfo.VoteAverage, movie.getVoteAverage());
                contentValues.put(Dbcotract.TableInfo.VoteCount, movie.getVoteCount());
                Uri uri = this.getContentResolver().insert(Dbcotract.TableInfo.CONTENT_URI, contentValues);
                if (uri != null) {
                    Log.d("insert uri:", "" + uri.toString());
                }
            }
            i++;

            if(cursor != null)
                cursor.close();

        } while (i < moviesDatas.size());


    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null&&(intent.getParcelableArrayListExtra("movies"))!=null ) {
            List<Movie> movies = intent.getParcelableArrayListExtra("movies");
            insertTodatabase(movies);
        }else {

         //   loadFavouriteMovies();
        }
        context = this;

    }
}
