package com.example.godaa.movieapplacation.helper;

import android.util.Log;

import com.example.godaa.movieapplacation.MovieApp;
import com.example.godaa.movieapplacation.model.Movie;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by godaa on 06/11/2017.
 */

public class apphelperdatabase {

    private static DaoSession mDaoSession;
     MovieTableDao movieTableDao;
    public apphelperdatabase() {
       // mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
        mDaoSession = MovieApp.getDaoSession();
        movieTableDao=mDaoSession.getMovieTableDao();
    }

    public  int get_if_exist(/*LocalDbHelper dbHelper,*/ long id) {
       /* SQLiteDatabase database=dbHelper.getReadableDatabase();
        Cursor cursor=  database.rawQuery("select * from " + Dbcotract.TableInfo.TableName + " where " + Dbcotract.TableInfo.Id
                + " = " + id, null);
        return cursor;*/
// fetch users with Joe as a first name born in 1970
/*        Query<User> query = userDao.queryBuilder().where(
                Properties.FirstName.eq("Joe"), Properties.YearOfBirth.eq(1970)
        ).build();
        List<User> joesOf1970 = query.list();*/
        QueryBuilder<MovieTable> movieTable = movieTableDao.queryBuilder().where(MovieTableDao.Properties.Id.eq(id));
        List<MovieTable> movieTables = movieTable.list();
        if (movieTables.size() > 0) {
            return 1;
        } else return 0;

    }

    public  void insertTodatabase(List<Movie> moviesDatas) {
       /* SQLiteDatabase sqLiteDatabase=localDbHelper.getWritableDatabase();
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
        sqLiteDatabase.close();*/
        int i = 0;

        do {
         //   noteDao.deleteByKey(id);

            Movie movie = moviesDatas.get(i);/*String id, String posterPath, String adult, String overview,
                    String releaseDate, String originalTitle, String originalLanguage,
                    String title, String popularity, String voteCount, String video,
                    String voteAverage, String Favourite*/
          /*  if (get_if_exist(movie.getId()) != 0) {
                Log.i("in insert data", "this is already exist id " + movie.getId().toString());
                i++;
                continue;
            }*/
            MovieTable movieTable = new MovieTable(movie.getId() + "", movie.getPosterPath(), movie.getAdult(), movie.getOverview(), movie.getReleaseDate(), movie.getOriginalTitle(), movie.getOriginalLanguage(), movie.getTitle(), movie.getPopularity() + "", movie.getVoteCount() + "", movie.getVideo(), movie.getVoteAverage() + "", "0");
            if (get_if_exist(movie.getId()) == 0) {

                movieTableDao.insert(movieTable);
            }
            i++;
        } while (i < moviesDatas.size());

    }

    public  void set_Favourite(Movie movie) {
       /* SQLiteDatabase sqLiteDatabase=localDbHelper.getWritableDatabase();
        String sql = " update " + Dbcotract.TableInfo.TableName + " set " + Dbcotract.TableInfo.Favourite +
                " = 1 " + " where " + Dbcotract.TableInfo.Id + " = " + movie.getId();
        sqLiteDatabase.execSQL(sql);*/
        MovieTable movieTable = new MovieTable(movie.getId() + "", movie.getPosterPath(), movie.getAdult(), movie.getOverview(), movie.getReleaseDate(), movie.getOriginalTitle(), movie.getOriginalLanguage(), movie.getTitle(), movie.getPopularity() + "", movie.getVoteCount() + "", movie.getVideo(), movie.getVoteAverage() + "", "1");
        movieTableDao.update(movieTable);
    }

    public  List<Movie> get_Favorite_movies() {
       /* SQLiteDatabase sqLiteDatabase=localDbHelper.getReadableDatabase();
        String sql = "select * from " + Dbcotract.TableInfo.TableName + " where " +
                Dbcotract.TableInfo.Favourite + " = 1";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        return cursor;*/
        List<MovieTable> movieTables = movieTableDao.loadAll();
        List<Movie> movies = new ArrayList<>();
        for (MovieTable movieTable : movieTables) {
            Movie movie = new Movie();
            if (movieTable.getFavourite().equals("0")) continue;
            movie.setId(Integer.parseInt(movieTable.getId()));
            movie.setOriginalTitle(movieTable.getOriginalTitle());
            movie.setOriginalLanguage(movieTable.getOriginalLanguage());
            movie.setTitle(movieTable.getTitle());
            movie.setPosterPath(movieTable.getPosterPath());
            movie.setPopularity(Double.parseDouble(movieTable.getPopularity()));
            movie.setVoteCount(Integer.parseInt(movieTable.getVoteCount()));
            movie.setVideo(movieTable.getVideo());
            movie.setVoteAverage(Double.parseDouble(movieTable.getVoteAverage()));
            movie.setAdult(movieTable.getAdult());
            movie.setOverview(movieTable.getOverview());
            movie.setReleaseDate(movieTable.getReleaseDate());
            movies.add(movie);
        }
        return movies;
    }

}
