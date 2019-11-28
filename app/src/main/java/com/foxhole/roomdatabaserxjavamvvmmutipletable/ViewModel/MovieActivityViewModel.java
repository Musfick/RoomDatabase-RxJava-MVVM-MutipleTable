package com.foxhole.roomdatabaserxjavamvvmmutipletable.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Movie;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Repository.MovieRepository;

import java.util.List;

import io.reactivex.Flowable;

public class MovieActivityViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;


    public MovieActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    //Get all Movie under the specific Genre
    public Flowable<List<Movie>> getAllMovies(int genre_id){
        return movieRepository.getAllMovies(genre_id);
    }

    //Get Loading State
    public MutableLiveData<Boolean> getIsLoading(){
        return movieRepository.getIsLoading();
    }
    //Insert Movie
    public void insert(Movie movie){
        movieRepository.insertMovie(movie);
    }

    //Update Movie
    public void update(Movie movie){
        movieRepository.updateMovie(movie);
    }

    //Delete Movie
    public void delete(Movie movie){
        movieRepository.deleteMovie(movie);
    }

    //Delete All Movie
    public void deleteAllMoviesByGenre( int genre_id){
        movieRepository.deleteAllMoviesByGenre(genre_id);
    }
}
