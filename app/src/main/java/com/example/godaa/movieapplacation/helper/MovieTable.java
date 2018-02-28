package com.example.godaa.movieapplacation.helper;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by godaa on 06/11/2017.
 */
@Entity(nameInDb = "MovieTable")

public class MovieTable {
    @Id
    private String id;
    @Property(nameInDb = "poster_path")
    private String posterPath;
    @Property(nameInDb = "adult")

    private String adult;
    @Property(nameInDb = "overview")

    private String overview;
    @Property(nameInDb = "release_date")

    private String releaseDate;

    @Property(nameInDb = "original_title")

    private String originalTitle;
    @Property(nameInDb = "original_language")

    private String originalLanguage;
    @Property(nameInDb = "title")

    private String title;

    @Property(nameInDb = "popularity")

    private String popularity;
    @Property(nameInDb = "vote_count")

    private String voteCount;
    @Property(nameInDb = "video")

    private String video;
    @Property(nameInDb = "vote_average")

    private String voteAverage;

    @Property(nameInDb = "favourite")

    private String Favourite;

    @Generated(hash = 1447157129)
    public MovieTable(String id, String posterPath, String adult, String overview,
                      String releaseDate, String originalTitle, String originalLanguage,
                      String title, String popularity, String voteCount, String video,
                      String voteAverage, String Favourite) {
        this.id = id;
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.Favourite = Favourite;
    }

    @Generated(hash = 2134125390)
    public MovieTable() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getAdult() {
        return this.adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return this.originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return this.popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVoteCount() {
        return this.voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getVideo() {
        return this.video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return this.voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getFavourite() {
        return this.Favourite;
    }

    public void setFavourite(String Favourite) {
        this.Favourite = Favourite;
    }


}
