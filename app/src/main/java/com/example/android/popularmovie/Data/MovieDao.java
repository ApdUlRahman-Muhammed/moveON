package com.example.android.popularmovie.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    Long insertMovie(MoviesConverter movie);

    @Query("SELECT * FROM MoviesConverter WHERE id =:movieId")
    LiveData<MoviesConverter> getMovies(int movieId);

    @Query("SELECT * FROM MoviesConverter")
    LiveData<List<MoviesConverter>> fetchAllMovie();


    @Update
    void updateTask(MoviesConverter movie);


    @Delete
    void deleteTask(MoviesConverter id);

}
