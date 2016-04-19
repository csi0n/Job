package com.csi0n.searchjob.enterpriseapp.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharePreferenceManager {
    static SharedPreferences sp;

    public static void init(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private static final String KEY_CACHED_TOKEN = "im_setting_token";

    public static void setKeyCachedToken(String cookie) {
        if (null != sp)
            sp.edit().putString(KEY_CACHED_TOKEN, cookie).commit();
    }

    public static String getKeyCachedToken() {
        if (null != sp)
            return sp.getString(KEY_CACHED_TOKEN, null);
        return null;
    }
    private static final String FLAG_IS_CONFIG_IS_SAVE="flag_is_config_is_save";
    public static void setFlagIsConfigIsSave(boolean is_fitst) {
        if (null != sp)
            sp.edit().putBoolean(FLAG_IS_CONFIG_IS_SAVE, is_fitst).commit();
    }

    public static boolean getFlagIsConfigIsSave() {
        boolean default_value = false;
        if (null != sp)
            return sp.getBoolean(FLAG_IS_CONFIG_IS_SAVE, default_value);
        return default_value;
    }
}
