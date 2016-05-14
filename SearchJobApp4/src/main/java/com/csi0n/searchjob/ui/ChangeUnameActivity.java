package com.csi0n.searchjob.ui;

import android.text.TextUtils;
import android.widget.EditText;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.response.ext.GetChangeUserInfoResponse;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.OnClick;
import roboguice.inject.ContentView;

/**
 * Created by chqss on 2016/5/13 0013.
 */
@ContentView(R.layout.aty_change_uname)
public class ChangeUnameActivity extends MvpActivity<ChangeUnamePresenter, ChangeUnamePresenter.IChangeUname> {
    @Bind(R.id.edit_content)
    EditText editContent;

    @OnClick(R.id.btn_change)
    public void onClick() {
        if (!TextUtils.isEmpty(getContent()))
            DoPostChangeUName(getContent());
        else
            showError("请输入用户昵称");
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "修改昵称";
        super.setActionBarRes(actionBarRes);
    }

    void DoPostChangeUName(String uname) {
        presenter.doGetChangeUserInfo(uname).subscribe(new AdvancedSubscriber<GetChangeUserInfoResponse>(this) {
            @Override
            public void onHandleSuccess(GetChangeUserInfoResponse response) {
                super.onHandleSuccess(response);
                showToast("修改成功");
                finish();
            }
        });
    }

    String getContent() {
        return editContent.getText().toString().trim();
    }
}
