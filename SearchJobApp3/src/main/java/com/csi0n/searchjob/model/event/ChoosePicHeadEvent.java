package com.csi0n.searchjob.model.event;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

/**
 * Created by csi0n on 4/25/16.
 */
public class ChoosePicHeadEvent extends BaseBean {
    private boolean isFromCamera;

    public ChoosePicHeadEvent(boolean isFromCamera) {
        this.isFromCamera = isFromCamera;
    }

    public boolean isFromCamera() {
        return isFromCamera;
    }

    public void setFromCamera(boolean fromCamera) {
        isFromCamera = fromCamera;
    }
}
