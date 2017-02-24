package com.cinematics.santosh.cinematics.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by santosh on 2/16/17.
 */

public class DateFormatter {

    private static DateFormatter dataFormatter;

    public static DateFormatter getInstance() {
        if (dataFormatter == null) {
            dataFormatter = new DateFormatter();
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

    public String getCurrentDate(){

        String currentDate = "";
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = df.format(c.getTime());

        return currentDate;
    }


}
