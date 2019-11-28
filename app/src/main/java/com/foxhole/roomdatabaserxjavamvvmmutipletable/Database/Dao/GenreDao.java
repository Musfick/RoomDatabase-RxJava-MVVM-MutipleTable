package com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Genre;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface GenreDao {

    //Insert new Genre
    @Insert
    void insert(Genre genre);

    //Update existing Genre
    @Update
    void update(Genre genre);

    //Delete Specific Genre and also delete movies under this genre
    @Delete
    void delete(Genre genre);

    //Delete all Genre from table
    @Query("DELETE FROM genre_table")
    void deleteAllGenre();

    //Get all Genre from table
    @Query("SELECT * FROM genre_table")
    Flowable<List<Genre>> getAllGenres();
}
