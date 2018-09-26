package com.example.apoorvdubey.udacitymoviestageone.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.apoorvdubey.udacitymoviestageone.Fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {
    public static final String
            KEY_PREF_POPULAR_MOVIES_SWITCH_ON = "popular_movie_switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
