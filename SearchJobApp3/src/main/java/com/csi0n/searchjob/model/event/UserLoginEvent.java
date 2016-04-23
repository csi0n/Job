package com.csi0n.searchjob.model.event;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;
import com.csi0n.searchjob.model.UserModel;

/**
 * Created by csi0n on 4/23/16.
 */
public class UserLoginEvent extends BaseBean {
    private UserModel user;

    public UserLoginEvent(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
