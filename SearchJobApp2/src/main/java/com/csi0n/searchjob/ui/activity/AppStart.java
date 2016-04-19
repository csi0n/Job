package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.AppStartController;
import com.csi0n.searchjob.lib.utils.StringUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by csi0n on 2015/12/14 0014.
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

    public void startMain() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_auto_login", true);
        skipActivityWithBundle(this, Main.class, bundle);
    }

    public void startLogin() {
        skipActivity(this, LoginActivity.class);
    }
}
