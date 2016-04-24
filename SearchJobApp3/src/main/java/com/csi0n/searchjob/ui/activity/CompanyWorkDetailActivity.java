package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.CompanyWorkDetailController;
import com.csi0n.searchjob.model.CompanyJobListModel;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by chqss on 2016/2/23 0023.
 */
@ContentView(R.layout.aty_company_work_detail)
public class CompanyWorkDetailActivity extends BaseActivity {
    @ViewInject(value = R.id.viewpagertab)
    private SmartTabLayout mSmartTabLayout;
    @ViewInject(value = R.id.viewpager)
    private ViewPager mViewPager;
    private CompanyJobListModel.CompanyJobModel companyJobBean;
    private CompanyWorkDetailController mCompanyWorkDetailController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = companyJobBean.getCompany_name();
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            companyJobBean = (CompanyJobListModel.CompanyJobModel) bundle.getSerializable(Config.MARK_COMAPNY_WORK_DETAIL_ACTIVITY_COMPANY_DATA);
        else
            finish();
        mCompanyWorkDetailController = new CompanyWorkDetailController(this);
        mCompanyWorkDetailController.initCompany();
    }

    public void setAdapter(FragmentPagerItemAdapter adapter) {
        mViewPager.setAdapter(adapter);
    }

    public void setViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(4);
        mSmartTabLayout.setViewPager(viewPager);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

}
