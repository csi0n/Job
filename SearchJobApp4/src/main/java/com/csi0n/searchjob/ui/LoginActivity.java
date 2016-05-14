package com.csi0n.searchjob.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.event.ext.UserLoginEvent;
import com.csi0n.searchjob.business.pojo.response.ext.GetLoginResponse;
import com.csi0n.searchjob.core.io.SharePreferenceManager;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;
import roboguice.inject.ContentView;

/**
 * Created by chqss on 2016/5/3 0003.
 */
@ContentView(R.layout.aty_login)
public class LoginActivity extends MvpActivity<LoginPresenter, LoginPresenter.ILoginView> {
    @Bind(value = R.id.edit_username)
    EditText mEditUsername;
    @Bind(value = R.id.edit_password)
    EditText mEditPassword;

    @OnClick(value = R.id.btn_login)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = mEditUsername.getText().toString().trim();
                String password = mEditPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username))
                    showError("请输入账号!");
                else if (TextUtils.isEmpty(password))
                    showError("请输入密码!");
                else
                    doLogin(username,password);
                break;
            case R.id.btn_register:
                startRegister();
                break;
            default:
                break;
        }
    }
    void doLogin(String username,String password){
        presenter.doGetLogin(username, password).subscribe(new AdvancedSubscriber<GetLoginResponse>(this) {
            @Override
            public void onHandleSuccess(GetLoginResponse response) {
                super.onHandleSuccess(response);
                Constants.LOGIN_USER=response.user;
                Constants.DEFAULT_TOKEN=response.token;
                SharePreferenceManager.setKeyCachedToken(response.token);
                EventBus.getDefault().post(new UserLoginEvent(response.user));
                finish();
            }

            @Override
            public void onHandleFail(String message, Throwable throwable) {
                super.onHandleFail(message, throwable);
                showError(message);
            }
        });
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "登录";
        super.setActionBarRes(actionBarRes);
    }

    private void startRegister() {
        startActivity(this, RegisterActivity.class);
    }
}
