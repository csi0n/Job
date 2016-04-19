package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CheckUpdate;
import com.csi0n.searchjob.ui.fragment.SettingNormalFragment;

/**
 * Created by csi0n on 2015/12/18 0018.
 */
public class SettingNormalController extends BaseController {
    private SettingNormalFragment mSettingNormalFragment;

    public SettingNormalController(SettingNormalFragment settingNormalFragment) {
        this.mSettingNormalFragment = settingNormalFragment;
    }

    public void initSettingNormal() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_line_chat_font_size:
                mSettingNormalFragment.startFontSize();
                break;
            case R.id.rl_line_check_update:
                CheckUpdate checkUpdate = new CheckUpdate(mSettingNormalFragment.getActivity(), Config.saveFolder,Config.APP_NAME,R.string.url_checkUpdate);
                checkUpdate.start();
                break;
            default:
                break;
        }
    }
}
