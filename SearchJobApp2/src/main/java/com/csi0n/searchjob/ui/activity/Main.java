package com.csi0n.searchjob.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.MainController;
import com.csi0n.searchjob.ui.fragment.BaseFragment;
import com.csi0n.searchjob.ui.fragment.ContactFragment;
import com.csi0n.searchjob.ui.fragment.SearchJobFragment;
import com.csi0n.searchjob.utils.ProgressLoading;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 2015/12/14 0014.
 */
@ContentView(R.layout.aty_main)
public class Main extends BaseActivity {
    private ContactFragment mContactFragment;
    private SearchJobFragment mSearchJobFragment;
    private MainController mMainController;
    @ViewInject(value = R.id.rd_news)
    public RadioButton mNews;
    @ViewInject(value = R.id.rd_contact)
    public RadioButton mContacts;
    @ViewInject(value = R.id.rd_search_job)
    public RadioButton mSearchJob;
    public boolean is_auto_login = false;
    @Event(value = R.id.rg_bottom, type = RadioGroup.OnCheckedChangeListener.class)
    private void onCheckChange(RadioGroup radioGroup, int id) {
        if (mMainController != null)
            mMainController.onCheckChange(radioGroup, id);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceive();
        initData();
        mMainController = new MainController(this);
        mMainController.initMain();
    }

    protected void initData() {
        Bundle bundle = getBundle();
        if (bundle != null) {
            is_auto_login = bundle.getBoolean("is_auto_login");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aty.unregisterReceiver(mainBroadcastReceiver);
    }

    @Override
    protected void initWidget() {
        mContactFragment = new ContactFragment();
        mSearchJobFragment = new SearchJobFragment();
    }

    public void changeFragment(BaseFragment targetFragment) {
        super.changeFragment(R.id.fl_content, targetFragment);
    }

    private MainBroadcastReceiver mainBroadcastReceiver;

    private void registerReceive() {
        mainBroadcastReceiver = new MainBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Config.INTENT_USER_LOGIN_SUCCESS);
        filter.addAction(Config.MARK_MAIN_ACTIVITY_CHANGE_CONTACT);
        filter.addAction(Config.MARK_MAIN_ACTIVITY_CHANGE_SEARCH_JOB);
        aty.registerReceiver(mainBroadcastReceiver, filter);
    }

    private class MainBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Config.INTENT_USER_LOGIN_SUCCESS.equals(intent.getAction())) {
                mNews.setEnabled(true);
                mContacts.setEnabled(true);
                mSearchJob.setEnabled(true);
            }else {
                if (mNews.isEnabled()&&mContacts.isEnabled()&&mSearchJob.isEnabled()){
                    if (Config.MARK_MAIN_ACTIVITY_CHANGE_CONTACT.equals(intent.getAction())){
                       mContacts.setChecked(true);
                    }else if (Config.MARK_MAIN_ACTIVITY_CHANGE_SEARCH_JOB.equals(intent.getAction())){
                        mSearchJob.setChecked(true);
                    }
                }
            }
        }
    }
    public ContactFragment getContactFragment() {
        return mContactFragment;
    }

    public SearchJobFragment getSearchJobFragment() {
        return mSearchJobFragment;
    }

    public void startLogin() {
        startActivity(aty, LoginActivity.class);
        aty.finish();
    }

    public void startWanZhiDaoHang() {
        startActivity(aty, WangzhiDaoHangActivity.class);
    }
}
