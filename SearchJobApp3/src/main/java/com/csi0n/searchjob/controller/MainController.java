package com.csi0n.searchjob.controller;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.Config;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.bean.EmptyModel;
import com.csi0n.searchjob.lib.widget.ProgressLoading;
import com.csi0n.searchjob.model.UserModel;
import com.csi0n.searchjob.model.event.HeadChangeEvent;
import com.csi0n.searchjob.model.event.UserLoginEvent;
import com.csi0n.searchjob.ui.activity.Main;
import com.csi0n.searchjob.utils.SharePreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.File;

/**
 * Created by chqss on 2016/4/19 0019.
 */
public class MainController extends BaseController {
    private Main mMain;
private ProgressLoading loading;
    public MainController(Main mMain) {
        this.mMain = mMain;
    }

    public void initMain() {
        if (mMain.isNeedLogin()) {
            verityToken(SharePreferenceManager.getKeyCachedToken());
        } else {
        }
        mMain.changeSearchJobFragment();
bindDeviceID();
    }
    private void bindDeviceID(){
    }

    private void verityToken(final String token) {
        Config.DEFAULT_TOKEN = token;
        PostParams params = getDefaultPostParams(R.string.url_checkTimeOut);
        params.put("token", token);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<UserModel>(UserModel.class) {
            @Override
            public void SuccessResult(UserModel result) throws JSONException {
                com.csi0n.searchjob.Config.LOGIN_USER = result;
                com.csi0n.searchjob.lib.utils.Config.DEFAULT_TOKEN=token;
                EventBus.getDefault().post(new UserLoginEvent(result));
            }

            @Override
            public void ErrorResult(int code, String str) {
                com.csi0n.searchjob.lib.utils.Config.DEFAULT_TOKEN=null;
            }
        });
        post.post();
    }

    public void onCheckChange(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rd_news:
                mMain.startWanZhiDaoHang();
                break;
            case R.id.rd_search_job:
                mMain.changeSearchJobFragment();
                break;
            case R.id.rd_me:
                if (com.csi0n.searchjob.Config.LOGIN_USER != null)
                    mMain.changeMeFragment();
                else {
                    mMain.startLoginActivity();
                    mMain.setRadioButtonCheck((RadioButton) radioGroup.findViewById(R.id.rd_search_job));
                }
                break;
            default:
                break;
        }
    }

    public void uploadHead(final File headfile) {
        loading=new ProgressLoading(mMain.aty,"上传中请稍后...");
        loading.show();
        PostParams params = getDefaultPostParams(R.string.url_user_changeInfo);
        params.put("head", headfile);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyModel>(EmptyModel.class) {
            @Override
            public void SuccessResult(EmptyModel result) throws JSONException {
                CLog.show("头像修改成功!");
                EventBus.getDefault().post(new HeadChangeEvent(headfile));
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

    }
}
