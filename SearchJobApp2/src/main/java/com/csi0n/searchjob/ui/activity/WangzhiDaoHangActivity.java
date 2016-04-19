package com.csi0n.searchjob.ui.activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.WanZhiDaoHangController;
import com.csi0n.searchjob.lib.utils.StringUtils;
import com.csi0n.searchjob.ui.fragment.WebViewFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 2/21/16.
 */
@ContentView(R.layout.aty_news_webview)
public class WangzhiDaoHangActivity extends BaseActivity {

    private WanZhiDaoHangController mWanZhiDaoHangController;
    @ViewInject(value = R.id.viewpagertab)
    private SmartTabLayout mSmartTabLayout;
    @ViewInject(value = R.id.viewpager)
    private ViewPager mViewPager;

    @Event(value = {R.id.t_webview_back, R.id.m_contact, R.id.m_home, R.id.m_search_job})
    private void onClick(View view) {
        if (mWanZhiDaoHangController != null)
            mWanZhiDaoHangController.onClick(view);
    }
    @Override
    protected void initWidget() {
        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (String title : StringUtils.getNewsTitle()) {
            pages.add(FragmentPagerItem.of(title, WebViewFragment.class));
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        mViewPager.setAdapter(adapter);
        mSmartTabLayout.setViewPager(mViewPager);
        mWanZhiDaoHangController = new WanZhiDaoHangController(this);
        mWanZhiDaoHangController.initWanZhiDaoHang();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN) {
            Intent intent = new Intent(Config.MARK_MAIN_ACTIVITY_CHANGE_CONTACT);
            sendBroadcast(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

}
