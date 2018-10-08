package com.example.apoorvdubey.udacitymoviestageone.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.apoorvdubey.udacitymoviestageone.Network.Model.Result;

import java.util.List;

@Dao
public interface ResultDao {

    @Query("SELECT * FROM result")
    List<Result> loadAllFavMovies();

    @Insert
    void insertFavMovie(Result result);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavMovie(Result result);

    @Delete
    void deleteFavMovie(Result result);

}
