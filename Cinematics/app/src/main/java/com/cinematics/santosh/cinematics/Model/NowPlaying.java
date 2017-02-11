package com.cinematics.santosh.cinematics.Model;

import java.util.ArrayList;


public class NowPlaying {
    private String page;
    private ArrayList<ResultsNowPlaying> results;
    private int total_pages;


    public String getPage() {
        return page;
    }

    public ArrayList<ResultsNowPlaying> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }
}
