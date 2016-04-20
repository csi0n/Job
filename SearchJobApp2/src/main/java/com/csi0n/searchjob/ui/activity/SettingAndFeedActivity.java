package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.SettingAndFeedController;
import com.csi0n.searchjob.ui.fragment.BaseFragment;
import com.csi0n.searchjob.ui.fragment.SettingCommunityFragment;
import com.csi0n.searchjob.ui.fragment.SettingContactFragment;
import com.csi0n.searchjob.ui.fragment.SettingFriendDynamicFragment;
import com.csi0n.searchjob.ui.fragment.SettingNormalFragment;
import com.csi0n.searchjob.ui.fragment.SettingPersonInfoFragment;
import com.csi0n.searchjob.ui.fragment.SettingPrivacyFragment;
import com.csi0n.searchjob.ui.fragment.SettingQunFragment;
import com.csi0n.searchjob.ui.fragment.SettingSearchJobFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 2015/12/17 0017.
 */
@ContentView(R.layout.aty_setting_and_feed)
public class SettingAndFeedActivity extends BaseActivity {
    public SettingPersonInfoFragment mSettingPersonInfoFragment;
    public SettingNormalFragment mSettingNormalFragment;
    public SettingCommunityFragment mSettingCommunityFragment;
    public SettingContactFragment mSettingContactFragment;
    public SettingFriendDynamicFragment mSettingFriendDynamicFragment;
    public SettingPrivacyFragment mSettingPrivacyFragment;
    public SettingQunFragment mSettingQunFragment;
    public SettingSearchJobFragment mSettingSearchJobFragment;
    public SettingAndFeedController mSettingAndFeedController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = getString(R.string.str_setting_and_feed);
        super.setActionBarRes(actionBarRes);
    }
    @Event(value = R.id.rg_setting, type = RadioGroup.OnCheckedChangeListener.class)
    private void onCheckChanged(RadioGroup radioGroup, int id) {
        if (mSettingAndFeedController != null)
            mSettingAndFeedController.onCheckChanged(radioGroup, id);
    }

    @Override
    protected void initWidget() {
        mSettingPersonInfoFragment = new SettingPersonInfoFragment();
        mSettingNormalFragment = new SettingNormalFragment();
        mSettingCommunityFragment = new SettingCommunityFragment();
        mSettingContactFragment = new SettingContactFragment();
        mSettingFriendDynamicFragment = new SettingFriendDynamicFragment();
        mSettingPrivacyFragment = new SettingPrivacyFragment();
        mSettingQunFragment = new SettingQunFragment();
        mSettingSearchJobFragment = new SettingSearchJobFragment();
        mSettingAndFeedController = new SettingAndFeedController(this);
        mSettingAndFeedController.initSettingAndFeed();


    }

    public void changeFragment(BaseFragment baseFragment) {
        super.changeFragment(R.id.fl_content, baseFragment);
    }
}
