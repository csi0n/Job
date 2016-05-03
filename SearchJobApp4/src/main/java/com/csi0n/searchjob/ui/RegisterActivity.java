package com.csi0n.searchjob.ui;
import android.view.View;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.ui.base.BaseActivity;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import butterknife.OnClick;
/**
 * Created by chqss on 2016/5/3 0003.
 */
public class RegisterActivity extends MvpActivity<RegisterPresenter, RegisterPresenter.IRegisterView> {
    @OnClick(value = {R.id.btn_register}) void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                break;
            default:
                break;
        }
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title="注册";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected int getRootView() {
        return R.layout.aty_register;
    }
}
