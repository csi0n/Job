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
import com.csi0n.searchjob.ui.activity.LoginActivity;
import com.csi0n.searchjob.utils.ProgressLoading;
import com.csi0n.searchjob.utils.SharePreferenceManager;
import com.csi0n.searchjob.utils.bean.LoginUser;
import com.csi0n.searchjob.utils.jpush.CBasicCallBack;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by csi0n on 2015/12/15 0015.
 */
public class LoginController extends BaseController {
    private LoginActivity mLoginActivity;
    private ProgressLoading loading;

    public LoginController(LoginActivity loginActivity) {
        this.mLoginActivity = loginActivity;
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
        HttpPost post = new HttpPost(postParams, new ObjectHttpCallBack<LoginUser>(LoginUser.class) {
            @Override
            public void SuccessResult(final LoginUser result) throws JSONException {
                JMessageClient.login(username, result.getI_password(), new CBasicCallBack() {
                    @Override
                    protected void SuccessResult() {
                        Config.LOGINUSER = result;
                        com.csi0n.searchjob.lib.utils.Config.DEFAULT_TOKEN = result.getToken();
                        SharePreferenceManager.setKeyCachedToken(result.getToken());
                        JPushInterface.setAlias(mLoginActivity, String.valueOf(JMessageClient.getMyInfo().getUserID()), new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                CLog.getInstance().iMessage(i == Config.JMESSAGE_SUCCESS_CODE ? "绑定推送ID成功" : "绑定推送ID失败!");
                            }
                        });
                        if (loading.isShowing())
                            loading.dismiss();
                        mLoginActivity.startMain();
                    }
                    @Override
                    protected void ErrorResult(int code, String s) {
                        super.ErrorResult(code, s);
                        if (loading.isShowing())
                            loading.dismiss();
                        CLog.show("聊天服务器密码被重置,请联系管理员");
                    }
                });
            }
            @Override
            public void ErrorResult(int code, String str) {
                super.ErrorResult(code, str);
                if (loading.isShowing())
                    loading.dismiss();
                mLoginActivity.setLoginEnable(true);
            }
        });
        post.post();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(mLoginActivity.getUserName())) {
                    CLog.getInstance().showError("用户名不能为空!");
                    return;
                } else if (TextUtils.isEmpty(mLoginActivity.getPassword())) {
                    CLog.getInstance().showError("密码不能为空!");
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
