package com.cinematics.santosh.cinematics.movies.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.cinematics.santosh.cinematics.ui.MoreInfoActivityController;
import com.cinematics.santosh.networkmodule.service.constants.APIConstants;
import com.cinematics.santosh.networkmodule.service.constants.AppIntentConstants;
import com.cinematics.santosh.networkmodule.service.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.service.model.MovieRecommendationCreditModel;
import com.cinematics.santosh.networkmodule.service.model.MoviesModel;
import com.cinematics.santosh.networkmodule.service.model.MoviesTVCastingModel;
import com.cinematics.santosh.networkmodule.service.model.TrailerModel;
import com.cinematics.santosh.networkmodule.service.retrofitclient.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class MoreInfoActivity extends MoreInfoActivityController<MovieRecommendationCreditModel> {

    private MoviesModel.Results results;

    public static void startActivityIntent(Context context, MoviesModel.Results results) {
        Intent intent = new Intent(context, MoreInfoActivity.class);
        intent.putExtra(AppIntentConstants.MOVIE_DETAILS, results);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        results = getIntent().getParcelableExtra(AppIntentConstants.MOVIE_DETAILS);
        String genre = APIConstants.getInstance().getMovieGenreList(results.genre_ids, this);

        String releaseDate = results.release_date;

        super.initActivity(results.title,
                results.vote_count,
                results.vote_average,
                results.overview,
                releaseDateFormatter(releaseDate),
                genre,
                results.poster_path != null ? results.poster_path : results.backdrop_path,
                results.backdrop_path != null ? results.backdrop_path : results.poster_path,
                ((float) NetworkConstants.backdropDim[1]) / NetworkConstants.backdropDim[0]);


        establishNetworkCall();


    }

    @Override
    protected void establishNetworkCall() {
        RetrofitClient.getInstance()
                .getNetworkClient()
                .getMovieDetails(results.id)
                .enqueue(this);
    }



    @Override
    protected void onNetworkResponse(Call<MovieRecommendationCreditModel> call, Response<MovieRecommendationCreditModel> response) {

        //-------------------------
        //  Similar Movies
        //-------------------------
        List<MoviesModel.Results> similarResults = response.body().recommendations.results;

        if (similarResults == null || similarResults.size() == 0) {
            hideRecyclerView();
        } else {
            showRecommendations(similarResults);
        }

        //------------------------------
        //  Trailers
        //------------------------------
        List<TrailerModel.Results> videoResults = response.body().videos.results;

        if(videoResults == null || videoResults.size() == 0){
            hideTrailersRecyclerView();
        }else {
            showTrailersView(videoResults);
        }

        if (videoResults != null) {
            for (TrailerModel.Results result : videoResults) {
                if (result.key != null && result.site != null && result.type != null && result.site.equalsIgnoreCase("youtube")) {
                    super.mYouTubeKey = result.key;
                    break;
                }
            }
        }

        //-----------------------
        //  Limited Cast  & Crew
        //-----------------------

        List<MoviesTVCastingModel.Cast> casts = response.body().credits.cast;
        List<MoviesTVCastingModel.Cast> topbilledCast = new ArrayList<MoviesTVCastingModel.Cast>();


        StringBuilder sb = new StringBuilder();
        if (casts != null && casts.size() > 0){
            for(int i=0 ; i< casts.size() && i<6;i++){
                topbilledCast.add(casts.get(i));
            }
            showLimitedCastAndCrewView(topbilledCast);

        }

        //-----------------------
        //  Full Cast  & Crew
        //-----------------------

        List<MoviesTVCastingModel.Cast> fullCastAndCrew = response.body().credits.cast;

        if (fullCastAndCrew != null && fullCastAndCrew.size() > 0){
        }


    }
}
