package com.cinematics.santosh.networkmodule.service.model;

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
