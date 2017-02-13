package com.cinematics.santosh.networkmodule.pojos.constants;


public interface NetworkConstants {
    String API_KEY = "dd2e1845a54d939af026b01b22b4dbc3";
    String GRACENOTE_API_KEY ="gdmqbd5rajkh7x3fw3y6rauk";
    String NETWORK_BASE_URL = "http://api.themoviedb.org/3/";
    String GRACENOTE_NETWORK_BASE_URL = "http://data.tmsapi.com/v1.1/movies/showings";

    //  URL'S PERTAINING TO IMAGES
    String IMG_SIMILAR_ITEMS_POSTER_URL = "http://image.tmdb.org/t/p/w92";
    String IMG_BASE_POSTER_URL = "http://image.tmdb.org/t/p/w342";
    String IMG_BASE_DIALOG_POSTER_URL = "http://image.tmdb.org/t/p/w300";
    String IMG_BASE_BACKDROP_URL = "https://image.tmdb.org/t/p/w780";
    String FACEBOOK_PROFILE_PIC_URL = "https://graph.facebook.com/v2.6/userid/picture?type=normal";

    String IMG_GAME_POSTER_URL = "http://image.tmdb.org/t/p/w185";
    String IMG_GAME_BACKDROP_URL = "http://image.tmdb.org/t/p/w342";

    // WIDTH AND HEIGHT DIMENSIONS OF THE IMAGES: (FORMAT: WIDTH X HEIGHT).
    int[] backdropDim = {300, 169};     // aspect ratio.
}
