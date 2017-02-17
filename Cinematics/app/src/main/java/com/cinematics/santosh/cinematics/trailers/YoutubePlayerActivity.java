package com.cinematics.santosh.cinematics.trailers;

import android.os.Bundle;

import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.Util.Constants;
import com.cinematics.santosh.networkmodule.service.constants.AppIntentConstants;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

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
    private String youtubeKey;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.youtubeplayer_activity);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.trailerYoutube);
        Bundle myBundle = getIntent().getExtras();
        if(myBundle!=null)
            movieId=myBundle.getInt(Constants.MOVIE_ID);
            youtubeKey = myBundle.getString(AppIntentConstants.TRAILER_LAUNCH);
//        fetchDataForMovieDetails(movieId);
        playTrailer();
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

    public void playTrailer() {
        youTubePlayerView.initialize(Constants.YOUBUE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
                    youTubePlayer.setFullscreen(true);
                    // loadVideo() will auto play video
                    // Use cueVideo() method, if you don't want to play it automatically
                    if(youtubeKey != null)
                    youTubePlayer.loadVideo(youtubeKey);

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
