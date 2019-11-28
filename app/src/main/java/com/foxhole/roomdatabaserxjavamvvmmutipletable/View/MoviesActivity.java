package com.foxhole.roomdatabaserxjavamvvmmutipletable.View;

import android.os.Bundle;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Adapter.MoviesAdapter;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Movie;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.ViewModel.MovieActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MoviesActivity extends AppCompatActivity implements CreateMovieDialog.OnCreateMovieListener {

    private static final String TAG = "MoviesActivity_Tag";
    private static final int START_DELAY = 2;

    private TextView mTitleView;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private MoviesAdapter moviesAdapter;
    private String mGenre = "";
    private int genre_id;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MovieActivityViewModel movieActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        mTitleView = findViewById(R.id.toolbar_title);
        mProgressBar = findViewById(R.id.progress);
        mRecyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        intToolbar();
        movieActivityViewModel = ViewModelProviders.of(this).get(MovieActivityViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mGenre = extras.getString("genre");
            genre_id = extras.getInt("uid");
            Log.d(TAG, "onStart: "+genre_id+""+mGenre);
            mTitleView.setText(mGenre+" Movies");

        }

        Disposable disposable = movieActivityViewModel.getAllMovies(genre_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        Log.d(TAG, "accept: getAllMovies");
                        moviesAdapter = new MoviesAdapter(movies);
                        mRecyclerView.setAdapter(moviesAdapter);
//                        for (Movie movie: movies){
//                            Log.d(TAG, "accept: "+movie.getTitle());
//                        }
                    }
                });

        compositeDisposable.add(disposable);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                movieActivityViewModel.delete(moviesAdapter.getMovieAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(mRecyclerView);


        //Check Loading State
        movieActivityViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d(TAG, "onChanged: "+aBoolean);
                if (aBoolean!=null){
                    if (aBoolean){
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    else {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddMovieDialog();
            }
        });
    }

    private void openAddMovieDialog() {
        CreateMovieDialog createMovieDialog = new CreateMovieDialog();
        createMovieDialog.show(getSupportFragmentManager(),"Create Movie Dialog");
    }

    private void intToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            deleteAllMovies();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllMovies() {
        movieActivityViewModel.deleteAllMoviesByGenre(genre_id);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public void saveNewMovie(Movie movie) {
        Movie currentMovie = movie;
        currentMovie.setGenre_id(genre_id);
        movieActivityViewModel.insert(currentMovie);
    }
}
