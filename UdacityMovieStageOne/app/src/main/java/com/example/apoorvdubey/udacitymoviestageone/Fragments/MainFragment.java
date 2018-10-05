package com.example.apoorvdubey.udacitymoviestageone.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.apoorvdubey.udacitymoviestageone.Activity.SettingsActivity;
import com.example.apoorvdubey.udacitymoviestageone.Adapter.AutoFitGridLayoutAdapter;
import com.example.apoorvdubey.udacitymoviestageone.Adapter.PosterViewAdapter;
import com.example.apoorvdubey.udacitymoviestageone.Network.Model.MoviesResponse;
import com.example.apoorvdubey.udacitymoviestageone.Network.Model.Result;
import com.example.apoorvdubey.udacitymoviestageone.R;
import com.example.apoorvdubey.udacitymoviestageone.Utils.Constants;
import com.example.apoorvdubey.udacitymoviestageone.Utils.EmployeeLoader;
import com.example.apoorvdubey.udacitymoviestageone.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.example.apoorvdubey.udacitymoviestageone.Utils.Constants.WIDTH;

public class MainFragment extends Fragment implements Toolbar.OnMenuItemClickListener, LoaderManager.LoaderCallbacks<MoviesResponse>, PosterViewAdapter.ItemClickListener {
    private Toolbar toolbar;
    private Boolean switchPref;
    private MoviesResponse response;
    private List<Result> resultList = new ArrayList<>();
    private TextView emptyView;
    private ProgressBar progressBar;
    private PosterViewAdapter posterViewAdapter;
    private RecyclerView recyclerView;
    private AutoFitGridLayoutAdapter layoutManager;
    private OnHeadlineSelectedListener mCallback;


    public MainFragment() {
    }

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position, MoviesResponse response);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSharedPreference();
        setView(view);
        setRecyclerView(view);
        setToolBar(view);
        if(Utils.isConnected(getActivity())) {
            if (getActivity().getSupportLoaderManager() == null)
                getActivity().getSupportLoaderManager().initLoader(Constants.LOADER_ID, null, this).forceLoad();
            else
                getActivity().getSupportLoaderManager().restartLoader(Constants.LOADER_ID, null, this).forceLoad();
        }else {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }

    }

    private void setToolBar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        if (switchPref) {
            toolbar.setTitle(R.string.POPULAR);
        } else {
            toolbar.setTitle(R.string.TOP_RATED);
        }
        toolbar.inflateMenu(R.menu.menu_main);
    }

    private void setSharedPreference() {
        if (getActivity() != null) {
            SharedPreferences sharedPref =
                    PreferenceManager.getDefaultSharedPreferences(getActivity());
            switchPref = sharedPref.getBoolean(SettingsActivity.KEY_PREF_POPULAR_MOVIES_SWITCH_ON, false);
        }
    }

    private void setView(View view) {
        emptyView = view.findViewById(R.id.empty_view);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    private void setRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new AutoFitGridLayoutAdapter(getActivity(), WIDTH);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    @NonNull
    @Override
    public Loader<MoviesResponse> onCreateLoader(int id, @Nullable Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        return new EmployeeLoader(getActivity());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<MoviesResponse> loader, MoviesResponse data) {
        response = data;
        if (data != null)
            setMyAdapterValues(data.getResults());
        else {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<MoviesResponse> loader) {
        emptyView.setVisibility(View.VISIBLE);
    }

    private void setMyAdapterValues(List<Result> response) {
        resultList.addAll(response);
        posterViewAdapter = new PosterViewAdapter(getActivity(), resultList);
        posterViewAdapter.setClickListener(this);
        recyclerView.setAdapter(posterViewAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View view, int position) {
//        DetailFragment fragment = new DetailFragment();
//        Bundle args = new Bundle();
//        args.putInt(Constants.POSITION, position);
//        args.putParcelable(Constants.RESPONSE, response);
//        fragment.setArguments(args);
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.framelayout, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//
        mCallback.onArticleSelected(position,response);
    }

}
