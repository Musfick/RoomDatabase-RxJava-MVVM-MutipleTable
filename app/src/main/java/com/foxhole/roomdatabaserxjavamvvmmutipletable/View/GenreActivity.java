package com.foxhole.roomdatabaserxjavamvvmmutipletable.View;

import android.content.Intent;
import android.os.Bundle;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Adapter.GenreAdapter;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Genre;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.R;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.ViewModel.GenreActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GenreActivity extends AppCompatActivity implements
        CreateGenreDialog.CreateGenreListener,
        GenreAdapter.OnGenreClickListener {

    private static final String TAG = "MainActivity_TAG";
    private static final int START_DELAY = 3;

    private GenreActivityViewModel genreActivityViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView mRecyclerView;
    private GenreAdapter genreAdapter;
    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        intToolbar();
        FloatingActionButton fab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressbar = findViewById(R.id.progress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        //ViewModel
        genreActivityViewModel = ViewModelProviders.of(this).get(GenreActivityViewModel.class);

        //Disposable for avoid memory leak
        Disposable disposable = genreActivityViewModel.getAllGenre().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Genre>>() {
                    @Override
                    public void accept(List<Genre> genres) throws Exception {
                        Log.d(TAG, "accept: Called");
                        setDataToRecyclerView(genres);
                    }
                });


        //Add Disposable
        compositeDisposable.add(disposable);

        //Check Loading State
        genreActivityViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d(TAG, "onChanged: "+aBoolean);
                if (aBoolean!=null){
                    if (aBoolean){
                        mProgressbar.setVisibility(View.VISIBLE);
                    }
                    else {
                        mProgressbar.setVisibility(View.GONE);
                    }
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateGenreDialog();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                genreActivityViewModel.deleteGenre(genreAdapter.getGenreAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    private void setDataToRecyclerView(List<Genre> genres) {
        genreAdapter = new GenreAdapter(genres);
        genreAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(genreAdapter);
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
            deleteGenreAndMovie();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteGenreAndMovie() {
        genreActivityViewModel.deleteAllGenre();
    }

    public void openCreateGenreDialog(){
        CreateGenreDialog createGenreDialog = new CreateGenreDialog();
        createGenreDialog.show(getSupportFragmentManager(),"create dialog");
    }

    @Override
    public void saveNewGenre(Genre genre) {
        Log.d(TAG, "saveNewGenre: "+genre.getGenre());
        genreActivityViewModel.insert(genre);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Remove Disposable
        compositeDisposable.dispose();
    }

    @Override
    public void onGenreClick(Genre genre) {
        Log.d(TAG, "onGenreClick: onItemClick");
        moveToMoviesActivity(genre);
    }
    public void moveToMoviesActivity(Genre genre){
        Intent intent = new Intent(GenreActivity.this,MoviesActivity.class);
        intent.putExtra("genre",genre.getGenre());
        intent.putExtra("uid",genre.getUid());
        startActivity(intent);
    }
}
