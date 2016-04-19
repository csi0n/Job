package com.csi0n.searchjob.enterpriseapp.ui.activity;

import android.view.View;

import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.controller.UserInfoController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 3/26/16.
 */
@ContentView(R.layout.aty_userinfo)
public class UserInfoActivity extends BaseActivity {
    @Event(value = {R.id.ll_change_password, R.id.ll_my_send, R.id.btn_logout, R.id.ll_change_user_info})
    private void onClick(View view) {
        if (mUserInfoController != null)
            mUserInfoController.onClick(view);
    }

    private UserInfoController mUserInfoController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "企业信息";
        actionBarRes.backGone = false;
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mUserInfoController = new UserInfoController(this);
        mUserInfoController.initUserInfo();
    }

    public void startChangePassword() {
        startActivity(aty, ChangePasswordActivity.class);
    }

    public void startMySend() {
        startActivity(aty, MySendHistoryActivity.class);
    }

    public void startChangeUserInfo() {
        startActivity(aty, ChangeUserInfoActivity.class);
    }
}
