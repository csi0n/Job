package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.ui.widget.qr.ui.CaptureActivity;
import com.csi0n.searchjob.utils.ProgressLoading;
import com.csi0n.searchjob.utils.bean.EmptyBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chqss on 2016/2/1 0001.
 */
public class CaptureController extends BaseController {
    private CaptureActivity mCaptureActivity;
    private ProgressLoading loading;
    public CaptureController(CaptureActivity mCaptureActivity) {
        this.mCaptureActivity = mCaptureActivity;
    }
    public void initCapture(){

    }

    public void getVerifyQRFromNet(final String verify_code) {
        loading = new ProgressLoading(mCaptureActivity, "验证中...");
        loading.show();
        PostParams params = getDefaultPostParams(R.string.url_verifyQR);
        params.put("verify_code", verify_code);
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {
                if (loading.isShowing())
                    loading.dismiss();
                mCaptureActivity.startShowUserInfo(verify_code);
            }

            @Override
            public void ErrorResult(int code, String str) {
                super.ErrorResult(code, str);
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
