package com.csi0n.searchjob.model.event;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;
import com.csi0n.searchjob.model.UserModel;

/**
 * Created by csi0n on 4/25/16.
 */
public class UserInfoEvent extends BaseBean {
    public UserInfoEvent(UserModel user) {
        this.user = user;
    }

    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
