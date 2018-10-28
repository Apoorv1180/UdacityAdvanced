package com.example.apoorvdubey.udacitymovieprojecttwo.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.DetailResult;

import java.util.List;

@Dao
public interface ResultDao {
    @Query("SELECT * FROM result where isFavourite LIKE 'true' ")
    LiveData<List<DetailResult>> loadAllFavMovies();

    @Query("SELECT isFavourite FROM result where title =(:title)")
    String getIfMovieIsFav(String title);

    @Query("SELECT * FROM result where popular =(:param)")
    LiveData<List<DetailResult>> loadAllMovies(String param);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavMovie(DetailResult result);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavMovie(DetailResult result);

}
