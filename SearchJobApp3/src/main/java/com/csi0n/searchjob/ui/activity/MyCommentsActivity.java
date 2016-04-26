package com.csi0n.searchjob.ui.activity;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.MyCommentsController;

import org.xutils.view.annotation.ContentView;

/**
 * Created by csi0n on 4/25/16.
 */
@ContentView(R.layout.aty_list)
public class MyCommentsActivity extends BaseActivity {
    private MyCommentsController mMyCommentsController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title="我的评论";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mMyCommentsController = new MyCommentsController(this);
        mMyCommentsController.initMyComments();
    }
}
