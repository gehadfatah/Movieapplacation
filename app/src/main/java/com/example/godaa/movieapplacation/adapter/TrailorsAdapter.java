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
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.godaa.movieapplacation.R;
import com.example.godaa.movieapplacation.model.Trailer;
import com.example.godaa.movieapplacation.model.TrailersData;

import java.util.ArrayList;
import java.util.List;

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

    public TrailorsAdapter(Context context,ArrayList<Trailer> trailersData) {
        Log.i("TrailorsAdapter  is", trailersData.toString());
        this.trailersData=trailersData;
        this.context=context;
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
         view=convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_trailer_item, parent,false);
             viewHoldertrailer = new ViewHoldertrailer(view);
            view.setTag(viewHoldertrailer);
        }else {
            viewHoldertrailer=(ViewHoldertrailer) view.getTag();
        }
        final Trailer trailer = trailersData.get(position);

       viewHoldertrailer.name.setText(trailer.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String url=context.getResources().getString(R.string.youtube)
                    +trailer.getKey();
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


    private class ViewHoldertrailer {
        TextView name;
        public ViewHoldertrailer(View view) {
            super();
            name = (TextView) view.findViewById(R.id.trailer_text);

        }
    }
}
