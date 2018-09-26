package com.example.apoorvdubey.udacitymoviestageone.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.apoorvdubey.udacitymoviestageone.Fragments.MainFragment;
import com.example.apoorvdubey.udacitymoviestageone.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        fragmentTransaction.replace(R.id.framelayout, mainFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
