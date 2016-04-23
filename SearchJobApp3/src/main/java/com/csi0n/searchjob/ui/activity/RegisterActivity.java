package com.csi0n.searchjob.ui.activity;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.RegisterController;

import org.xutils.view.annotation.ContentView;

/**
 * Created by csi0n on 4/23/16.
 */
@ContentView(R.layout.aty_register)
public class RegisterActivity extends BaseActivity {
    private RegisterController mRegisterController;

    @Override
    protected void initWidget() {
        mRegisterController = new RegisterController(this);
        mRegisterController.initRegister();
    }
}
