package com.jiuling.commonbusiness.util;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesUtils {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String LOGIN = "login";
    public static final String ACCEE_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String PHONE = "phone";
    public static final String EXPIRATION = "expiration";

    public SharedPreferencesUtils() {
    }

    public SharedPreferencesUtils(Context context, String spName) {
        sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putInfo(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getInfo(String key) {
        return sharedPreferences.getString(key, "0");
    }

    public void cleanInfo() {
        editor.clear();
        editor.commit();
    }

    public String getExpiration(Context context){
        sharedPreferences = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return getInfo(EXPIRATION);
    }


    public String getAcceeToken(Context context){
        sharedPreferences = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return getInfo(ACCEE_TOKEN);
    }

    public String getPhone(Context context){
        sharedPreferences = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return getInfo(PHONE);
    }


}
