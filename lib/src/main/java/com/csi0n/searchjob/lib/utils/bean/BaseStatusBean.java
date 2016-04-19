package com.csi0n.searchjob.lib.utils.bean;

/**
 * Created by chqss on 2016/3/6 0006.
 */
public class BaseStatusBean<T> extends BaseBean {
    private int status;
    private String info;
    private  T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
