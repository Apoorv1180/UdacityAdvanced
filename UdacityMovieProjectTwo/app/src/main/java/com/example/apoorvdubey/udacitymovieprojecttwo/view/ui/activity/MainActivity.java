package com.example.apoorvdubey.udacitymovieprojecttwo.view.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.apoorvdubey.udacitymovieprojecttwo.R;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.DetailResult;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Movie;
import com.example.apoorvdubey.udacitymovieprojecttwo.view.callback.OnHeadlineSelectedListener;
import com.example.apoorvdubey.udacitymovieprojecttwo.view.ui.fragment.DetailFragment;
import com.example.apoorvdubey.udacitymovieprojecttwo.view.ui.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.apoorvdubey.udacitymovieprojecttwo.view.ui.fragment.DetailFragment.ARG_RESPONSE_LIST;

public class MainActivity extends AppCompatActivity implements OnHeadlineSelectedListener {
    private SharedPreferences mSharedPreferences;
    private MainFragment mainFragment;
    private DetailFragment detailFragment;
    private String sortOrder;
    private int position;
    private Movie response;
    private List<DetailResult> resultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sortOrder = getSortOrder();
        setToolbar();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.sort_order), sortOrder);
            mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.framelayout, mainFragment);
            fragmentTransaction.commit();
        } else {
            fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
        }
    }

    private String getSortOrder() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return mSharedPreferences.getString(getString(R.string.sp_key_sort_order_list), getResources().getString(R.string.POPULAR));
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(sortOrder);
        getSupportActionBar().setTitle(sortOrder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onArticleSelected(int position, List<DetailResult> response) {
        this.position = position;
        this.resultList = response;
        detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(DetailFragment.ARG_POSITION, position);
        args.putParcelableArrayList(ARG_RESPONSE_LIST, (ArrayList<? extends Parcelable>) resultList);
        detailFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, detailFragment, getString(R.string.detail_frag));
        transaction.addToBackStack(getString(R.string.detail_frag));
        transaction.commit();
    }
}
