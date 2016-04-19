package com.csi0n.searchjob.ui.activity;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.WriteDynamicController;

import org.xutils.view.annotation.ContentView;

/**
 * Created by csi0n on 2015/12/30 0030.
 */
@ContentView(R.layout.aty_write_dynamic)
public class WriteDynamicActivity extends BaseActivity {
    private WriteDynamicController mWriteDynamicController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title=getString(R.string.str_write_dynamic);
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mWriteDynamicController = new WriteDynamicController(this);
        mWriteDynamicController.initWriteDynamic();
    }
}
