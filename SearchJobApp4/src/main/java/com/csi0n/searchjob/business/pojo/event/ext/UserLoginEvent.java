package com.csi0n.searchjob.business.pojo.event.ext;

import com.csi0n.searchjob.business.pojo.event.BaseEvent;
import com.csi0n.searchjob.business.pojo.model.ext.MyModel;

/**
 * Created by csi0n on 5/6/16.
 */
public class UserLoginEvent extends BaseEvent{
    public MyModel LoginUser;

    public UserLoginEvent(MyModel loginUser) {
        LoginUser = loginUser;
    }
}
