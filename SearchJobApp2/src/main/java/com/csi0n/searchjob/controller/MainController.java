package com.csi0n.searchjob.controller;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.CheckUpdate;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.ui.activity.Main;
import com.csi0n.searchjob.utils.ProgressLoading;
import com.csi0n.searchjob.utils.SharePreferenceManager;
import com.csi0n.searchjob.utils.bean.EmptyBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by csi0n on 12/23/15.
 */
public class MainController extends BaseController {
    private Main mMain;
    private ProgressLoading loading;

    public MainController(Main main) {
        this.mMain = main;
    }

    public void initMain() {
        mMain.mNews.setEnabled(false);
        mMain.mContacts.setEnabled(false);
        mMain.mSearchJob.setEnabled(false);
        CheckUpdate checkUpdate = new CheckUpdate(mMain,Config.saveFolder,Config.APP_NAME,R.string.url_checkUpdate);
        checkUpdate.start();
        if (mMain.is_auto_login) {
            checkLogin();
        } else {
            Intent intent = new Intent(Config.INTENT_USER_LOGIN_SUCCESS);
            mMain.sendBroadcast(intent);
            mMain.changeFragment(mMain.getContactFragment());
        }
    }

    public void onCheckChange(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rd_news:
                mMain.startWanZhiDaoHang();
                break;
            case R.id.rd_contact:
                mMain.changeFragment(mMain.getContactFragment());
                break;
            case R.id.rd_search_job:
                mMain.changeFragment(mMain.getSearchJobFragment());
                break;
            default:
                break;
        }
    }

    private void checkLogin() {
        loading = new ProgressLoading(mMain, "自动登录中...");
        loading.show();
        final String TOKEN = SharePreferenceManager.getKeyCachedToken();
        if (TextUtils.isEmpty(TOKEN)) {
            if (loading.isShowing())
                loading.dismiss();
            mMain.startLogin();
        } else {
            PostParams postParams = getDefaultPostParams(R.string.url_checkTimeOut);
            postParams.put("token", TOKEN);
            HttpPost post=new HttpPost(postParams, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
                @Override
                public void SuccessResult(EmptyBean result) throws JSONException {
                    UserInfo userInfo=JMessageClient.getMyInfo();
                    if (TextUtils.isEmpty(String.valueOf(userInfo.getUserID())))
                    mMain.startLogin();
                    JPushInterface.setAlias(mMain, String.valueOf(userInfo.getUserID()), new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            CLog.getInstance().iMessage(i == Config.JMESSAGE_SUCCESS_CODE ? "绑定推送ID成功" : "绑定推送ID失败!");
                        }
                    });
                    com.csi0n.searchjob.lib.utils.Config.DEFAULT_TOKEN = TOKEN;
                    Intent intent = new Intent(Config.INTENT_USER_LOGIN_SUCCESS);
                    mMain.sendBroadcast(intent);
                    mMain.changeFragment(mMain.getContactFragment());
                    if (loading.isShowing())
                        loading.dismiss();
                }

                @Override
                public void ErrorResult(int code, String str) {
                    super.ErrorResult(code, str);
                    if (loading.isShowing())
                        loading.dismiss();
                    mMain.startLogin();
                }
            });
            post.post();
        }
    }

    @Override
    public void onClick(View view) {

    }
}