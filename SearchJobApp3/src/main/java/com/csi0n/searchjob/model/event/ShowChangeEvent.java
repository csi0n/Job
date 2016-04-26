package com.csi0n.searchjob.model.event;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;
import com.csi0n.searchjob.model.ShowModel;

/**
 * Created by chqss on 2016/4/26 0026.
 */
public class ShowChangeEvent extends BaseBean {
    private ShowModel show;

    public ShowChangeEvent(ShowModel show) {
        this.show = show;
    }

    public ShowModel getShow() {
        return show;
    }

    public void setShow(ShowModel show) {
        this.show = show;
    }
}
