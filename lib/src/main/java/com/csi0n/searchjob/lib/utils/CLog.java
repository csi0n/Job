package com.csi0n.searchjob.lib.utils;

import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;


import org.xutils.x;

/**
 * Created by csi0n on 15/9/21.
 */
public class CLog {
    private final String TAG = this.getClass().getName();
    private static CLog instance;
    private boolean isDebug;

    private CLog() {
        this.isDebug = Config.DEBUG;
    }

    public static CLog getInstance() {
        if (instance == null)
            instance = new CLog();
        return instance;
    }

    public void iMessage(String message) {
        if (isDebug)
            Log.i(TAG, message);
    }

    public void eMessage(String message) {
        if (isDebug)
            Log.e(TAG, message);
    }

    public void dMessage(String message) {
        if (isDebug)
            Log.d(TAG, message);
    }

    public void showJMessageClientError(int i, String s) {
        if (i == 801003)
            show("用户名密码错误!");
        else
            show("聊天服务器发生错误错误信息:" + s + "错误号:" + i);
    }

    public void showError(String str) {
        show(str);
    }

    public void showNormalError(int code, String str) {
        show("与服务器通讯发生错误,失败号:" + code + "失败信息:" + str);
    }

    public static void show(CharSequence text) {
        if (text.length() < 10) {
            Toast.makeText(x.app(), text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(x.app(), text, Toast.LENGTH_LONG).show();
        }
    }

    public static void show(@StringRes int resId) {
        show(x.app().getString(resId));
    }
}
