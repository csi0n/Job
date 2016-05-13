package com.csi0n.searchjob.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.ui.base.BaseActivity;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.csi0n.searchjob.ui.companydeatil.CompanyWorkDetailFragmentA;
import com.csi0n.searchjob.ui.companydeatil.CompanyWorkDetailFragmentB;
import com.csi0n.searchjob.ui.companydeatil.CompanyWorkDetailFragmentC;
import com.csi0n.searchjob.ui.companydeatil.CompanyWorkDetailFragmentD;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import butterknife.Bind;
import roboguice.inject.ContentView;

/**
 * Created by chqss on 2016/5/12 0012.
 */
@ContentView(R.layout.aty_company_work_detail)
public class CompanyWorkDetailActivity extends MvpActivity<CompanyWorkDetailPresenter, CompanyWorkDetailPresenter.ICompanyWorkDetailView> {

    @Bind(R.id.viewpagertab)
    SmartTabLayout mViewpagerTab;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title="公司详细";
        super.setActionBarRes(actionBarRes);
    }

    private void init(){
        Bundle bundle=getBundle();
        if (bundle==null)
            finish();
        FragmentPagerItems pages = new FragmentPagerItems(this);
        int i = 0;
        for (String title : Constants.getCompanyWorkDetailTop()) {
            switch (i) {
                case 0:
                    pages.add(FragmentPagerItem.of(title, CompanyWorkDetailFragmentA.class));
                    break;
                case 1:
                    pages.add(FragmentPagerItem.of(title, CompanyWorkDetailFragmentB.class));
                    break;
                case 2:
                    pages.add(FragmentPagerItem.of(title, CompanyWorkDetailFragmentC.class));
                    break;
                case 3:
                    pages.add(FragmentPagerItem.of(title, CompanyWorkDetailFragmentD.class));
                    break;
            }
            i++;
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewpagerTab.setViewPager(mViewPager);
    }
}
