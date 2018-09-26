package com.example.apoorvdubey.udacitymoviestageone.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.preference.PreferenceManager;

import com.example.apoorvdubey.udacitymoviestageone.Activity.SettingsActivity;
import com.example.apoorvdubey.udacitymoviestageone.Network.Client.RetrofitClient;
import com.example.apoorvdubey.udacitymoviestageone.Network.Interface.MostRatedMovieApiInterface;
import com.example.apoorvdubey.udacitymoviestageone.Network.Interface.PopularMovieApiInterface;
import com.example.apoorvdubey.udacitymoviestageone.Network.Model.MoviesResponse;

import java.io.IOException;

import retrofit2.Call;

public class EmployeeLoader extends AsyncTaskLoader<MoviesResponse> {

    public boolean sortOrder;
    public Context context;
    public MoviesResponse response;
    private PopularMovieApiInterface popularMovieApiInterface;
    private MostRatedMovieApiInterface mostRatedMovieApiInterface;

    public EmployeeLoader(Context context) {
        super(context);
        this.context = context;
    }

    private boolean getSortOrder() {
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getBoolean
                (SettingsActivity.KEY_PREF_POPULAR_MOVIES_SWITCH_ON, false);
    }

    @Override
    public MoviesResponse loadInBackground() {
        sortOrder = getSortOrder();
        if (sortOrder) {
            response = fetchMyPopularMovieData();
        } else {
            response = fetchMyTopRatedMovieData();
        }
        return response;
    }

    private MoviesResponse fetchMyPopularMovieData() {
        popularMovieApiInterface = RetrofitClient.getClient().create(PopularMovieApiInterface.class);
        Call<MoviesResponse> call = popularMovieApiInterface.getPopularMovies(Constants.getApiKeyV3());
        try {
            response = call.execute().body();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private MoviesResponse fetchMyTopRatedMovieData() {
        mostRatedMovieApiInterface = RetrofitClient.getClient().create(MostRatedMovieApiInterface.class);
        Call<MoviesResponse> call = mostRatedMovieApiInterface.getMostRatedMovies(Constants.getApiKeyV3());
        try {
            response = call.execute().body();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}