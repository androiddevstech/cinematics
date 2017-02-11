package com.cinematics.santosh.cinematics;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.cinematics.santosh.cinematics.databinding.AppMainActivityBinding;
import com.cinematics.santosh.cinematics.movies.FavoriteMoviesFragment;
import com.cinematics.santosh.cinematics.movies.MoviesLaunchFragment;
import com.cinematics.santosh.cinematics.movies.movies.MoviesFragment;
import com.cinematics.santosh.cinematics.tvseries.TVSeriesFragment;
import com.cinematics.santosh.cinematics.ui.TabFragmentController;
import com.cinematics.santosh.databasemodule.userpreferences.UserPreferences;
import com.cinematics.santosh.networkmodule.pojos.retrofitclient.RetrofitClient;


public class BaseActivity extends AppCompatActivity implements MoviesLaunchFragment.OnFragmentInteractionListener,
        View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        TVSeriesFragment.OnFragmentInteractionListener,
        TabFragmentController.OnFragmentInteractionListener,
        MoviesFragment.OnFragmentInteractionListener,
        FavoriteMoviesFragment.OnFragmentInteractionListener

{

    private boolean mMovieGenreLoaded, mTvGenreLoaded;
    private UserPreferences mPreferences;
    private RetrofitClient.APIClient mApiClient;
    private AppMainActivityBinding mBinding;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingViews();
        mApiClient = RetrofitClient.getInstance().getNetworkClient();
        mPreferences = UserPreferences.getAppPreferenceInstance(this);


    }

    private void bindingViews(){
        mBinding = DataBindingUtil.setContentView(this,R.layout.app_main_activity);
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.movies_fragment_container,new MoviesLaunchFragment()).commit();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (item.getItemId()){
            case R.id.action_movies:
                transaction.replace(R.id.movies_fragment_container,new MoviesLaunchFragment()).commit();
                break;
            case R.id.action_tvshows:
                transaction.replace(R.id.movies_fragment_container,new TVSeriesFragment()).commit();
                break;
            case R.id.action_celeb:
                break;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
