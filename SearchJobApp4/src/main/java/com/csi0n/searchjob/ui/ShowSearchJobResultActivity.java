package com.csi0n.searchjob.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobListByKeyResponse;
import com.csi0n.searchjob.core.io.SharePreferenceManager;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.ui.adapter.SearchJobFragmentAdapter;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;

import java.util.Arrays;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import roboguice.inject.ContentView;

/**
 * Created by chqss on 2016/5/12 0012.
 */
@ContentView(R.layout.aty_list)
public class ShowSearchJobResultActivity extends MvpActivity<ShowSearchJobResultPresenter, ShowSearchJobResultPresenter.ISearchJobPresenterView> implements BGARefreshLayout.BGARefreshLayoutDelegate{

    @Bind(R.id.list)
    ListView mList;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout mBGARefreshLayout;

    private SearchJobFragmentAdapter adapter;
    private String key;

    private int CURRENT_PAGE = 1;
    private int TEMP_COUNT = 0;
    private boolean is_busy = false;
    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "显示结果";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init() {
        Bundle bundle=getBundle();
        if (bundle==null)
            finish();
        key=bundle.getString(Constants.MARK_SHOW_SEARCH_JOB_ACTIVITY_KEY);
        mBGARefreshLayout.setDelegate(this);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        adapter=new SearchJobFragmentAdapter(this);
        mList.setAdapter(adapter);
        getJobList(CURRENT_PAGE);
    }
    void getJobList(final int page){
        is_busy=true;
       presenter.getSearchJobListByKey(page,key, SharePreferenceManager.getFlagLocalConfigVersion()).subscribe(new AdvancedSubscriber<GetSearchJobListByKeyResponse>(){
           @Override
           public void onHandleSuccess(GetSearchJobListByKeyResponse response) {
               super.onHandleSuccess(response);
               if (page==1)
                   adapter.datas.clear();
               TEMP_COUNT=response.companyJobs.length;
               adapter.datas.addAll(Arrays.asList(response.companyJobs));
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onHandleFinish() {
               super.onHandleFinish();
               is_busy=false;
               endRefresh();
           }
       });
    }
    void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            CURRENT_PAGE = 1;
            getJobList(CURRENT_PAGE);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT >= Constants.DEFAULT_PAGE) {
                CURRENT_PAGE++;
                getJobList(CURRENT_PAGE);
                return false;
            }
        }
        return true;
    }
}
