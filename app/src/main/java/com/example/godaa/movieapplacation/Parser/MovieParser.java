package com.example.godaa.movieapplacation.Parser;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.godaa.movieapplacation.Interface.OnExcute;

import org.json.JSONObject;

import retrofit2.http.Url;

/**
 * Created by godaa on 03/05/2017.
 */
public class MovieParser {
    Context context;
    OnExcute onExcute;
    RequestQueue requestQueue;
    public MovieParser() {
        super();
    }

    public MovieParser(OnExcute onExcute, Context context) {
        this.onExcute=onExcute;
        this.context=context;
    }

    public void getData(String  url) {
        requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest  jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onExcute.Onsuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onExcute.Onerror(error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
