package com.example.godaa.movieapplacation.helper;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by godaa on 29/04/2017.
 */
public class Dbcotract {
    public static final String AUTHORITY = "com.example.godaa.movieapplacation";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    public static final String PATH_MOVIES= "MovieTable";
    public Dbcotract() {
        super();
    }

    public static  class TableInfo implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String DatabaseName = "MovieApp";
        public static final String TableName = "MovieTable";
        public static final String Id = "id";
        public static final String OriginalTitle = "original_title";
        public static final String OriginalLanguage = "original_language";
        public static final String Title = "title";
        public static final String PosterPath = "poster_path";
        public static final String Popularity = "popularity";
        public static final String VoteCount = "vote_count";
        public static final String Video = "video";
        public static final String VoteAverage = "vote_average";
        public static final String Adult = "adult";
        public static final String OverView = "overview";
        public static final String ReleaseDate = "release_date";
        public static final String Favourite = "favourite";
    }
}
