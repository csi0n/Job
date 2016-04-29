package com.csi0n.searchjobapp.ui;

import android.os.Bundle;

import com.csi0n.searchjobapp.R;
import com.csi0n.searchjobapp.ui.base.mvp.MvpActivity;

public class MainActivity extends MvpActivity<MainPresenter, MainPresenter.IMainView> implements MainPresenter.IMainView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);
    }
}
