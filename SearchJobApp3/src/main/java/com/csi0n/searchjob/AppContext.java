package com.csi0n.searchjob;

import android.app.Application;

import com.csi0n.searchjob.lib.C;
import com.csi0n.searchjob.utils.SharePreferenceManager;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by csi0n on 2015/12/14 0014.
 */
public class AppContext extends Application {
    public static AppContext getApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        getApplicationContext = this;
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        SharePreferenceManager.init(getApplicationContext(), Config.IM_CONFIGS);
        C.init(this, Config.saveFolder, Config.FILE_NAME_SUFFIX);
    }
}
