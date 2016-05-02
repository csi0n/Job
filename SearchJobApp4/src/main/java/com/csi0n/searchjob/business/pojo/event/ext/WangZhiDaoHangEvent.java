package com.csi0n.searchjob.business.pojo.event.ext;

import com.csi0n.searchjob.business.pojo.event.BaseEvent;

/**
 * Created by chqss on 2016/5/1 0001.
 */
public class WangZhiDaoHangEvent extends BaseEvent {
    public boolean back;
    public boolean home;

    public WangZhiDaoHangEvent(boolean back, boolean home) {
        this.back = back;
        this.home = home;
    }
}
