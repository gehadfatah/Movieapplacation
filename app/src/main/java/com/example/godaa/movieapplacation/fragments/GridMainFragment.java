package com.example.godaa.movieapplacation.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

import com.example.godaa.movieapplacation.BuildConfig;
import com.example.godaa.movieapplacation.Interface.Callbackinterface;
import com.example.godaa.movieapplacation.Interface.ServiceCalback;
import com.example.godaa.movieapplacation.R;
import com.example.godaa.movieapplacation.activity.MainActivity;
import com.example.godaa.movieapplacation.adapter.GridLayoutAdapter;
import com.example.godaa.movieapplacation.helper.Dbcotract;
import com.example.godaa.movieapplacation.helper.MovieService;
import com.example.godaa.movieapplacation.model.Movie;
import com.example.godaa.movieapplacation.model.MoviesData;
import com.example.godaa.movieapplacation.restApi.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by godaa on 28/04/2017.
 */
public class GridMainFragment extends Fragment implements Callbackinterface, ServiceCalback {
    RecyclerView recyclerView;
    Callbackinterface mCallbackinterface;
    public static MoviesData moviesData = null;
    ArrayList<Movie> movies;
    ProgressDialog progressDialog;
    boolean mTwopane;
    static Context contextApp = null;
    static String selectedCategory;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbackinterface = (MainActivity) context;
    }

    public static GridMainFragment newInstance(Context context, MoviesData moviesData, String SelectedCategory) {
        GridMainFragment mGridMainFragment = new GridMainFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(context.getResources().getString(R.string.movies), moviesData);
        //bundle.putString(context.getResources().getString(R.string.selected_category), selected_category);
        mGridMainFragment.setArguments(bundle);
        contextApp = context;
        selectedCategory = SelectedCategory;
        return mGridMainFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putParcelable(getResources().getString(R.string.movies), moviesData);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
     /*   if (savedInstanceState != null && savedInstanceState.getParcelable(getResources().getString(R.string.movies)) != null) {
            moviesData = savedInstanceState.getParcelable(getResources().getString(R.string.movies));
            if (moviesData != null) Loadgrid(moviesData.getResults());
        }*/
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setRetainInstance(true);
    }

    SharedPreferences sharedPreferences;
    String previous_selected_category;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_view, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog = new ProgressDialog(getContext());
        mTwopane = getContext().getResources().getBoolean(R.bool.mTwoBane);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(contextApp);
        previous_selected_category = sharedPreferences.getString(getString(R.string.pref_unit_key),
                getString(R.string.pref_units_popular));
        //if (savedInstanceState == null && getArguments().getParcelable(getResources().getString(R.string.movies))==null)
        if (getArguments().getParcelable(getResources().getString(R.string.movies)) == null || !selectedCategory.equals(previous_selected_category)) {
            getdata();
        } else {
            moviesData = getArguments().getParcelable(getResources().getString(R.string.movies));
            Loadgrid(moviesData.getResults());

        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getdata(/*final LocalDbHelper localDbHelper*/) {
       /* if (progressDialog != null)
            progressDialog.show();*/
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(contextApp);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (selectedCategory) {

            case "popular":
                editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_popular));
                break;
            case "top_rated":
                editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_top_rated));

                break;
            case "favourite":
                editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_favourited));
                //current_category = getString(R.string.pref_units_favourited);
                break;
        }
        editor.apply();
        if (!selectedCategory.equals(getString(R.string.pref_units_favourited))) {

            RestClient.ApiService apiService = RestClient.getClient(getContext()).
                    create(RestClient.ApiService.class);
            apiService.getMovies(selectedCategory, BuildConfig.API_KEY).enqueue(new Callback<MoviesData>() {
                @Override
                public void onResponse(Call<MoviesData> call, Response<MoviesData> response) {
                    moviesData = response.body();
                    movies = (ArrayList<Movie>) moviesData.getResults();

                    Log.i("return ", "from database");
                    Loadgrid(movies);
                    if (getActivity() != null) {
                        // insertTodatabase(movies);
                        startServiceMovie();
                    }
                }

                @Override
                public void onFailure(Call<MoviesData> call, Throwable t) {
                    Log.e("Gridmainfragmrent  ", t.getMessage());

                }
            });

        } else {
            loadFavouriteMovies();
            // startServiceMovie();

        }
    }

    private void startServiceMovie() {
        Intent intent = new Intent(getContext(), MovieService.class);
        intent.putParcelableArrayListExtra("movies", movies);
        // intent.putExtra("dd", GridMainFragment.this);
        getActivity().startService(intent);
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
