package com.example.godaa.movieapplacation.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.godaa.movieapplacation.R;
import com.example.godaa.movieapplacation.model.Trailer;
import com.example.godaa.movieapplacation.model.TrailersData;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by godaa on 30/04/2017.
 */
public class TrailorsAdapter extends BaseAdapter {
    ArrayList<Trailer> trailersData;
    ViewHoldertrailer viewHoldertrailer;
    Context context;
    View view;

    public TrailorsAdapter() {
        super();
    }

    public TrailorsAdapter(Context context, ArrayList<Trailer> trailersData) {
        Log.i("TrailorsAdapter  is", trailersData.toString());
        this.trailersData = trailersData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return trailersData.size();
    }

    @Override
    public Object getItem(int position) {
        return trailersData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_trailer_item, parent, false);
            viewHoldertrailer = new ViewHoldertrailer(view);
            view.setTag(viewHoldertrailer);
        } else {
            viewHoldertrailer = (ViewHoldertrailer) view.getTag();
        }
        final Trailer trailer = trailersData.get(position);

        String videoId;

        try {
            videoId = extractYoutubeId(context.getResources().getString(R.string.youtube)
                    + trailer.getKey());

            String img_url = "http://img.youtube.com/vi/" + videoId + "/0.jpg"; // this is link which will give u thumnail image of that video
            // picasso jar file download image for u and set image in imagview
            Picasso.with(context)
                    .load(img_url)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(viewHoldertrailer.filmImage);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = context.getResources().getString(R.string.youtube)
                            + trailer.getKey();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    parent.getContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No application can handle this request.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    public String extractYoutubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }


    private class ViewHoldertrailer {
        ImageView filmImage;

        public ViewHoldertrailer(View view) {
            super();
            filmImage = view.findViewById(R.id.img_thumnail);

        }
    }
}
