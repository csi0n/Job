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
import com.csi0n.searchjob.ui.activity.ChangeUnameActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

/**
 * Created by csi0n on 4/25/16.
 */
public class ChangeUnameController extends BaseController {
    private ChangeUnameActivity mChangeUnameActivity;

    public ChangeUnameController(ChangeUnameActivity mChangeUnameActivity) {
        this.mChangeUnameActivity = mChangeUnameActivity;
    }

    public void initChange() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                String content = mChangeUnameActivity.getContent();
                if (TextUtils.isEmpty(content))
                    CLog.show("请输入昵称!");
                else
                    do_change_uname(content);
                break;
            default:
                break;
        }
    }

    private void do_change_uname(final String uname) {
        PostParams params = getDefaultPostParams(R.string.url_user_changeInfo);
        params.put("uname", uname);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyModel>(EmptyModel.class) {
            @Override
            public void SuccessResult(EmptyModel result) throws JSONException {
                CLog.show("修改成功!");
                UserModel user = Config.LOGIN_USER;
                user.setUname(uname);
                EventBus.getDefault().post(new UserInfoEvent(user));
                mChangeUnameActivity.finish();
            }
        });
        post.post();
    }
}
