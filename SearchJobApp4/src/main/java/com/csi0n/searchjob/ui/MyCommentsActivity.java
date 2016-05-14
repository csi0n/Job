package com.csi0n.searchjob.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.callback.EmptySubscriber;
import com.csi0n.searchjob.business.pojo.response.ext.GetMyCommentsResponse;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.ui.adapter.MyCommentsAdapter;
import com.csi0n.searchjob.ui.base.BaseActivity;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.csi0n.searchjob.ui.widget.EmptyErrorType;
import com.csi0n.searchjob.ui.widget.EmptyLayout;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout.BGARefreshLayoutDelegate;
import roboguice.inject.ContentView;

/**
 * Created by chqss on 2016/5/13 0013.
 */
@ContentView(R.layout.aty_list)
public class MyCommentsActivity extends MvpActivity<MyCommentsPresenter, MyCommentsPresenter.IMyComments> implements BGARefreshLayoutDelegate{
    @Bind(R.id.list)
    ListView mList;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout mRefreshLayout;
    @Bind(R.id.emptyLayout)
    EmptyLayout mEmptyLayout;

    MyCommentsAdapter adapter;
    int CURRENT_PAGE=1;
    int TEMP_COUNT=0;
    boolean is_busy=false;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title="我的评论";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        goNext();
    }
    void init(){
        mEmptyLayout.setShowError(EmptyErrorType.NO_COMMENTS);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        mRefreshLayout.setDelegate(this);
        adapter=new MyCommentsAdapter(this);
        mList.setAdapter(adapter);
    }
    void goNext(){
        DoGetMyComments(CURRENT_PAGE);
    }
    void DoGetMyComments(final int page){
        is_busy=true;
        presenter.getMyComments(page).subscribe(new EmptySubscriber<GetMyCommentsResponse>(mEmptyLayout){
            @Override
            public void onHandleSuccess(GetMyCommentsResponse response) {
                super.onHandleSuccess(response);
                if (page==1)
                    adapter.datas.clear();
                TEMP_COUNT=response.datas.length;
                adapter.datas.addAll(Arrays.asList(response.datas));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onHandleEmptyData() {
                super.onHandleEmptyData();
                TEMP_COUNT=0;
            }

            @Override
            public void onHandleFinish() {
                super.onHandleFinish();
                is_busy=false;
                endRefresh();
            }
        });
    }

    void endRefresh(){
        mRefreshLayout.endLoadingMore();
        mRefreshLayout.endRefreshing();
    }
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!is_busy){
            CURRENT_PAGE=1;
            mEmptyLayout.reSetCount();
            DoGetMyComments(CURRENT_PAGE);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT >= Constants.DEFAULT_PAGE) {
                CURRENT_PAGE++;
                DoGetMyComments(CURRENT_PAGE);
                return true;
            } else
                return false;
        }
        return true;
    }
}
