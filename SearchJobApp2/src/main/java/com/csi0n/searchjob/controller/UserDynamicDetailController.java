package com.csi0n.searchjob.controller;
import android.view.View;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.ui.activity.UserDynamicDetailActivity;

/**
 * Created by csi0n on 2015/12/30 0030.
 */
public class UserDynamicDetailController  extends BaseController {
    private UserDynamicDetailActivity mUserDynamicDetailActivity;
    public UserDynamicDetailController(UserDynamicDetailActivity userDynamicDetailActivity){
        this.mUserDynamicDetailActivity=userDynamicDetailActivity;
    }
    public void initUserDynamic(){

    }@Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_back:
                mUserDynamicDetailActivity.finish();
                break;
            default:
                break;
        }
    }
}
