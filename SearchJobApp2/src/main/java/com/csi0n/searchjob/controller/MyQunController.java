package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.ui.activity.MyQunActivity;

/**
 * Created by csi0n on 12/26/15.
 */
public class MyQunController  extends BaseController {
    private MyQunActivity mMyQunActivity;
    public MyQunController(MyQunActivity myQunActivity){
        this.mMyQunActivity=myQunActivity;
    }
    public void initMyQun(){

    }@Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_back:
                mMyQunActivity.finish();
                break;
            default:
                break;
        }
    }
}
