package com.example.apoorvdubey.udacitymoviestageone.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.apoorvdubey.udacitymoviestageone.Activity.SettingsActivity;
import com.example.apoorvdubey.udacitymoviestageone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment implements Toolbar.OnMenuItemClickListener{

    private Toolbar toolbar;
    private String switchPref;
    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSharedPreference();
        setToolBar(view);
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
    private void setSharedPreference() {
        if (getActivity() != null) {
            SharedPreferences sharedPref =
                    PreferenceManager.getDefaultSharedPreferences(getActivity());
            switchPref = sharedPref.getString(getActivity().getString(R.string.sp_key_sort_order_list),getActivity().getResources().getString(R.string.popular_movies));
        }
    }

    private void setToolBar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        if (switchPref.equals("POPULAR MOVIES")) {
            toolbar.setTitle(R.string.POPULAR);
        } else if (switchPref.equals("TOP RATED MOVIES"))
        {
            toolbar.setTitle(R.string.TOP_RATED);
        }
        else
            toolbar.setTitle("FAVOURITE MOVIES");

        toolbar.inflateMenu(R.menu.menu_main);
    }
}
