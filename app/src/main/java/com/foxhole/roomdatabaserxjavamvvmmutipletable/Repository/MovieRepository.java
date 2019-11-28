package com.foxhole.roomdatabaserxjavamvvmmutipletable.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao.GenreDao;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao.MovieDao;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.MovieDatabase;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Genre;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class MovieRepository {

    private static final String TAG = "MovieRepository";

    private GenreDao genreDao;
    private MovieDao movieDao;
    private Flowable<List<Genre>> allGenres;
    private Flowable<List<Movie>> allMovies;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();


    public MovieRepository(Application application){
        MovieDatabase movieDatabase = MovieDatabase.getInstance(application);
        genreDao = movieDatabase.genreDao();
        movieDao = movieDatabase.movieDao();


    }

    //Get all Genre
    public Flowable<List<Genre>> getAllGenre(){
        return genreDao.getAllGenres();
    }

    //Get Loading State
    public MutableLiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    //Get all Movie under the specific Genre
    public Flowable<List<Movie>> getAllMovies(int genre_id){
        return movieDao.getAllMoviesByGenre(genre_id);
    }

    //Insert Genre
    public void insertGenre(final Genre genre){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                genreDao.insert(genre);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: Called");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Called");
                isLoading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage());
            }
        });
    }

    //Update Genre
    public void updateGenre(final Genre genre){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                genreDao.update(genre);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called");
                    }
                });
    }

    //Delete Genre
    public void deleteGenre(final Genre genre){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                genreDao.delete(genre);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: Called");
                        deleteAllMoviesByGenre(genre.getUid());
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+ e.getMessage());
                    }
                });
    }

    //Delete all Genre
    public void deleteAllGenre(){

        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                genreDao.deleteAllGenre();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        deleteAllMovies();
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called"+e.getMessage());
                    }
                });
    }


    //Insert Movie
    public void insertMovie(final Movie movie){

        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                movieDao.insert(movie);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called"+e.getMessage());
                    }
                });
    }

    //Update Movie
    public void updateMovie(final Movie movie){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                movieDao.update(movie);
            }
        }).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called"+e.getMessage());
                    }
                });
    }

    //Delete Movie
    public void deleteMovie(final Movie movie){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                movieDao.delete(movie);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    //Delete all Movie
    public void deleteAllMovies(){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                movieDao.deleteAllMovies();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    //Delete all Movies By Genre
    public void deleteAllMoviesByGenre(final int genre_id){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                movieDao.deleteAllMoviesUnderGenre(genre_id);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

}
