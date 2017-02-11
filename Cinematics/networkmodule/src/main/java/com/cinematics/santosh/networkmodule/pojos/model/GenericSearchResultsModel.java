package com.cinematics.santosh.networkmodule.pojos.model;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;



public class GenericSearchResultsModel {
    public List<Results> results;

    public static class Results {
        public String name;

        @JsonSetter("title")
        public void setTitle(String title) {
            this.name = title;
        }
    }
}
