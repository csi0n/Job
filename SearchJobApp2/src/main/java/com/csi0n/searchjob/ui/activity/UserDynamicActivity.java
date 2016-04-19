package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.UserDynamicController;
import com.csi0n.searchjob.ui.widget.ScrollViewComplete;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 2015/12/27 0027.
 */
@ContentView(R.layout.aty_user_dynamic)
public class UserDynamicActivity extends BaseActivity {
    private UserDynamicController mUserDynamicController;
    @ViewInject(value = R.id.list)
    public ListView mList;
    @ViewInject(value = R.id.rl_modulename_refresh)
    public BGARefreshLayout mBGARefreshLayout;

    @Event(value = {R.id.right_btn})
    private void onClick(View view) {
        if (mUserDynamicController != null)
            mUserDynamicController.onClick(view);
    }
    public boolean isSelf=false;
    @Override
    protected void initWidget() {
        isSelf = getIntent().getBooleanExtra(Config.MARK_USER_DYNAMIC_ACTIVITY_IS_SELF, false);
        mUserDynamicController = new UserDynamicController(this);
        mUserDynamicController.initUserDynamic();
        if (isSelf) {
            mTVTitle.setText(getString(R.string.str_my_dynamic));
        } else {
            mTVTitle.setText(getString(R.string.str_friend_dynamic));
        }
    }
    public void startSetting() {
        startActivity(this, SettingAndFeedActivity.class);
    }

    public void startWriteDynamic() {
        startActivity(this, WriteDynamicActivity.class);
    }
}
