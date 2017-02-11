package com.cinematics.santosh.databasemodule.userpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.cinematics.santosh.databasemodule.DatabaseConstants;

public class UserPreferences {
    private static final String PREF_FILE_NAME = "com.cinematics.santosh.APP_PREF_FILE";
    private final SharedPreferences mPreference;

    private UserPreferences(Context context) {
        mPreference = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static UserPreferences getAppPreferenceInstance(Context context) {
        return new UserPreferences(context);
    }

    public void setPreferenceData(String key, String value) {
        mPreference.edit().putString(key, value).apply();
    }

    public String getPreferenceData(String key) {
        return mPreference.getString(key, null);
    }

    public void removePreferenceData(String key) {
        mPreference.edit().remove(key).apply();
    }

    public void clearAllPreferenceData() {
        mPreference.edit().clear().apply();
    }

    public void clearAllUserPreferenceData() {
        mPreference.edit()
                .remove(DatabaseConstants.USER_ID)
                .remove(DatabaseConstants.USER_EMAIL)
                .remove(DatabaseConstants.USER_NAME)
                .remove(DatabaseConstants.USER_PROFILE_PIC)
                .apply();
    }
}
