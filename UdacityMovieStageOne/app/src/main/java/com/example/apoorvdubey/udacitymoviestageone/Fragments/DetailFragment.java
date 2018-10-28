

package com.example.apoorvdubey.udacitymoviestageone.Fragments;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.apoorvdubey.udacitymoviestageone.Network.Model.MoviesResponse;
import com.example.apoorvdubey.udacitymoviestageone.R;
import com.example.apoorvdubey.udacitymoviestageone.Utils.Constants;
import com.example.apoorvdubey.udacitymoviestageone.database.MoviesDatabase;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_POSITION = "position";
    public static final String ARG_RESPONSE = "response";
    private MoviesResponse response;
    private int position;
    private ImageView mvImageView;
    private TextView mvTitle, mvReleaseDate, mvVoteAvg, mvPlotSummary;
    MoviesDatabase moviesDatabase;
    FloatingActionButton addFav;
    public DetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        moviesDatabase= MoviesDatabase.getsInstance(getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ARG_POSITION)) {
            position = getArguments().getInt(ARG_POSITION);
        }
        if (bundle != null && bundle.containsKey(ARG_RESPONSE)) {
            response = getArguments().getParcelable(ARG_RESPONSE);
        }
        addFav=view.findViewById(R.id.fav_add);
        mvImageView = view.findViewById(R.id.movie_poster_view);
        mvPlotSummary = view.findViewById(R.id.movie_plot_summary);
        mvReleaseDate = view.findViewById(R.id.movie_release_date);
        mvTitle = view.findViewById(R.id.movie_title);
        mvVoteAvg = view.findViewById(R.id.movie_vote_avg);
        addFav.setOnClickListener(this);
        if (response.getResults().get(position).getVoteAverage() != null) {
            mvVoteAvg.setText(response.getResults().get(position).getVoteAverage().toString());
        } else
            mvVoteAvg.setText(getString(R.string.na));
        if (response.getResults().get(position).getTitle() != null) {
            mvTitle.setText(response.getResults().get(position).getTitle().toString());
        } else mvTitle.setText(getString(R.string.na));
        if (response.getResults().get(position).getReleaseDate() != null) {
            mvReleaseDate.setText(response.getResults().get(position).getReleaseDate().toString());
        } else {
            mvReleaseDate.setText(getString(R.string.na));
        }
        if (response.getResults().get(position).getOverview() != null) {
            mvPlotSummary.setText(response.getResults().get(position).getOverview().toString());
        } else {
            mvPlotSummary.setText(getString(R.string.na));
        }
        if (response.getResults().get(position).getPosterPath() != null)
            Picasso.with(getActivity()).load(Constants.getBaseUrlPoster() + Constants.getPosterSize() + response.getResults().get(position).getPosterPath()).into(mvImageView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fav_add:
                moviesDatabase.resultDao().insertFavMovie(response.getResults().get(position));
                break;
        }
    }
}
