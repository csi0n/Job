package com.csi0n.searchjob.controller;

import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.bean.BaseStatusBean;
import com.csi0n.searchjob.ui.fragment.SettingJinLirenFragment;
import com.csi0n.searchjob.utils.bean.EmptyBean;
import com.csi0n.searchjob.utils.bean.RequestIDCode;

import org.json.JSONException;

/**
 * Created by csi0n on 4/10/16.
 */
public class SettingJinliRenController extends BaseController {
    private SettingJinLirenFragment mSettingJinLirenFragment;

    public SettingJinliRenController(SettingJinLirenFragment settingJinLirenFragment) {
        this.mSettingJinLirenFragment = settingJinLirenFragment;
    }

    public void initJinliren() {
        requestIDCode();
    }

    private void requestIDCode() {
        PostParams params = getDefaultPostParams(R.string.url_requestIDCode);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<RequestIDCode>(RequestIDCode.class) {
            @Override
            public void SuccessResult(RequestIDCode result) throws JSONException {
                mSettingJinLirenFragment.setName(result.getReal_name());
                mSettingJinLirenFragment.setCode(result.getReal_code());
            }

            @Override
            public void EmptyData(BaseStatusBean<RequestIDCode> b) throws JSONException {
                mSettingJinLirenFragment.setEditCodeEnable(true);
                mSettingJinLirenFragment.setEditNameEnable(true);
                mSettingJinLirenFragment.setSaveBtnEnable(true);
                super.EmptyData(b);
            }
        });
        post.post();
    }

    private void saveIDCode(String name, String code) {
        PostParams params = getDefaultPostParams(R.string.url_saveIDCode);
        params.put("code", code);
        params.put("name", name);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {
                CLog.show("保存成功!");
                mSettingJinLirenFragment.setEditCodeEnable(false);
                mSettingJinLirenFragment.setEditNameEnable(false);
                mSettingJinLirenFragment.setSaveBtnEnable(false);
            }
        });
        post.post();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (TextUtils.isEmpty(mSettingJinLirenFragment.getName()))
                    CLog.show("请输入姓名");
                else if (TextUtils.isEmpty(mSettingJinLirenFragment.getCode()))
                    CLog.show("请输入身份证号码!");
                else if (mSettingJinLirenFragment.getCode().length() != 15 || mSettingJinLirenFragment.getCode().length() != 18)
                    CLog.show("身份证号码格式不正确!");
                else
                    saveIDCode(mSettingJinLirenFragment.getName(), mSettingJinLirenFragment.getCode());
                break;
            default:
                break;
        }
    }
}
