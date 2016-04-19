package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by chqss on 2016/3/25 0025.
 */
public class UserListBean extends BaseBean {
    private List<UserBean> data;

    public List<UserBean> getData() {
        return data;
    }

    public void setData(List<UserBean> data) {
        this.data = data;
    }
}
