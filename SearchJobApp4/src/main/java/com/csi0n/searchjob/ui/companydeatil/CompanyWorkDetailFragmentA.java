package com.csi0n.searchjob.ui.companydeatil;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailAResponse;
import com.csi0n.searchjob.core.io.DbManager;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.database.dao.FuLi;
import com.csi0n.searchjob.ui.adapter.CompanyWorkDetailAListAdapter;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;
import com.csi0n.searchjob.ui.widget.EmptyErrorType;
import com.csi0n.searchjob.ui.widget.EmptyLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import me.next.tagview.TagCloudView;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailFragmentA extends MvpFragment<CompanyWorkDetailAPresenter, CompanyWorkDetailAPresenter.ICompanyWorkDetailAPresenter> implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @Bind(R.id.mList)
    ListView mList;
    @Bind(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    private int company_id;
    @Bind(R.id.emptyLayout)
    EmptyLayout mEmptyLayout;
    private boolean is_busy = false;
    private ViewHolder holder = null;
    private CompanyWorkDetailAListAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mEmptyLayout.setShowError(EmptyErrorType.HIDE_LAYOUT);
        company_id = mvpActivity.getBundle().getInt(Constants.MARK_COMPANY_WORK_DETAIL_COMPANY_ID);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mvpActivity, false));
        mBGARefreshLayout.setCustomHeaderView(getHeadView(), true);
        mBGARefreshLayout.setDelegate(this);
        adapter = new CompanyWorkDetailAListAdapter(mvpActivity);
        mList.setAdapter(adapter);
        DoGetCompanyDetailA(company_id);
    }

    private View getHeadView() {
        View view = View.inflate(mvpActivity, R.layout.view_company_work_detail_a_header, null);
        holder = new ViewHolder(view);
        return view;
    }

    void DoGetCompanyDetailA(int company_id) {
        is_busy = true;
        presenter.doGetSearchJobDetailA(company_id).subscribe(new AdvancedSubscriber<GetSearchJobDetailAResponse>(mEmptyLayout) {
            @Override
            public void onHandleSuccess(GetSearchJobDetailAResponse response) {
                super.onHandleSuccess(response);
                setCompanyInforTop(response);
                if (response.today_money_backs != null) {
                    adapter.datas.clear();
                    adapter.datas.addAll(Arrays.asList(response.today_money_backs));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onHandleFinish() {
                super.onHandleFinish();
                is_busy = false;
                endRefresh();
            }
        });
    }

    public void setCompanyInforTop(GetSearchJobDetailAResponse getSearchJobDetailAResponse) {
        holder.tvGangWei.setText(getSearchJobDetailAResponse.job_type);
        holder.tvUseType.setText(getSearchJobDetailAResponse.use_type);
        holder.tvGongzi.setText(getSearchJobDetailAResponse.gongzi);
        holder.tvGongziDetail.setText(getSearchJobDetailAResponse.gongzi_detail);
        holder.tvWorkTime.setText(getSearchJobDetailAResponse.work_time);
        holder.tvShebao.setText(getSearchJobDetailAResponse.shebao ? "无" : "按照国家规定缴纳社保.");
        holder.tvLocation.setText(getSearchJobDetailAResponse.work_location);
        holder.tvDegree.setText(getSearchJobDetailAResponse.degree_wanted);
        holder.tvWorkLife.setText(getSearchJobDetailAResponse.work_life);
        holder.tvMoreInfo.setText(getSearchJobDetailAResponse.more_infor);
        holder.tvWorkInfo.setText(getSearchJobDetailAResponse.work_infor);
        holder.tvToday.setText(getSearchJobDetailAResponse.day);
        List<String> stringList = new ArrayList<>();
        String[] fulis = getSearchJobDetailAResponse.fuli.split(",");
        for (int i = 0; i < fulis.length; i++) {
            FuLi fuliBean = DbManager.findFuLiByID(fulis[i]);
            if (fuliBean != null)
                stringList.add(fuliBean.getName());
        }
        holder.tagFuli.setTags(stringList);
        String[] ages = getSearchJobDetailAResponse.age.split(",");
        if (ages.length == 2) {
            holder.tvWorkXuqiu.setText(ages[0] + " " + ages[1]);
        } else {
            holder.tvWorkXuqiu.setText(getSearchJobDetailAResponse.age);
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.frag_list;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            mEmptyLayout.reSetCount();
            DoGetCompanyDetailA(company_id);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            DoGetCompanyDetailA(company_id);
            return false;
        }
        return true;
    }

    public void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }

    static class ViewHolder {
        @Bind(R.id.tv_gang_wei)
        TextView tvGangWei;
        @Bind(R.id.tv_use_type)
        TextView tvUseType;
        @Bind(R.id.tv_gongzi)
        TextView tvGongzi;
        @Bind(R.id.tv_gongzi_detail)
        TextView tvGongziDetail;
        @Bind(R.id.tv_work_time)
        TextView tvWorkTime;
        @Bind(R.id.tv_shebao)
        TextView tvShebao;
        @Bind(R.id.tv_location)
        TextView tvLocation;
        @Bind(R.id.tag_fuli)
        TagCloudView tagFuli;
        @Bind(R.id.tv_work_xuqiu)
        TextView tvWorkXuqiu;
        @Bind(R.id.tv_degree)
        TextView tvDegree;
        @Bind(R.id.tv_work_life)
        TextView tvWorkLife;
        @Bind(R.id.tv_more_info)
        TextView tvMoreInfo;
        @Bind(R.id.tv_work_info)
        TextView tvWorkInfo;
        @Bind(R.id.tv_today)
        TextView tvToday;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
