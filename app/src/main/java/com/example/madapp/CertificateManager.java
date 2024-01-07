package com.example.madapp;

import android.content.Context;
import android.content.SharedPreferences;

public class CertificateManager {

    private static final String PREF_NAME = "CertificatePrefs";
    private static final String LAST_ISSUANCE_TIMESTAMP = "lastIssuanceTimestamp";
    private static final String LAST_DOWNLOAD_TIMESTAMP = "lastDownloadTimestamp";

    public static long getLastIssuanceTimestamp(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getLong(LAST_ISSUANCE_TIMESTAMP, 0);
    }

    public static void setLastIssuanceTimestamp(Context context, long timestamp) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putLong(LAST_ISSUANCE_TIMESTAMP, timestamp);
        editor.apply();
    }

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

