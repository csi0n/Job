package com.csi0n.searchjob.controller;

import android.view.View;
import android.widget.RadioGroup;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.ui.activity.SettingAndFeedActivity;
import com.csi0n.searchjob.ui.fragment.BaseFragment;

/**
 * Created by csi0n on 12/17/15.
 */
public class SettingAndFeedController extends BaseController {
    private SettingAndFeedActivity mSettingAndFeedActivity;

    public SettingAndFeedController(SettingAndFeedActivity settingAndFeedActivity) {
        this.mSettingAndFeedActivity = settingAndFeedActivity;
    }

    public void initSettingAndFeed() {
        changeFragment(mSettingAndFeedActivity.mSettingPersonInfoFragment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }

    public void onCheckChanged(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rb_user_info:
                changeFragment(mSettingAndFeedActivity.mSettingPersonInfoFragment);
                break;
            case R.id.rb_normal_use:
                changeFragment(mSettingAndFeedActivity.mSettingNormalFragment);
                break;
            case R.id.rb_contact:
                changeFragment(mSettingAndFeedActivity.mSettingContactFragment);
                break;
            case R.id.rb_friend_dynamic:
                changeFragment(mSettingAndFeedActivity.mSettingFriendDynamicFragment);
                break;
            case R.id.rb_privacy:
                changeFragment(mSettingAndFeedActivity.mSettingPrivacyFragment);
                break;
            case R.id.rb_qun:
                changeFragment(mSettingAndFeedActivity.mSettingQunFragment);
                break;
            case R.id.rb_search_job:
                changeFragment(mSettingAndFeedActivity.mSettingSearchJobFragment);
                break;
            case R.id.rb_jingliren:
                changeFragment(mSettingAndFeedActivity.mSettingJinlirenFragment);
                break;
            default:
                break;
        }
    }

    private void changeFragment(BaseFragment baseFragment) {
        mSettingAndFeedActivity.changeFragment(baseFragment);
    }
}
