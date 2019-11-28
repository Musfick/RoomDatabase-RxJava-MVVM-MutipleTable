package com.foxhole.roomdatabaserxjavamvvmmutipletable.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Movie;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.R;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Utils.DataConverter;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> movieList;

    public MoviesAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_movie_layout,null);
        MoviesViewHolder moviesViewHolder = new MoviesViewHolder(view);
        return moviesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {

        Movie movie = movieList.get(position);
        holder.mTitleView.setText(movie.getTitle());
        holder.mYearView.setText(movie.getRelease_data());
        holder.mImageView.setImageBitmap(DataConverter.convertByteArray2Image(movie.getImage()));
    }

    public Movie getMovieAt(int position){
        Movie movie = movieList.get(position);
        movie.setUid(movieList.get(position).getUid());
        return movie;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder{

        public TextView mYearView;
        public TextView mTitleView;
        public ImageView mImageView;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            mYearView = itemView.findViewById(R.id.year);
            mImageView = itemView.findViewById(R.id.image_view);
            mTitleView = itemView.findViewById(R.id.title);
        }
    }
}
