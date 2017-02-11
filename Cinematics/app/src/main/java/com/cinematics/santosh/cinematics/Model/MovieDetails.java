package com.cinematics.santosh.cinematics.Model;


import java.util.List;

public class MovieDetails {
    private int id;
    private String poster_path;
    private String backdrop_path;
    private Videos videos;
    private String original_title;
    private String overview;
    private List<Genres> genres;
    public int getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public Videos getVideos() {
        return videos;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public List<Genres> getGenres() {
        return genres;
    }
}
