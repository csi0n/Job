package com.csi0n.searchjob.ui.activity;

import android.view.View;
import android.widget.EditText;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ChangePasswordController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 4/25/16.
 */
@ContentView(R.layout.aty_change_password)
public class ChangePasswordActivity extends BaseActivity {
    @ViewInject(value = R.id.edit_old_password)
    private EditText mEditOldPassword;
    @ViewInject(value = R.id.edit_new_password)
    private EditText mEditNewPassword;
    @ViewInject(value = R.id.edit_re_new_password)
    private EditText mEditReNewPassword;

    @Event(value = {R.id.btn_change})
    private void onClick(View view) {
        if (mChangePasswordController != null)
            mChangePasswordController.onClick(view);
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title="修改密码";
        super.setActionBarRes(actionBarRes);
    }

    public String getEditOldPassword() {
        return mEditOldPassword.getText().toString();
    }

    public String getEditNewPassword() {
        return mEditNewPassword.getText().toString();
    }

    public String getEditReNewPassword() {
        return mEditReNewPassword.getText().toString();
    }

    private ChangePasswordController mChangePasswordController;

    @Override
    protected void initWidget() {
        mChangePasswordController = new ChangePasswordController(this);
        mChangePasswordController.initChangePassword();
    }
}
