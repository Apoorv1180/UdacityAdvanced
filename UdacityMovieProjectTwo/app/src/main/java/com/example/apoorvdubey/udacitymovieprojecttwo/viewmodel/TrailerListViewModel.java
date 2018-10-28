package com.example.apoorvdubey.udacitymovieprojecttwo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.DetailResult;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Trailer;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.repository.DataRepository;

import java.util.List;

public class TrailerListViewModel extends AndroidViewModel {
    private final LiveData<List<Trailer>> trailerListObservable;
    DataRepository dataRepository;

    public TrailerListViewModel(Application application, Integer mParam) {
        super(application);

        dataRepository = DataRepository.getInstance(application);
        trailerListObservable = DataRepository.getInstance(application).getTrailerList(mParam);
    }

    public LiveData<List<Trailer>> getTrailerListObservable() {
        return trailerListObservable;
    }

    public void Insert(Trailer result) {
        dataRepository.insertTrailer(result);
    }
}