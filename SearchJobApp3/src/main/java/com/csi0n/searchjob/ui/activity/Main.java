package com.csi0n.searchjob.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.MainController;
import com.csi0n.searchjob.ui.fragment.BaseFragment;
import com.csi0n.searchjob.ui.fragment.SearchJobFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.aty_main)
public class Main extends BaseActivity {
    @ViewInject(value = R.id.rd_search_job)
    private RadioButton mRdSearchJob;

    @Event(value = R.id.rg_bottom, type = RadioGroup.OnCheckedChangeListener.class)
    private void onCheckChange(RadioGroup radioGroup, int id) {
        if (mMainController != null)
            mMainController.onCheckChange(radioGroup, id);
    }

    private SearchJobFragment mSearchJobFragment;
    private boolean isNeedLogin = false;
    private MainController mMainController;
    @Override
    protected void initWidget() {
        Bundle bundle = getBundle();
        if (bundle != null)
            isNeedLogin = bundle.getBoolean(Config.MARK_MAIN_IS_NEED_AUTO_LOGIN, false);
        registerReceive();
        mSearchJobFragment = new SearchJobFragment();
        changeFragment(mSearchJobFragment);
        mMainController = new MainController(this);
        mMainController.initMain();
    }

    public boolean isNeedLogin() {
        return isNeedLogin;
    }

    public SearchJobFragment getSearchJobFragment() {
        return mSearchJobFragment;
    }

    public void startWanZhiDaoHang() {
        startActivity(aty, WangzhiDaoHangActivity.class);
    }

    private MainBroadcastReceiver mainBroadcastReceiver;

    private void registerReceive() {
        mainBroadcastReceiver = new MainBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Config.MARK_MAIN_ACTIVITY_CHANGE_SEARCH_JOB);
        aty.registerReceiver(mainBroadcastReceiver, filter);
    }

    private class MainBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Config.MARK_MAIN_ACTIVITY_CHANGE_SEARCH_JOB.equals(intent.getAction())) {
                mRdSearchJob.setChecked(true);
            }
        }
    }

    public void changeFragment(BaseFragment targetFragment) {
        super.changeFragment(R.id.fl_content, targetFragment);
    }

    @Override
    protected void onDestroy() {
        aty.unregisterReceiver(mainBroadcastReceiver);
        super.onDestroy();
    }
}
