package com.csi0n.searchjob.controller;

import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.bean.EmptyModel;
import com.csi0n.searchjob.model.ShowModel;
import com.csi0n.searchjob.model.event.ShowChangeEvent;
import com.csi0n.searchjob.ui.activity.ChangeNameAndCodeActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

/**
 * Created by csi0n on 4/25/16.
 */
public class ChangeNameAndCodeController extends BaseController {
    private ChangeNameAndCodeActivity mChangeNameAndCodeActivity;

    public ChangeNameAndCodeController(ChangeNameAndCodeActivity mChangeNameAndCodeActivity) {
        this.mChangeNameAndCodeActivity = mChangeNameAndCodeActivity;
    }

    public void initChangeNameAndCode() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                String name = mChangeNameAndCodeActivity.getName();
                String code = mChangeNameAndCodeActivity.getCode();
                if (TextUtils.isEmpty(name))
                    CLog.show("请输入姓名!");
                else if (TextUtils.isEmpty(code))
                    CLog.show("请输入身份证号码!");
                else
                    do_change_name_and_code(name, code);
                break;
            default:
                break;
        }
    }

    private void do_change_name_and_code(final String name, final String code) {
        PostParams params = getDefaultPostParams(R.string.url_user_changeInfo);
        params.put("name", name);
        params.put("code", code);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyModel>(EmptyModel.class) {
            @Override
            public void SuccessResult(EmptyModel result) throws JSONException {
                ShowModel showModel=mChangeNameAndCodeActivity.getShowData();
                showModel.setReal_name(name);
                showModel.setReal_code(code);
                EventBus.getDefault().post(new ShowChangeEvent(showModel));
                mChangeNameAndCodeActivity.finish();
            }
        });
        post.post();
    }

}
