package com.csi0n.searchjob.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.AppContext;
import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ChangeGroupListController;
import com.csi0n.searchjob.utils.bean.Show;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.jpush.android.util.x;

/**
 * Created by csi0n on 2015/12/30 0030.
 * 用户分组修改
 */
@ContentView(R.layout.aty_list)
public class ChangeGroupListActivity extends BaseActivity {
    private ChangeGroupListController mChangeGroupListController;
    @ViewInject(value = R.id.list)
    private ListView mList;
    @ViewInject(value = R.id.tv_title)
    private TextView mTVTitle;
    @ViewInject(value = R.id.tv_more)
    private TextView mTVMore;
    @ViewInject(value = R.id.refreshLayout)
    public BGARefreshLayout mBGARefreshLayout;
    private View mFootView;
    public InputMethodManager imm;
    private Show.FriendInfo FRIEND_INFO;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = getString(R.string.str_change_group_list);
        actionBarRes.more = getString(R.string.str_complete);
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        Bundle bundle = getBundle();
        if (bundle != null)
            FRIEND_INFO = (Show.FriendInfo) bundle.getSerializable(Config.MARK_CHANGE_GROUP_LIST_ACTIVITY_FRIEND_INFO);
        else
            finish();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mFootView = View.inflate(this, R.layout.view_group_list_item_footer, null);
        mChangeGroupListController = new ChangeGroupListController(this);
        mChangeGroupListController.initChangeGroupList();
        mBGARefreshLayout.setDelegate(mChangeGroupListController);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(org.xutils.x.app(), true));
        mList.setOnItemClickListener(mChangeGroupListController);
    }

    public Show.FriendInfo getFRIEND_INFO() {
        return FRIEND_INFO;
    }

    public void setListAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
    }

    public void addFooterView() {
        mList.addFooterView(mFootView);
    }

    public void setStopRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }

    @Override
    protected void onMoreClick() {
        super.onMoreClick();
        if (mChangeGroupListController != null)
            mChangeGroupListController.onClick(findViewById(R.id.tv_more));
    }
}
