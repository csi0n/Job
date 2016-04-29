package com.csi0n.searchjob.enterpriseapp.ui.activity;

import android.widget.BaseAdapter;
import android.widget.ListView;

import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.controller.CommentsController;
import com.csi0n.searchjob.lib.widget.EmptyLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 3/29/16.
 */
@ContentView(R.layout.aty_list)
public class CommentsActivity extends BaseActivity {
    @ViewInject(R.id.refreshLayout)
    public BGARefreshLayout mBGARefreshLayout;
    @ViewInject(R.id.list)
    public ListView mList;
    @ViewInject(value = R.id.empty_layout)
    private EmptyLayout mEmptyLayout;
    private CommentsController mCommentsController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title="评论";
        super.setActionBarRes(actionBarRes);
    }
    @Override
    protected void initWidget() {
        mCommentsController = new CommentsController(this);
        mCommentsController.initComments();
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(aty, true));
        mBGARefreshLayout.setDelegate(mCommentsController);
        mList.setOnItemClickListener(mCommentsController);
        mEmptyLayout.setOnLayoutClickListener(mCommentsController);

    }

    public void setEmptyLayoutErrorType(int type) {
        mEmptyLayout.setErrorType(type);
    }

    public void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }

    public void setAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
    }
}
