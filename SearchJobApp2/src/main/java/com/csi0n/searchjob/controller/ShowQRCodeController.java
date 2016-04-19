package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.ui.activity.ShowQRCodeActivity;

/**
 * Created by csi0n on 2016/1/1 0001.
 */
public class ShowQRCodeController extends BaseController {
    private ShowQRCodeActivity mShowQRCodeActivity;
    public ShowQRCodeController(ShowQRCodeActivity showQRCodeActivity){
        this.mShowQRCodeActivity=showQRCodeActivity;
    }
    public void initShowQRCode(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_scan:
                mShowQRCodeActivity.startScanQR();
                break;
            default:
                break;
        }
    }
}
