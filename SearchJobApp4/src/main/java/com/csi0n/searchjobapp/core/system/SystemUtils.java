package com.csi0n.searchjobapp.core.system;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.csi0n.searchjobapp.R;
import com.csi0n.searchjobapp.app.App;
import com.csi0n.searchjobapp.core.io.FileUtils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import butterknife.BindString;

/**
 * Created by chqss on 2016/5/1 0001.
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
    protected static final String PREFS_FILE = "gank_device_id.xml";
    protected static final String PREFS_DEVICE_ID = "gank_device_id";
    protected static String uuid;
    public static  String getUDID()
    {
        final String androidId = Settings.Secure.getString(App.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
        try {
            if (!"9774d56d682e549c".equals(androidId)) {
                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
            } else {
                final String deviceId = ((TelephonyManager) App.getInstance().getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();
                uuid = deviceId!=null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")).toString() : UUID.randomUUID().toString();
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return uuid;
    }
    public static String getVersion() {
        try {
            PackageManager manager = App.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(App.getInstance().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            return "没有找到版本号";
        }
    }
}
