package com.example.godaa.movieapplacation.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.godaa.movieapplacation.model.Movie;
import com.example.godaa.movieapplacation.model.MoviesData;

import java.util.List;

/**
 * Created by godaa on 29/04/2017.
 */
public class LocalDbHelper extends SQLiteOpenHelper {
    public static final int database_version=1;
    String coma = " ,";
    String TextType = " TEXT";
    //LocalDbHelper localDbHelper;
    public LocalDbHelper(Context context) {
        super(context, Dbcotract.TableInfo.DatabaseName, null, database_version);
    }
       public String sqliteTable = "CREATE  TABLE " + Dbcotract.TableInfo.TableName + "(" +
            Dbcotract.TableInfo.Id + TextType+" PRIMARY KEY " +coma+
           Dbcotract.TableInfo.OriginalTitle + TextType + coma +
           Dbcotract.TableInfo.OriginalLanguage + TextType + coma +
               Dbcotract.TableInfo.Title +TextType +coma+
               Dbcotract.TableInfo.PosterPath + TextType + coma +
           Dbcotract.TableInfo.Popularity + TextType + coma +
           Dbcotract.TableInfo.VoteCount + TextType + coma +
           Dbcotract.TableInfo.Video + TextType + coma +
           Dbcotract.TableInfo.VoteAverage + TextType + coma +
           Dbcotract.TableInfo.Adult + TextType + coma +
           Dbcotract.TableInfo.OverView + TextType + coma +
               Dbcotract.TableInfo.ReleaseDate+TextType+coma+
           Dbcotract.TableInfo.Favourite + TextType +
               ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqliteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists" + Dbcotract.TableInfo.TableName);
        db.execSQL(sqliteTable);
    }

    public static Cursor get_if_exist(LocalDbHelper dbHelper, long id) {
        SQLiteDatabase database=dbHelper.getReadableDatabase();
      Cursor cursor=  database.rawQuery("select * from " + Dbcotract.TableInfo.TableName + " where " + Dbcotract.TableInfo.Id
                + " = " + id, null);
        return cursor;
    }

    public static void insertTodatabase(List<Movie> moviesDatas, LocalDbHelper localDbHelper) {
        SQLiteDatabase sqLiteDatabase=localDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int i=0;
        do {

            Movie movie = moviesDatas.get(i);
            if (LocalDbHelper.get_if_exist(localDbHelper, movie.getId()).moveToFirst()) {
                Log.i("in insert data", "this is already exist id " + movie.getId().toString());
                i++;
               continue;
            }
            contentValues.put(Dbcotract.TableInfo.Id,movie.getId());
            contentValues.put(Dbcotract.TableInfo.PosterPath,movie.getPosterPath());
            contentValues.put(Dbcotract.TableInfo.Adult,movie.getAdult());
            contentValues.put(Dbcotract.TableInfo.Favourite,"0");
            contentValues.put(Dbcotract.TableInfo. OriginalLanguage,movie.getOriginalLanguage());
            contentValues.put(Dbcotract.TableInfo.ReleaseDate,movie.getReleaseDate());
            contentValues.put(Dbcotract.TableInfo.OriginalTitle,movie.getOriginalTitle());
            contentValues.put(Dbcotract.TableInfo.Video,movie.getVideo());
            contentValues.put(Dbcotract.TableInfo.OverView,movie.getOverview());
            contentValues.put(Dbcotract.TableInfo.Popularity,movie.getPopularity());
            contentValues.put(Dbcotract.TableInfo.VoteAverage,movie.getVoteAverage());
            contentValues.put(Dbcotract.TableInfo.VoteCount,movie.getVoteCount());
            sqLiteDatabase.insert(Dbcotract.TableInfo.TableName, null, contentValues);
             i++;
        }while (i<moviesDatas.size());
        sqLiteDatabase.close();
    }

    public static void set_Favourite(Movie movie,LocalDbHelper localDbHelper) {
         SQLiteDatabase sqLiteDatabase=localDbHelper.getWritableDatabase();
        String sql = " update " + Dbcotract.TableInfo.TableName + " set " + Dbcotract.TableInfo.Favourite +
                " = 1 " + " where " + Dbcotract.TableInfo.Id + " = " + movie.getId();
        sqLiteDatabase.execSQL(sql);
    }

    public static Cursor get_Favorite_movies(LocalDbHelper localDbHelper) {
        SQLiteDatabase sqLiteDatabase=localDbHelper.getReadableDatabase();
        String sql = "select * from " + Dbcotract.TableInfo.TableName + " where " +
                Dbcotract.TableInfo.Favourite + " = 1";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
                return cursor;
    }

}
