package com.csi0n.searchjob.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.ui.activity.BaseActivity;

import org.xutils.x;

/**
 * Created by csi0n on 2015/12/14 0014.
 */
public abstract class BaseFragment extends Fragment {
    protected abstract void initWidget();

    private boolean injected = false;
    public BaseActivity aty;
    protected BaseFragment currentFragment;
    protected Handler uiHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = (BaseActivity) getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
        uiHandler = new Handler(aty.getMainLooper());
        initWidget();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onStart() {
        super.onStart();
        CLog.getInstance().iMessage(this.getClass().getName() + "**********onStart**********");
    }

    @Override
    public void onResume() {
        super.onResume();
        CLog.getInstance().iMessage(this.getClass().getName() + "**********onResume**********");
    }

    @Override
    public void onPause() {
        super.onPause();
        CLog.getInstance().iMessage(this.getClass().getName() + "**********onPause**********");
    }

    @Override
    public void onStop() {
        super.onStop();
        CLog.getInstance().iMessage(this.getClass().getName() + "**********onStop**********");
    }

    @Override
    public void onDestroy() {
        CLog.getInstance().iMessage(this.getClass().getName() + "**********onDestroy**********");
        super.onDestroy();
    }


    public void startActivity(Class<?> classz) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), classz);
        getActivity().startActivity(intent);
    }
    public void startActivityWithBunde(Class<?> classz,Bundle bundle){
        Intent intent=new Intent();
        intent.setClass(getActivity(),classz);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    public void changeFragment(int resView, BaseFragment targetFragment) {
        if (targetFragment.equals(currentFragment)) {
            return;
        }
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
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
