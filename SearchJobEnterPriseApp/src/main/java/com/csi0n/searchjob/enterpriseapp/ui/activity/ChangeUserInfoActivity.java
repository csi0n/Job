package com.csi0n.searchjob.enterpriseapp.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csi0n.searchjob.enterpriseapp.Config;
import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.controller.ChangeUserInfoController;
import com.csi0n.searchjob.lib.utils.bean.Area;
import com.csi0n.searchjob.lib.utils.bean.City;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by csi0n on 3/29/16.
 */
@ContentView(R.layout.aty_change_user_info)
public class ChangeUserInfoActivity extends BaseActivity {
    @ViewInject(value = R.id.edit_city)
    private EditText mEditCity;
    @ViewInject(value = R.id.edit_area)
    private EditText mEditArea;
    @ViewInject(value = R.id.edit_address_detail)
    private EditText mEditAddressDetail;
    @ViewInject(value = R.id.edit_intro)
    private EditText mEditIntro;
    @ViewInject(value = R.id.btn_choose_area)
    private Button mChooseArea;

    @Event(value = {R.id.btn_choose_city, R.id.btn_choose_area})
    private void onClick(View view) {
        if (mChangeUserInfoController != null)
            mChangeUserInfoController.onClick(view);
    }


    private ChangeUserInfoController mChangeUserInfoController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "修改企业信息";
        actionBarRes.more = "完成";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void onMoreClick() {
        super.onMoreClick();
        if (mChangeUserInfoController != null)
            mChangeUserInfoController.onClick(findViewById(R.id.tv_more));
    }

    public void setCity(String city) {
        mEditCity.setText(city);
    }

    public void setArea(String area) {
        mEditArea.setText(area);
    }

    public void setAddressDetail(String address) {
        mEditAddressDetail.setText(address);
    }

    public String getAddressDetail() {
       return mEditAddressDetail.getText().toString();
    }

    public void setIntro(String string) {
        mEditIntro.setText(string);
    }
public String getIntro(){return mEditIntro.getText().toString();}
    @Override
    protected void initWidget() {
        if (Config.LOGIN_COMPANY_USER == null)
            finish();
        mChangeUserInfoController = new ChangeUserInfoController(this);
        mChangeUserInfoController.initChangeUserInfo();
    }
}
