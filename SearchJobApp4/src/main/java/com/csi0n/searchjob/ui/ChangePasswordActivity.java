package com.csi0n.searchjob.ui;

import android.widget.EditText;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.response.ext.GetChangeUserInfoResponse;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.OnClick;
import roboguice.inject.ContentView;

/**
 * Created by chqss on 2016/5/13 0013.
 */
@ContentView(R.layout.aty_change_password)
public class ChangePasswordActivity extends MvpActivity<ChangePasswordPresenter, ChangePasswordPresenter.IChangePassword> {

    @Bind(R.id.edit_old_password)
    EditText editOldPassword;
    @Bind(R.id.edit_new_password)
    EditText editNewPassword;
    @Bind(R.id.edit_re_new_password)
    EditText editReNewPassword;

    @OnClick(R.id.btn_change)
    public void onClick() {
        if (getOldPassword().isEmpty())
            showError("请输入旧密码");
        else if (getNewPassword().isEmpty())
            showError("请输入新密码");
        else if (getReNewPassword().isEmpty())
            showError("请确认新密码");
        else if (!getNewPassword().equals(getReNewPassword()))
            showError("新密码和确认密码不一致");
        else
            DoPostNewPassWord(getOldPassword(), getNewPassword());
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "修改密码";
        super.setActionBarRes(actionBarRes);
    }

    void DoPostNewPassWord(String old_password, String new_password) {
        presenter.doGetChangeUserInfo(old_password, new_password).subscribe(new AdvancedSubscriber<GetChangeUserInfoResponse>() {
            @Override
            public void onHandleSuccess(GetChangeUserInfoResponse response) {
                super.onHandleSuccess(response);
                showToast("修改成功");
                finish();
            }
        });
    }

    String getOldPassword() {
        return editOldPassword.getText().toString();
    }

    String getNewPassword() {
        return editReNewPassword.getText().toString();
    }

    String getReNewPassword() {
        return editReNewPassword.getText().toString();
    }
}
