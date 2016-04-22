package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.StringUtils;
import com.csi0n.searchjob.ui.activity.CompanyWorkDetailActivity;
import com.csi0n.searchjob.ui.fragment.CompanyWorkDetailFragmentA;
import com.csi0n.searchjob.ui.fragment.CompanyWorkDetailFragmentB;
import com.csi0n.searchjob.ui.fragment.CompanyWorkDetailFragmentC;
import com.csi0n.searchjob.ui.fragment.CompanyWorkDetailFragmentD;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * Created by chqss on 2016/3/3 0003.
 */
public class CompanyWorkDetailController extends BaseController {
    private CompanyWorkDetailActivity mCompanyWorkDetailActivity;


    public CompanyWorkDetailController(CompanyWorkDetailActivity companyWorkDetailActivity) {
        this.mCompanyWorkDetailActivity = companyWorkDetailActivity;
    }

    public void initCompany() {
        FragmentPagerItems pages = new FragmentPagerItems(mCompanyWorkDetailActivity);
        int i = 0;
        for (String title : StringUtils.getCompanyWorkDetailTop()) {
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
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(mCompanyWorkDetailActivity.getSupportFragmentManager(), pages);
        mCompanyWorkDetailActivity.setAdapter(adapter);
        mCompanyWorkDetailActivity.setViewPager(mCompanyWorkDetailActivity.getViewPager());
    }

    @Override
    public void onClick(View view) {

    }
}
