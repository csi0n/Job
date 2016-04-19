package com.csi0n.searchjob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.AppContext;
import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.AddFriendListController;
import com.csi0n.searchjob.controller.ChangeGroupListController;
import com.csi0n.searchjob.utils.bean.AddFriendListBean;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by csi0n on 2015/12/20 0020.
 */
@ContentView(R.layout.aty_list)
public class AddFriendListActivity extends BaseActivity {
    private AddFriendListController mAddFriendListController;
    @ViewInject(value = R.id.refreshLayout)
    private BGARefreshLayout mBGARefreshLayout;
    @ViewInject(value = R.id.list)
    private ListView mList;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = getString(R.string.str_add_friend_list);
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mAddFriendListController = new AddFriendListController(this);
        mAddFriendListController.initAddFriendList();
        mBGARefreshLayout.setDelegate(mAddFriendListController);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(x.app(), true));
    }

    public void startUserInfo(final String user_id) {
        Bundle bundle = new Bundle();
        bundle.putString(Config.MARK_USER_INFO_ACTIVITY_USER_ID, user_id);
        bundle.putBoolean(Config.MARK_USER_INFO_ACTIVITY_IS_FROM_CAPTURE, true);
        skipActivityWithBundle(this, UserInforActivity.class, bundle);
    }

    public void setAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
    }

    public void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }
}
