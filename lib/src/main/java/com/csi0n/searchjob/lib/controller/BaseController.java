package com.csi0n.searchjob.lib.controller;

import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.lib.utils.Config;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.StringUtils;

import org.xutils.x;

/**
 * Created by csi0n on 2015/12/30 0030.
 */
public abstract class BaseController {
    public abstract void onClick(View view);

    protected String getString(int id) {
        return StringUtils.getString(id);
    }

    protected PostParams getDefaultPostParams(int id) {
        return getPostParams(id);
    }

    public static PostParams getStaticDefaultPostParams(int id) {
        return getPostParams(id);
    }

    private static PostParams getPostParams(int id) {
        PostParams params = new PostParams(x.app().getString(id));
        if (!TextUtils.isEmpty(Config.DEFAULT_TOKEN))
            params.put("token", Config.DEFAULT_TOKEN);
        return params;
    }
}
