package com.example.godaa.movieapplacation.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.godaa.movieapplacation.Interface.Callbackinterface;
import com.example.godaa.movieapplacation.R;
import com.example.godaa.movieapplacation.model.Movie;
import com.example.godaa.movieapplacation.model.Trailer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Pc1 on 28/02/2018.
 */

public class TrailorsRAdapter extends  RecyclerView.Adapter<TrailorsRAdapter.ViewHolder> {
    ArrayList<Trailer> trailersData;
    Context context;

    public TrailorsRAdapter(Context context, ArrayList<Trailer> trailersData) {
        Log.i("TrailorsAdapter  is", trailersData.toString());
        this.trailersData = trailersData;
        this.context = context;
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return trailersData.size();
    }
    @Override
    public TrailorsRAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_trailer_item, parent, false);

        return (new TrailorsRAdapter.ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(TrailorsRAdapter.ViewHolder holder, int position) {

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
                    .into(holder.filmImage);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String url = context.getResources().getString(R.string.youtube)
                            + trailer.getKey();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);

                    }

            }
        });

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView filmImage;

        public ViewHolder(View view) {
            super(view);
            filmImage = view.findViewById(R.id.img_thumnail);

        }
    }
}
