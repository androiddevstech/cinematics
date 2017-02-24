package com.cinematics.santosh.cinematics.util;

import com.cinematics.santosh.networkmodule.service.model.MoviesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santosh on 2/17/17.
 *
 * This Util is used for filtering the data from TMBD api.
 * For instance upcoming movies will be filtered agianst the todays date with future data.
 * More filters like movies base on companies like HBO, NETFLIX etc
 */

public class ContentFilterUtil {

    private static ContentFilterUtil contentFilterUtil;

    public static ContentFilterUtil getInstance() {
        if (contentFilterUtil == null) {
            contentFilterUtil = new ContentFilterUtil();
        }

        return contentFilterUtil;
    }

    public List<MoviesModel.Results> getUpcomingMovies(final List<MoviesModel.Results> mMoviesList, boolean isDateInFuture) {

        List<MoviesModel.Results> filteredList = new ArrayList<MoviesModel.Results>();

        for(MoviesModel.Results mResults : mMoviesList){
            if(isDateInFuture){
                filteredList.add(mResults);
            }
        }
        return filteredList;
    }

    public List<MoviesModel.Results> filterMoviesByLanguage(final List<MoviesModel.Results> mMoviesList, String language) {

        List<MoviesModel.Results> filteredList = new ArrayList<MoviesModel.Results>();

        for(MoviesModel.Results mResults : mMoviesList){
            if(language.equalsIgnoreCase(mResults.original_language)){
                filteredList.add(mResults);
            }
        }
        return filteredList;
    }


}
