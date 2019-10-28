package com.example.android.popularmovie;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.popularmovie.Adapter.RecycleViewAdapter;
import com.example.android.popularmovie.Adapter.TrailersAdapter;
import com.example.android.popularmovie.Data.MoviesConverter;
import com.example.android.popularmovie.Model.MovieViewModel;
import com.example.android.popularmovie.Model.ReviewModel;
import com.example.android.moviapp2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetail extends AppCompatActivity {
    int id;
    TextView Title;
    TextView voteAvg;
    TextView OverView;
    TextView reviewTV;
    TextView trailersTV;
    TextView releaseDate;
    ImageView moviePoster;
    ToggleButton toggleButton;
    TextView reviewsPlaceholder;
    MoviesConverter moviesConverter;
    private MovieViewModel movieViewModel;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setContentView(R.layout.activity_detail);
        String imagBaseUrl = "http://image.tmdb.org/t/p/w185/";
        Title = findViewById(R.id.title_tv);
        voteAvg = findViewById(R.id.vote_avarage_tv);
        releaseDate = findViewById(R.id.release_date_tv);
        moviePoster = findViewById(R.id.poster_iv);
        OverView = findViewById(R.id.over_view_tv);
        reviewTV = findViewById(R.id.reviews);
        reviewsPlaceholder = findViewById(R.id.reviewsPlaceHolder);
        trailersTV = findViewById(R.id.trailers);
        Intent detailIntent = getIntent();
        if (detailIntent.getExtras() != null) {
            Bundle detailBundel = detailIntent.getExtras();
            String title = detailBundel.getString("Title");
            String posterPath = detailBundel.getString("PosterPath");
            double vot_avg = detailBundel.getDouble("VoteAvg");
            String overView =detailBundel.getString("Overview");
            Title.setText(title);
            String released_date =detailBundel.getString("ReleaseDate");
            voteAvg.setText("Rate :" +vot_avg);
            releaseDate.setText(released_date);
            OverView.setText(overView);
            id = detailBundel.getInt("Id");
            moviesConverter = new MoviesConverter(id,vot_avg,posterPath,title,overView,released_date);
            if (detailIntent.hasExtra("reviewsList")) {
                ArrayList<ReviewModel> reviewList = detailIntent.getParcelableArrayListExtra("reviewsList");
                if (reviewList.size() == 0) {
                    reviewsPlaceholder.setVisibility(View.VISIBLE);
                    reviewsPlaceholder.setText("There are no reviews for this movie");
                } else {
                    initRecycleView(reviewList);
                    for (int i = 0; i < reviewList.size(); i++) {
                    }
                }
            }
            if (detailIntent.hasExtra("trailersList")) {
                ArrayList<String> trailersList = detailIntent.getStringArrayListExtra("trailersList");
                if (trailersList.size() != 0) {
                    for (int i = 0; i < trailersList.size(); i++) {
                        initTrailersRecycleView(trailersList);
                    }
                } else {
                    trailersTV.setVisibility(View.GONE);
                }
            }
            Picasso.with(this).load(imagBaseUrl + detailBundel.getString("PosterPath")).into(moviePoster);
        }
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        toggleButton = findViewById(R.id.favourite_button_fb);
        observe(movieViewModel, id);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((ToggleButton) v).isChecked();
                if (checked) {
                    Log.e("MovieDetail", "insert id " + moviesConverter.getId());
                    insertMovie(moviesConverter);
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like));
                } else {
                    delete(moviesConverter);
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike));
                }
            }
        });
    }

    //This method to display reviews in Recycle view in the next step
    public void initRecycleView(ArrayList<ReviewModel> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(this, list);
        recyclerView.setAdapter(recycleViewAdapter);
    }

    //This method to display trailers in Recycle view in the next step
    public void initTrailersRecycleView(ArrayList<String> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.trailers_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        TrailersAdapter trailerAdapter = new TrailersAdapter(this, list);
        recyclerView.setAdapter(trailerAdapter);
    }

    public void insertMovie(MoviesConverter movie) {
        movieViewModel.insert(movie);
    }

    public void delete(MoviesConverter movie) {
        movieViewModel.delete(movie);
    }

    public void observe(MovieViewModel viewModel, final int id) {
        Log.e("MovieDetail", "observe search id " + id);
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getAllMovies().observe(this, new Observer<List<MoviesConverter>>() {
            @Override
            public void onChanged(List<MoviesConverter> movies) {
                flag = 0;
                Log.e("MovieDetail", "movies size " + movies.size());
                for (int j = 0; j < movies.size(); j++) {
                    Log.e("MovieDetail", "loop id " + movies.get(j).getId());
                    if (id == movies.get(j).getId()) {
                        flag = 1;
                    }
                }
                if (flag == 1) {
                    toggleButton.setChecked(true);
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like));
                } else {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike));
                    toggleButton.setChecked(false);
                }
            }
        });
    }
}
