package com.example.android.popularmovie.Data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class MovieRepository {
    private String DB_NAME = "db_task";
    private MovieDatabase movieDatabase;
    private MovieDao movieDao;

    //    private LiveData<List<Note>> allNotes;
    public MovieRepository(Context context) {
        movieDatabase = MovieDatabase.getINSTANCE(context);
        movieDao= movieDatabase.daoAccess();
    }

    public LiveData<MoviesConverter> getMovie(int id) {
        return movieDatabase.daoAccess().getMovies(id);
    }

    public LiveData<List<MoviesConverter>> getMovies() {
        return movieDatabase.daoAccess().fetchAllMovie();
    }

    public void insert(MoviesConverter movie) {

        new InsertAsyncTask(movieDao).execute(movie);
    }

    public void update(MoviesConverter movie) {
        new UpdateAsyncTask(movieDao).execute(movie);
    }

    public void delete(MoviesConverter note) {
        new DeleteAsyncTask(movieDao).execute(note);
    }


    public static class InsertAsyncTask extends AsyncTask<MoviesConverter, Void, Void> {
        MovieDao movieDao;

        public InsertAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MoviesConverter... movies) {
            for(int i =0 ; i<movies.length ;i++)
            movieDao.insertMovie(movies[i]);
            return null;
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<MoviesConverter, Void, Void> {
        MovieDao noteDao;

        public UpdateAsyncTask(MovieDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(MoviesConverter... notes) {
            noteDao.updateTask(notes[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<MoviesConverter, Void, Void> {
        MovieDao noteDao;

        public DeleteAsyncTask(MovieDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(MoviesConverter... movies) {
            noteDao.deleteTask(movies[0]);
            return null;
        }
    }
}
