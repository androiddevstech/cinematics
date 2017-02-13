package com.cinematics.santosh.cinematics.moreinfo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.Util.Constants;
import com.cinematics.santosh.cinematics.Util.UriBuilderUtil;
import com.cinematics.santosh.cinematics.Util.VolleyTon;
import com.cinematics.santosh.cinematics.databinding.ActivityCommonInfoBinding;
import com.cinematics.santosh.cinematics.trailers.YoutubePlayerActivity;
import com.google.gson.Gson;

import com.cinematics.santosh.cinematics.Model.MovieDetails;

public class MoreInfoActivity extends AppCompatActivity {

    private int movieId;
    private MovieDetails movieDetails;
    private ActivityCommonInfoBinding mCommonInfoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCommonInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_common_info);
        mCommonInfoBinding.toolbar.setNavigationIcon(R.drawable.ic_menu_gallery);
        mCommonInfoBinding.toolbar.setTitle(" ");
        mCommonInfoBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle myBundle = getIntent().getExtras();
        if(myBundle!=null)
            movieId=Integer.parseInt(myBundle.getString(Constants.MOVIE_ID));
        fetchDataForMovieDetails(movieId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mCommonInfoBinding.movieDetailsBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoreInfoActivity.this,YoutubePlayerActivity.class);
                intent.putExtra(Constants.MOVIE_ID,movieId);
                startActivity(intent);
            }
        });
    }

    private void fetchDataForMovieDetails(int movieId) {
        String url = UriBuilderUtil.getURLForMovieDetailsWithID(movieId);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson= new Gson();
                movieDetails = gson.fromJson(response,MovieDetails.class);
                String backdrop_path= movieDetails.getBackdrop_path();
                String poster_path = movieDetails.getPoster_path();
                final String movieTitle = movieDetails.getOriginal_title();

                String url = Constants.IMAGE_BASE_URL + backdrop_path;
                String url_poster = Constants.IMAGE_BASE_URL + poster_path;

                ImageLoader imageLoader = VolleyTon.getInstance().getImageLoader();

//                mCommonInfoBinding.movieDetailsBackdrop.setImageUrl(url, imageLoader);
//                mCommonInfoBinding.movieDetailsPoster.setImageUrl(url_poster,imageLoader);
                mCommonInfoBinding.toolbar.setTitle(" ");

                mCommonInfoBinding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

                    boolean isShow = false;
                    int scrollRange = -1;
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.getTotalScrollRange();
                        }
                        if (scrollRange + verticalOffset == 0) {
                            mCommonInfoBinding.coolapsingToolbarLayout.setTitle(movieTitle);
                            isShow = true;
                        } else if(isShow) {
                            mCommonInfoBinding.coolapsingToolbarLayout.setTitle(" ");
                            isShow = false;
                        }
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyTon.getInstance().addToRequestQueue(stringRequest);
    }
}
