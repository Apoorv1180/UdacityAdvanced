package com.example.apoorvdubey.udacitymovieprojecttwo.view.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.apoorvdubey.udacitymovieprojecttwo.view.ui.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment()).commit();
    }
}
