package com.example.android.popularmovie.Model;

import com.example.android.popularmovie.Data.MoviesConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MoviesList {
    @SerializedName("results")
    @Expose
    private List<MoviesConverter> moviesList;

    public List<MoviesConverter> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<MoviesConverter> moviesList) {
        this.moviesList = moviesList;
    }

}