package com.example.godaa.movieapplacation.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.godaa.movieapplacation.Interface.Callbackinterface;
import com.example.godaa.movieapplacation.R;
import com.example.godaa.movieapplacation.fragments.DetailFragment;
import com.example.godaa.movieapplacation.fragments.GridMainFragment;
import com.example.godaa.movieapplacation.model.Movie;

public class MainActivity extends AppCompatActivity implements Callbackinterface {
    ImageView back;
    TextView title;
    // Callbackinterface callbackinterface;
    private Movie movie;
    boolean mainOrDetailFragment = true;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // do nothing, just override

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        movie = savedInstanceState.getParcelable(getResources().getString(R.string.movie));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getResources().getString(R.string.movie), movie);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        back = (ImageView) findViewById(R.id.back_image);
        title = (TextView) findViewById(R.id.title_acct);


        loadMainFragment();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMainFragment();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mainOrDetailFragment) {
            finish();
        } else {
            loadMainFragment();

        }

    }

    void loadDetailFragment(Movie movie) {
        mainOrDetailFragment = false;
        boolean mTwoBane = getResources().getBoolean(R.bool.mTwoBane);
        if (mTwoBane) {
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment,
                    DetailFragment.newInstance(this, movie)).commit();

        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,
                    DetailFragment.newInstance(this, movie)).commit();
            title.setText(this.getResources().getString(R.string.movieDetails));
            back.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void loadMainFragment() {
        mainOrDetailFragment = true;
        back.setVisibility(View.GONE);
        title.setText(this.getResources().getString(R.string.movies));
        getSupportFragmentManager().beginTransaction().addToBackStack("jdk").replace(R.id.main_fragment
                , new GridMainFragment()).commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.menuSort);
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this
                , R.array.pref_units_options, android.R.layout.simple_spinner_dropdown_item); //  create the adapter from a StringArray
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item); // get the spinner
        spinner.setAdapter(mSpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String selectedCategory = "";
                switch (position) {

                    case 0:
                        editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_popular));
                        editor.apply();
                        loadMainFragment();
                        break;
                    case 1:
                        editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_top_rated));
                        editor.apply();
                        loadMainFragment();
                        break;
                    case 2:
                        editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_favourited));
                        editor.apply();
                        loadMainFragment();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }*/
        if (id == R.id.menuSort) {

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void OnItemSelcted(Movie movie) {
        this.movie = movie;
        loadDetailFragment(movie);
    }
}
