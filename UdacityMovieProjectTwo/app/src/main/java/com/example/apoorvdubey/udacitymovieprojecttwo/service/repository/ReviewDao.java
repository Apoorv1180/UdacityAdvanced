package com.example.apoorvdubey.udacitymovieprojecttwo.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Review;

import java.util.List;

@Dao
public interface ReviewDao {
    @Query("SELECT * FROM review where movieId =(:value)")
    LiveData<List<Review>> loadAllReviews(Integer value);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReview(Review result);


}
