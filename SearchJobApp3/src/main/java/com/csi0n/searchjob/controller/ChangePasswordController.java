package com.csi0n.searchjob.controller;

import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.bean.EmptyModel;
import com.csi0n.searchjob.ui.activity.ChangePasswordActivity;

import org.json.JSONException;

/**
 * Created by csi0n on 4/25/16.
 */
public class ChangePasswordController extends BaseController {
    private ChangePasswordActivity mChangePasswordActivity;

    public ChangePasswordController(ChangePasswordActivity mChangePasswordActivity) {
        this.mChangePasswordActivity = mChangePasswordActivity;
    }

    public void initChangePassword() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                String old_pass_word = mChangePasswordActivity.getEditOldPassword();
                String new_pass_word = mChangePasswordActivity.getEditNewPassword();
                String re_new_pass_word = mChangePasswordActivity.getEditReNewPassword();
                if (TextUtils.isEmpty(old_pass_word))
                    CLog.show("旧密码不能为空!");
                else if (TextUtils.isEmpty(new_pass_word))
                    CLog.show("新密码不能为空!");
                else if (TextUtils.isEmpty(re_new_pass_word))
                    CLog.show("确认新密码不能为空!");
                else if (!new_pass_word.equals(re_new_pass_word))
                    CLog.show("确认密码不一致!");
                else
                    do_change_password(old_pass_word, new_pass_word);
                break;
            default:
                break;

        }
    }

    private void do_change_password(String old_pass_word, String new_pass_word) {
        PostParams params = getDefaultPostParams(R.string.url_user_changeInfo);
        params.put("old_pass_number", old_pass_word);
        params.put("new_pass_number", new_pass_word);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyModel>(EmptyModel.class) {
            @Override
            public void SuccessResult(EmptyModel result) throws JSONException {
                CLog.show("修改成功!");
                mChangePasswordActivity.finish();
            }
        });
        post.post();
    }
}
