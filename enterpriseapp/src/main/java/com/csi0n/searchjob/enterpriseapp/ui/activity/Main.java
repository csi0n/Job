package com.csi0n.searchjob.enterpriseapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.csi0n.searchjob.enterpriseapp.Config;
import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.controller.MainController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.FileUtils;
import com.csi0n.searchjob.lib.widget.CropImage.Crop;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

@ContentView(R.layout.aty_main)
public class Main extends BaseActivity {
    @ViewInject(value = R.id.ri_head)
    private RoundImageView mHead;
    @ViewInject(value = R.id.tv_name)
    private TextView mTVname;

    @Event(value = {R.id.ll_user_info, R.id.ll_normal_user, R.id.ll_comments, R.id.ri_head})
    private void onClick(View view) {
        if (mMainController != null)
            mMainController.onClick(view);
    }

    private MainController mMainController;
    private boolean need_check_timeout = false;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = getString(R.string.str_main);
        actionBarRes.more = "添加";
        actionBarRes.backGone = true;
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            finish();
        need_check_timeout = bundle.getBoolean(Config.MARK_MAIN_ACTIVITY_CHECK_TIME_OUT);
        mMainController = new MainController(this);
        mMainController.initMain();
    }

    public void setHead(String url) {
        Picasso.with(aty).load(com.csi0n.searchjob.lib.utils.Config.BASE_URL + url).into(mHead);
    }

    public void setHead(File file) {
        Picasso.with(aty).load(file).into(mHead);
    }

    public void setName(String name) {
        mTVname.setText(name);
    }

    public void startLogin() {
        skipActivity(aty, LoginActivity.class);
    }

    public boolean isNeed_check_timeout() {
        return need_check_timeout;
    }

    @Override
    protected void onMoreClick() {
        super.onMoreClick();
        Bundle bundle=new Bundle();
        bundle.putBoolean(Config.MARK_SEND_ACTIVITY_IS_FROM_MONEY_BACK,false);
        skipActivityWithBundleWithOutExit(aty,SendActivity.class,bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.PIC_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            beginCrop(Uri.fromFile(FileUtils.getSaveFile(Config.saveFolder, "TEMP_PIC.png")), "upload_pic", Config.D_PIC_FROM_CAMERA);
        } else if (requestCode == Config.PIC_FROM_DISK && resultCode == Activity.RESULT_OK) {
            beginCrop(data.getData(), "TEMP_PIC", Config.D_PIC_FROM_DISK);
        } else if (requestCode == Config.D_PIC_FROM_CAMERA || requestCode == Config.D_PIC_FROM_DISK) {
            handleCrop(requestCode, resultCode, data);
        } else {
            CLog.show("不知名操作");
        }
    }

    private void beginCrop(Uri source, String name, int requestCode) {
        Uri destination = Uri.fromFile(FileUtils.getSaveFile(Config.saveFolder, name.indexOf(".png") > 0 ? name : name + ".png"));
        Crop.of(source, destination).asSquare().start(this, requestCode);
    }

    private void handleCrop(int requestCode, int resultCode, Intent result) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Config.PIC_FROM_CAMERA) {
                mMainController.do_changeHead(FileUtils.getSaveFile(Config.saveFolder, "upload_pic.png"));
            } else if (requestCode == Config.PIC_FROM_DISK) {
                mMainController.do_changeHead(FileUtils.getSaveFile(Config.saveFolder, "TEMP_PIC.png"));
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            CLog.show(Crop.getError(result).getMessage());
        } else {
            CLog.getInstance().iMessage("resultCode：" + resultCode + "Activity.RESULT_OK:" + Activity.RESULT_OK);
        }
    }

    public void startUserInfo() {
        startActivity(aty, UserInfoActivity.class);
    }

    public void startNormal() {
        Bundle bundle=new Bundle();
        bundle.putBoolean(Config.MARK_SEND_ACTIVITY_IS_FROM_MONEY_BACK,true);
        skipActivityWithBundleWithOutExit(aty,SendActivity.class,bundle);
    }

    public void startComments() {
        startActivity(aty, CommentsActivity.class);
    }
}
