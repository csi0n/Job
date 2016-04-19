package com.csi0n.searchjob.lib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.csi0n.searchjob.lib.R;

import org.xutils.x;

import java.text.SimpleDateFormat;

/**
 * Created by csi0n on 2015/12/14 0014.
 */
public class StringUtils {
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new java.util.Date());
    }

    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            return getString(R.string.str_can_not_find_version_name);
        }
    }

    public static String getString(@StringRes int id) {
        return x.app().getString(id);
    }

    public static int getColor(@ColorRes int id) {
        return x.app().getResources().getColor(id);
    }

    public static String[] getNewsTitle() {
        String data[] = {"0", "1", "2", "3", "4", "5", "6"};
        return data;
    }

    public static String[] getCompanyWorkDetailTop() {
        String data[] = {"返现岗位", "企业介绍", "经理人", "评论"};
        return data;
    }

    public static String switchNewsURL(int title) {
        switch (title) {
            case 0:
                return "http://hao.360.cn";
            case 1:
                return "http://h5.mse.360.cn/product/navi/news.html";
            case 2:
                return "http://h5.mse.360.cn/product/navi/video.html";
            case 3:
                return "http://h5.mse.360.cn/product/navi/mall.html";
            case 4:
                return "http://h5.mse.360.cn/product/navi/xiaohua.html";
            case 5:
                return "http://h5.mse.360.cn/product/navi/life.html";
            case 6:
                return "http://h5.mse.360.cn/product/navi/zhaopin.html";
            default:
                return null;
        }
    }
}
