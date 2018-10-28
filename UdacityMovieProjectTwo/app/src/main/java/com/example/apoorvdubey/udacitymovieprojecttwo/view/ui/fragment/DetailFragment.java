package com.example.apoorvdubey.udacitymovieprojecttwo.view.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apoorvdubey.udacitymovieprojecttwo.R;
import com.example.apoorvdubey.udacitymovieprojecttwo.Utils.Constants;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.DetailResult;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Movie;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Review;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Trailer;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.repository.MoviesDatabase;
import com.example.apoorvdubey.udacitymovieprojecttwo.view.adapter.ReviewAdapter;
import com.example.apoorvdubey.udacitymovieprojecttwo.view.adapter.TrailerViewAdapter;
import com.example.apoorvdubey.udacitymovieprojecttwo.viewmodel.MovieListViewModel;
import com.example.apoorvdubey.udacitymovieprojecttwo.viewmodel.MyReviewModelFactory;
import com.example.apoorvdubey.udacitymovieprojecttwo.viewmodel.MyTrailerModelFactory;
import com.example.apoorvdubey.udacitymovieprojecttwo.viewmodel.MyViewModelFactory;
import com.example.apoorvdubey.udacitymovieprojecttwo.viewmodel.ReviewListViewModel;
import com.example.apoorvdubey.udacitymovieprojecttwo.viewmodel.TrailerListViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment implements View.OnClickListener, TrailerViewAdapter.ItemClickListener {

    public static final String ARG_POSITION = "position";
    public static final String ARG_RESPONSE_LIST = "responseList";
    private Movie response;
    private String sortOrder;
    private SharedPreferences mSharedPreferences;
    private int position;
    private ImageView mvImageView;
    private TextView mvTitle, mvReleaseDate, mvVoteAvg, mvPlotSummary;
    FloatingActionButton addFav, shareFav;
    MoviesDatabase moviesDatabase;
    private List<DetailResult> resultList = new ArrayList<>();
    private List<Trailer> trailerList = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();
    MovieListViewModel viewModel;
    TrailerListViewModel trailerListViewModel;
    ReviewListViewModel reviewListViewModel;
    RecyclerView trailerRecyclerView;
    RecyclerView reviewRecyclerView;
    TrailerViewAdapter trailerViewAdapter;
    LinearLayoutManager trailerLlm;
    LinearLayoutManager reviewLlm;
    ReviewAdapter reviewAdapter;
    CardView reviewCardView, trailerCardView;
    TextView reviewEmptyView, trailerEmptyView;
    ProgressBar progressBarReview, progressBarTrailer;

    public DetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        sortOrder = getSortOrder();
        moviesDatabase = MoviesDatabase.getsInstance(getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        setView(root);
        return root;
    }

    private void setView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ARG_POSITION)) {
            position = getArguments().getInt(ARG_POSITION);
        }
        if (bundle != null && bundle.containsKey(ARG_RESPONSE_LIST)) {
            resultList = getArguments().getParcelableArrayList(ARG_RESPONSE_LIST);
        }
        progressBarTrailer = view.findViewById(R.id.progress_bar_trailer);
        shareFav = view.findViewById(R.id.fav_share);
        progressBarReview = view.findViewById(R.id.progress_bar_review);
        progressBarReview.setVisibility(View.VISIBLE);
        progressBarTrailer.setVisibility(View.VISIBLE);
        reviewEmptyView = view.findViewById(R.id.empty_review_view);
        trailerEmptyView = view.findViewById(R.id.empty_trailer_view);
        reviewCardView = view.findViewById(R.id.review_card_view);
        trailerCardView = view.findViewById(R.id.trailer_card_view);
        addFav = view.findViewById(R.id.fav_add);
        mvImageView = view.findViewById(R.id.movie_poster_view);
        mvPlotSummary = view.findViewById(R.id.movie_plot_summary);
        mvReleaseDate = view.findViewById(R.id.movie_release_date);
        mvTitle = view.findViewById(R.id.movie_title);
        mvVoteAvg = view.findViewById(R.id.movie_vote_avg);
        reviewRecyclerView = view.findViewById(R.id.review_rv);
        trailerRecyclerView = view.findViewById(R.id.trailer_rv);
        trailerLlm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        reviewLlm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        trailerViewAdapter = new TrailerViewAdapter(getActivity(), trailerList);
        reviewAdapter = new ReviewAdapter(getActivity(), reviewList);
        reviewRecyclerView.setLayoutManager(reviewLlm);
        trailerRecyclerView.setLayoutManager(trailerLlm);
        reviewRecyclerView.setAdapter(reviewAdapter);
        trailerRecyclerView.setAdapter(trailerViewAdapter);
        addFav.setOnClickListener(this);
        shareFav.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel =
                ViewModelProviders.of(this, new MyViewModelFactory(this.getActivity().getApplication(), sortOrder))
                        .get(MovieListViewModel.class);
        trailerListViewModel = ViewModelProviders.of(this, new MyTrailerModelFactory(this.getActivity().getApplication(), resultList.get(position).getId()))
                .get(TrailerListViewModel.class);
        reviewListViewModel = ViewModelProviders.of(this, new MyReviewModelFactory(this.getActivity().getApplication(), resultList.get(position).getId()))
                .get(ReviewListViewModel.class);

        observeReviewObservable(reviewListViewModel);
        observeTrailerObservable(trailerListViewModel);
    }

    private void observeReviewObservable(ReviewListViewModel reviewListViewModel) {
        reviewLlm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        reviewAdapter = new ReviewAdapter(getActivity(), reviewList);
        reviewRecyclerView.setLayoutManager(reviewLlm);
        progressBarReview.setVisibility(View.VISIBLE);
        reviewListViewModel.getReviewListObservable().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                if (reviews.size() == 0) {
                    reviewCardView.setVisibility(View.GONE);
                    reviewEmptyView.setVisibility(View.VISIBLE);
                    progressBarReview.setVisibility(View.GONE);
                } else {
                    reviewCardView.setVisibility(View.VISIBLE);
                    reviewEmptyView.setVisibility(View.GONE);
                    reviewList.clear();
                    reviewList.addAll(reviews);
                    reviewRecyclerView.setAdapter(reviewAdapter);
                    progressBarReview.setVisibility(View.GONE);
                }
            }
        });
    }

    private void observeTrailerObservable(TrailerListViewModel trailerListViewModel) {
        trailerLlm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        trailerViewAdapter = new TrailerViewAdapter(getActivity(), trailerList);
        trailerRecyclerView.setLayoutManager(trailerLlm);
        trailerViewAdapter.setClickListener(this);
        progressBarTrailer.setVisibility(View.VISIBLE);
        trailerListViewModel.getTrailerListObservable().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                if (trailers.size() == 0) {
                    trailerCardView.setVisibility(View.GONE);
                    trailerEmptyView.setVisibility(View.VISIBLE);
                    progressBarTrailer.setVisibility(View.GONE);
                } else {
                    trailerCardView.setVisibility(View.VISIBLE);
                    trailerEmptyView.setVisibility(View.GONE);
                    trailerList.clear();
                    trailerList.addAll(trailers);
                    trailerRecyclerView.setAdapter(trailerViewAdapter);
                    progressBarTrailer.setVisibility(View.GONE);
                }
            }
        });
    }

    private String getSortOrder() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return mSharedPreferences.getString(getString(R.string.sp_key_sort_order_list), getResources().getString(R.string.POPULAR));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (resultList.get(position).getVoteAverage() != null) {
            mvVoteAvg.setText(resultList.get(position).getVoteAverage().toString());
        } else
            mvVoteAvg.setText(getString(R.string.na));
        if (resultList.get(position).getTitle() != null) {
            mvTitle.setText(resultList.get(position).getTitle().toString());
        } else mvTitle.setText(getString(R.string.na));
        if (resultList.get(position).getReleaseDate() != null) {
            mvReleaseDate.setText(resultList.get(position).getReleaseDate().toString());
        } else {
            mvReleaseDate.setText(getString(R.string.na));
        }
        if (resultList.get(position).getOverview() != null) {
            mvPlotSummary.setText(resultList.get(position).getOverview().toString());
        } else {
            mvPlotSummary.setText(getString(R.string.na));
        }
        if (resultList.get(position).getPosterPath() != null)
            Picasso.with(getActivity()).load(Constants.getBaseUrlPoster() + Constants.getPosterSize() + resultList.get(position).getPosterPath()).into(mvImageView);


        if (resultList.get(position).getIsFavourite() == null) {
            addFav.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.cardview_light_background)));
            addFav.setImageResource(R.drawable.ic_favorite_border_unselected);
            addFav.setSelected(false);

        } else if (resultList.get(position).getIsFavourite().equals(getString(R.string._true_))) {
            addFav.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.cardview_light_background)));
            addFav.setImageResource(R.drawable.ic_favorite_black_selected);
            addFav.setSelected(true);

        } else {
            addFav.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.cardview_light_background)));
            addFav.setImageResource(R.drawable.ic_favorite_border_unselected);
            addFav.setSelected(false);
        }
        shareFav.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.cardview_light_background)));
        shareFav.setImageResource(R.drawable.ic_share_black_selected);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fav_add:
                if (resultList.get(position).getIsFavourite() != null) {
                    if (addFav.isSelected()) {
                        addFav.setSelected(false);
                        addFav.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.cardview_light_background)));
                        addFav.setImageResource(R.drawable.ic_favorite_border_unselected);
                        resultList.get(position).setIsFavourite(getString(R.string._false_));
                        viewModel.Update(resultList.get(position));
                    } else {
                        addFav.setSelected(true);
                        addFav.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.cardview_light_background)));
                        addFav.setImageResource(R.drawable.ic_favorite_black_selected);
                        resultList.get(position).setIsFavourite(getString(R.string._true_));
                        viewModel.Update(resultList.get(position));
                    }
                    break;
                } else {
                    addFav.setSelected(true);
                    addFav.setImageResource(R.drawable.ic_favorite_black_selected);
                    addFav.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.cardview_light_background)));
                    resultList.get(position).setIsFavourite(getString(R.string._true_));
                    viewModel.Update(resultList.get(position));
                }
                break;
            case R.id.fav_share:
                if (trailerList.size() != 0) {
                    String key = trailerList.get(0).getKey();
                    shareTextUrl(key);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View view, String key) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_uri) + key)));
    }

    private void shareTextUrl(String key) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.movie_trailer));
        share.putExtra(Intent.EXTRA_TEXT, getString(R.string.youtube_uri) + key);
        startActivity(Intent.createChooser(share, getString(R.string.share_link)));
    }
}
