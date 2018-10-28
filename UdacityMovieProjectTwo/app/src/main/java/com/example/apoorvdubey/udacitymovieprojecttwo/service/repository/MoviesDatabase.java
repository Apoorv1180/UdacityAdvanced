package com.example.apoorvdubey.udacitymovieprojecttwo.service.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.apoorvdubey.udacitymovieprojecttwo.Utils.Constants;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.DetailResult;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Review;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Trailer;

@Database(entities = {DetailResult.class, Trailer.class, Review.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = Constants.DATABASE_NAME;
    private static MoviesDatabase sInstance;

    public static MoviesDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext()
                        , MoviesDatabase.class
                        , MoviesDatabase.DATABASE_NAME).allowMainThreadQueries()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract ResultDao resultDao();

    public abstract TrailerDao trailerDao();

    public abstract ReviewDao reviewDao();
}
