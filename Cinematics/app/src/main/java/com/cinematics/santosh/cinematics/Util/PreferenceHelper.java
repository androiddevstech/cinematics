package com.cinematics.santosh.cinematics.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private SharedPreferences prefs;
    public static final String API_ACCESS_TOKEN = "access_token";

    public PreferenceHelper(Context context) {
        prefs = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
    }


    public void saveToken(String access_token) {
        final SharedPreferences.Editor edit = prefs.edit();
        edit.putString(API_ACCESS_TOKEN, access_token);
        edit.commit();
    }


    public String getToken() {
        return prefs.getString(API_ACCESS_TOKEN, null);
    }
}
