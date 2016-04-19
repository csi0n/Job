package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.AppStartController;
import com.csi0n.searchjob.lib.utils.StringUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by chqss on 2016/4/19 0019.
 */
@ContentView(R.layout.aty_start)
public class AppStart extends BaseActivity {
    @ViewInject(R.id.iv_start_pic)
    private ImageView mIVStartPic;
    @ViewInject(R.id.tv_ver)
    private TextView mTVVer;
    private AppStartController mAppStartController;

    @Override
    protected void initWidget() {
        mIVStartPic.setImageResource(R.mipmap.pic_app_start);
        mTVVer.setText("版本:" + StringUtils.getVersion(this));
        mAppStartController = new AppStartController(this);
        mAppStartController.initAppStart();
    }

    public void startMain(boolean is_login) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Config.MARK_MAIN_IS_NEED_AUTO_LOGIN, is_login);
        skipActivityWithBundle(this, Main.class, bundle);
    }
}
