package com.csi0n.searchjob.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.MainController;
import com.csi0n.searchjob.lib.AppManager;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.FileUtils;
import com.csi0n.searchjob.lib.utils.SystemUtils;
import com.csi0n.searchjob.lib.widget.CropImage.Crop;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.model.SearchJobKeyCacheModel;
import com.csi0n.searchjob.model.event.ChoosePicHeadEvent;
import com.csi0n.searchjob.model.event.HeadChangeEvent;
import com.csi0n.searchjob.ui.fragment.BaseFragment;
import com.csi0n.searchjob.ui.fragment.MeFragment;
import com.csi0n.searchjob.ui.fragment.SearchJobFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.ex.DbException;
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
    private MeFragment mMeFragment;
    private boolean isNeedLogin = false;
    private MainController mMainController;
    private AlertView alertView;

    @Override
    protected void initWidget() {
        Bundle bundle = getBundle();
        if (bundle != null)
            isNeedLogin = bundle.getBoolean(Config.MARK_MAIN_IS_NEED_AUTO_LOGIN, false);
        mSearchJobFragment = new SearchJobFragment();
        mMeFragment = new MeFragment();
        mMainController = new MainController(this);
        mMainController.initMain();
        registerReceive();
        EventBus.getDefault().register(this);
    }

    public boolean isNeedLogin() {
        return isNeedLogin;
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

    public void changeSearchJobFragment() {
        changeFragment(mSearchJobFragment);
    }

    public void changeMeFragment() {
        changeFragment(mMeFragment);
    }

    public void startLoginActivity() {
        startActivity(aty, LoginActivity.class);
    }

    public void setRadioButtonCheck(RadioButton radioButton) {
        radioButton.setChecked(true);
    }

    private void changeFragment(BaseFragment targetFragment) {
        super.changeFragment(R.id.fl_content, targetFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aty.unregisterReceiver(mainBroadcastReceiver);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.PIC_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            beginCrop(Uri.fromFile(FileUtils.getSaveFile(Config.saveFolder, "TEMP_PIC.png")), "upload_pic", Config.D_PIC_FROM_CAMERA);
        } else if (requestCode == Config.PIC_FROM_DISK && resultCode == Activity.RESULT_OK) {
            beginCrop(data.getData(), "TEMP_PIC", Config.D_PIC_FROM_DISK);
        } else if (requestCode == Config.D_PIC_FROM_CAMERA||requestCode==Config.D_PIC_FROM_DISK) {
            handleCrop(requestCode, resultCode, data);
        } else if (resultCode==Activity.RESULT_CANCELED){
            CLog.getInstance().iMessage("取消操作");
        }else {
            CLog.show("不知名操作");
        }
    }

    private void beginCrop(Uri source, String name, int requestCode) {
        Uri destination = Uri.fromFile(FileUtils.getSaveFile(Config.saveFolder, name.indexOf(".png") > 0 ? name : name + ".png"));
        Crop.of(source, destination).asSquare().start(this, requestCode);
    }

    private void handleCrop(int requestCode, int resultCode, Intent result) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Config.D_PIC_FROM_CAMERA) {
                mMainController.uploadHead(FileUtils.getSaveFile(Config.saveFolder, "upload_pic.png"));
            } else if (requestCode == Config.D_PIC_FROM_DISK) {
                mMainController.uploadHead(FileUtils.getSaveFile(Config.saveFolder, "TEMP_PIC.png"));
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            CLog.getInstance().iMessage("取消操作");
        } else if (resultCode == Crop.RESULT_ERROR) {
            CLog.show(Crop.getError(result).getMessage());
        } else {
            CLog.getInstance().iMessage("resultCode：" + resultCode + "Activity.RESULT_OK:" + Activity.RESULT_OK);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            alertView = new AlertView("退出", "是否退出?", "取消", new String[]{"退出"}, null, aty, AlertView.Style.Alert, new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {
                    if (position != AlertView.CANCELPOSITION) {
                        AppManager.getAppManager().AppExit(aty);
                    }
                }
            }).setCancelable(true);
            alertView.show();
        }
        return false;
    }

    @Subscribe
    public void onEvent(ChoosePicHeadEvent choosePicHeadEvent) {
        if (choosePicHeadEvent.isFromCamera())
            SystemUtils.openCamera(aty, Config.PIC_FROM_CAMERA, Config.saveFolder);
        else
            SystemUtils.openPic(aty, Config.PIC_FROM_DISK);
    }
}
