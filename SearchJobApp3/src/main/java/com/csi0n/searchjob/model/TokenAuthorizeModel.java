package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

/**
 * Created by csi0n on 4/24/16.
 */
public class TokenAuthorizeModel extends BaseBean {

    /**
     * token : e4981ca3120c5a5ae20e1a28831828d0
     * user : {"uid":"15585450","login_status":"1","sex":"0","intro":"这个人很懒什么都没有留下!","uname":"小宝宝"}
     */

    private String token;
    private UserModel user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
