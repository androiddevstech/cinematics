package com.cinematics.santosh.networkmodule.service.model;

import java.util.List;


public class MoviesTVCastingModel {
    public List<Cast> cast;

    public static class Cast {
        public String character;
        public String name;
        public String profile_path;
    }
}
