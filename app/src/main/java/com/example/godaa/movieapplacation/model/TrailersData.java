package com.example.godaa.movieapplacation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by godaa on 30/04/2017.
 */
public class TrailersData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private ArrayList<Trailer> results ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }

    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }

}
