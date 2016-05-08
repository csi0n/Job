package com.csi0n.searchjob.app;

import android.app.Application;

import com.csi0n.searchjob.core.io.DbManager;
import com.csi0n.searchjob.core.io.SharePreferenceManager;
import com.csi0n.searchjob.core.string.Constants;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class App extends Application {

    private static App instance;
    public static App getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initInject();
        initDb();
        SharePreferenceManager.init(instance, Constants.Preference_Name);
    }
    void initInject(){
        InjectHelp.init(this);
    }
    void initDb(){
        DbManager.initDb();}
}
