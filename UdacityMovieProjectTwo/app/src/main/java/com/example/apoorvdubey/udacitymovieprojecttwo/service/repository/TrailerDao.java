package com.example.apoorvdubey.udacitymovieprojecttwo.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Trailer;

import java.util.List;

@Dao
public interface TrailerDao {

    @Query("SELECT * FROM trailer where movieId =(:value)")
    LiveData<List<Trailer>> loadAllTrailers(Integer value);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrailer(Trailer result);
}
