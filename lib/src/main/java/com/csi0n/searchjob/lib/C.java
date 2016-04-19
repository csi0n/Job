package com.csi0n.searchjob.lib;

import android.app.Application;

import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.Config;
import com.csi0n.searchjob.lib.utils.FileUtils;

import org.xutils.x;

/**
 * Created by chqss on 2016/3/24 0024.
 */
public class C {
    public static  void init(Application application,String floder_name,String crash_file_name){
        CrashHandler.create(application,floder_name,crash_file_name);
        x.Ext.init(application);
        x.Ext.setDebug(Config.DEBUG);
        FileUtils.init(floder_name);
    }
}
