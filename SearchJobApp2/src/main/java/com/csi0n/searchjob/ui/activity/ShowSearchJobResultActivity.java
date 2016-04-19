package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ShowSearchJobResultController;
import com.csi0n.searchjob.lib.widget.EmptyLayout;
import com.csi0n.searchjob.utils.bean.CompanyJobListBean;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/3/23 0023.
 */

@ContentView(R.layout.aty_list)
public class ShowSearchJobResultActivity extends BaseActivity {

private ShowSearchJobResultController mShowSearchJobResultController;
    @ViewInject(value = R.id.refreshLayout)
    private BGARefreshLayout mBGARefreshLayout;
    @ViewInject(value = R.id.list)
    private ListView mList;
    @ViewInject(value = R.id.empty_layout)
    private EmptyLayout mEmptyLayout;
    private String SEARCH_KEY;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title="搜索:"+SEARCH_KEY;
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        Bundle bundle=getIntent().getExtras();
        if (bundle==null)
            finish();
        SEARCH_KEY=bundle.getString(Config.MARK_SHOW_SEARCH_JOB_RESULT_KEY);
        mShowSearchJobResultController=new ShowSearchJobResultController(this);
        mShowSearchJobResultController.initShowSearchJobResult();
        mList.setOnItemClickListener(mShowSearchJobResultController);
        mBGARefreshLayout.setDelegate(mShowSearchJobResultController);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(x.app(), true));
        mEmptyLayout.setOnLayoutClickListener(mShowSearchJobResultController);
    }
    public void setAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
    }

    public void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }
    public void startCompanyWorkDetail(CompanyJobListBean.CompanyJobBean companyJobBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.MARK_COMAPNY_WORK_DETAIL_ACTIVITY_COMPANY_DATA, companyJobBean);
        aty.skipActivityWithBundleWithOutExit(aty, CompanyWorkDetailActivity.class, bundle);
    }
    public void setEmptyLayoutType(int type){
        mEmptyLayout.setErrorType(type);
    }
    public String getSEARCH_KEY(){
        return SEARCH_KEY;
    }
}
