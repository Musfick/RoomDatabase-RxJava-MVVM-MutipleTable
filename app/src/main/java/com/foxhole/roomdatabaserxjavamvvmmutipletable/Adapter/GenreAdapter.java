package com.foxhole.roomdatabaserxjavamvvmmutipletable.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Genre;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.R;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Utils.DataConverter;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.AdapterViewHolder> {

    private List<Genre> genreList;
    private OnGenreClickListener onGenreClickListener;

    public GenreAdapter(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public void setItemClickListener(OnGenreClickListener onGenreClickListener){
        this.onGenreClickListener = onGenreClickListener;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_item_layout,null);
        AdapterViewHolder adapterViewHolder = new AdapterViewHolder(view,onGenreClickListener);
        return adapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

        Genre singleGenre = genreList.get(position);
        holder.mImageView.setImageBitmap(DataConverter.convertByteArray2Image(singleGenre.getImage()));
        holder.mGenreText.setText(singleGenre.getGenre());
    }

    public Genre getGenreAt(int position){
        Genre genre = genreList.get(position);
        genre.setUid(genreList.get(position).getUid());
        return genre;
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private TextView mGenreText;
        private MaterialCardView mCardView;
        private OnGenreClickListener onGenreClickListener;

        public AdapterViewHolder(@NonNull View itemView, OnGenreClickListener onGenreClickListener) {
            super(itemView);
            this.onGenreClickListener = onGenreClickListener;
            mImageView = itemView.findViewById(R.id.image_view);
            mGenreText = itemView.findViewById(R.id.text_view);
            mCardView = itemView.findViewById(R.id.card_view);
            mCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Genre currentGenre = genreList.get(position);
            onGenreClickListener.onGenreClick(currentGenre);
        }
    }

    public interface OnGenreClickListener{
        void onGenreClick(Genre genre);
    }
}
