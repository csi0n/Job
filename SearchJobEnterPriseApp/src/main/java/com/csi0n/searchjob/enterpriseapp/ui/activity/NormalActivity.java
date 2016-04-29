package com.csi0n.searchjob.enterpriseapp.ui.activity;

import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.controller.NormalController;

import org.xutils.view.annotation.ContentView;

/**
 * Created by csi0n on 3/27/16.
 * 普工发布或修改
 */
@ContentView(R.layout.aty_normal)
public class NormalActivity extends BaseActivity {
    private NormalController mNormalController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "普工发布";
        actionBarRes.backGone = false;
        actionBarRes.more = "完成";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mNormalController = new NormalController(this);
        mNormalController.initNormal();
    }
}
