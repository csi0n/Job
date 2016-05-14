package com.csi0n.searchjob.ui;
import android.os.Bundle;
import android.widget.TextView;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.core.system.SystemUtils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.BindString;
import roboguice.inject.ContentView;

/**
 * Created by chqss on 2016/4/29 0029.
 */
@ContentView(R.layout.aty_start)
public class AppStartActivity extends MvpActivity<AppStartPresenter, AppStartPresenter.IAppStartView> implements AppStartPresenter.IAppStartView {
    @Bind(value = R.id.tv_ver) TextView tv_ver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        gotoNext();
    }
    @BindString(value = R.string.version) String VERSION;
    private void init() {
        tv_ver.setText(VERSION+SystemUtils.getVersion());
    }

    private void gotoNext() {
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(getContext(),MainActivity.class);
            }
        }, 1000);
    }
}
