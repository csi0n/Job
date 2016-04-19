package com.csi0n.searchjob.controller;

import android.content.Intent;
import android.view.View;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.ui.activity.WangzhiDaoHangActivity;
import com.csi0n.searchjob.utils.bean.WangZhiDaoHang;

import cn.jpush.im.android.eventbus.EventBus;

/**
 * Created by csi0n on 2/21/16.
 */
public class WanZhiDaoHangController extends BaseController {
    private WangzhiDaoHangActivity mWangzhiDaoHangActivity;

    public WanZhiDaoHangController(WangzhiDaoHangActivity mWangzhiDaoHangActivity) {
        this.mWangzhiDaoHangActivity = mWangzhiDaoHangActivity;
    }

    public void initWanZhiDaoHang() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.t_webview_back:
                WangZhiDaoHang wangZhiDaoHang = new WangZhiDaoHang();
                wangZhiDaoHang.setBack(true);
                wangZhiDaoHang.setHome(false);
                EventBus.getDefault().post(wangZhiDaoHang);
                break;
            case R.id.m_contact:
                Intent intent = new Intent(Config.MARK_MAIN_ACTIVITY_CHANGE_CONTACT);
                mWangzhiDaoHangActivity.sendBroadcast(intent);
                mWangzhiDaoHangActivity.finish();
                break;
            case R.id.m_home:
                WangZhiDaoHang wangZhiDaoHan = new WangZhiDaoHang();
                wangZhiDaoHan.setBack(false);
                wangZhiDaoHan.setHome(true);
                EventBus.getDefault().post(wangZhiDaoHan);
                break;
            case R.id.m_search_job:
                Intent inten2 = new Intent(Config.MARK_MAIN_ACTIVITY_CHANGE_SEARCH_JOB);
                mWangzhiDaoHangActivity.sendBroadcast(inten2);
                mWangzhiDaoHangActivity.finish();
                break;
            default:
                break;
        }
    }
}
