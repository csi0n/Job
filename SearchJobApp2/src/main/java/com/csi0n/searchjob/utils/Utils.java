package com.csi0n.searchjob.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.csi0n.searchjob.AppContext;

/**
 * Created by chqss on 2016/2/21 0021.
 */
public class Utils {
    public static boolean isWiFi(Context cxt) {
        ConnectivityManager cm = (ConnectivityManager) cxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // wifi的状态：ConnectivityManager.TYPE_WIFI
        // 3G的状态：ConnectivityManager.TYPE_MOBILE
        NetworkInfo.State state = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        return NetworkInfo.State.CONNECTED == state;
    }
    public static void restartApplication() {
        final Intent intent = AppContext.getApplicationContext.getPackageManager().getLaunchIntentForPackage(AppContext.getApplicationContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AppContext.getApplicationContext.startActivity(intent);
    }
}
