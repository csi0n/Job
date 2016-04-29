package com.csi0n.searchjob.enterpriseapp.ui.activity;

import android.widget.BaseAdapter;
import android.widget.ListView;

import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.controller.MySendHistoryController;
import com.csi0n.searchjob.lib.widget.EmptyLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 3/27/16.
 */
@ContentView(R.layout.aty_list)
public class MySendHistoryActivity extends BaseActivity {
    @ViewInject(R.id.refreshLayout)
    public BGARefreshLayout mBGARefreshLayout;
    @ViewInject(R.id.list)
    public ListView mList;
    @ViewInject(value = R.id.empty_layout)
    private EmptyLayout mEmptyLayout;
    private MySendHistoryController mMySendHistoryController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "发布历史";
        actionBarRes.backGone = false;
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mMySendHistoryController = new MySendHistoryController(this);
        mMySendHistoryController.initMySendHistory();
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(aty, true));
        mBGARefreshLayout.setDelegate(mMySendHistoryController);
        mList.setOnItemClickListener(mMySendHistoryController);
        mEmptyLayout.setOnLayoutClickListener(mMySendHistoryController);
    }

    public void setEmptyLayoutErrorType(int type) {
        mEmptyLayout.setErrorType(type);
    }

    public void setAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
    }
    public void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }
}
