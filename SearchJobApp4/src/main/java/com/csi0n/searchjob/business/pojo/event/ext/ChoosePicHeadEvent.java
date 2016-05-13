package com.csi0n.searchjob.business.pojo.event.ext;

import com.csi0n.searchjob.business.pojo.event.BaseEvent;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public class ChoosePicHeadEvent extends BaseEvent {
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
