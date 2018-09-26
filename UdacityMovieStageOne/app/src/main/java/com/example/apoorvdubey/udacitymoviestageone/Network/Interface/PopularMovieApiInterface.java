package com.example.apoorvdubey.udacitymoviestageone.Network.Interface;



import com.example.apoorvdubey.udacitymoviestageone.Network.Model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PopularMovieApiInterface {
        @GET("movie/popular")
        Call<MoviesResponse> getPopularMovies(@Query("api_key") String api_key);
}