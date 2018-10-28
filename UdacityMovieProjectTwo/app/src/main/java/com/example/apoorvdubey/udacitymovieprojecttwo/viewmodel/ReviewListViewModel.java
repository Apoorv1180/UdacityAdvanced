package com.example.apoorvdubey.udacitymovieprojecttwo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Review;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.repository.DataRepository;

import java.util.List;

public class ReviewListViewModel extends AndroidViewModel {
    private final LiveData<List<Review>> reviewListObservable;

    DataRepository dataRepository;

    public ReviewListViewModel(Application application, Integer mParam) {
        super(application);

        dataRepository = DataRepository.getInstance(application);
        reviewListObservable = DataRepository.getInstance(application).getReviewList(mParam);
    }

    public LiveData<List<Review>> getReviewListObservable() {
        return reviewListObservable;
    }

    public void Insert(Review result) {
        dataRepository.insertReview(result);
    }

}