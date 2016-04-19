package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.AppManager;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.ui.fragment.SettingPersonInfoFragment;
import com.csi0n.searchjob.ui.widget.timeandaddresschoose.ChangeAddressDialog;
import com.csi0n.searchjob.ui.widget.timeandaddresschoose.ChangeBirthDialog;
import com.csi0n.searchjob.utils.SharePreferenceManager;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by csi0n on 2015/12/18 0018.
 */
public class SettingPersonInfoController extends BaseController {
    private SettingPersonInfoFragment mSettingPersonInfoFragment;
    private ChangeBirthDialog mChangeBirthDialog;
    private ChangeAddressDialog mChangeAddressDialog;
    private AlertView alertView;

    public SettingPersonInfoController(SettingPersonInfoFragment settingPersonInfoFragment){
        this.mSettingPersonInfoFragment=settingPersonInfoFragment;
    }
    public void initSettingPersonNormal(){

    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_line_head:
                alertView = new AlertView("上传头像", null, "取消", null,
                        new String[]{"拍照", "从相册中选择"},
                        mSettingPersonInfoFragment.getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:

                                break;
                            case 1:

                                break;
                        }
                    }
                });
                alertView.show();
                break;
            case R.id.rl_line_sex:
                alertView = new AlertView("请选择性别", null, "取消", null,
                        new String[]{"男", "女"},
                        mSettingPersonInfoFragment.getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:

                                break;
                            case 1:

                                break;
                        }
                    }
                });
                alertView.show();
                break;
            case R.id.rl_line_address:
                mChangeAddressDialog = new ChangeAddressDialog(mSettingPersonInfoFragment.getActivity());
                mChangeAddressDialog.setAddress("江苏", "无锡");
                mChangeAddressDialog.show();
                mChangeAddressDialog.setAddresskListener(new ChangeAddressDialog.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String city) {

                    }
                });
                break;
            case R.id.rl_line_birthday:
                mChangeBirthDialog = new ChangeBirthDialog(mSettingPersonInfoFragment.getActivity());
                mChangeBirthDialog.setDate(2014, 1, 1);
                mChangeBirthDialog.show();
                mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {
                    @Override
                    public void onClick(String year, String month, String day) {

                    }
                });
                break;
            case R.id.rl_line_qr:
                mSettingPersonInfoFragment.startShowQRCode();
                break;
            case R.id.btn_logout:
                SharePreferenceManager.setKeyCachedToken("");
                JMessageClient.logout();
                AppManager.getAppManager().restartApplication(mSettingPersonInfoFragment.getActivity());
                break;
            default:
                break;
        }
    }
}
