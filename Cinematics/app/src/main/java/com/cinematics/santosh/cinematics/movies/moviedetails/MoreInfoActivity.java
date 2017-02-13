package com.cinematics.santosh.cinematics.movies.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.cinematics.santosh.cinematics.ui.MoreInfoActivityController;
import com.cinematics.santosh.networkmodule.pojos.constants.APIConstants;
import com.cinematics.santosh.networkmodule.pojos.constants.AppIntentConstants;
import com.cinematics.santosh.networkmodule.pojos.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.pojos.model.MovieRecommendationCreditModel;
import com.cinematics.santosh.networkmodule.pojos.model.MoviesModel;
import com.cinematics.santosh.networkmodule.pojos.retrofitclient.RetrofitClient;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;


public class MoreInfoActivity extends MoreInfoActivityController<MovieRecommendationCreditModel> {

    private MoviesModel.Results results;
    private RecyclerView recyclerView;

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

        super.initActivity(results.title,results.overview,
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


    }
}
