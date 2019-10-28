package com.example.android.popularmovie.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.popularmovie.Model.ReviewModel;
import com.example.android.moviapp2.R;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    Context context;
    ArrayList<ReviewModel> reviewList;

    public RecycleViewAdapter(Context c, ArrayList<ReviewModel> rlist) {
        this.context = c;
        this.reviewList = rlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        viewHolder.authorTV.setText(reviewList.get(i).getAuthor());
        viewHolder.contentTv.setText(reviewList.get(i).getContent());
        viewHolder.showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.showMore.setVisibility(View.GONE);
                viewHolder.contentTv.setMaxLines(Integer.MAX_VALUE);
                viewHolder.showLess.setVisibility(View.VISIBLE);
            }

        });

        viewHolder.showLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.showLess.setVisibility(View.GONE);
                viewHolder.contentTv.setMaxLines(5);
                viewHolder.showMore.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView authorTV;
        TextView contentTv;
        Button showMore;
        Button showLess;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            authorTV = itemView.findViewById(R.id.author_tv);
            contentTv = itemView.findViewById(R.id.content_tv);
            showMore = itemView.findViewById(R.id.show_more);
            showLess = itemView.findViewById(R.id.show_less);

        }
    }
}
