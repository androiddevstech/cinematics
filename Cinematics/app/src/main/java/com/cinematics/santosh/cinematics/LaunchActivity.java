package com.cinematics.santosh.cinematics;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cinematics.santosh.cinematics.Util.Constants;
import com.cinematics.santosh.cinematics.Util.UriBuilderUtil;
import com.cinematics.santosh.cinematics.Util.VolleyTon;
import com.cinematics.santosh.cinematics.databinding.ActivityMainBinding;
import com.cinematics.santosh.cinematics.moreinfo.MoreInfoActivity;
import com.cinematics.santosh.cinematics.movies.movies.MoviesAdapter;
import com.cinematics.santosh.networkmodule.pojos.retrofitclient.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;

import com.cinematics.santosh.cinematics.Model.NowPlaying;
import com.cinematics.santosh.cinematics.Model.ResultsNowPlaying;

public class LaunchActivity extends AppCompatActivity {

    private MoviesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int totalNumberOfPages=0;
    private MovieStationOnClickListener movieStationOnClickListener;
    private ProgressBar progressBar;
    private ActivityMainBinding mBinding;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;

    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;
    private boolean FAB_Status = false;
    protected RetrofitClient.APIClient mApiClient;
    private FragmentManager mFragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mBinding.nowPlayingProgressBar.setIndeterminate(true);
        mBinding.toolbar.setTitle("Popular Movies");

        mFragmentManager = getSupportFragmentManager();
//        mFragmentManager.beginTransaction().add(R.id.movies_fragment_container, new MoviesLaunchFragment()).commit();

        movieStationOnClickListener = new MovieStationOnClickListener() {
            @Override
            public void onClick(int position,View view, ResultsNowPlaying resultsNowPlaying) {
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(LaunchActivity.this,
                        Pair.create(view,"selectedMovie")
                ).toBundle();

                Intent intent = new Intent(LaunchActivity.this,MoreInfoActivity
                        .class);
                intent.putExtra(Constants.MOVIE_ID,resultsNowPlaying.getId());
                startActivity(intent,bundle);
            }
        };

        mAdapter = new MoviesAdapter(LaunchActivity.this,new ArrayList<ResultsNowPlaying>(),movieStationOnClickListener);
        mLayoutManager = new GridLayoutManager(this,1);
        mBinding.latestMoviesCarousel.setLayoutManager(mLayoutManager);
        mBinding.latestMoviesCarousel.setAdapter(mAdapter);

        show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);

        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FAB_Status == false){
                    expandFabLayout();
                    FAB_Status = true;
                }else{
                    hideFAB();
                    FAB_Status = false;
                }
            }
        });
    }

    private void expandFabLayout(){

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mBinding.fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (mBinding.fab1.getWidth() * 3);
        layoutParams.bottomMargin += (int) (mBinding.fab1.getHeight() * 0.25);
        mBinding.fab1.setLayoutParams(layoutParams);
        mBinding.fab1.startAnimation(show_fab_1);
        mBinding.fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) mBinding.fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (mBinding.fab2.getWidth() * 2.12);
        layoutParams2.bottomMargin += (int) (mBinding.fab2.getHeight() * 2.12);
        mBinding.fab2.setLayoutParams(layoutParams2);
        mBinding.fab2.startAnimation(show_fab_2);
        mBinding.fab2.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) mBinding.fab3.getLayoutParams();
        layoutParams3.rightMargin += (int) (mBinding.fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (mBinding.fab3.getHeight() * 3);
        mBinding.fab3.setLayoutParams(layoutParams3);
        mBinding.fab3.startAnimation(show_fab_3);
        mBinding.fab3.setClickable(true);
    }

    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mBinding.fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (mBinding.fab1.getWidth() * 3);
        layoutParams.bottomMargin -= (int) (mBinding.fab1.getHeight() * 0.25);
        mBinding.fab1.setLayoutParams(layoutParams);
        mBinding.fab1.startAnimation(hide_fab_1);
        mBinding.fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) mBinding.fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (mBinding.fab2.getWidth() * 2.12);
        layoutParams2.bottomMargin -= (int) (mBinding.fab2.getHeight() * 2.12);
        mBinding.fab2.setLayoutParams(layoutParams2);
        mBinding.fab2.startAnimation(hide_fab_2);
        mBinding.fab2.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) mBinding.fab3.getLayoutParams();
        layoutParams3.rightMargin -= (int) (mBinding.fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (mBinding.fab3.getHeight() * 3);
        mBinding.fab3.setLayoutParams(layoutParams3);
        mBinding.fab3.startAnimation(hide_fab_3);
        mBinding.fab3.setClickable(false);
    }

    private void fetchLatestMovieData(int pageNumber) {
        String url = UriBuilderUtil.getURLForNowPlayingWithPageNumber(pageNumber);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson= new Gson();
                NowPlaying nowPlaying= gson.fromJson(response, NowPlaying.class);
                mAdapter.updateNowPlaying(nowPlaying.getResults());
                totalNumberOfPages=nowPlaying.getTotal_pages();
                mBinding.latestMoviesCarousel.setVisibility(View.VISIBLE);
                mBinding.nowPlayingProgressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mBinding.latestMoviesCarousel.setVisibility(View.VISIBLE);
                mBinding.nowPlayingProgressBar.setVisibility(View.GONE);

            }
        });
        VolleyTon.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the LaunchActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public interface MovieStationOnClickListener{
        void onClick(int position,View v,ResultsNowPlaying resultsNowPlaying);
    }
}
