package com.example.apoorvdubey.udacitymovieprojecttwo.view.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.apoorvdubey.udacitymovieprojecttwo.R;
import com.example.apoorvdubey.udacitymovieprojecttwo.Utils.Constants;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.DetailResult;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Movie;
import com.example.apoorvdubey.udacitymovieprojecttwo.view.adapter.AutoFitGridLayoutAdapter;
import com.example.apoorvdubey.udacitymovieprojecttwo.view.adapter.PosterViewAdapter;
import com.example.apoorvdubey.udacitymovieprojecttwo.view.callback.OnHeadlineSelectedListener;
import com.example.apoorvdubey.udacitymovieprojecttwo.viewmodel.MovieListViewModel;
import com.example.apoorvdubey.udacitymovieprojecttwo.viewmodel.MyViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements PosterViewAdapter.ItemClickListener {
    private String sortOrder;
    private TextView emptyView;
    private ProgressBar progressBar;
    private PosterViewAdapter posterViewAdapter;
    private RecyclerView recyclerView;
    private AutoFitGridLayoutAdapter layoutManager;
    private OnHeadlineSelectedListener mCallback;
    private Movie movieResponse;

    private List<DetailResult> resultList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sortOrder = this.getArguments().getString(getString(R.string.sort_order));
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        setRecyclerView(root);
        setView(root);
        posterViewAdapter = new PosterViewAdapter(getActivity(), resultList);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMyAdapterValues(resultList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + getString(R.string.error));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final MovieListViewModel viewModel =
                ViewModelProviders.of(this, new MyViewModelFactory(this.getActivity().getApplication(), sortOrder))
                        .get(MovieListViewModel.class);
        observeViewModel(viewModel);
    }

    private void observeViewModel(MovieListViewModel viewModel) {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getMovieListObservable().observe(this, new Observer<List<DetailResult>>() {
            @Override
            public void onChanged(@Nullable List<DetailResult> movie) {
                if (movie != null) {
                    if (movie.size() != 0) {
                        resultList.clear();
                        resultList.addAll(movie);
                        posterViewAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        emptyView.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                        posterViewAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void setMyAdapterValues(List<DetailResult> response) {
        posterViewAdapter = new PosterViewAdapter(getActivity(), response);
        posterViewAdapter.setClickListener(this);
        recyclerView.setAdapter(posterViewAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    private void setView(View view) {
        emptyView = view.findViewById(R.id.empty_view);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    private void setRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new AutoFitGridLayoutAdapter(getActivity(), Constants.WIDTH);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onItemClick(View view, int position) {
        mCallback.onArticleSelected(position, resultList);
    }
}