package com.example.godaa.movieapplacation;

import android.app.Application;

import com.example.godaa.movieapplacation.helper.DaoMaster;
import com.example.godaa.movieapplacation.helper.DaoSession;
import com.example.godaa.movieapplacation.helper.DbOpenHelper;
import com.example.godaa.movieapplacation.helper.MovieTable;
import com.example.godaa.movieapplacation.helper.apphelperdatabase;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by godaa on 06/11/2017.
 */

public class MovieApp extends Application {
    private static DaoSession mDaoSession;
    static public apphelperdatabase mapphelperdatabase;
    public MovieApp() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDaoSession =
                new DaoMaster(new DbOpenHelper(this, "MovieApp").getWritableDb()).newSession();
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
       // mapphelperdatabase=new apphelperdatabase(new DbOpenHelper(getApplicationContext(),));
        // USER CREATION FOR DEMO PURPOSE
        if(mDaoSession.getMovieTableDao().loadAll().size() == 0){
          //  mDaoSession.getMovieTableDao().insert(new MovieTable("1",));
        }
    }
    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
    public static apphelperdatabase getapphelperdatabase() {
        if (mapphelperdatabase==null) {
            mapphelperdatabase=new apphelperdatabase();
        }
        return mapphelperdatabase;
    }
}
