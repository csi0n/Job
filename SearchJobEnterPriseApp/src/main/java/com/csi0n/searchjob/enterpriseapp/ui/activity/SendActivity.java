package com.csi0n.searchjob.enterpriseapp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.csi0n.searchjob.enterpriseapp.Config;
import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.controller.SendController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 3/27/16.
 */
@ContentView(R.layout.aty_send)
public class SendActivity extends BaseActivity {
    @ViewInject(value = R.id.edit_job_type)
    private EditText mEditJobType;
    @ViewInject(value = R.id.edit_fuli)
    private EditText mEditFuli;
    @ViewInject(value = R.id.edit_gongzi_1)
    private EditText mEditGongzi1;
    @ViewInject(value = R.id.edit_gongzi_2)
    private EditText mEditGongzi2;
    @ViewInject(value = R.id.edit_gongzi_detail)
    private EditText mEditGongziDetail;
    @ViewInject(value = R.id.edit_work_time)
    private EditText mEditWorkTime;
    @ViewInject(value = R.id.edit_age_1)
    private EditText mEditAge1;
    @ViewInject(value = R.id.edit_age_2)
    private EditText mEditAge2;
    @ViewInject(value = R.id.edit_work_life)
    private EditText mEditWorkLife;
    @ViewInject(value = R.id.edit_degree_wanted)
    private EditText mDegreeWanted;
    @ViewInject(value = R.id.edit_more_info)
    private EditText mEditMoreinfo;
    @ViewInject(value = R.id.edit_work_info)
    private EditText mWorkinfo;
    @ViewInject(value = R.id.edit_work_location)
    private EditText mEditWorklocation;
    @ViewInject(value = R.id.edit_user_type)
    private EditText mEditUsertype;
    @ViewInject(value = R.id.rb_1)
    private RadioButton mRB1;
    @ViewInject(value = R.id.rb_2)
    private RadioButton mRB2;
    @ViewInject(value = R.id.rb_3)
    private RadioButton mRB3;
    @ViewInject(value = R.id.chk_shebao)
    private CheckBox chkSheBao;
    private boolean isFromNormal = false;

    @Event(value = {R.id.btn_choose_job_type, R.id.btn_choose_fuli})
    private void onClick(View view) {
        if (mSendController != null)
            mSendController.onClick(view);
    }

    @Event(value = R.id.rg_choose_sex)
    private void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (mSendController != null)
            mSendController.onCheckedChanged(radioGroup, i);
    }

    @Event(value = R.id.chk_shebao)
    private void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (mSendController != null)
            mSendController.onCheckedChanged(compoundButton, b);
    }

    private SendController mSendController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "发布职位";
        actionBarRes.backGone = false;
        actionBarRes.more = "发布";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        Bundle bundle = getBundle();
        if (bundle == null)
            finish();
        isFromNormal = bundle.getBoolean(Config.MARK_SEND_ACTIVITY_IS_FROM_MONEY_BACK);
        mSendController = new SendController(this);
        mSendController.initSend();
    }

    public void setJobType(String jobType) {
        mEditJobType.setText(jobType);
    }

    public void setFuli(String fuli) {
        mEditFuli.setText(fuli);
    }

    public String getEditGongzi1() {
        return mEditGongzi1.getText().toString();
    }

    public void setEditGongzi1(String gongzi1) {
        mEditGongzi1.setText(gongzi1);
    }

    public void setEditGongzi2(String gongzi2) {
        mEditGongzi2.setText(gongzi2);
    }

    public String getEditGongzi2() {
        return mEditGongzi2.getText().toString();
    }

    public String getEditGongziDetail() {
        return mEditGongziDetail.getText().toString();
    }

    public void setEditGongziDetail(String gongzidetail) {
        mEditGongziDetail.setText(gongzidetail);
    }

    public String getmEditWorkTime() {
        return mEditWorkTime.getText().toString();
    }

    public void setEditWorkTime(String work_time) {
        mEditWorkTime.setText(work_time);
    }

    public String getEditAge1() {
        return mEditAge1.getText().toString();
    }

    public void setEditAge1(String age1) {
        mEditAge1.setText(age1);
    }

    public void setEditAge2(String age2) {
        mEditAge2.setText(age2);
    }

    public String getEditAge2() {
        return mEditAge2.getText().toString();
    }

    public String getEditWorkinfo() {
        return mWorkinfo.getText().toString();
    }

    public void setEditWorkInfo(String work_info) {
        mWorkinfo.setText(work_info);
    }

    public String getEditWorkLife() {
        return mEditWorkLife.getText().toString();
    }

    public void setEditWorkLife(String workLife) {
        mEditWorkLife.setText(workLife);
    }

    public String getDegreeWanted() {
        return mDegreeWanted.getText().toString();
    }

    public void setDegreeWanted(String degreewanted) {
        mDegreeWanted.setText(degreewanted);
    }

    public String getEditMoreinfo() {
        return mEditMoreinfo.getText().toString();
    }

    public void setEditMoreInfo(String more_info) {
        mEditMoreinfo.setText(more_info);
    }

    public String getEditWorklocation() {
        return mEditWorklocation.getText().toString();
    }

    public void setEditWorklocation(String mEditWorklocation) {
        this.mEditWorklocation.setText(mEditWorklocation);
    }

    public String getEditUsertype() {
        return mEditUsertype.getText().toString();
    }

    public void setEditJobTypeError(String error) {
        mEditJobType.setError(error);
    }

    public void setEditGongzi1Error(String error) {
        this.mEditGongzi1.setError(error);
    }

    public void setEditGongzi2Error(String error) {
        this.mEditGongzi2.setError(error);
    }

    public void setEditAge2Error(String error) {
        this.mEditAge2.setError(error);
    }

    public void setEditGongziDetailError(String error) {
        mEditGongziDetail.setError(error);
    }

    public void setEditWorkTimeError(String error) {
        this.mEditWorkTime.setError(error);
    }

    public void setEditFuliError(String error) {
        this.mEditFuli.setError(error);
    }

    public void setEditWorkLifeError(String error) {
        this.mEditWorkLife.setError(error);
    }

    public void setDegreeWantedError(String error) {
        this.mDegreeWanted.setError(error);
    }

    public void setEditMoreinfoError(String error) {
        this.mEditMoreinfo.setError(error);
    }

    public void setmWorkinfoError(String error) {
        this.mWorkinfo.setError(error);
    }

    public void setEditWorklocationError(String error) {
        this.mEditWorklocation.setError(error);
    }

    public void setEditUsertypeError(String error) {
        this.mEditUsertype.setError(error);
    }

    public void setEditUserType(String userType) {
        mEditUsertype.setText(userType);
    }

    public void setRb1Check(boolean check) {
        mRB1.setChecked(check);
    }

    public void setRb2Check(boolean check) {
        mRB2.setChecked(check);
    }

    public void setRb3Check(boolean check) {
        mRB3.setChecked(check);
    }

    public void setChkSheBao(boolean b) {
        chkSheBao.setChecked(b);
    }

    public boolean isFromNormal() {
        return isFromNormal;
    }

    @Override
    protected void onMoreClick() {
        super.onMoreClick();
        if (mSendController != null)
            mSendController.onClick(findViewById(R.id.tv_more));
    }
}
