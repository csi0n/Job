package com.csi0n.searchjob.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.model.ext.MyModel;
import com.csi0n.searchjob.business.pojo.response.ext.GetChangeUserInfoResponse;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.OnClick;
import roboguice.inject.ContentView;

/**
 * Created by chqss on 2016/5/13 0013.
 */
@ContentView(R.layout.aty_change_name_and_code)
public class ChangeNameAndCodeActivity extends MvpActivity<ChangeNameAndCodePresenter, ChangeNameAndCodePresenter.IChangeNameAndCode> {

    @Bind(R.id.edit_name)
    EditText editName;
    @Bind(R.id.edit_code)
    EditText editCode;

    @OnClick(R.id.btn_change)
    public void onClick() {
        if (TextUtils.isEmpty(getName())) {
            showError("请输入姓名");
        } else if (TextUtils.isEmpty(getCode()))
            showError("请输入身份证号码");
        else
            DoPostNameAndCode(getName(), getCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init() {
        MyModel my = Constants.LOGIN_USER;
        if (!my.real_name.isEmpty())
            editName.setText(my.real_name);
        if (!my.real_code.isEmpty())
            editCode.setText(my.real_code);
    }

    void DoPostNameAndCode(String name, String code) {
        presenter.doGetChangeUserInfo(name, code).subscribe(new AdvancedSubscriber<GetChangeUserInfoResponse>() {
            @Override
            public void onHandleSuccess(GetChangeUserInfoResponse response) {
                super.onHandleSuccess(response);
                showToast("修改成功");
                finish();
            }

            @Override
            public void onHandleFail(String message, Throwable throwable) {
                super.onHandleFail(message, throwable);
                showError(message);
            }
        });
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "修改身份信息";
        super.setActionBarRes(actionBarRes);
    }

    String getName() {
        return editName.getText().toString();
    }

    String getCode() {
        return editCode.getText().toString();
    }
}
