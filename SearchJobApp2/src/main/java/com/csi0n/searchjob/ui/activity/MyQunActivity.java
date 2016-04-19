package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.MyQunController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 12/26/15.
 */
@ContentView(R.layout.aty_my_qun)
public class MyQunActivity extends BaseActivity {
    private MyQunController mMyQunController;
    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = getString(R.string.str_my_qun);
        super.setActionBarRes(actionBarRes);
    }
    @Override
    protected void initWidget() {
        mMyQunController = new MyQunController(this);
        mMyQunController.initMyQun();
    }
}
