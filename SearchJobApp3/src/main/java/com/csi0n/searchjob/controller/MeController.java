package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.SystemUtils;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.model.event.ChoosePicHeadEvent;
import com.csi0n.searchjob.ui.fragment.MeFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by csi0n on 4/25/16.
 */
public class MeController extends BaseController {
    private MeFragment mMeFragment;
    private AlertView alertView;

    public MeController(MeFragment mMeFragment) {
        this.mMeFragment = mMeFragment;
    }

    public void initMe() {
        mMeFragment.initViewText(Config.LOGIN_USER);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ri_head:
                alertView = new AlertView("上传头像", null, "取消", null,
                        new String[]{"拍照", "从相册中选择"},
                        mMeFragment.getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:
                                EventBus.getDefault().post(new ChoosePicHeadEvent(true));
                                break;
                            case 1:
                                EventBus.getDefault().post(new ChoosePicHeadEvent(false));
                                break;
                        }
                    }
                });
                alertView.show();
                break;
            case R.id.btn_update_document:
                mMeFragment.startUpdateDocument();
                break;
            case R.id.btn_comments:
                mMeFragment.startComments();
                break;
            case R.id.btn_change_password:
                mMeFragment.startChangePassword();
                break;
            case R.id.tv_uname:
                mMeFragment.startChangeUname();
                break;
            case R.id.tv_intro:
                mMeFragment.startChangeIntro();
                break;
            default:
                break;
        }
    }
}
