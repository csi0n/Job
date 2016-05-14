package com.csi0n.searchjob.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.event.ext.UserInfoChangeEvent;
import com.csi0n.searchjob.business.pojo.event.ext.ChoosePicHeadEvent;
import com.csi0n.searchjob.business.pojo.event.ext.MainFragmentSkipEvent;
import com.csi0n.searchjob.business.pojo.event.ext.UserLoginEvent;
import com.csi0n.searchjob.business.pojo.response.ext.GetChangeUserInfoResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetCheckTimeOutResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetCheckUserAppVerResponse;
import com.csi0n.searchjob.core.io.FileUtils;
import com.csi0n.searchjob.core.io.SharePreferenceManager;
import com.csi0n.searchjob.core.log.CLog;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.core.system.SystemUtils;
import com.csi0n.searchjob.lib.AppManager;
import com.csi0n.searchjob.lib.widget.CropImage.Crop;
import com.csi0n.searchjob.lib.widget.ProgressLoading;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.ui.base.BaseFragment;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.csi0n.searchjob.ui.home.MeFragment;
import com.csi0n.searchjob.ui.home.SearchJobFragment;
import com.csi0n.searchjob.ui.home.WangzhiDaoHangActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.Bind;
import roboguice.inject.ContentView;

import static com.csi0n.searchjob.core.string.Constants.MainSkipTYPE.*;

@ContentView(R.layout.aty_main)
public class MainActivity extends MvpActivity<MainPresenter, MainPresenter.IMainView> implements MainPresenter.IMainView {
    MeFragment mMeFragment;
    SearchJobFragment mSearchJobFragment;
    @Bind(value = R.id.rg_bottom)
    RadioGroup mBottom;
    @Bind(value = R.id.rd_news)
    RadioButton mRdNews;
    @Bind(value = R.id.rd_search_job)
    RadioButton mRdSearchJob;
    @Bind(value = R.id.rd_me)
    RadioButton mRdMe;
    AlertView alertView;
    ProgressLoading loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autoLogin();
        init();
        setListeners();
        goNext();
    }

    void autoLogin(){
        presenter.doGetCheckUpdate().subscribe(new AdvancedSubscriber<GetCheckUserAppVerResponse>(){
            @Override
            public void onHandleSuccess(GetCheckUserAppVerResponse response) {
                super.onHandleSuccess(response);
            }
        });
        final String token= SharePreferenceManager.getKeyCachedToken();
        if (!TextUtils.isEmpty(token)){
            presenter.doCheckTimeOut(token).subscribe(new AdvancedSubscriber<GetCheckTimeOutResponse>(){
                @Override
                public void onHandleSuccess(GetCheckTimeOutResponse response) {
                    super.onHandleSuccess(response);
                    Constants.DEFAULT_TOKEN=token;
                    Constants.LOGIN_USER=response.user;
                    EventBus.getDefault().post(new UserLoginEvent(response.user));
                    CLog.e("AUTO LOGIN SUCCESS!");
                }

                @Override
                public void onHandleFail(String message, Throwable throwable) {
                    super.onHandleFail(message, throwable);
                    CLog.e("AUTO LOGIN FAILED "+message);
                    SharePreferenceManager.setKeyCachedToken("");
                }
            });
        }
    }
    void init() {
        mMeFragment = new MeFragment();
        mSearchJobFragment = new SearchJobFragment();
    }

    void setListeners() {
        mBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rd_news:
                        startWangZhiDaoHang();
                        break;
                    case R.id.rd_search_job:
                        changeSearchJob();
                        break;
                    case R.id.rd_me:
                        if (Constants.LOGIN_USER != null)
                            changeMe();
                        else{
                            EventBus.getDefault().post(new MainFragmentSkipEvent(SEARCHJOB));
                            startLogin();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    void goNext() {
        changeSearchJob();
    }

    void changeMe() {
        changeFragment(mMeFragment);
    }

    void changeSearchJob() {
        changeFragment(mSearchJobFragment);
    }

    void changeFragment(BaseFragment targetFragment) {
        super.changeFragment(R.id.fl_content, targetFragment);
    }

    void startWangZhiDaoHang() {
        startActivity(getContext(), WangzhiDaoHangActivity.class);
    }

    void startLogin() {
        startActivity(getContext(), LoginActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.PIC_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            beginCrop(Uri.fromFile(FileUtils.getSaveFile(Constants.saveFolder, "TEMP_PIC.png")), "upload_pic", Constants.D_PIC_FROM_CAMERA);
        } else if (requestCode == Constants.PIC_FROM_DISK && resultCode == Activity.RESULT_OK) {
            beginCrop(data.getData(), "TEMP_PIC", Constants.D_PIC_FROM_DISK);
        } else if (requestCode == Constants.D_PIC_FROM_CAMERA||requestCode==Constants.D_PIC_FROM_DISK) {
            handleCrop(requestCode, resultCode, data);
        } else if (resultCode==Activity.RESULT_CANCELED){
            CLog.i("取消操作");
        }else {
            showError("不知名操作");
        }
    }
    private void beginCrop(Uri source, String name, int requestCode) {
        Uri destination = Uri.fromFile(FileUtils.getSaveFile(Constants.saveFolder, name.indexOf(".png") > 0 ? name : name + ".png"));
        Crop.of(source, destination).asSquare().start(this, requestCode);
    }
    private void handleCrop(int requestCode, int resultCode, Intent result) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.D_PIC_FROM_CAMERA) {
                uploadHead(FileUtils.getSaveFile(Constants.saveFolder, "upload_pic.png"));
            } else if (requestCode == Constants.D_PIC_FROM_DISK) {
                uploadHead(FileUtils.getSaveFile(Constants.saveFolder, "TEMP_PIC.png"));
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            CLog.i("取消操作");
        } else if (resultCode == Crop.RESULT_ERROR) {
            showError(Crop.getError(result).getMessage());
        } else {
            CLog.i("resultCode：" + resultCode + "Activity.RESULT_OK:" + Activity.RESULT_OK);
        }
    }
    void uploadHead(final File headfile) {
        presenter.doGetChangeUserInfo(headfile).subscribe(new AdvancedSubscriber<GetChangeUserInfoResponse>(this){
            @Override
            public void onHandleSuccess(GetChangeUserInfoResponse response) {
                super.onHandleSuccess(response);
                showToast("上传成功!");
                EventBus.getDefault().post(new UserInfoChangeEvent(headfile, UserInfoChangeEvent.ChangeType.HEAD));
            }
        });
    }
    @Override
    public void onEvent(Object object) {
        super.onEvent(object);
        if (object.getClass()==MainFragmentSkipEvent.class){
            switch (((MainFragmentSkipEvent)object).MAIN_SKIP_TYPE) {
                case ME:
                    mRdNews.setChecked(true);
                    break;
                case SEARCHJOB:
                    mRdSearchJob.setChecked(true);
                    break;
                case WANGZHIDAOHANG:
                    mRdNews.setChecked(true);
                    break;
                default:
                    break;
            }
        }else if (object.getClass()== ChoosePicHeadEvent.class){
            if (((ChoosePicHeadEvent)object).isFromCamera()){
                SystemUtils.openCamera(this, Constants.PIC_FROM_CAMERA, Constants.saveFolder);
            }else {
                SystemUtils.openPic(this, Constants.PIC_FROM_DISK);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            alertView = new AlertView("退出", "是否退出?", "取消", new String[]{"退出"}, null, this, AlertView.Style.Alert, new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {
                    if (position != AlertView.CANCELPOSITION) {
                        AppManager.getAppManager().AppExit(MainActivity.this);
                    }
                }
            }).setCancelable(true);
            alertView.show();
        }
        return false;
    }
}
