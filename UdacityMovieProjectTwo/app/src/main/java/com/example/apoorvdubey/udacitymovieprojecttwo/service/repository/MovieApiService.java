package com.example.apoorvdubey.udacitymovieprojecttwo.service.repository;

import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Movie;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.ReviewResponse;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("movie/top_rated")
    Call<Movie> getMostRatedMovies(@Query("api_key") String api_key);

    @GET("movie/popular")
    Call<Movie> getPopularMovies(@Query("api_key") String api_key);

    @GET("movie/{id}/videos")
    Call<TrailerResponse> getMovieTrailers(@Path("id") Integer movieId, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getMovieReviews(@Path("movie_id") Integer movieId, @Query("api_key") String api_key);

}