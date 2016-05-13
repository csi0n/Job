package com.csi0n.searchjob.business.pojo.event.ext;

import com.csi0n.searchjob.business.pojo.event.BaseEvent;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public class UserInfoChangeEvent extends BaseEvent {
    public Object object;

    public ChangeType type;

    public UserInfoChangeEvent(Object object, ChangeType type) {
        this.object = object;
        this.type = type;
    }

    public enum ChangeType{
        HEAD,UNAME
    }
}
