package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.ui.activity.WriteDynamicActivity;

/**
 * Created by csi0n on 2015/12/30 0030.
 */
public class WriteDynamicController extends BaseController {
    private WriteDynamicActivity mWriteDynamicActivity;

    public WriteDynamicController(WriteDynamicActivity writeDynamicActivity) {
        this.mWriteDynamicActivity = writeDynamicActivity;
    }

    public void initWriteDynamic() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }
}
