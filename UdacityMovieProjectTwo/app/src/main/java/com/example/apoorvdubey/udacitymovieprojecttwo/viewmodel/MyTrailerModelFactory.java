package com.example.apoorvdubey.udacitymovieprojecttwo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class MyTrailerModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private Integer mParam;

    public MyTrailerModelFactory(Application application, Integer param) {
        mApplication = application;
        mParam = param;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new TrailerListViewModel(mApplication, mParam);
    }
}