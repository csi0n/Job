package com.csi0n.searchjob.controller;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.ui.activity.ChangeRemarkActivity;
import com.csi0n.searchjob.utils.bean.EmptyBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by csi0n on 2015/12/30 0030.
 */
public class ChangeRemarkController extends BaseController implements TextWatcher{
    private ChangeRemarkActivity mChangeRemarkActivity;

    public ChangeRemarkController(ChangeRemarkActivity changeRemarkActivity) {
        this.mChangeRemarkActivity = changeRemarkActivity;
    }

    public void initChangeRemark() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                mChangeRemarkActivity.finish();
                break;
            case R.id.tv_more:
                if (TextUtils.isEmpty(mChangeRemarkActivity.getContent())) {
                    CLog.getInstance().showError( "备注不能为空!");
                } else {
                    changeRemark(mChangeRemarkActivity.getContent());
                }
                break;
            default:
                break;
        }
    }


    private void changeRemark(final String content) {
        PostParams params =getDefaultPostParams(R.string.url_changeRemark);
        params.put("id", mChangeRemarkActivity.getFRIEND_DATA().getId());
        params.put("remark", content);
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {
                CLog.show( "修改成功!");
                mChangeRemarkActivity.finish();
            }
        });
        post.post();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
