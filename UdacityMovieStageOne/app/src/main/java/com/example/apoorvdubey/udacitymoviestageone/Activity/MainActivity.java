package com.example.apoorvdubey.udacitymoviestageone.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.apoorvdubey.udacitymoviestageone.Fragments.DetailFragment;
import com.example.apoorvdubey.udacitymoviestageone.Fragments.MainFragment;
import com.example.apoorvdubey.udacitymoviestageone.Network.Model.MoviesResponse;
import com.example.apoorvdubey.udacitymoviestageone.R;

public class MainActivity extends AppCompatActivity implements MainFragment.OnHeadlineSelectedListener {

    private MainFragment mainFragment;
    private DetailFragment detailFragment = null;
    private int position=-1;
    private MoviesResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



        for(int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++){
            Log.i("yoyo", "Found fragment: " + fragmentManager.getBackStackEntryAt(entry).getName());
        }
        if (fragmentManager.getBackStackEntryCount() == 0) {
            mainFragment = new MainFragment();
            fragmentTransaction.replace(R.id.framelayout, mainFragment);
            fragmentTransaction.commit();
        }
        else {
                fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1);
                    }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position=savedInstanceState.getInt(DetailFragment.ARG_POSITION);
        response=savedInstanceState.getParcelable(DetailFragment.ARG_RESPONSE);
        Bundle args = new Bundle();
        args.putInt(DetailFragment.ARG_POSITION, position);
        args.putParcelable(DetailFragment.ARG_RESPONSE, response);
    }

    @Override
    public void onArticleSelected(int position, MoviesResponse response) {
            this.position = position;
            this.response = response;
            detailFragment = new DetailFragment();
            Bundle args = new Bundle();
            args.putInt(DetailFragment.ARG_POSITION, position);
            args.putParcelable(DetailFragment.ARG_RESPONSE, response);
            detailFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.framelayout, detailFragment, "DETAIL_FRAGMENT");
            transaction.addToBackStack("DETAIL_FRAGMENT");
            transaction.commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DetailFragment.ARG_RESPONSE,response);
        outState.putInt(DetailFragment.ARG_POSITION,position);
    }
}