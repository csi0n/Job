package com.csi0n.searchjob.lib.utils;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.x;

/**
 * Created by csi0n on 2015/12/14 0014.
 */
public class HttpPost {
    private PostParams mPostParams;
    private CommonCallback<String> mCallback;
    public HttpPost(PostParams mPostParams, CommonCallback<String> mCallback) {
        this.mPostParams = mPostParams;
        this.mCallback = mCallback;
    }
    public void post() {
        x.http().post(mPostParams.getParams(), mCallback);
    }
}
