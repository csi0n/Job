package com.csi0n.searchjob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.AppManager;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.ui.fragment.BaseFragment;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by csi0n on 2015/12/14 0014.
 */
public abstract class BaseActivity extends FragmentActivity {
    public class ActionBarRes {
        public CharSequence title;
        public CharSequence more;
    }

    private final ActionBarRes actionBarRes = new ActionBarRes();
    public TextView mTVTitle;
    public TextView mTVMore;
    public TextView mTVBack;
    public BaseActivity aty;
    protected BaseFragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!"com.csi0n.searchjob".equals(getApplication().getPackageName()))
            AppManager.getAppManager().AppExit(this);

        AppManager.getAppManager().addActivity(this);
        aty = this;
        x.view().inject(this);
        try {
            mTVTitle = (TextView) findViewById(R.id.tv_title);
            mTVMore = (TextView) findViewById(R.id.tv_more);
            mTVBack = (TextView) findViewById(R.id.tv_back);
            findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPress();
                }
            });
            mTVMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMoreClick();
                }
            });
        } catch (NullPointerException e) {
        }

        initWidget();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        CLog.getInstance().iMessage(this.getClass().getName() + "**********onCreate**********");
    }

    protected abstract void initWidget();

    @Override
    protected void onStart() {
        super.onStart();
        CLog.getInstance().iMessage(this.getClass().getName() + "**********onStart**********");
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        setActionBarRes(actionBarRes);
        setTitleText(actionBarRes.title);
        setMoreText(actionBarRes.more);
        CLog.getInstance().iMessage(this.getClass().getName() + "**********onResume**********");
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        CLog.getInstance().iMessage(this.getClass().getName() + "**********onPause**********");
    }

    @Override
    protected void onStop() {
        super.onStop();
        CLog.getInstance().iMessage(this.getClass().getName() + "**********onStop**********");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CLog.getInstance().iMessage(this.getClass().getName() + "**********onRestart**********");
    }

    @Override
    protected void onDestroy() {
        CLog.getInstance().iMessage(this.getClass().getName() + "**********onDestroy**********");
        super.onDestroy();
    }

    public void skipActivity(Context context, Class<?> classz) {
        startActivity(context, classz, null);
        aty.finish();
    }

    public void skipActivityWithBundle(Context context, Class<?> classz, Bundle bundle) {
        startActivity(context, classz, bundle);
        aty.finish();
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

    protected void setActionBarRes(ActionBarRes actionBarRes) {
    }

    protected void onBackPress() {
        finish();
    }

    protected void onMoreClick() {
    }

    protected void setTitleText(CharSequence title) {
        if (mTVTitle != null)
            mTVTitle.setText(title);
    }

    protected void setMoreText(CharSequence more) {
        if (mTVMore != null)
            mTVMore.setText(more);
    }
}
