package com.example.godaa.movieapplacation.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.godaa.movieapplacation.R;
import com.example.godaa.movieapplacation.model.Review;
import com.example.godaa.movieapplacation.model.ReviewsData;

import java.util.List;

/**
 * Created by godaa on 30/04/2017.
 */
public class ReviewsAdapter extends BaseAdapter {
    List<Review> reviewsData;
    View view;
    Context context;
    public ReviewsAdapter(Context context,List<Review> results) {
        Log.i("in reviewsadapter", results.toString());

        this.reviewsData=results;
        this.context=context;
    }

    @Override
    public int getCount() {
        return reviewsData.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
       view =convertView;
        ViewHolderReviews viewHolderReviews;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_review_item, parent, false);
             viewHolderReviews = new ViewHolderReviews(view);
            view.setTag(viewHolderReviews);
        }else {
            viewHolderReviews = (ViewHolderReviews) view.getTag();
        }
        final Review review = reviewsData.get(position);
        viewHolderReviews.author.setText(review.getAuthor());
        viewHolderReviews.content.setText(review.getContent());
        String urll = reviewsData.get(position).getUrl();
        final SpannableString content = new SpannableString(urll);
        content.setSpan(new UnderlineSpan(), 0, urll.length(), 0);
        viewHolderReviews.url.setText(" Link Review : "+content);
        //viewHolderReviews.url.setText(review.getUrl());
        viewHolderReviews.url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String url = review.getUrl();
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No application can handle this request.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private class ViewHolderReviews {
        TextView author,content,url;
        public ViewHolderReviews(View view) {
            author = (TextView) view.findViewById(R.id.author);
            content = (TextView) view.findViewById(R.id.content);
            url = (TextView) view.findViewById(R.id.url_review);
        }
    }
}
