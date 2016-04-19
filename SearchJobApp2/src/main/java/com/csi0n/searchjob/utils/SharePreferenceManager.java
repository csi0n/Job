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

    private static final String FLAG_SHOW_NEARBY_DIALOG = "flag_show_nearby_dialog";

    public static void setFlagShowNearbyDialog(boolean ishow) {
        if (null != sp)
            sp.edit().putBoolean(FLAG_SHOW_NEARBY_DIALOG, ishow).commit();
    }

    public static boolean getFlagShowNearbyDialog() {
        boolean default_value = true;
        if (null != sp)
            return sp.getBoolean(FLAG_SHOW_NEARBY_DIALOG, default_value);
        return default_value;
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

    private static final String FLAG_CHAT_TEXT_SIZE = "flag_chat_text_size";

    public static void setFlagChatTextSize(int mode) {
        if (null != sp)
            sp.edit().putInt(FLAG_CHAT_TEXT_SIZE, mode).commit();
    }

    public static int getFlagChatTextSize() {
        int default_value = Config.CHAT_TXT_SIZE_DEFAULT;
        if (null != sp)
            return sp.getInt(FLAG_CHAT_TEXT_SIZE, default_value);
        return default_value;
    }

}
