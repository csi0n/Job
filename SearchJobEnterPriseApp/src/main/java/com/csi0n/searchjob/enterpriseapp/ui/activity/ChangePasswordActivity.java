package com.csi0n.searchjob.enterpriseapp.ui.activity;

import android.view.View;
import android.widget.EditText;

import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.controller.ChangePasswordController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 3/27/16.
 */
@ContentView(R.layout.aty_change_password)
public class ChangePasswordActivity extends BaseActivity {
    @ViewInject(value = R.id.edit_new_password)
    private EditText mNewPassword;
    @ViewInject(value = R.id.edit_old_password)
    private EditText mOldPassword;
    @ViewInject(value = R.id.edit_confirm_password)
    private EditText mConfirmPassword;
    @Event(value = {R.id.btn_submit})
    private void onClick(View view){
        if (mChangePasswordController!=null)
            mChangePasswordController.onClick(view);
    }
    private ChangePasswordController mChangePasswordController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title="修改密码";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mChangePasswordController = new ChangePasswordController(this);
        mChangePasswordController.initChangePassword();
    }

    public String getNewPassword() {
        return mNewPassword.getText().toString();
    }

    public String getOldPassword() {
        return mOldPassword.getText().toString();
    }

    public String getConfirmPassword() {
        return mConfirmPassword.getText().toString();
    }
}
