package com.csi0n.searchjob.utils.jpush;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.lib.utils.CLog;

import cn.jpush.im.api.BasicCallback;

/**
 * Created by chqss on 2016/2/1 0001.
 */
public abstract class CBasicCallBack extends BasicCallback {
    protected abstract void SuccessResult();

    @Override
    public void gotResult(int i, String s) {
        if (i == Config.JMESSAGE_SUCCESS_CODE)
            SuccessResult();
        else
            ErrorResult(i, s);
    }

    protected void ErrorResult(int code, String s) {
        CLog.getInstance().showJMessageClientError(code, s);
    }
}
