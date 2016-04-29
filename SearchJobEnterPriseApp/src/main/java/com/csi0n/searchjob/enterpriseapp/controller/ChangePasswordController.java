package com.csi0n.searchjob.enterpriseapp.controller;

import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.ui.activity.ChangePasswordActivity;
import com.csi0n.searchjob.enterpriseapp.utils.bean.EmptyBean;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;

import org.json.JSONException;

/**
 * Created by csi0n on 3/27/16.
 */
public class ChangePasswordController extends BaseController {
    private ChangePasswordActivity mChangePasswordActivity;

    public ChangePasswordController(ChangePasswordActivity changePasswordActivity) {
        this.mChangePasswordActivity = changePasswordActivity;
    }

    public void initChangePassword() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                String newPassword = mChangePasswordActivity.getNewPassword();
                String oldPassword = mChangePasswordActivity.getOldPassword();
                String confirmPassword = mChangePasswordActivity.getConfirmPassword();
                if (TextUtils.isEmpty(oldPassword))
                    CLog.show("请输入旧密码");
                else if (TextUtils.isEmpty(newPassword))
                    CLog.show("请输入新密码");
                else if (TextUtils.isEmpty(confirmPassword))
                    CLog.show("请输入确认密码");
                else if (!newPassword.equals(confirmPassword))
                    CLog.show("两次密码不一致!");
                else if (oldPassword.equals(newPassword))
                    CLog.show("新密码和旧密码不能相同");
                else
                    do_change_password(oldPassword, newPassword);
                break;
            default:
                break;
        }
    }

    private void do_change_password(String oldPassword, String newPassword) {
        PostParams params=getDefaultPostParams(R.string.url_changeInfo);
        params.put("old_password",oldPassword);
        params.put("new_password",newPassword);
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {

            }
        });
        post.post();
    }
}
