package com.csi0n.searchjob.enterpriseapp;

import android.app.Application;

import com.csi0n.searchjob.enterpriseapp.utils.SharePreferenceManager;
import com.csi0n.searchjob.lib.C;
import com.csi0n.searchjob.lib.utils.CLog;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by chqss on 2016/3/24 0024.
 */
public class AppContext extends Application {
    private static AppContext app;
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
       JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        C.init(app,Config.saveFolder,Config.FILE_NAME_SUFFIX);
        SharePreferenceManager.init(app,Config.SHARE_PREFERENCE_NAME);
    }
    public static AppContext getApp(){
        return app;
    }
}
