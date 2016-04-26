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
import com.csi0n.searchjob.lib.utils.bean.EmptyModel;
import com.csi0n.searchjob.model.UserModel;
import com.csi0n.searchjob.model.event.UserInfoEvent;
import com.csi0n.searchjob.ui.activity.ChangeIntroActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

/**
 * Created by csi0n on 4/25/16.
 */
public class ChangeIntroController extends BaseController {
    private ChangeIntroActivity mChangeIntroActivity;

    public ChangeIntroController(ChangeIntroActivity mChangeIntroActivity) {
        this.mChangeIntroActivity = mChangeIntroActivity;
    }

    public void initChangeIntro() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                String content = mChangeIntroActivity.getContent();
                if (TextUtils.isEmpty(content))
                    CLog.show("个人介绍不能为空!");
                else
                    do_change_intro(mChangeIntroActivity.getContent());
                break;
            default:
                break;
        }
    }

    private void do_change_intro(final String intro) {
        PostParams params = getDefaultPostParams(R.string.url_user_changeInfo);
        params.put("intro", intro);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyModel>(EmptyModel.class) {
            @Override
            public void SuccessResult(EmptyModel result) throws JSONException {
                CLog.show("修改成功!");
                UserModel userModel= Config.LOGIN_USER;
                userModel.setIntro(intro);
                EventBus.getDefault().post(new UserInfoEvent(userModel));
                mChangeIntroActivity.finish();
            }
        });
        post.post();
    }
}
