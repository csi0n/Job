package com.csi0n.searchjob.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.CompanyWorkDetailAController;
import com.csi0n.searchjob.model.CompanyJobListModel;
import com.csi0n.searchjob.model.CompanyWorkDetailAModel;
import com.csi0n.searchjob.model.FuliModel;
import com.csi0n.searchjob.utils.BGANormalRefreshViewHolder;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import me.next.tagview.TagCloudView;

/**
 * Created by chqss on 2016/2/23 0023.
 */
@ContentView(R.layout.frag_list)
public class CompanyWorkDetailFragmentA extends BaseFragment {
    @ViewInject(value = R.id.refreshLayout)
    private BGARefreshLayout mBGARefreshLayout;
    @ViewInject(value = R.id.list)
    private ListView mList;
    private TextView mGangWei, mTVUseType, mGongZi, mWorkTime, mSheBao, mLocation, mWorkXuQiu, mDegree, mWorkLife, mMoreInfo, mWorkInfo, mToday, mSearch;
    private TagCloudView mTag;
    private CompanyWorkDetailAController mCompanyWorkDetailAController;
    private CompanyJobListModel.CompanyJobModel companyJobBean;
    private DbManager db = x.getDb(Config.dbConfig);

    @Override
    protected void initWidget() {
        Bundle bundle = aty.getIntent().getExtras();
        if (bundle != null)
            companyJobBean = (CompanyJobListModel.CompanyJobModel) bundle.getSerializable(Config.MARK_COMAPNY_WORK_DETAIL_ACTIVITY_COMPANY_DATA);
        mCompanyWorkDetailAController = new CompanyWorkDetailAController(this);
        mCompanyWorkDetailAController.initCompany();
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(aty, false));
        mBGARefreshLayout.setCustomHeaderView(getHeadView(), true);
        mBGARefreshLayout.setDelegate(mCompanyWorkDetailAController);
    }

    private View getHeadView() {
        View view = View.inflate(aty, R.layout.view_company_work_detail_a_header, null);
        mGangWei = (TextView) view.findViewById(R.id.tv_gang_wei_bo);
        mTVUseType = (TextView) view.findViewById(R.id.tv_use_type);
        mGongZi = (TextView) view.findViewById(R.id.tv_gongzi);
        mWorkTime = (TextView) view.findViewById(R.id.tv_work_time);
        mSheBao = (TextView) view.findViewById(R.id.tv_sheba);
        mLocation = (TextView) view.findViewById(R.id.tv_location);
        mTag = (TagCloudView) view.findViewById(R.id.tag);
        mWorkXuQiu = (TextView) view.findViewById(R.id.tv_work_xuqiu);
        mDegree = (TextView) view.findViewById(R.id.tv_degree);
        mWorkLife = (TextView) view.findViewById(R.id.tv_work_life);
        mMoreInfo = (TextView) view.findViewById(R.id.tv_more_info);
        mWorkInfo = (TextView) view.findViewById(R.id.tv_work_info);
        mToday = (TextView) view.findViewById(R.id.tv_today);
        mSearch = (TextView) view.findViewById(R.id.tv_search);
        mSearch.setOnClickListener(mCompanyWorkDetailAController);
        return view;
    }

    public void setCompanyInforTop(CompanyWorkDetailAModel companyInforTop) {
        mGangWei.setText(companyInforTop.getJob_type());
        mTVUseType.setText(companyInforTop.getUse_type());
        mGongZi.setText(companyInforTop.getGongzi());
        mWorkTime.setText(companyInforTop.getWork_time());
        mSheBao.setText(Integer.valueOf(companyInforTop.getShebao()) == 0 ? "无" : "按照国家规定缴纳社保.");
        mLocation.setText(companyInforTop.getWork_location());
        mDegree.setText(companyInforTop.getDegree_wanted());
        mWorkLife.setText(companyInforTop.getWork_life());
        mMoreInfo.setText(companyInforTop.getMore_infor());
        mWorkInfo.setText(companyInforTop.getWork_infor());
        mToday.setText(companyInforTop.getDay());
        try {
            List<String> stringList = new ArrayList<>();
            String[] fulis = companyInforTop.getFuli().split(",");
            for (int i = 0; i < fulis.length; i++) {
                FuliModel fuliBean = db.selector(FuliModel.class).where("id", "=", fulis[i]).findFirst();
                if (fuliBean != null)
                    stringList.add(fuliBean.getName());
            }
            mTag.setTags(stringList);
        } catch (DbException e) {
        }
        String[] ages = companyInforTop.getAge().split(",");
        if (ages.length == 2) {
            mWorkXuQiu.setText(ages[0] + " " + ages[1]);
        } else {
            mWorkXuQiu.setText(companyInforTop.getAge());
        }
    }

    public void endRefersh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }
    public CompanyJobListModel.CompanyJobModel getCompanyJobBean() {
        return companyJobBean;
    }

    public void setAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
    }
}
