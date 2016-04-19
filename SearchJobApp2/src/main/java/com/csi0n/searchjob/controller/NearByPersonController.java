package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.ui.activity.NearByPersonActivity;

/**
 * Created by csi0n on 2015/12/20 0020.
 */
public class NearByPersonController  extends BaseController {
    private NearByPersonActivity mNearByPersonActivity;
    public NearByPersonController(NearByPersonActivity nearByPersonActivity){
        this.mNearByPersonActivity=nearByPersonActivity;
    }
    public void initNearByPerson(){

    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_back:
                mNearByPersonActivity.finish();
                break;
            default:
                break;
        }
    }
}

