package com.cinematics.santosh.cinematics.tvseries.tvseriesmoreinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cinematics.santosh.cinematics.ui.SeriesMoreInfoActivityController;
import com.cinematics.santosh.cinematics.util.DateFormatter;
import com.cinematics.santosh.networkmodule.service.constants.APIConstants;
import com.cinematics.santosh.networkmodule.service.constants.AppIntentConstants;
import com.cinematics.santosh.networkmodule.service.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.service.model.SeriesDetailModel;
import com.cinematics.santosh.networkmodule.service.model.SeriesModel;
import com.cinematics.santosh.networkmodule.service.model.TVRecommendationCreditsModel;
import com.cinematics.santosh.networkmodule.service.retrofitclient.RetrofitClient;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by santosh on 2/16/17.
 */

public class TVSeriesMoreInfoActivity extends SeriesMoreInfoActivityController<TVRecommendationCreditsModel> {

    private SeriesModel.Results results;

    public static void startActivityIntent(Context context, SeriesModel.Results results){
        Intent intent = new Intent(context,TVSeriesMoreInfoActivity.class);
        intent.putExtra(AppIntentConstants.SERIES_DETAILS,results);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        results = getIntent().getParcelableExtra(AppIntentConstants.SERIES_DETAILS);

        String genre = APIConstants.getInstance().getMovieGenreList(results.genre_ids, this);

        String releaseDate = results.first_air_date;
        String formattedData = DateFormatter.getInstance().releaseDateFormatter(releaseDate);

        super.initAcitivity(results.name,
                results.vote_count,
                results.vote_average,
                results.overview,
                formattedData,
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
                .getTVSeriesDetails(results.id)
                .enqueue(this);
    }



    @Override
    protected void onNetworkResponse(Call<TVRecommendationCreditsModel> call, Response<TVRecommendationCreditsModel> response) {

    }
}
