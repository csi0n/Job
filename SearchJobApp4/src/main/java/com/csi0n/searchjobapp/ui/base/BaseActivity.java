package com.csi0n.searchjobapp.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.csi0n.searchjobapp.ui.base.mvp.ILoadDataView;

import butterknife.ButterKnife;
import roboguice.activity.RoboFragmentActivity;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class BaseActivity extends RoboFragmentActivity implements ILoadDataView {
    static final String LOADING_DIALOG_TAG = "loading_dialog";
    private DialogFragment loadingDialogFragment;
    protected Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        uiHandler=new Handler(getMainLooper());
    }
    @Override
    public void showLoading() {

    }


    @Override
    public void showLoading(int loadingType) {
        switch (loadingType){
            case LOADING_TYPE_DEFAULT:
                default:
                    showDefaultStyleLoadingDialog(null);
                    break;
        }
    }
    // 显示用默认样式的Loading对话框
    private void showDefaultStyleLoadingDialog(String loadingTitle) {
        hideDefaultStyleLoadingDialog();
        DialogFragment newFragment = LoadingDialogFragment.newInstance(loadingTitle);
        newFragment.show(getSupportFragmentManager(), LOADING_DIALOG_TAG);
        loadingDialogFragment = newFragment;
    }
    //  隐藏默认样式的loading对话框
    private void hideDefaultStyleLoadingDialog() {
        if (loadingDialogFragment != null) {
            loadingDialogFragment.dismiss();
            loadingDialogFragment = null;
        }
    }
    @Override
    public void hideLoading() {
        hideLoading(LOADING_TYPE_DEFAULT);
    }

    @Override
    public void hideLoading(int loadingType) {
        switch (loadingType){
            case LOADING_TYPE_DEFAULT:
                default:
                    hideDefaultStyleLoadingDialog();
                    break;
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(BaseActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
