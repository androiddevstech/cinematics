package com.cinematics.santosh.networkmodule.service.retrofitclient;

import com.cinematics.santosh.networkmodule.service.constants.APIConstants;
import com.cinematics.santosh.networkmodule.service.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.service.model.CelebrityBioDataModel;
import com.cinematics.santosh.networkmodule.service.model.CelebrityModel;
import com.cinematics.santosh.networkmodule.service.model.GenericSearchResultsModel;
import com.cinematics.santosh.networkmodule.service.model.GenreModel;
import com.cinematics.santosh.networkmodule.service.model.ImageModel;
import com.cinematics.santosh.networkmodule.service.model.MovieRecommendationCreditModel;
import com.cinematics.santosh.networkmodule.service.model.MoviesModel;
import com.cinematics.santosh.networkmodule.service.model.SeriesModel;
import com.cinematics.santosh.networkmodule.service.model.TVRecommendationCreditsModel;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class RetrofitClient implements Interceptor {

    private static final RetrofitClient mRetrofitClientInstance = new RetrofitClient();
    private APIClient mApiClient;
    private OkHttpClient mOkHttpClient;


    private RetrofitClient() {

        mOkHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(this)
                .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(NetworkConstants.NETWORK_BASE_URL)
                        .addConverterFactory(JacksonConverterFactory.create(APIConstants.getInstance().getObjectMapper()))
                        .client(mOkHttpClient)
                        .build();
                mApiClient = retrofit.create(APIClient.class);
    }


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        HttpUrl url = request.url()
                .newBuilder()
                .addQueryParameter("api_key", NetworkConstants.API_KEY)
                .build();

        request = request.newBuilder()
                .url(url)
                .build();

        return chain.proceed(request);

    }


    public static RetrofitClient getInstance() {
                return mRetrofitClientInstance;
    }


    public APIClient getNetworkClient() {
        return mApiClient;
    }

    public void cancelAllRequests() {
        mOkHttpClient.dispatcher().cancelAll();
    }

    public interface APIClient {

        // MOVIES       --------------------------------------------------------------------------------------

        @GET("discover/movie?" /*+
                "primary_release_date.gte=2017-02-22" +
                "&primary_release_date.lte=2017-03-03" */+
                "&sort_by=release_date.desc" +
                "release_date.gte=2000"+
                "&with_original_language=en" +
                "&with_release_type=3" +
                "&region=US")
        Call<MoviesModel> getUpcomingMovies(@Query("page") int page,
                                            @Query("2017-01-01") String todaysDate);

        @GET("movie/now_playing")
        Call<MoviesModel> getNowPlayingMovies(@Query("page") int page);

        @GET("discover/movie?" +
                "sort_by=release_date.desc" +
                "&with_original_language=te" +
                "&with_release_type=3" +
                "&primary_release_date.lte=2016-02-03" +
                "&region=US&with_original_language=en" +
                "&vote_average.gte=6" +
                "&sort_by=vote_count.desc" +
                "&with_release_type=4")
        Call<MoviesModel> getPopularMovies(@Query ("page") int page);



        @GET("discover/movie?primary_release_date.gte=2017-03-04" +
                "&primary_release_date.lte=2017-03-18" +
                "&sort_by=release_date.desc" +
                "&with_original_language=en" +
                "&with_release_type=3")
        Call<MoviesModel> getFrenchMovies(@Query("page") int page);

        @GET("movie/{movie_id}?append_to_response=recommendations,credits,videos")
        Call<MovieRecommendationCreditModel> getMovieDetails(@Path("movie_id") int movieId);

        @GET("genre/movie/list")
        Call<GenreModel> getMovieGenreList();

        @GET("search/movie")
        Call<MoviesModel> getMovieSearchResults(@Query("query") String queryString, @Query("page") int pageNumber);

        @GET("search/movie")
        Call<GenericSearchResultsModel> getMovieSearchSuggestions(@Query("query") String queryString, @Query("page") int pageNumber);

        @GET("movie/{movie_id}/images")
        Call<ImageModel> getMovieImages(@Path("movie_id") int movieId);


        // TV SERIES    --------------------------------------------------------------------------------------

        @GET("tv/on_the_air")
        Call<SeriesModel> getOnTheAirTVSeries(@Query("page") int page);

        @GET("tv/top_rated")
        Call<SeriesModel> getTopRatedTVSeries(@Query("page") int page);

        @GET("/tv/{tv_id}")
        Call<SeriesModel> getSeriesDetails(@Path("tv_id") int tvId);

        @GET("tv/{tv_id}?append_to_response=recommendations,credits,videos")
        Call<TVRecommendationCreditsModel> getTVSeriesDetails(@Path("tv_id") int tvId);

        @GET("genre/tv/list")
        Call<GenreModel> getTVGenreList();

        @GET("search/tv")
        Call<SeriesModel> getTVSearchResults(@Query("query") String queryString, @Query("page") int pageNumber);

        @GET("search/tv")
        Call<GenericSearchResultsModel> getTVSeriesSearchSuggestions(@Query("query") String queryString, @Query("page") int pageNumber);

        @GET("tv/{tv_id}/images")
        Call<ImageModel> getTVSeriesImages(@Path("tv_id") int tvId);


        // CELEBRITIES  --------------------------------------------------------------------------------------

        @GET("person/popular")
        Call<CelebrityModel> getPopularCelebrities(@Query("page") int page);

        @GET("person/{celeb_id}?append_to_response=movie_credits,tv_credits")
        Call<CelebrityBioDataModel> getCelebrityDetails(@Path("celeb_id") int celebId);

        @GET("search/person")
        Call<CelebrityModel> getCelebritySearchResults(@Query("query") String queryString, @Query("page") int pageNumber);

        @GET("search/person")
        Call<GenericSearchResultsModel> getCelebritySearchSuggestions(@Query("query") String queryString, @Query("page") int pageNumber);

        @GET("person/{celeb_id}/images")
        Call<ImageModel> getCelebrityImages(@Path("celeb_id") int celebId);


    }
}
