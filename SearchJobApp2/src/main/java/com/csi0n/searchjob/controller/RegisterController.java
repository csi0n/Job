package com.csi0n.searchjob.controller;

import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.AppContext;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.ui.activity.RegisterActivity;

/**
 * Created by csi0n on 2015/12/15 0015.
 */
public class RegisterController extends BaseController {

    private RegisterActivity mRegisterActivity;

    public RegisterController(RegisterActivity registerActivity) {
        this.mRegisterActivity = registerActivity;
    }

    public void initRegister() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                break;
            default:
                break;
        }
    }

    private void do_reg() {

    }
}
