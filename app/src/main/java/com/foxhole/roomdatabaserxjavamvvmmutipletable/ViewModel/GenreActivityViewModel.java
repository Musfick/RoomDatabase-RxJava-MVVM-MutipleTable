package com.foxhole.roomdatabaserxjavamvvmmutipletable.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Genre;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Movie;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Repository.MovieRepository;

import java.util.List;

import io.reactivex.Flowable;

public class GenreActivityViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    public GenreActivityViewModel(@NonNull Application application) {
        super(application);

        movieRepository = new MovieRepository(application);
    }

    //Get all Genre
    public Flowable<List<Genre>> getAllGenre(){
        return movieRepository.getAllGenre();
    }

    //Get Loading State
    public MutableLiveData<Boolean> getIsLoading(){
        return movieRepository.getIsLoading();
    }

    //Insert Genre
    public void insert(Genre genre){
        movieRepository.insertGenre(genre);
    }

    //Update Genre
    public void updateGenre(Genre genre){
        movieRepository.updateGenre(genre);
    }

    //Delete Genre
    public void deleteGenre(Genre genre){
        movieRepository.deleteGenre(genre);
    }

    //Delete All Genre
    public void deleteAllGenre()
    {
        movieRepository.deleteAllGenre();
    }

}
