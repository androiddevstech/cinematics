package com.cinematics.santosh.networkmodule.service.model;

import java.util.List;


public class TrailerModel {
    public List<Results> results;
    public static class Results{
        public String key;
        public String site;
        public String type;
        public String name;
    }
}
