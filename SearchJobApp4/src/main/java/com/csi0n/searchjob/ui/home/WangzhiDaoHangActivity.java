package com.csi0n.searchjob.ui.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.csi0n.searchjob.business.pojo.event.ext.MainFragmentSkipEvent;
import com.csi0n.searchjob.business.pojo.event.ext.WangZhiDaoHangEvent;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.csi0n.searchjobapp.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import org.greenrobot.eventbus.EventBus;
import butterknife.Bind;
import butterknife.OnClick;
import roboguice.inject.ContentView;

/**
 * Created by chqss on 2016/5/1 0001.
 */
@ContentView(R.layout.aty_news_webview)
public class WangzhiDaoHangActivity extends MvpActivity<WangzhiDaoHangPresenter, WangzhiDaoHangPresenter.IWangzhiDaoHangView> implements WangzhiDaoHangPresenter.IWangzhiDaoHangView {
    @Bind(value = R.id.viewpagertab)
    private SmartTabLayout mSmartTabLayout;
    @Bind(value = R.id.viewpager)
    private ViewPager mViewPager;
    @OnClick(value = {R.id.t_webview_back, R.id.m_home, R.id.m_search_job}) void onClick(View view) {
        switch (view.getId()) {
            case R.id.t_webview_back:
                EventBus.getDefault().post(new WangZhiDaoHangEvent(true,false));
                break;
            case R.id.m_home:
                EventBus.getDefault().post(new WangZhiDaoHangEvent(false,true));
                break;
            case R.id.m_search_job:
                EventBus.getDefault().post(new MainFragmentSkipEvent(Constants.MainSkipTYPE.SEARCHJOB));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (String title : Constants.getNewsTitle()) {
            pages.add(FragmentPagerItem.of(title, WebViewFragment.class));
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        mViewPager.setAdapter(adapter);
        mSmartTabLayout.setViewPager(mViewPager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN) {
            EventBus.getDefault().post(new MainFragmentSkipEvent(Constants.MainSkipTYPE.SEARCHJOB));
        }
        return super.onKeyDown(keyCode, event);
    }
}
