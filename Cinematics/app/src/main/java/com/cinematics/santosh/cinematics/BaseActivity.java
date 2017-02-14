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
import com.cinematics.santosh.databasemodule.DatabaseConstants;
import com.cinematics.santosh.databasemodule.userpreferences.UserPreferences;
import com.cinematics.santosh.networkmodule.pojos.constants.APIConstants;
import com.cinematics.santosh.networkmodule.pojos.model.GenreModel;
import com.cinematics.santosh.networkmodule.pojos.retrofitclient.RetrofitClient;
import com.cinematics.santosh.networkmodule.pojos.retrofitclient.networkwrappers.CallbackWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;


public class BaseActivity extends AppCompatActivity implements MoviesLaunchFragment.OnFragmentInteractionListener,
        View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        TVSeriesFragment.OnFragmentInteractionListener,
        TabFragmentController.OnFragmentInteractionListener,
        MoviesFragment.OnFragmentInteractionListener,
        FavoriteMoviesFragment.OnFragmentInteractionListener

{

    private UserPreferences mPrefManager;

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
        mPrefManager = UserPreferences.getAppPreferenceInstance(this);
        mApiClient.getMovieGenreList().enqueue(movieGenreResponse);


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

    private Callback<GenreModel> movieGenreResponse = new CallbackWrapper<GenreModel>() {
        @Override
        public void onNetworkResponse(Call<GenreModel> call, retrofit2.Response<GenreModel> response) {
            HashMap<Integer, String> movieMap = new HashMap<>(26);

            for (GenreModel.Genres genres : response.body().genres)
                movieMap.put(genres.id, genres.name);

            APIConstants.getInstance().setMovieGenreMap(movieMap);
            mMovieGenreLoaded = true;

            try {
                if (mPrefManager.getPreferenceData(DatabaseConstants.MOVIE_GENRE_CACHE) == null) {
                    mPrefManager.setPreferenceData(DatabaseConstants.MOVIE_GENRE_CACHE,
                            APIConstants.getInstance().getObjectMapper().writeValueAsString(movieMap));
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onNetworkFailure(Call<GenreModel> call, Throwable t) {
//            Utils.displayNetworkErrorSnackBar(findViewById(android.R.id.content), AppMainActivity.this);
        }
    };

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
