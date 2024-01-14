package com.example.madapp;

import android.content.Context;
import android.content.SharedPreferences;

public class CertificateManager {

    private static final String PREF_NAME = "CertificatePrefs";
    private static final String LAST_DOWNLOAD_TIMESTAMP = "lastDownloadTimestamp";

    // Get the last download timestamp for a specific user from SharedPreferences
    public static long getLastDownloadTimestamp(Context context, String userId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getLong(LAST_DOWNLOAD_TIMESTAMP + "_" + userId, 0);
    }

    // Set the current timestamp as the last download timestamp for a specific user in SharedPreferences
    public static void setLastDownloadTimestamp(Context context, String userId, long timestamp) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putLong(LAST_DOWNLOAD_TIMESTAMP + "_" + userId, timestamp);
        editor.apply();
    }
}

