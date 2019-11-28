package com.foxhole.roomdatabaserxjavamvvmmutipletable.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "genre_table")
public class Genre {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String genre;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Genre(String genre, byte[] image) {
        this.genre = genre;
        this.image = image;
    }

    public int getUid() {
        return uid;
    }

    public String getGenre() {
        return genre;
    }

    public byte[] getImage() {
        return image;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
