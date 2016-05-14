package com.csi0n.searchjob.ui.companydeatil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.callback.EmptySubscriber;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailCResponse;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.ui.adapter.CompanyWorkDetailCListAdapter;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;
import com.csi0n.searchjob.ui.widget.EmptyErrorType;
import com.csi0n.searchjob.ui.widget.EmptyLayout;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailFragmentC extends MvpFragment<CompanyWorkDetailCPresenter, CompanyWorkDetailCPresenter.ICompanyWorkDetailCPresenter> implements BGARefreshLayout.BGARefreshLayoutDelegate{

    @Bind(R.id.mList)
    ListView mList;
    @Bind(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @Bind(R.id.emptyLayout)
    EmptyLayout mEmptyLayout;

    CompanyWorkDetailCListAdapter adapter;
    int TEMP_COUNT=0;
    int CURRENT_PAGE=1;
    boolean is_busy=false;
    int company_id;
    @Override
    protected int getFragmentLayout() {
        return R.layout.frag_list;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        DoGetCompanyWorkDetailC(CURRENT_PAGE);
    }
    void init(){
        mEmptyLayout.setShowError(EmptyErrorType.NO_JINGLIREN);
        company_id=mvpActivity.getBundle().getInt(Constants.MARK_COMPANY_WORK_DETAIL_COMPANY_ID);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mvpActivity, true));
        mBGARefreshLayout.setDelegate(this);
        adapter=new CompanyWorkDetailCListAdapter(mvpActivity);
        mList.setAdapter(adapter);
    }

    void DoGetCompanyWorkDetailC(final int page){
        is_busy=true;
        presenter.doGetSearchJobDetailC(page,company_id).subscribe(new EmptySubscriber<GetSearchJobDetailCResponse>(mEmptyLayout){
            @Override
            public void onHandleSuccess(GetSearchJobDetailCResponse response) {
                super.onHandleSuccess(response);
                if (page==1)
                    adapter.datas.clear();
                TEMP_COUNT=response.datas.length;
                adapter.datas.addAll(Arrays.asList(response.datas));
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
    void endRefresh(){
        mBGARefreshLayout.endLoadingMore();
        mBGARefreshLayout.endRefreshing();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            CURRENT_PAGE = 1;
            mEmptyLayout.reSetCount();
            DoGetCompanyWorkDetailC(CURRENT_PAGE);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT>=Constants.DEFAULT_PAGE){
                CURRENT_PAGE++;
                DoGetCompanyWorkDetailC(CURRENT_PAGE);
                return true;
            }else
                return false;
        }
        return true;
    }
}
