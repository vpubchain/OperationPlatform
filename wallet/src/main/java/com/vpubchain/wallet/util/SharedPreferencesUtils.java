package com.vpubchain.wallet.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/3/15.
 */
public class SharedPreferencesUtils {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String spName ="jiulingwallet";

    public SharedPreferencesUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putInfo(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getInfo(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void cleanInfo() {
        editor.clear();
        editor.commit();
    }
}
