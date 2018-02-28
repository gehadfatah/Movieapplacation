package com.example.godaa.movieapplacation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by godaa on 29/04/2017.
 */
public class Movie implements Parcelable{
    public Movie() {
        super();
    }


    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("adult")
    @Expose
    private String adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    protected Movie(Parcel in) {
        posterPath = in.readString();
        adult = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        id = Integer.parseInt(in.readString());
        originalTitle = in.readString();
        originalLanguage = in.readString();
        title = in.readString();
        backdropPath = in.readString();
        popularity =Double.parseDouble(in.readString());
        voteCount = Integer.parseInt(in.readString());
        video = in.readString();
        voteAverage =Double.parseDouble(in.readString()) ;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(adult);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(String.valueOf(id));
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeString(title);
        dest.writeString(backdropPath);
        dest.writeString(String.valueOf(popularity));
        dest.writeString(String.valueOf(voteCount));
        dest.writeString(video);
        dest.writeString(String.valueOf(voteAverage));
    }
}


