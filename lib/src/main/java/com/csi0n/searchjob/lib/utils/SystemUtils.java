package com.csi0n.searchjob.lib.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

/**
 * Created by chqss on 2016/2/21 0021.
 */
public class SystemUtils {
    public static boolean isWiFi(Context cxt) {
        ConnectivityManager cm = (ConnectivityManager) cxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // wifi的状态：ConnectivityManager.TYPE_WIFI
        // 3G的状态：ConnectivityManager.TYPE_MOBILE
        NetworkInfo.State state = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        return NetworkInfo.State.CONNECTED == state;
    }
    public static void restartApplication(Application application) {
        final Intent intent = application.getPackageManager().getLaunchIntentForPackage(application.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        application.startActivity(intent);
    }
    public static void openPic(Activity titleBarFragment, int id) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            titleBarFragment.startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    id);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            titleBarFragment.startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    id);
        }
    }
    public static void openCamera(Activity titleBarFragment,int id,String saveFolder){
        Intent intent;
        intent=new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(FileUtils.getSaveFile(saveFolder,"TEMP_PIC.png")));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        titleBarFragment.startActivityForResult(intent, id);
    }
}
