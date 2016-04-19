package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ChangeRemarkController;
import com.csi0n.searchjob.utils.bean.Show;
import com.csi0n.searchjob.utils.bean.UserBean;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 2015/12/30 0030.
 */
@ContentView(R.layout.aty_change_remark)
public class ChangeRemarkActivity extends BaseActivity {
    private ChangeRemarkController mChangeRemarkController;
    @ViewInject(value = R.id.et_content)
    private EditText mContent;

    private Show.FriendInfo.FriendData FRIEND_DATA;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.more=getString(R.string.str_change);
        actionBarRes.title=getString(R.string.str_change_remark);
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        Bundle bundle = getBundle();
        if (bundle != null)
            FRIEND_DATA = (Show.FriendInfo.FriendData) bundle.getSerializable(Config.MARK_CHANGE_REMARK_ACTIVITY_FRIEND_DATA);
        else
            finish();
        mChangeRemarkController = new ChangeRemarkController(this);
        mChangeRemarkController.initChangeRemark();
        mContent.addTextChangedListener(mChangeRemarkController);
    }
    public Show.FriendInfo.FriendData getFRIEND_DATA() {
        return FRIEND_DATA;
    }

    public String getContent() {
        return mContent.getText().toString().trim();
    }

    @Override
    protected void onMoreClick() {
        super.onMoreClick();
        if(mChangeRemarkController!=null)
            mChangeRemarkController.onClick(findViewById(R.id.tv_more));
    }
}
