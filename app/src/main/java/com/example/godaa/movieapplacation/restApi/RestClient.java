package com.example.godaa.movieapplacation.restApi;

import android.content.Context;

import com.example.godaa.movieapplacation.R;
import com.example.godaa.movieapplacation.model.MoviesData;
import com.example.godaa.movieapplacation.model.ReviewsData;
import com.example.godaa.movieapplacation.model.TrailersData;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by godaa on 29/04/2017.
 */
//retrieve data using retrofit
public class RestClient {
    public static Retrofit getClient(Context context) {
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().
                addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit=new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getResources().getString(R.string.main_url))
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public interface ApiService{
        @GET("3/movie/{category}")
        Call<MoviesData> getMovies(@Path("category") String category,@Query("api_key") String api_key);

        @GET("3/movie/{id}/videos")
        Call<TrailersData> getTrailers( @Path("id") String id,@Query("api_key") String api_key);

        @GET("3/movie/{id}/reviews")
        Call<ReviewsData> getReviews( @Path("id") String id,@Query("api_key") String api_key);

    }


    }

