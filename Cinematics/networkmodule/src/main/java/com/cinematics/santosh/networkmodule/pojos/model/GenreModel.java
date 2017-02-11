package com.cinematics.santosh.networkmodule.pojos.model;

import java.util.List;



public class GenreModel {
    public List<Genres> genres;

    public static class Genres {
        public Integer id;
        public String name;
    }
}
