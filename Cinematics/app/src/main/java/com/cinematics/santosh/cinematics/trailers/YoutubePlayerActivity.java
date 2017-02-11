package com.cinematics.santosh.cinematics.trailers;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.Util.Constants;
import com.cinematics.santosh.cinematics.Util.UriBuilderUtil;
import com.cinematics.santosh.cinematics.Util.VolleyTon;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;

import com.cinematics.santosh.cinematics.Model.MovieDetails;
import com.cinematics.santosh.cinematics.Model.ResultsNowPlaying;

/**
 * Created by santosh on 2/1/17.
 */

public class YoutubePlayerActivity extends YouTubeBaseActivity {

    private YouTubePlayerView youTubePlayerView;
    private MovieDetails movieDetails;
    private YouTubePlayerLaunch youTubePlayerLaunchListener;
    private int movieId;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.youtubeplayer_activity);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.trailerYoutube);
        Bundle myBundle = getIntent().getExtras();
        if(myBundle!=null)
            movieId=myBundle.getInt(Constants.MOVIE_ID);
        fetchDataForMovieDetails(movieId);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void fetchDataForMovieDetails(final int movieId) {
        String url = UriBuilderUtil.getURLForMovieDetailsWithID(movieId);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson= new Gson();
                movieDetails = gson.fromJson(response,MovieDetails.class);
                playTrailer(movieDetails);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyTon.getInstance().addToRequestQueue(stringRequest);
    }

    public void playTrailer(final  MovieDetails mMovieDetails) {
        youTubePlayerView.initialize(Constants.YOUBUE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    youTubePlayer.setFullscreen(true);
                    // loadVideo() will auto play video
                    // Use cueVideo() method, if you don't want to play it automatically
                    youTubePlayer.cueVideo(mMovieDetails.getVideos().getResults().get(0).getKey());

                    // Hiding player controls
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    public interface YouTubePlayerLaunch{
        void onClick(ResultsNowPlaying resultsNowPlaying);
    }


}
