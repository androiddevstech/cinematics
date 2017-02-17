package com.cinematics.santosh.networkmodule.service.model;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;


public class ImageModel {
    public List<Backdrop> backdrops;

    @JsonSetter("profiles")
    public void setProfiles(List<Backdrop> profiles) {
        backdrops = profiles;
    }

    public static class Backdrop {
        public String file_path;
    }
}
