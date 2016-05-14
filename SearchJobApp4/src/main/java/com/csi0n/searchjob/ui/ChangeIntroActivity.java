package com.csi0n.searchjob.ui;

import android.os.Bundle;
import android.widget.EditText;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.model.ext.MyModel;
import com.csi0n.searchjob.business.pojo.response.ext.GetChangeUserInfoResponse;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.ui.base.BaseActivity;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.OnClick;
import roboguice.inject.ContentView;

/**
 * Created by chqss on 2016/5/13 0013.
 */
@ContentView(R.layout.aty_change_intro)
public class ChangeIntroActivity extends MvpActivity<ChangeIntroPresenter, ChangeIntroPresenter.IChangeIntro> {
    @Bind(R.id.edit_content)
    EditText editContent;

    @OnClick(R.id.btn_change)
    public void onClick() {
        if (getContent().isEmpty())
            showError("签名信息不能为空~");
        else
            DoPostIntro(getContent());
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title="修改签名";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init() {
        MyModel my = Constants.LOGIN_USER;
        if (!my.intro.isEmpty())
            editContent.setText(my.intro);
    }

    void DoPostIntro(String intro) {
        presenter.doGetChangeUserInfo(intro).subscribe(new AdvancedSubscriber<GetChangeUserInfoResponse>(this) {
            @Override
            public void onHandleSuccess(GetChangeUserInfoResponse response) {
                super.onHandleSuccess(response);
                showToast("保存成功");
                finish();
            }

            @Override
            public void onHandleFail(String message, Throwable throwable) {
                super.onHandleFail(message, throwable);
                showError(message);
            }
        });
    }

    String getContent() {
        return editContent.getText().toString();
    }
}
