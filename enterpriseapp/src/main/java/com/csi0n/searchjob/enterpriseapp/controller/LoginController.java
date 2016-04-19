package com.csi0n.searchjob.enterpriseapp.controller;

import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.ui.activity.LoginActivity;
import com.csi0n.searchjob.enterpriseapp.utils.SharePreferenceManager;
import com.csi0n.searchjob.enterpriseapp.utils.bean.TokenBean;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.Config;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;

import org.json.JSONException;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by chqss on 2016/3/25 0025.
 */
public class LoginController extends BaseController {
    private LoginActivity mLoginActivity;

    public LoginController(LoginActivity loginActivity) {
        this.mLoginActivity = loginActivity;
    }

    public void initLogin() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(mLoginActivity.getUserName()))
                    CLog.show(R.string.str_please_enter_username);
                else if (TextUtils.isEmpty(mLoginActivity.getPassword()))
                    CLog.show(R.string.str_please_enter_password);
                else
                    do_login(mLoginActivity.getUserName(), mLoginActivity.getPassword());
                break;
            default:
                break;
        }
    }
    private void do_login(final String username, String password) {
        mLoginActivity.setLoginEnable(false);
        PostParams params = getDefaultPostParams(R.string.url_enterPriseAuthorize);
        params.put("username", username);
        params.put("password", password);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<TokenBean>(TokenBean.class) {
            @Override
            public void SuccessResult(TokenBean result) throws JSONException {
                Config.DEFAULT_TOKEN=result.getToken();
                SharePreferenceManager.setKeyCachedToken(result.getToken());
                JPushInterface.setAlias(mLoginActivity, username, new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        if (i==com.csi0n.searchjob.enterpriseapp.Config.JPUSH_SUCCESS_CODE)
                            CLog.getInstance().iMessage("绑定ID成功!");
                        else
                            CLog.getInstance().iMessage("绑定ID失败!");
                    }
                });
                mLoginActivity.startMain();
            }

            @Override
            public void ErrorResult(int code, String str) {
                super.ErrorResult(code, str);
                mLoginActivity.setLoginEnable(true);
            }
        });
        post.post();
    }
}
