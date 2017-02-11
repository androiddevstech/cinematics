package com.cinematics.santosh.networkmodule.pojos.model;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;


public class CelebrityBioDataModel {
    public List<String> also_known_as;
    public String biography;
    public String birthday;
    public String deathday;
    public Integer id;
    public String name;
    public String place_of_birth;
    public Credits movie_credits;
    public Credits tv_credits;

    public static class Credits {
        public List<Cast> cast;

        public static class Cast {
            public String title;
            public String character;

            @JsonSetter("name")
            public void setName(String name) {
                this.title = name;
            }
        }
    }
}
