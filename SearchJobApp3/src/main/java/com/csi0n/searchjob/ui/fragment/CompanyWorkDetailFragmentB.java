package com.csi0n.searchjob.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.CompanyWorkDetailBController;
import com.csi0n.searchjob.model.CompanyJobListModel;
import com.csi0n.searchjob.model.CompanyWorkDetailBHeaderModel;
import com.csi0n.searchjob.ui.activity.ShowJobTypeDetailActivity;
import com.csi0n.searchjob.utils.BGANormalRefreshViewHolder;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/2/23 0023.
 */
@ContentView(R.layout.frag_list)
public class CompanyWorkDetailFragmentB extends BaseFragment {
    @ViewInject(value = R.id.refreshLayout)
    private BGARefreshLayout mBGARefreshLayout;
    @ViewInject(value = R.id.list)
    private ListView mList;
    private CompanyWorkDetailBController mCompanyWorkDetailBController;
    private ImageView mHeadImageView;
    private TextView mTVcompanyname, mTVcompanytype, mTVcompanyintro;

    private CompanyJobListModel.CompanyJobModel companyJobBean;

    @Override
    protected void initWidget() {
        Bundle bundle = aty.getIntent().getExtras();
        if (bundle != null)
            companyJobBean = (CompanyJobListModel.CompanyJobModel) bundle.getSerializable(Config.MARK_COMAPNY_WORK_DETAIL_ACTIVITY_COMPANY_DATA);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(aty, false));
        mBGARefreshLayout.setCustomHeaderView(getHeadView(), true);
        mCompanyWorkDetailBController = new CompanyWorkDetailBController(this);
        mCompanyWorkDetailBController.initCompanyWorkDetailB();
        mBGARefreshLayout.setDelegate(mCompanyWorkDetailBController);
        mList.setOnItemClickListener(mCompanyWorkDetailBController);
    }

    private View getHeadView() {
        View view = View.inflate(aty, R.layout.view_company_work_detail_b_header, null);
        mHeadImageView = (ImageView) view.findViewById(R.id.iv_head);
        mTVcompanyname = (TextView) view.findViewById(R.id.tv_company_name);
        mTVcompanytype = (TextView) view.findViewById(R.id.tv_company_type);
        mTVcompanyintro = (TextView) view.findViewById(R.id.tv_company_intro);
        return view;
    }

    public CompanyJobListModel.CompanyJobModel getCompanyJobBean() {
        return companyJobBean;
    }

    public void setHeadView(CompanyWorkDetailBHeaderModel searchJobDetailBHeaderBean) {
        Picasso.with(aty).load(com.csi0n.searchjob.lib.utils.Config.BASE_URL + searchJobDetailBHeaderBean.getImage()).into(mHeadImageView);
        mTVcompanyname.setText(searchJobDetailBHeaderBean.getName());
        mTVcompanytype.setText(searchJobDetailBHeaderBean.getType());
        mTVcompanyintro.setText(searchJobDetailBHeaderBean.getIntro());
    }

    public void setAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
    }

    public void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }
    public boolean hasHeader() {
        if (mList.getHeaderViewsCount() == 0)
            return false;
        else
            return true;
    }
    public void startShowSearchJobTypeActivity(String job_id){
        Bundle bundle=new Bundle();
        bundle.putString(Config.MARK_SHOW_SEARCH_JOB_TYPE_ACTIVITY,job_id);
        startActivityWithBunde(ShowJobTypeDetailActivity.class,bundle);
    }
}
