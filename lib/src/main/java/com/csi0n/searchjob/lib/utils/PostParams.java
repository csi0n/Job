package com.csi0n.searchjob.lib.utils;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by csi0n on 2015/12/15 0015.
 */
public class PostParams {
    private RequestParams mRequestParams;

    public PostParams(int url) {
        CLog.getInstance().eMessage(Config.BASE_URL+x.app().getString(url));
        mRequestParams = new RequestParams(Config.BASE_URL + x.app().getString(url));
    }

    public PostParams(String url) {
        mRequestParams = new RequestParams(Config.BASE_URL + url);
    }

    public void put(String key, String value) {
        mRequestParams.addQueryStringParameter(key, value);
    }
    public void put(String key,File file){
        mRequestParams.addBodyParameter(key,file);
    }

    public RequestParams getParams() {
        return mRequestParams;
    }
}
