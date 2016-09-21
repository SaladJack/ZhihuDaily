package com.example.administrator.zhihudaily.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPrefUtils {

    private static final String SHARED_PREF_IS_NIGHE_MODE = "shared_pref_is_nighe_mode";


    private static SharedPreferences getDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isNightMode(Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(SHARED_PREF_IS_NIGHE_MODE, false);
    }

    public static void setNightMode(Context context, boolean isNightMode) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean(SHARED_PREF_IS_NIGHE_MODE, isNightMode).apply();
    }

}
