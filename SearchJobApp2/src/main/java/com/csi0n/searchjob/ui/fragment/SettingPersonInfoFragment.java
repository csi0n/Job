package com.csi0n.searchjob.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.SettingPersonInfoController;
import com.csi0n.searchjob.ui.activity.ShowQRCodeActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by csi0n on 2015/12/17 0017.
 * 个人信息
 */
@ContentView(R.layout.frag_setting_person_info)
public class SettingPersonInfoFragment extends BaseFragment {
    private SettingPersonInfoController mSettingPersonInfoController;

    @Event(value = {R.id.rl_line_head, R.id.rl_line_uname, R.id.rl_line_qr, R.id.rl_line_sex, R.id.rl_line_address, R.id.rl_line_sign, R.id.rl_line_birthday,R.id.btn_logout})
    private void onClick(View view) {
        if (mSettingPersonInfoController != null)
            mSettingPersonInfoController.onClick(view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettingPersonInfoController = new SettingPersonInfoController(this);
        mSettingPersonInfoController.initSettingPersonNormal();
    }

    @Override
    protected void initWidget() {

    }

    public void startShowQRCode() {
        startActivity(ShowQRCodeActivity.class);
    }
}
