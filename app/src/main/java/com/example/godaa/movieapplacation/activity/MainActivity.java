package com.example.godaa.movieapplacation.activity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.godaa.movieapplacation.Interface.Callbackinterface;
import com.example.godaa.movieapplacation.R;
import com.example.godaa.movieapplacation.fragments.DetailFragment;
import com.example.godaa.movieapplacation.fragments.GridMainFragment;
import com.example.godaa.movieapplacation.model.Movie;
import com.example.godaa.movieapplacation.model.MoviesData;

public class MainActivity extends AppCompatActivity implements Callbackinterface {
    ImageView back;
    TextView title;
    Fragment mcurrentFragment = null;
    GridMainFragment mGridMainFragment = null;
    String TAG_RETAINED_FRAGMENT;
    // Callbackinterface callbackinterface;
    private Movie movie;
    boolean mainOrDetailFragment = true;
    MoviesData moviesData = null;
    int currentFragment;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // do nothing, just override

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        movie = savedInstanceState.getParcelable(getResources().getString(R.string.movie));
        moviesData = savedInstanceState.getParcelable(getResources().getString(R.string.movies));
         currentFragment = savedInstanceState.getInt("currentFragment");
        // mcurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState,"myfragment");

        if (currentFragment == 0) {
          //  loadMainFragment();
        }else {
            loadDetailFragment(movie);
        }
      /*  if (mcurrentFragment instanceof GridMainFragment) {
            loadMainFragment();
        } else if (mcurrentFragment instanceof DetailFragment) {
            loadDetailFragment(movie);

        }*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(getResources().getString(R.string.movie), movie);
        // getSupportFragmentManager().putFragment(outState,"myfragment",mcurrentFragment);

        outState.putParcelable(getResources().getString(R.string.movies), moviesData);
           outState.putInt("currentFragment",mainOrDetailFragment?0:1);

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
    /*    Spinner spinner = (Spinner) findViewById(R.id.spinnermovie);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pref_units_options, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);*/

        //  Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (savedInstanceState == null) {

            loadMainFragment();
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMainFragment();
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (GridMainFragment.moviesData != null)
            moviesData = GridMainFragment.moviesData;
        if (isFinishing()) {
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            // we will not need this fragment anymore, this may also be a good place to signal
            // to the retained fragment object to perform its own cleanup.
            fm.beginTransaction().remove(mGridMainFragment).commit();
        }
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
        Fragment fragment = mcurrentFragment == null ? DetailFragment.newInstance(this, movie) : mcurrentFragment;
        if (mTwoBane) {
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment,
                    fragment).commit();
            mcurrentFragment = getSupportFragmentManager().findFragmentById(R.id.detail_fragment);


        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,
                    fragment).commit();
            title.setText(this.getResources().getString(R.string.movieDetails));
            title.setMaxLines(1);
            back.setVisibility(View.VISIBLE);
            mcurrentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    SharedPreferences sharedPreferences;
    String selected_category;
    String current_category = "popular";

    private void loadMainFragment() {

        mainOrDetailFragment = true;
        back.setVisibility(View.GONE);
        title.setText(this.getResources().getString(R.string.movies));
        //  Fragment fragment = mcurrentFragment == null ? DetailFragment.newInstance(this, movie) : mcurrentFragment;
        // if (mcurrentFragment != null) {
        /*    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment
                    ,mcurrentFragment).commit();*/
        // }else{
  /*      sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        selected_category = sharedPreferences.getString(getString(R.string.pref_unit_key),
                getString(R.string.pref_units_popular));*/
      /*  boolean sameCategory = true;
        if (selected_category .equals(current_category) ) {
             sameCategory = true;
        }else {
            sameCategory = true;

        }*/
        // find the retained fragment on activity restarts
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        mGridMainFragment = (GridMainFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

       /* getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment
                    , GridMainFragment.newInstance(getApplicationContext(), moviesData, current_category)).commit();
*/
        if (mGridMainFragment == null) {
            // add the fragment
            mGridMainFragment = GridMainFragment.newInstance(getApplicationContext(), moviesData, current_category);
            fm.beginTransaction().replace(R.id.main_fragment,mGridMainFragment, TAG_RETAINED_FRAGMENT).commit();
            // load data from a data source or perform any calculation
            // mGridMainFragment.setData(loadMyData());
        }

        //   }
        //mcurrentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
   /*     getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.menuSort);
        ArrayAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this
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
                      //  editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_popular));
                        current_category=getString(R.string.pref_units_popular);
                        loadMainFragment();

                        break;
                    case 1:
                      //  editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_top_rated));
                        current_category= getString(R.string.pref_units_top_rated);
                        loadMainFragment();

                        break;
                    case 2:
                      //  editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_favourited));
                        current_category = getString(R.string.pref_units_favourited);
                        loadMainFragment();

                        break;
                }
              //  editor.apply();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
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
            switch (id) {

                case R.id.menu_popular:
                    //  editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_popular));
                    current_category=getString(R.string.pref_units_popular);
                    loadMainFragment();

                    break;
                case R.id.menu_top_rated:
                    //  editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_top_rated));
                    current_category= getString(R.string.pref_units_top_rated);
                    loadMainFragment();

                    break;
                case R.id.menu_favourite:
                    //  editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_favourited));
                    current_category = getString(R.string.pref_units_favourited);
                    loadMainFragment();

                    break;
            }
            //  editor.apply();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void OnItemSelcted(Movie movie) {
        this.movie = movie;
        loadDetailFragment(movie);
    }

  /*  @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String selectedCategory = "";
        switch (i) {

            case 0:
                //  editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_popular));
                current_category = getString(R.string.pref_units_popular);
                loadMainFragment();

                break;
            case 1:
                //  editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_top_rated));
                current_category = getString(R.string.pref_units_top_rated);
                loadMainFragment();

                break;
            case 2:
                //  editor.putString(getString(R.string.pref_unit_key), getString(R.string.pref_units_favourited));
                current_category = getString(R.string.pref_units_favourited);
                loadMainFragment();

                break;
        }
        //  editor.apply();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/
}
