package com.csi0n.searchjob.ui.activity;

import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.MyCommentsController;
import com.csi0n.searchjob.lib.widget.EmptyLayout;
import com.csi0n.searchjob.utils.BGANormalRefreshViewHolder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 4/25/16.
 */
@ContentView(R.layout.aty_list)
public class MyCommentsActivity extends BaseActivity {
    @ViewInject(value = R.id.refreshLayout)
    private BGARefreshLayout mBGARefreshLayout;
    @ViewInject(value = R.id.list)
    private ListView mList;
    @ViewInject(value = R.id.empty_layout)
    private EmptyLayout mEmptyLayout;
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
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(aty, true));
        mBGARefreshLayout.setDelegate(mMyCommentsController);
        mEmptyLayout.setOnLayoutClickListener(mMyCommentsController);
        mList.setOnItemClickListener(mMyCommentsController);
    }
    public void setAdapter(BaseAdapter adapter){
        mList.setAdapter(adapter);
    }
    public void setErrorLayout(int type){
        mEmptyLayout.setErrorType(type);
    }
    public void endRefresh(){
        mBGARefreshLayout.endLoadingMore();
        mBGARefreshLayout.endRefreshing();
    }
}
