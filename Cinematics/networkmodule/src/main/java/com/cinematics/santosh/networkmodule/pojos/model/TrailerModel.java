package com.cinematics.santosh.networkmodule.pojos.model;

import java.util.List;


public class TrailerModel {
    public List<Results> results;
    public static class Results{
        public String key;
        public String site;
        public String type;
    }
}
