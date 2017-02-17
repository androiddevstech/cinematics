package com.cinematics.santosh.cinematics.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by santosh on 2/16/17.
 */

public class DataFormatter {

    private static DataFormatter dataFormatter;

    public static DataFormatter getInstance() {
        if (dataFormatter == null) {
            dataFormatter = new DataFormatter();
        }

        return dataFormatter;
    }

    public String releaseDateFormatter(String releaseDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date formattedDate = null;
        try {
            formattedDate = simpleDateFormat.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        simpleDateFormat.applyPattern("MMM dd yyyy");

        return simpleDateFormat.format(formattedDate);
    }
}
