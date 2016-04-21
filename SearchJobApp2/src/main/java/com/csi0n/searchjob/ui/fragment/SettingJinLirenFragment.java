package com.csi0n.searchjob.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.SettingJinliRenController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 4/10/16.
 */
@ContentView(R.layout.frag_jingliren)
public class SettingJinLirenFragment extends BaseFragment {
    @ViewInject(value = R.id.edit_name)
    private EditText mEditName;
    @ViewInject(value = R.id.edit_code)
    private EditText mEditCode;
    @ViewInject(value = R.id.btn_save)
    private Button btnSave;

    @Event(value = {R.id.btn_save})
    private void onClick(View view) {
        if (mSettingJinliRenController != null)
            mSettingJinliRenController.onClick(view);
    }

    private SettingJinliRenController mSettingJinliRenController;

    @Override
    protected void initWidget() {
        mSettingJinliRenController = new SettingJinliRenController(this);
        mSettingJinliRenController.initJinliren();
    }

    public void setEditNameEnable(boolean enable) {
        mEditName.setEnabled(enable);
    }

    public void setEditCodeEnable(boolean enable) {
        mEditCode.setEnabled(enable);
    }

    public void setSaveBtnEnable(boolean enable) {
        btnSave.setEnabled(enable);
    }

    public String getName() {
        return mEditName.getText().toString();
    }

    public String getCode() {
        return mEditCode.getText().toString();
    }

    public void setName(String name) {
        mEditName.setText(name);
    }

    public void setCode(String code) {
        mEditCode.setText(code);
    }
}
