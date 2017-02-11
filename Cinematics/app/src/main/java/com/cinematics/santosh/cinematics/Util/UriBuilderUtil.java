package com.cinematics.santosh.cinematics.Util;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class UriBuilderUtil {

    public static String getUrlForMoviesInNearbyTheatre(){
        Uri.Builder builder= new Uri.Builder();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());

        builder.scheme("http")
                .authority("data.tmsapi.com")
                .appendPath("v1.1")
                .appendPath("movies")
                .appendPath("showings")
                .appendQueryParameter("startDate",formattedDate)
                .appendQueryParameter("zip","90245")
                .appendQueryParameter("api_key",Constants.API_KEY_GRACENOTE);

        return builder.build().toString();
    }


    public static String getURLForNowPlaying(){
        Uri.Builder builder= new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("now_playing")
                .appendQueryParameter("api_key",Constants.API_KEY);

        return builder.build().toString();
    }

    public static String getURLForNowPlayingWithPageNumber(int pageNumber){
        Uri.Builder builder= new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("now_playing")
                .appendQueryParameter("api_key",Constants.API_KEY)
                .appendQueryParameter("page",pageNumber+"");

        return builder.build().toString();
    }

    public static String getURLForMovieDetailsWithID(int id){
        Uri.Builder builder= new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(id+"")
                .appendQueryParameter("api_key",Constants.API_KEY)
                .appendQueryParameter("append_to_response","images,videos");

        return builder.build().toString();
    }
}
