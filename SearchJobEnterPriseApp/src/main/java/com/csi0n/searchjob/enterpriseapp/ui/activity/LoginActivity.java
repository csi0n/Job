package com.csi0n.searchjob.enterpriseapp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csi0n.searchjob.enterpriseapp.Config;
import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.controller.LoginController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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

    @Event(value = {R.id.btn_login})
    private void onClick(View view) {
        if (mLoginController != null)
            mLoginController.onClick(view);
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title=getString(R.string.str_login);
        actionBarRes.backGone=true;
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
        bundle.putBoolean(Config.MARK_MAIN_ACTIVITY_CHECK_TIME_OUT,false);
        skipActivityWithBundle(this, Main.class,bundle);
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
