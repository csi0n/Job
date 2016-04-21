package com.csi0n.searchjob.lib.utils;
import com.csi0n.searchjob.lib.utils.bean.BaseStatusBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;


import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by chqss on 2016/3/6 0006.
 */
public abstract class ObjectHttpCallBack<T> implements Callback.CommonCallback<String>{
    private Class<T> classz;
    public ObjectHttpCallBack(Class<T> classz) {
        this.classz=classz;
    }
    public abstract void SuccessResult(T result) throws JSONException;

    public void ErrorResult(int code, String str) {
        CLog.getInstance().showNormalError(code, str);
    }

    @Override
    public void onSuccess(String result) {
        CLog.getInstance().iMessage(result);
        try {
            JSONObject json=new JSONObject(result);
            BaseStatusBean<T> baseStatusBean=new BaseStatusBean<>();
            baseStatusBean.setStatus(json.getInt("status"));
            baseStatusBean.setInfo(json.getString("info"));
            if (baseStatusBean.getStatus() == Config.SUCCESS_CODE) {
                baseStatusBean.setData(new Gson().fromJson(json.getString("data"),classz));
                SuccessResult( baseStatusBean.getData());
            } else if (baseStatusBean.getStatus() == Config.CODE_EMPTY)
                EmptyData(baseStatusBean);
            else if (baseStatusBean.getStatus()==Config.CODE_NOUPDATE){

            }
            else
                ErrorResult(baseStatusBean.getStatus(), baseStatusBean.getInfo());
        } catch (JSONException e) {
            onError(e, true);
        }finally {
            onFinished();
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        if (ex.getClass() == SocketTimeoutException.class) {
            ErrorResult(1000, "连接超时!");
        } else if (ex.getClass() == ConnectException.class) {
            ErrorResult(1010, "数据服务器已关闭,请等待服务器重新开启!");
        } else if (ex.getClass() == JSONException.class) {
            ErrorResult(Config.ERROR_CODE_JSON, Config.ERROR_STRING_JSON);
        } else {
            ErrorResult(1001, "未知异常!");
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        ErrorResult(1002, "用户取消操作!");
    }

    public void EmptyData(BaseStatusBean<T> b) throws JSONException {
        CLog.show(b.getInfo());
    }

    @Override
    public void onFinished() {
    }

}
