package com.csi0n.searchjob.ui.activity;

import android.view.View;
import android.widget.RadioGroup;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.RegisterController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by csi0n on 2015/12/15 0015.
 */
@ContentView(R.layout.aty_register)
public class RegisterActivity extends BaseActivity {
    private RegisterController mRegisterController;
    @Event(value = {R.id.btn_register})
    private void onClick(View view) {
        if (mRegisterController != null)
            mRegisterController.onClick(view);
    }
    @Event(value = R.id.rg_sex, type = RadioGroup.OnCheckedChangeListener.class)
    private void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rb_female:
                male = false;
                break;
            case R.id.rb_male:
                male = true;
                break;
            default:
                break;
        }
    }

    private boolean male = true;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = getString(R.string.str_reg);
        super.setActionBarRes(actionBarRes);
    }
    @Override
    protected void initWidget() {
        mRegisterController = new RegisterController(this);
        mRegisterController.initRegister();
    }
}
