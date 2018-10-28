package com.example.apoorvdubey.udacitymovieprojecttwo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.DetailResult;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.repository.DataRepository;

import java.util.List;

public class MovieListViewModel extends AndroidViewModel {
    private final LiveData<List<DetailResult>> movieListObservable;
    DataRepository dataRepository;

    public MovieListViewModel(Application application, String mParam) {
        super(application);

        dataRepository = DataRepository.getInstance(application);
        movieListObservable = DataRepository.getInstance(application).getMovieList(mParam);
    }

    public LiveData<List<DetailResult>> getMovieListObservable() {
        return movieListObservable;
    }

    public void Insert(DetailResult result) {
        dataRepository.insertMovie(result);
    }

    public void Update(DetailResult result) {
        dataRepository.updateMovie(result);
    }
}