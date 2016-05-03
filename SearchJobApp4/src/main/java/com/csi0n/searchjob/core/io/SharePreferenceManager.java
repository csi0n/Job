package com.csi0n.searchjob.core.io;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceManager {
    static SharedPreferences sp;

    public static void init(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private static final String FLAG_LOCAL_CONFIG_VERSION = "flag_local_config_version";

    public static String getFlagLocalConfigVersion() {
        if (null != sp)
            return sp.getString(FLAG_LOCAL_CONFIG_VERSION, null);
        return null;
    }

    public static void setFlagLocalConfigVersion(String flagLocalConfigVersion) {
        if (null != sp)
            sp.edit().putString(FLAG_LOCAL_CONFIG_VERSION, flagLocalConfigVersion).commit();
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

    private static final String FLAG_IS_FIRST_START_SEARCH_JOB_FRAGMENT = "flag_is_first_start_search_job_fragment";

    public static void setFlagIsFirstStartSearchJobFragment(boolean is_fitst) {
        if (null != sp)
            sp.edit().putBoolean(FLAG_IS_FIRST_START_SEARCH_JOB_FRAGMENT, is_fitst).commit();
    }

    public static boolean getFlagIsFirstStartSearchJobFragment() {
        boolean default_value = true;
        if (null != sp)
            return sp.getBoolean(FLAG_IS_FIRST_START_SEARCH_JOB_FRAGMENT, default_value);
        return default_value;
    }

}
