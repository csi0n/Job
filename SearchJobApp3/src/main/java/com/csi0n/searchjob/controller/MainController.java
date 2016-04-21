package com.csi0n.searchjob.controller;

import android.view.View;
import android.widget.RadioGroup;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.ui.activity.Main;
import com.csi0n.searchjob.utils.SharePreferenceManager;

/**
 * Created by chqss on 2016/4/19 0019.
 */
public class MainController extends BaseController {
    private Main mMain;

    public MainController(Main mMain) {
        this.mMain = mMain;
    }

    public void initMain() {
        if (mMain.isNeedLogin()) {
            verityToken(SharePreferenceManager.getKeyCachedToken());
        } else {
        }
        mMain.changeSearchJobFragment();
    }

    private void verityToken(String token) {

    }

    public void onCheckChange(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rd_news:
                mMain.startWanZhiDaoHang();
                break;
            case R.id.rd_search_job:
                mMain.changeSearchJobFragment();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {

    }
}
