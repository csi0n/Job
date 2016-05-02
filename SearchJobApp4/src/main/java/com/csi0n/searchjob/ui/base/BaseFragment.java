package com.csi0n.searchjob.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import roboguice.fragment.RoboFragment;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public abstract class BaseFragment extends RoboFragment {
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutRes=getFragmentLayout();
        return inflater.inflate(layoutRes,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    /**
     * 每个Fragment自己的布局
     */
    protected abstract int getFragmentLayout();
}
