package com.example.godaa.movieapplacation.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.godaa.movieapplacation.BuildConfig;
import com.example.godaa.movieapplacation.R;
import com.example.godaa.movieapplacation.adapter.ReviewsAdapter;
import com.example.godaa.movieapplacation.adapter.TrailorsAdapter;
import com.example.godaa.movieapplacation.helper.LocalDbHelper;
import com.example.godaa.movieapplacation.model.Movie;
import com.example.godaa.movieapplacation.model.Review;
import com.example.godaa.movieapplacation.model.ReviewsData;
import com.example.godaa.movieapplacation.model.Trailer;
import com.example.godaa.movieapplacation.model.TrailersData;
import com.example.godaa.movieapplacation.restApi.RestClient;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import it.sephiroth.android.library.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by godaa on 28/04/2017.
 */
public class DetailFragment extends Fragment {
    final  static String TAG=DetailFragment.class.getSimpleName();
    Movie movie;
    ReviewsData reviewsData;
    List<Review> reviewList=new ArrayList<>();
    LinearLayout footer;

    //RelativeLayout Header;
    View Header;
    ListView listViewTrailers;
    TrailersData trailersData;
    ArrayList<Trailer> trailerList ;
    LocalDbHelper localDbHelper;
    AlertDialog dialog;
    ListView listViewReviews;

    public DetailFragment() {

    }

    public DetailFragment(Movie movie) {
        this.movie=movie;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putParcelable("movie",moviedata);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            movie = savedInstanceState.getParcelable("movie");
            //if (movie != null)
             //   Loadgrid(moviesData.getResults());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialog = new SpotsDialog(getActivity());
        dialog.show();
        View view = inflater.inflate(R.layout.detail_fragment, container,false);

         Header = inflater.inflate(R.layout.view_header,listViewTrailers, false);
        footer = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.view_footer,listViewTrailers, false);

        listViewTrailers = (ListView) view.findViewById(R.id.list_detail_view);

        loadHeader();
        loadTAiler(movie.getId());
        loadfooter();
        dialog.dismiss();
        return view;
    }

    private void loadTAiler(Integer id) {
         final RestClient.ApiService apiService = RestClient.getClient(getContext()).create(RestClient.ApiService.class);
        apiService.getTrailers( id.toString(),BuildConfig.API_KEY).enqueue(new Callback<TrailersData>() {
            @Override
            public void onResponse(Call<TrailersData> call, Response<TrailersData> response) {
                trailersData=response.body();
                trailerList=trailersData.getResults();
                Log.i(TAG, "result is " + trailerList.toString());
                listViewTrailers.addHeaderView(Header);
                listViewTrailers.setAdapter(new TrailorsAdapter(getActivity(),trailerList));
            }

            @Override
            public void onFailure(Call<TrailersData> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
        /*String url = getActivity().getResources().getString(R.string.main_url) +
                "3/movie/"+movie.getId() + "/videos?api_key="  + BuildConfig.API_KEY;
        Log.i(TAG, url);
        MovieParser movieParser=new MovieParser(new OnExcute() {
            @Override
            public void Onsuccess(Object o) {
                JSONObject jsonObject = (JSONObject) o;
                JSONArray jsonArray;
                Gson gson=new Gson();
                Type type=new TypeToken<ArrayList<Trailer>>(){}.getType();

                try {
                    jsonArray = jsonObject.getJSONArray("results");
                    trailerList = gson.fromJson(jsonArray.toString(),  type);
                 //   Log.i(TAG,"odof "+ trailerList.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listViewTrailers.setAdapter(new TrailorsAdapter(getActivity(),trailerList));
                listViewTrailers.addHeaderView(Header);
            }

            @Override
            public void Onerror(VolleyError volleyError) {

            }
        }, getActivity());
        movieParser.getData(url);*/
    }

    private void loadfooter() {

        listViewReviews = (ListView) footer.findViewById(R.id.list_reviews);
        RestClient.ApiService apiService = RestClient.getClient(getContext()).create(RestClient.ApiService.class);
        apiService.getReviews(movie.getId().toString(),BuildConfig.API_KEY).enqueue(new Callback<ReviewsData>() {
            @Override
            public void onResponse(Call<ReviewsData> call, Response<ReviewsData> response) {
                 reviewsData=response.body();
                reviewList=reviewsData.getResults();
                Log.i(TAG, "review list" + reviewList.size());
                listViewReviews.setAdapter(new ReviewsAdapter(getActivity(),reviewList));
                if (reviewList.size()>0) {
                    listViewTrailers.addFooterView(footer);
                }
            }

            @Override
            public void onFailure(Call<ReviewsData> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });


    }

    private void loadHeader() {
        ImageView imageView = (ImageView)Header.findViewById(R.id.image_detail);
        TextView year = (TextView) Header.findViewById(R.id.year);
        TextView duration = (TextView) Header.findViewById(R.id.duration);
        TextView rate = (TextView) Header.findViewById(R.id.rate);
        TextView description = (TextView) Header.findViewById(R.id.description);
        TextView title = (TextView) Header.findViewById(R.id.title_movie);
        final Button button = (Button) Header.findViewById(R.id.favourite_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localDbHelper=new LocalDbHelper(getActivity().getApplicationContext());
                LocalDbHelper.set_Favourite(movie,localDbHelper);
                Toast.makeText(getContext(),"Successfully set this Movie to Favourite",Toast.LENGTH_LONG).show();
              button.setBackgroundColor(getActivity().getResources().getColor(R.color.fav_bg));
            }
        });
        String Image_url = getActivity().getResources().getString(R.string.image_url) + movie.getPosterPath();
        Picasso.with(getContext())
                .load(Image_url)
                .into(imageView);
        year.setText(movie.getReleaseDate().split("-")[0]);
        title.setText(movie.getTitle());
        rate.setText(movie.getVoteAverage().toString()+" / 10");
        description.setText(movie.getOverview());
    }
}
