package com.csi0n.searchjob.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.AppManager;
import com.csi0n.searchjob.ui.base.mvp.ILoadDataView;

import butterknife.Bind;
import butterknife.ButterKnife;
import roboguice.activity.RoboActionBarActivity;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public abstract class BaseActivity extends RoboActionBarActivity implements ILoadDataView {
    @Nullable
    @Bind(value = R.id.tv_back)
    TextView mTvBack;
    @Nullable
    @Bind(value = R.id.tv_more)
    TextView mTvMore;
    @Nullable
    @Bind(value = R.id.tv_title)
    TextView mTvTitle;

    public class ActionBarRes {
        public CharSequence title;
        public CharSequence more;
    }

    private final ActionBarRes actionBarRes = new ActionBarRes();
    static final String LOADING_DIALOG_TAG = "loading_dialog";
    private DialogFragment loadingDialogFragment;
    protected Handler uiHandler;
    protected BaseFragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        try {
            initActionBar();
            initListener();
        } catch (Exception e) {
        }
        uiHandler=new Handler(getMainLooper());
        AppManager.getAppManager().addActivity(this);
    }

    private void initActionBar() throws Exception{
        setActionBarRes(actionBarRes);
        mTvTitle.setText(actionBarRes.title);
        mTvMore.setText(actionBarRes.more);
    }

    private void initListener() throws Exception {

        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPress();
            }
        });
        mTvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMore();
            }
        });
    }

    protected void setActionBarRes(ActionBarRes actionBarRes) {
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

    protected void onBackPress() {
        finish();
    }

    protected void onClickMore() {

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
