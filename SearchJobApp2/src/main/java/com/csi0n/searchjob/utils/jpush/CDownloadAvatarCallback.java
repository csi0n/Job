package com.csi0n.searchjob.utils.jpush;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.lib.utils.CLog;

import java.io.File;

import cn.jpush.im.android.api.callback.DownloadAvatarCallback;

/**
 * Created by chqss on 2016/2/1 0001.
 */
public abstract class CDownloadAvatarCallback extends DownloadAvatarCallback {
    protected abstract void SuccessResult(File file);

    @Override
    public void gotResult(int i, String s, File file) {
        if (i == Config.JMESSAGE_SUCCESS_CODE)
            SuccessResult(file);
        else
            CLog.getInstance().iMessage("获取用户头像失败!");
    }
}
