package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csi0n.searchjob.AppContext;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.LoginController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by csi0n on 2015/12/14 0014.
 */
@ContentView(R.layout.aty_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.edit_username)
    public EditText mEditUsername;
    @ViewInject(R.id.edit_password)
    private EditText mEditPassword;
    private LoginController mLoginController;
    @ViewInject(R.id.btn_login)
    private Button mLogin;

    @Event(value = {R.id.btn_login, R.id.btn_register})
    private void onClick(View view) {
        if (mLoginController != null)
            mLoginController.onClick(view);
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title=getString(R.string.str_login);
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initWidget() {
        mTVBack.setVisibility(View.GONE);
        mTVMore.setVisibility(View.GONE);
        mLoginController = new LoginController(this);
        mLoginController.initLogin();
    }
    public void startMain() {
        Bundle bundle=new Bundle();
        bundle.putBoolean("is_auto_login",false);
        skipActivityWithBundle(this, Main.class,bundle);
    }
    public void startRegister() {
        startActivity(this, RegisterActivity.class);
    }
    public String getUserName() {
        return mEditUsername.getText().toString().trim();
    }
    public String getPassword() {
        return mEditPassword.getText().toString().trim();
    }
    public void setLoginEnable(final boolean enable) {
        mLogin.setEnabled(enable);
    }
}
