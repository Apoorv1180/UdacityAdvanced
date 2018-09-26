package com.example.apoorvdubey.udacitymoviestageone.Network.Interface;

import com.example.apoorvdubey.udacitymoviestageone.Network.Model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MostRatedMovieApiInterface {
    @GET("movie/top_rated")
    Call<MoviesResponse> getMostRatedMovies(@Query("api_key") String api_key);
}