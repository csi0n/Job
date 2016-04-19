package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ShowSearchUserResultController;
import com.csi0n.searchjob.lib.widget.EmptyLayout;
import com.csi0n.searchjob.utils.bean.UserBean;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 2015/12/27 0027.
 */
@ContentView(R.layout.aty_list)
public class ShowSearchUserResultActivity extends BaseActivity {
    @ViewInject(value = R.id.list)
    private ListView mList;
    @ViewInject(value = R.id.refreshLayout)
    public BGARefreshLayout mBGARefreshLayout;
    @ViewInject(value = R.id.empty_layout)
    private EmptyLayout mEmptyLayout;
    private String key;
    private ShowSearchUserResultController mShowSearchUserResultController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "搜索结果";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        Bundle bundle = getBundle();
        if (bundle != null)
            key = bundle.getString(Config.MARK_SHOW_SEARCH_RESULT_ACTIVITY_KEY);
        else
            finish();
        mShowSearchUserResultController = new ShowSearchUserResultController(this);
        mShowSearchUserResultController.initShowSearchUser();
        mEmptyLayout.setOnLayoutClickListener(mShowSearchUserResultController);
        mBGARefreshLayout.setDelegate(mShowSearchUserResultController);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(x.app(), true));
        mList.setOnItemClickListener(mShowSearchUserResultController);
    }
    public void setEmptyLayoutType(int type){
       mEmptyLayout.setErrorType(type);
    }
    public String getKey() {
        return key;
    }

    public ListView getList() {
        return mList;
    }

    public void setStopRefresh() {
        mBGARefreshLayout.endRefreshing();
    }

    public void setStopLoadMore() {
        mBGARefreshLayout.endLoadingMore();
    }

    public void startShowUserInfor(final UserBean userBean) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Config.MARK_USER_INFO_ACTIVITY_IS_FROM_CAPTURE, false);
        bundle.putSerializable(Config.MARK_USER_INFO_ACTIVITY_USER_DATA, userBean);
        skipActivityWithBundleWithOutExit(this, UserInforActivity.class, bundle);
    }
}
