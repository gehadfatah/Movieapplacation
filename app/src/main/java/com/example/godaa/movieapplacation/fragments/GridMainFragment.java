package com.example.godaa.movieapplacation.fragments;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.godaa.movieapplacation.BuildConfig;
import com.example.godaa.movieapplacation.Interface.Callbackinterface;
import com.example.godaa.movieapplacation.Interface.OnExcute;
import com.example.godaa.movieapplacation.Interface.ServiceCalback;
import com.example.godaa.movieapplacation.R;
import com.example.godaa.movieapplacation.activity.MainActivity;
import com.example.godaa.movieapplacation.adapter.GridLayoutAdapter;
import com.example.godaa.movieapplacation.helper.Dbcotract;
import com.example.godaa.movieapplacation.helper.MovieService;
import com.example.godaa.movieapplacation.helper.favService;
import com.example.godaa.movieapplacation.model.Movie;
import com.example.godaa.movieapplacation.model.MoviesData;
import com.example.godaa.movieapplacation.restApi.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by godaa on 28/04/2017.
 */
public class GridMainFragment extends Fragment implements Callbackinterface, ServiceCalback {
    //String TaG=get
    RecyclerView recyclerView;
    Callbackinterface mCallbackinterface;
    MoviesData moviesData;
    ArrayList<Movie> movies;
    ProgressDialog progressDialog;
    Context context = getContext();
    boolean mTwopane;
    //Cursor cursor = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbackinterface = (MainActivity) context;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("moviesdata", moviesData);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            moviesData = savedInstanceState.getParcelable("moviesData");
            if (moviesData != null)
                Loadgrid(moviesData.getResults());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_view, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // LocalDbHelper localDbHelper = new LocalDbHelper(getContext());
        progressDialog = new ProgressDialog(getContext());
        mTwopane = getContext().getResources().getBoolean(R.bool.mTwoBane);
        getdata(/*localDbHelper*/);
        return view;

    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    public void Loadgrid(List<Movie> movies) {
        recyclerView.setAdapter(new GridLayoutAdapter(movies, getActivity(), this));

        Log.i("return", "from gridlaoutadapterr");
        //just for first load in two pane
        if (mTwopane) {
            mCallbackinterface.OnItemSelcted(movies.get(0));
        }


    }

    public void getdata(/*final LocalDbHelper localDbHelper*/) {
       /* if (progressDialog != null)
            progressDialog.show();*/
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String selected_category = sharedPreferences.getString(getString(R.string.pref_unit_key),
                getString(R.string.pref_units_popular));
        if (!selected_category.equals(getString(R.string.pref_units_favourited))) {

            RestClient.ApiService apiService = RestClient.getClient(getContext()).
                    create(RestClient.ApiService.class);
            apiService.getMovies(selected_category, BuildConfig.API_KEY).enqueue(new Callback<MoviesData>() {
                @Override
                public void onResponse(Call<MoviesData> call, Response<MoviesData> response) {
                    moviesData = response.body();
                    movies = (ArrayList<Movie>) moviesData.getResults();
                    // Log.i("gridfragment",response.body().toString());
                    // movies=response.body().getResults();
                    // LocalDbHelper.insertTodatabase(movies, localDbHelper);
                    // MovieApp.getapphelperdatabase().insertTodatabase(movies);

                    Log.i("return ", "from database");
                    Loadgrid(movies);
                    if (getActivity() != null) {
                        // insertTodatabase(movies);
                        startServiceMovie();
                    }

               /*     if (progressDialog != null)
                        progressDialog.dismiss();*/

                }

                @Override
                public void onFailure(Call<MoviesData> call, Throwable t) {
                    Log.e("Gridmainfragmrent  ", t.getMessage());
                 /*   if (progressDialog != null)
                        progressDialog.dismiss();*/

                }
            });

        } else {
            //loadFavouriteMovies(localDbHelper);
            loadFavouriteMovies();
            // Intent intent = new Intent(getContext(), favService.class);
            //getActivity().startService(intent);
            //  Loadgrid(moviesfav);
            // movieService.loadFavouriteMovies();
        }
    }

    private void startServiceMovie() {
        Intent intent = new Intent(getContext(), MovieService.class);
        intent.putParcelableArrayListExtra("movies", movies);
        getActivity().startService(intent);
    }

    public void insertTodatabase(final List<Movie> moviesDatas) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i = 0;

                do {
                    Movie movie = moviesDatas.get(i);
                    Cursor cursor = getActivity().getContentResolver().query(Dbcotract.TableInfo.CONTENT_URI,
                            new String[]{Dbcotract.TableInfo.Id}, Dbcotract.TableInfo.Id + "=?", new String[]{moviesDatas.get(i).getId().toString()}, Dbcotract.TableInfo.Id);
                    // Cursor cursor = moviesDBHelper.get_if_exist(moviesDBHelper, movieDetailData.get(i).getId());
                   /* Cursor cursor = getActivity().getContentResolver().query(Uri.withAppendedPath(
                            Dbcotract.TableInfo.CONTENT_URI, String.valueOf(movie.getId())),
                            null, null *//*MoviesContract.MoviesEntry.Id + " LIKE ?"*//**//*+movieDetailData.getId()*//*, new String[]{moviesDatas.get(i).getId().toString()}, Dbcotract.TableInfo.Id);
*/
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
                        Uri uri = getActivity().getContentResolver().insert(Dbcotract.TableInfo.CONTENT_URI, contentValues);
                        if (uri != null) {
                            Log.d("insert uri:", "" + uri.toString());
                        }


                    }
                    i++;
                    if (cursor != null)
                        cursor.close();


                } while (i < moviesDatas.size());


            }
        };
        handler.post(runnable);


    }

    public void loadFavouriteMovies() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Cursor cursor = getActivity().getContentResolver().query(Dbcotract.TableInfo.CONTENT_URI,
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
                    //servicecallback.loadUi(movies);
                    Loadgrid(movies);

                }
            }
        };
        handler.postDelayed(runnable, 0);

    }

    /*  private void loadFavouriteMovies() {
        movies = MovieApp.getapphelperdatabase().get_Favorite_movies();
        Loadgrid(movies);

    }*/


    @Override
    public void OnItemSelcted(Movie movie) {
        mCallbackinterface.OnItemSelcted(movie);
    }


    List<Movie> moviesfav = new ArrayList<>();

    @Override
    public void loadUi(List<Movie> movies) {
        moviesfav = movies;
        Loadgrid(movies);

    }
}
