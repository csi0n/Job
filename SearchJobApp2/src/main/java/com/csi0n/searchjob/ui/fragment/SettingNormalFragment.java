package com.csi0n.searchjob.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.SettingNormalController;
import com.csi0n.searchjob.ui.activity.FontSizeActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by csi0n on 2015/12/17 0017.
 */
@ContentView(R.layout.frag_setting_normal)
public class SettingNormalFragment extends BaseFragment {
    private SettingNormalController mSettingNormalController;
    @Event(value = {R.id.rl_line_chat_font_size,R.id.rl_line_check_update})
    private void onClick(View view){
        if (mSettingNormalController!=null)
            mSettingNormalController.onClick(view);
    }

    @Override
    protected void initWidget() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSettingNormalController=new SettingNormalController(this);
        mSettingNormalController.initSettingNormal();
    }
    public void startFontSize(){
        startActivity(FontSizeActivity.class);

    }
}
