package com.csi0n.searchjobapp.app;

import android.app.Application;

import butterknife.ButterKnife;

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
    }
    void initInject(){
        InjectHelp.init(this);
    }
}
