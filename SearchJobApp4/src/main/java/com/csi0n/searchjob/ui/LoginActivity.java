package com.csi0n.searchjob.ui;
import android.view.View;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import butterknife.OnClick;
/**
 * Created by chqss on 2016/5/3 0003.
 */
public class LoginActivity extends MvpActivity<LoginPresenter, LoginPresenter.ILoginView> {
    @OnClick(value = {R.id.btn_login, R.id.btn_register})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                break;
            case R.id.btn_register:
                break;
            default:
                break;
        }
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title="登录";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected int getRootView() {
        return R.layout.aty_login;
    }

}
