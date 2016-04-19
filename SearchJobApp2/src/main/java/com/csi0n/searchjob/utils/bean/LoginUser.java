package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

/**
 * Created by csi0n on 2016/1/1 0001.
 */
public class LoginUser extends BaseBean {
    private String token;
    private String i_password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getI_password() {
        return i_password;
    }

    public void setI_password(String i_password) {
        this.i_password = i_password;
    }
}
