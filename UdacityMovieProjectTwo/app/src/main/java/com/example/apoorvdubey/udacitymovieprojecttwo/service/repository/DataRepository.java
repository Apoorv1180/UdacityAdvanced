package com.example.apoorvdubey.udacitymovieprojecttwo.service.repository;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.example.apoorvdubey.udacitymovieprojecttwo.Utils.Constants;
import com.example.apoorvdubey.udacitymovieprojecttwo.Utils.Utils;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.DetailResult;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Movie;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Review;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.ReviewResponse;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Trailer;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.TrailerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRepository {
    private MovieApiService movieApiService;
    private static DataRepository dataRepository;
    private ResultDao movieDao;
    private TrailerDao trailerDao;
    private ReviewDao resultDao;
    private static Context context;

    private DataRepository(Application application) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        movieApiService = retrofit.create(MovieApiService.class);
        MoviesDatabase moviesDatabase = MoviesDatabase.getsInstance(application);
        movieDao = moviesDatabase.resultDao();
        trailerDao = moviesDatabase.trailerDao();
        resultDao = moviesDatabase.reviewDao();
    }

    public synchronized static DataRepository getInstance(Application application) {
        if (dataRepository == null) {
            if (dataRepository == null) {
                dataRepository = new DataRepository(application);
                context = application.getApplicationContext();
            }
        }
        return dataRepository;
    }

    public LiveData<List<DetailResult>> getMovieList(String sortId) {
        final MutableLiveData<List<DetailResult>> data = new MutableLiveData<>();
        if (Utils.isConnected(context)) {
            switch (sortId) {
                case Constants.TOP_RATED_MOVIE:
                    movieApiService.getMostRatedMovies(Constants.getApiKeyV3()).enqueue(new Callback<Movie>() {
                        @Override
                        public void onResponse(Call<Movie> call, Response<Movie> response) {
                            for (int i = 0; i < response.body().getResults().size(); i++) {
                                DetailResult res = response.body().getResults().get(i);
                                res.setPopular(Constants.TOP_RATED_MOVIE);
                                String isFav = movieDao.getIfMovieIsFav(res.title);
                                if (isFav != null) {
                                    res.setIsFavourite(isFav);
                                }
                                insertMovie(res);
                            }
                        }

                        @Override
                        public void onFailure(Call<Movie> call, Throwable t) {
                            data.setValue(null);
                        }
                    });
                    return movieDao.loadAllMovies(sortId);
                case Constants.POP_MOVIES:
                    movieApiService.getPopularMovies(Constants.getApiKeyV3()).enqueue(new Callback<Movie>() {
                        @Override
                        public void onResponse(Call<Movie> call, Response<Movie> response) {
                            for (int i = 0; i < response.body().getResults().size(); i++) {
                                DetailResult res = response.body().getResults().get(i);
                                res.setPopular(Constants.POP_MOVIES);
                                String isFav = movieDao.getIfMovieIsFav(res.title);
                                if (isFav != null) {
                                    res.setIsFavourite(isFav);
                                }
                                insertMovie(res);
                            }
                        }

                        @Override
                        public void onFailure(Call<Movie> call, Throwable t) {
                            data.setValue(null);
                        }
                    });
                    return movieDao.loadAllMovies(sortId);
                case Constants.FAV_MOVIE:
                    return movieDao.loadAllFavMovies();
            }
        } else {
            if (!sortId.equals(Constants.FAV_MOVIE)) {
                return movieDao.loadAllMovies(sortId);
            } else
                return movieDao.loadAllFavMovies();
        }
        return null;
    }

    public LiveData<List<Review>> getReviewList(Integer id) {
        final Integer movieid = id;
        final MutableLiveData<List<Review>> listReview = new MutableLiveData<>();
        movieApiService.getMovieReviews(id, Constants.getApiKeyV3()).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                listReview.setValue(response.body().getResults());
                for (int i = 0; i < response.body().getResults().size(); i++) {
                    response.body().getResults().get(i).setMovieId(response.body().getId());
                    insertReview(response.body().getResults().get(i));
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                listReview.setValue(null);
            }
        });
        if (Utils.isConnected(context))
            return listReview;
        else
            return resultDao.loadAllReviews(movieid);
    }

    public LiveData<List<Trailer>> getTrailerList(final Integer id) {
        final MutableLiveData<List<Trailer>> listReview = new MutableLiveData<>();
        final Integer movieid = id;
        movieApiService.getMovieTrailers(id, Constants.getApiKeyV3()).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                listReview.setValue(response.body().getResults());
                for (int i = 0; i < response.body().getResults().size(); i++) {
                    response.body().getResults().get(i).setMovieId(response.body().getId());
                    insertTrailer(response.body().getResults().get(i));
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                listReview.setValue(null);
            }
        });
        if (Utils.isConnected(context))
            return listReview;
        else
            return trailerDao.loadAllTrailers(movieid);
    }

    public void insertMovie(DetailResult result) {
        new InsertMovieAsynTask(movieDao).execute(result);
    }

    public void updateMovie(DetailResult result) {
        new UpdateMovieAsynTask(movieDao).execute(result);
    }

    public void insertTrailer(Trailer result) {
        new InsertTrailerAsynTask(trailerDao).execute(result);
    }

    public void insertReview(Review result) {
        new InsertReviewAsynTask(resultDao).execute(result);
    }

    private static class InsertMovieAsynTask extends AsyncTask<DetailResult, Void, Void> {
        private ResultDao resultDao;

        private InsertMovieAsynTask(ResultDao resultDao) {
            this.resultDao = resultDao;
        }

        @Override
        protected Void doInBackground(DetailResult... detailResults) {
            resultDao.insertFavMovie(detailResults[0]);
            return null;
        }
    }

    private static class UpdateMovieAsynTask extends AsyncTask<DetailResult, Void, Void> {
        private ResultDao resultDao;

        private UpdateMovieAsynTask(ResultDao resultDao) {
            this.resultDao = resultDao;
        }

        @Override
        protected Void doInBackground(DetailResult... detailResults) {
            resultDao.updateFavMovie(detailResults[0]);
            return null;
        }
    }

    private static class InsertTrailerAsynTask extends AsyncTask<Trailer, Void, Void> {
        private TrailerDao trailerDao;

        private InsertTrailerAsynTask(TrailerDao resultDao) {
            this.trailerDao = resultDao;
        }

        @Override
        protected Void doInBackground(Trailer... detailResults) {
            trailerDao.insertTrailer(detailResults[0]);
            return null;
        }
    }

    private static class InsertReviewAsynTask extends AsyncTask<Review, Void, Void> {
        private ReviewDao reviewDao;

        private InsertReviewAsynTask(ReviewDao resultDao) {
            this.reviewDao = resultDao;
        }

        @Override
        protected Void doInBackground(Review... detailResults) {
            reviewDao.insertReview(detailResults[0]);
            return null;
        }
    }
}