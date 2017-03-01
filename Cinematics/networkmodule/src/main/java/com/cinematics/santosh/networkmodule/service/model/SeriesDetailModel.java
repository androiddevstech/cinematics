package com.cinematics.santosh.networkmodule.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by santosh on 2/24/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeriesDetailModel {
    public String backdrop_path;
    public int id;
    public int number_of_seasons;
    public String overview;
    public String poster_path;
    public String name;
    public int vote_average;
    public int vote_count;
    public List<Integer> genre_ids;
    public String first_air_date;

}
