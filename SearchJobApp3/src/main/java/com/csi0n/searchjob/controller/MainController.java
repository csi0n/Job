package com.csi0n.searchjob.controller;

import android.view.View;
import android.widget.RadioGroup;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.Config;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.model.UserModel;
import com.csi0n.searchjob.model.event.UserLoginEvent;
import com.csi0n.searchjob.ui.activity.Main;
import com.csi0n.searchjob.utils.SharePreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

/**
 * Created by chqss on 2016/4/19 0019.
 */
public class MainController extends BaseController {
    private Main mMain;

    public MainController(Main mMain) {
        this.mMain = mMain;
    }

    public void initMain() {
        if (mMain.isNeedLogin()) {
            verityToken(SharePreferenceManager.getKeyCachedToken());
        } else {
        }
        mMain.changeSearchJobFragment();
    }

    private void verityToken(String token) {
        Config.DEFAULT_TOKEN = token;
        PostParams params = getDefaultPostParams(R.string.url_checkTimeOut);
        params.put("token", token);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<UserModel>(UserModel.class) {
            @Override
            public void SuccessResult(UserModel result) throws JSONException {
                com.csi0n.searchjob.Config.LOGIN_USER = result;
                EventBus.getDefault().post(new UserLoginEvent(result));

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
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {

    }
}
