package com.csi0n.searchjob.controller;

import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.widget.ProgressLoading;
import com.csi0n.searchjob.model.TokenAuthorizeModel;
import com.csi0n.searchjob.model.UserModel;
import com.csi0n.searchjob.model.event.UserLoginEvent;
import com.csi0n.searchjob.ui.activity.LoginActivity;
import com.csi0n.searchjob.utils.SharePreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by csi0n on 4/23/16.
 */
public class LoginController extends BaseController {
    private LoginActivity mLoginActivity;
    private ProgressLoading loading;

    public LoginController(LoginActivity mLoginActivity) {
        this.mLoginActivity = mLoginActivity;
    }

    public void initLogin() {

    }

    private void ActionLogin(final String username, final String password) {
        loading = new ProgressLoading(mLoginActivity.aty, "登录中...");
        loading.show();
        mLoginActivity.setLoginEnable(false);
        PostParams postParams = getDefaultPostParams(R.string.url_Authorize);
        postParams.put("username", username);
        postParams.put("password", password);
        HttpPost post = new HttpPost(postParams, new ObjectHttpCallBack<TokenAuthorizeModel>(TokenAuthorizeModel.class) {
            @Override
            public void SuccessResult(final TokenAuthorizeModel result) throws JSONException {
                EventBus.getDefault().post(new UserLoginEvent(result.getUser()));
                Config.LOGIN_USER = result.getUser();
                com.csi0n.searchjob.lib.utils.Config.DEFAULT_TOKEN=result.getToken();
                SharePreferenceManager.setKeyCachedToken(result.getToken());
                mLoginActivity.finish();
            }

            @Override
            public void ErrorResult(int code, String str) {
                super.ErrorResult(code, str);
                mLoginActivity.setLoginEnable(true);
            }

            @Override
            public void onFinished() {
                super.onFinished();
                if (loading.isShowing())
                    loading.dismiss();
            }
        });
        post.post();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(mLoginActivity.getUserName())) {
                    CLog.show("用户名不能为空!");
                    return;
                } else if (TextUtils.isEmpty(mLoginActivity.getPassword())) {
                    CLog.show("密码不能为空!");
                    return;
                } else {
                    ActionLogin(mLoginActivity.getUserName(), mLoginActivity.getPassword());
                }
                break;
            case R.id.btn_register:
                mLoginActivity.startRegister();
                break;
            default:
                break;
        }
    }
}
