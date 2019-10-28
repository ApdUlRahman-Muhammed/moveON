package com.example.android.popularmovie.Data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {MoviesConverter.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase INSTANCE;

    public abstract MovieDao daoAccess();

    public  static MovieDatabase  getINSTANCE(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),MovieDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return INSTANCE;
    }
    public static Callback roomCalback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDBAsyncTask(INSTANCE).execute();
        }
    };

    private static class  populateDBAsyncTask extends AsyncTask<Void,Void,Void> {
        private  MovieDao movieDao;
        private populateDBAsyncTask(MovieDatabase db){
            movieDao =db.daoAccess();
        }
        @Override
        protected Void doInBackground(Void... voids) {
           // movieDao.insertMovie(new MoviesConverter(12,"Hello","This message to welcome any one you may know or not "));
            return null;
        }
    }
}
