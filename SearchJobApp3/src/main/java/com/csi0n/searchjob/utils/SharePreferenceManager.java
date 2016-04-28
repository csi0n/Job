package com.csi0n.searchjob.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.csi0n.searchjob.Config;

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

    public static final String KEY_DEVICE_ID="key_device_id";
    public static void setKeyDeviceID(String deviceID){
        if (null!=sp)
            sp.edit().putString(KEY_DEVICE_ID,deviceID).commit();
    }
    public static String getKeyDeviceId(){
        if (null!=sp)
            return sp.getString(KEY_DEVICE_ID,null);
        return null;
    }

    private static final String FLAG_NOTICE_MODE = "flag_notice_mode";

    public static void setFlagNoticeMode(int mode) {
        if (null != sp) {
            sp.edit().putInt(FLAG_NOTICE_MODE, mode).commit();
        }
    }

    public static int getFlagNoticeMode() {
        int default_code = Config.NOTI_MODE_DEFAULT;
        if (null != sp) {
            return sp.getInt(FLAG_NOTICE_MODE, default_code);
        }
        return default_code;
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
