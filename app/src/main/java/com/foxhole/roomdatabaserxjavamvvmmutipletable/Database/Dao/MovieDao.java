package com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Movie;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MovieDao {

    //Insert Movie
    @Insert
    void insert(Movie movie);

    //Update existing movie
    @Update
    void update(Movie movie);

    //Delete existing movie
    @Delete
    void delete(Movie movie);

    //Delete all movies from table;
    @Query("DELETE FROM movie_table")
    void deleteAllMovies();

    //Get Movie list under specific genre
    @Query("SELECT * FROM movie_table WHERE genre_id LIKE :genre_id")
    Flowable<List<Movie>> getAllMoviesByGenre(int genre_id);
//    WHERE genre_id==:genre_id int genre_id

    //Delete movies by genre
    @Query("DELETE FROM movie_table WHERE genre_id==:genre_id")
    void deleteAllMoviesUnderGenre(int genre_id);
}
