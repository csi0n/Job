package com.csi0n.searchjob.ui.companydeatil;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailBHeaderResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailBResponse;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.ui.adapter.CompanyWorkDetailBListAdapter;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;
import com.csi0n.searchjob.ui.widget.EmptyErrorType;
import com.csi0n.searchjob.ui.widget.EmptyLayout;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailFragmentB extends MvpFragment<CompanyWorkDetailBPresenter, CompanyWorkDetailBPresenter.ICompanyWorkDetailBPresenter> implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.mList)
    ListView mList;
    @Bind(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @Bind(R.id.emptyLayout)
    EmptyLayout mEmptyLayout;

    int company_id;
    int CURRENT_PAGE = 1;
    int TEMP_COUNT = 0;
    boolean is_busy = false;

    CompanyWorkDetailBListAdapter adapter;
    ViewHolder holder;

    @Override
    protected int getFragmentLayout() {
        return R.layout.frag_list;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        DoGetCompanyWorkDetailHeader(company_id);
        DoGetCompanyWorkDetailB(CURRENT_PAGE);
    }

    void init() {
        mEmptyLayout.setShowError(EmptyErrorType.HIDE_LAYOUT);
        company_id = mvpActivity.getBundle().getInt(Constants.MARK_COMPANY_WORK_DETAIL_COMPANY_ID);
        adapter = new CompanyWorkDetailBListAdapter(mvpActivity);
        mList.setAdapter(adapter);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mvpActivity, true));
        mBGARefreshLayout.setCustomHeaderView(getHeadView(), true);
        mBGARefreshLayout.setDelegate(this);
    }

    void DoGetCompanyWorkDetailHeader(int company_id) {
        presenter.doGetSearchJobDetailBHeader(company_id).subscribe(new AdvancedSubscriber<GetSearchJobDetailBHeaderResponse>(mEmptyLayout) {
            @Override
            public void onHandleSuccess(GetSearchJobDetailBHeaderResponse response) {
                super.onHandleSuccess(response);
                initHeaderView(response);
            }
        });
    }

    void DoGetCompanyWorkDetailB(final int page) {
        is_busy = true;
        presenter.doGetSearchJobDetailB(page, company_id).subscribe(new AdvancedSubscriber<GetSearchJobDetailBResponse>(mEmptyLayout) {
            @Override
            public void onHandleSuccess(GetSearchJobDetailBResponse response) {
                super.onHandleSuccess(response);
                TEMP_COUNT = response.data.length;
                if (page == 1)
                    adapter.datas.clear();
                adapter.datas.addAll(Arrays.asList(response.data));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onHandleEmptyData() {
                super.onHandleEmptyData();
                TEMP_COUNT = 0;
            }

            @Override
            public void onHandleFinish() {
                super.onHandleFinish();
                is_busy = false;
                endRefresh();
            }
        });
    }

    void initHeaderView(GetSearchJobDetailBHeaderResponse response) {
        Picasso.with(mvpActivity).load(Constants.BaseUrl + response.image).into(holder.ivHead);
        holder.tvCompanyName.setText(response.name);
        holder.tvCompanyType.setText(response.type);
        holder.tvCompanyIntro.setText(response.intro);
    }

    View getHeadView() {
        View view = View.inflate(mvpActivity, R.layout.view_company_work_detail_b_header, null);
        holder = new ViewHolder(view);
        return view;
    }

    void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            mEmptyLayout.reSetCount();
            CURRENT_PAGE = 1;
            DoGetCompanyWorkDetailB(CURRENT_PAGE);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT>=Constants.DEFAULT_PAGE){
                CURRENT_PAGE++;
                DoGetCompanyWorkDetailB(CURRENT_PAGE);
                return false;
            }
        }
        return true;
    }

    static class ViewHolder {
        @Bind(R.id.iv_head)
        ImageView ivHead;
        @Bind(R.id.tv_company_name)
        TextView tvCompanyName;
        @Bind(R.id.tv_company_type)
        TextView tvCompanyType;
        @Bind(R.id.tv_company_intro)
        TextView tvCompanyIntro;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
