package com.csi0n.searchjob.utils.jpush;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.lib.utils.CLog;

import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by chqss on 2016/2/1 0001.
 */
public abstract class CGetUserInfoCallback extends GetUserInfoCallback {
    protected abstract void SuccessResult(UserInfo userInfo);

    @Override
    public void gotResult(int i, String s, UserInfo userInfo) {
        if (i == Config.JMESSAGE_SUCCESS_CODE)
            SuccessResult(userInfo);
        else
            CLog.getInstance().iMessage("获取用户信息失败!");
    }
}
