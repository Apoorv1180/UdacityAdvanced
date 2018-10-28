package com.example.apoorvdubey.udacitymoviestageone.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.apoorvdubey.udacitymoviestageone.Network.Model.Result;

@Database(entities = {Result.class}, version = 1,exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {
    private static final String LOG_TAG = MoviesDatabase.class.getSimpleName();
    private static final Object LOCK =new Object();
    private static final String DATABASE_NAME = "moviedatabase";
    private static MoviesDatabase sInstance;

    public static MoviesDatabase getsInstance(Context context) {
        if(sInstance==null){
            synchronized (LOCK){
                Log.d(LOG_TAG,"Creating New Database Instance");
                sInstance= Room.databaseBuilder(context.getApplicationContext()
                        ,MoviesDatabase.class
                        ,MoviesDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG,"Fetching Database Instance");
        return sInstance;
    }

    public abstract ResultDao resultDao();
}
