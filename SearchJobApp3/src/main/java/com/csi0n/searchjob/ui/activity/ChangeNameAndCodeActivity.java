package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ChangeNameAndCodeController;
import com.csi0n.searchjob.model.ShowModel;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 4/25/16.
 */
@ContentView(R.layout.aty_change_name_and_code)
public class ChangeNameAndCodeActivity extends BaseActivity {
    @ViewInject(value = R.id.edit_name)
    private EditText mEditName;
    @ViewInject(value = R.id.edit_code)
    private EditText mEditCode;

    @Event(value = {R.id.btn_change})
    private void onClick(View view) {
        if (mChangeNameAndCodeController != null)
            mChangeNameAndCodeController.onClick(view);
    }
    private ShowModel SHOW_DATA;
    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title="修改姓名和身份证";
        super.setActionBarRes(actionBarRes);
    }

    private ChangeNameAndCodeController mChangeNameAndCodeController;

    @Override
    protected void initWidget() {
        Bundle bundle=getBundle();
        if (bundle==null)
            finish();
        SHOW_DATA= (ShowModel) bundle.getSerializable(Config.MARK_CHANGE_NAME_AND_CODE_ACTIVITY);
        mChangeNameAndCodeController = new ChangeNameAndCodeController(this);
        mChangeNameAndCodeController.initChangeNameAndCode();
    }

    public String getName() {
        return mEditName.getText().toString();
    }

    public String getCode() {
        return mEditCode.getText().toString();
    }

    public ShowModel getShowData(){
        return SHOW_DATA;
    }
}
