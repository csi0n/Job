package com.csi0n.searchjob.enterpriseapp.controller;

import android.view.View;

import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.ui.activity.UserInfoActivity;
import com.csi0n.searchjob.enterpriseapp.utils.SharePreferenceManager;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.SystemUtils;

import org.xutils.x;

/**
 * Created by csi0n on 3/26/16.
 */
public class UserInfoController extends BaseController {
    private UserInfoActivity mUserInfoActivity;

    public UserInfoController(UserInfoActivity userInfoActivity) {
        this.mUserInfoActivity = userInfoActivity;
    }

    public void initUserInfo() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_change_password:
                mUserInfoActivity.startChangePassword();
                break;
            case R.id.ll_my_send:
                mUserInfoActivity.startMySend();
                break;
            case R.id.btn_logout:
                do_logout();
                break;
            case R.id.ll_change_user_info:
                mUserInfoActivity.startChangeUserInfo();
                break;
            default:
                break;
        }
    }

    public void do_logout() {
        SharePreferenceManager.setKeyCachedToken("");
        SystemUtils.restartApplication(x.app());
    }
}
