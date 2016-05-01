package com.csi0n.searchjobapp.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.csi0n.searchjobapp.ui.base.mvp.ILoadDataView;

import butterknife.ButterKnife;
import roboguice.activity.RoboActionBarActivity;
import roboguice.activity.RoboFragmentActivity;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class BaseActivity extends RoboActionBarActivity implements ILoadDataView {
    static final String LOADING_DIALOG_TAG = "loading_dialog";
    private DialogFragment loadingDialogFragment;
    protected Handler uiHandler;
    protected BaseFragment currentFragment;
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
    public void skipActivityWithBundle(Context context, Class<?> classz, Bundle bundle) {
        startActivity(context, classz, bundle);
        finish();
    }

    public void skipActivityWithBundleWithOutExit(Context context, Class<?> classz, Bundle bundle) {
        startActivity(context, classz, bundle);
    }

    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    public void startActivity(Context context, Class<?> classz) {
        startActivity(context, classz, null);
    }

    private void startActivity(Context context, Class<?> classz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, classz);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void changeFragment(int resView, BaseFragment targetFragment) {
        if (targetFragment.equals(currentFragment)) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass().getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        if (currentFragment != null && currentFragment.isVisible()) {
            transaction.hide(currentFragment);
        }
        currentFragment = targetFragment;
        transaction.commit();
    }
}
