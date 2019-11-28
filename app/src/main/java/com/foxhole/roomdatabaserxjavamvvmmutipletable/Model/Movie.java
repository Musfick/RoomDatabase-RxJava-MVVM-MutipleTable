package com.foxhole.roomdatabaserxjavamvvmmutipletable.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_table")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    private int genre_id;
    private String title;
    private String rating;
    private String release_data;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte [] image;

    public Movie(String title, String rating, String release_data, byte[] image) {
        this.title = title;
        this.rating = rating;
        this.release_data = release_data;
        this.image = image;
    }

    public int getUid() {
        return uid;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String getRelease_data() {
        return release_data;
    }

    public byte[] getImage() {
        return image;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }
}
