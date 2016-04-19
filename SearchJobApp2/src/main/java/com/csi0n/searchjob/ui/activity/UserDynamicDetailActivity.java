package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.UserDynamicDetailController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 2015/12/30 0030.
 */
@ContentView(R.layout.aty_user_dynamic_detail)
public class UserDynamicDetailActivity extends BaseActivity {

    private UserDynamicDetailController mUserDynamicDetailController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = getString(R.string.str_dynamic_detail);
        super.setActionBarRes(actionBarRes);
    }
    @Override
    protected void initWidget() {
        mUserDynamicDetailController = new UserDynamicDetailController(this);
        mUserDynamicDetailController.initUserDynamic();
    }
}
