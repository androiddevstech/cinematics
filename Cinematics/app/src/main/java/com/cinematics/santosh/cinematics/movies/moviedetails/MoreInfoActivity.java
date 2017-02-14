package com.cinematics.santosh.cinematics.movies.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.cinematics.santosh.cinematics.ui.MoreInfoActivityController;
import com.cinematics.santosh.networkmodule.pojos.constants.APIConstants;
import com.cinematics.santosh.networkmodule.pojos.constants.AppIntentConstants;
import com.cinematics.santosh.networkmodule.pojos.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.pojos.model.MovieRecommendationCreditModel;
import com.cinematics.santosh.networkmodule.pojos.model.MoviesModel;
import com.cinematics.santosh.networkmodule.pojos.model.MoviesTVCastingModel;
import com.cinematics.santosh.networkmodule.pojos.model.TrailerModel;
import com.cinematics.santosh.networkmodule.pojos.retrofitclient.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class MoreInfoActivity extends MoreInfoActivityController<MovieRecommendationCreditModel> {

    private MoviesModel.Results results;
    private TrailerModel.Results trailerResults;

    public static void startActivityIntent(Context context, MoviesModel.Results results) {
        Intent intent = new Intent(context, MoreInfoActivity.class);
        intent.putExtra(AppIntentConstants.MOVIE_DETAILS, results);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        results = getIntent().getParcelableExtra(AppIntentConstants.MOVIE_DETAILS);
        trailerResults = getIntent().getParcelableExtra(AppIntentConstants.TRAILER_LAUNCH);
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
        //  Cast  & Crew
        //-----------------------

        List<MoviesTVCastingModel.Cast> casts = response.body().credits.cast;

        StringBuilder sb = new StringBuilder();
        if (casts != null){

        }
        /*    for (int i = 0; i < casts.size() && i < 5; i++) {
                sb.append(Utils.toString(casts.get(i).name)).append(" as ").append(Utils.toString(casts.get(i).character)).append("\n");
            }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);

        if (textViewMovieCast != null)
            textViewMovieCast.setText(Utils.toString(sb));*/

    }
}
