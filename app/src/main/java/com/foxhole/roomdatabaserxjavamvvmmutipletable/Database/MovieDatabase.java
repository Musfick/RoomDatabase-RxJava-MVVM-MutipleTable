package com.foxhole.roomdatabaserxjavamvvmmutipletable.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao.GenreDao;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao.MovieDao;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Genre;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Movie;

@Database(entities = {Genre.class, Movie.class}, version = 1,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase instance;

    public abstract GenreDao genreDao();
    public abstract MovieDao movieDao();

    public static synchronized MovieDatabase getInstance(Context context){
        if(instance==null){
            //If instance is null that's mean database is not created and create new database
            instance = Room.databaseBuilder(context.getApplicationContext(),MovieDatabase.class,"movie_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
