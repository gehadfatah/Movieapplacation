package com.example.godaa.movieapplacation.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.godaa.movieapplacation.Interface.Callbackinterface;
import com.example.godaa.movieapplacation.R;
import com.example.godaa.movieapplacation.model.Movie;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by godaa on 29/04/2017.
 */
public class GridLayoutAdapter extends  RecyclerView.Adapter<GridLayoutAdapter.ViewHolder> {

    List<Movie> movies;
    Context context;
    Callbackinterface callbackinterface;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_image_item, parent, false);

        return (new ViewHolder(view));
    }

    public GridLayoutAdapter(List<Movie> movies, Context context,Callbackinterface callbackinterface) {
        Log.i("gridlayoutadapter", " im here");
        this.movies=movies;
        this.context=context;//for mainactivity
        this.callbackinterface=callbackinterface;//for callbackinteface in gridmainfragment not in mainactivity
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        String image_url = context.getResources().getString(R.string.image_url)+
                movie.getPosterPath();
        Log.i("gridlayoutadapter", " onBindViewHolder here and imageurl ia "+image_url);

        Picasso.with(context)
                .load(image_url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movies != null) {
                    callbackinterface.OnItemSelcted(movie);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }
}
