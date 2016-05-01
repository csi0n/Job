package com.csi0n.searchjobapp.ui;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.csi0n.searchjobapp.R;
import com.csi0n.searchjobapp.ui.base.mvp.MvpActivity;

import butterknife.OnCheckedChanged;
import roboguice.inject.ContentView;

@ContentView(R.layout.aty_main)
public class MainActivity extends MvpActivity<MainPresenter, MainPresenter.IMainView> implements MainPresenter.IMainView {
    @OnCheckedChanged(value = R.id.rg_bottom)void onCheckChanged(RadioGroup radioGroup, int id){
        switch (id){
            case R.id.rd_news:

                break;
            case R.id.rd_search_job:

                break;
            case R.id.rd_me:

                break;
            default:
                break;
        }
    }
    public static final String MAIN_ACTIVITY_HAS_TOKEN="main_activity_has_token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        goNext();
    }
    private void init(){

    }
    private void goNext(){

    }
}
