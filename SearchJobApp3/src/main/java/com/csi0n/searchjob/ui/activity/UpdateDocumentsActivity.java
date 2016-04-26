package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.UpdateDocumentsController;
import com.csi0n.searchjob.model.ShowModel;
import com.csi0n.searchjob.model.event.HeadChangeEvent;
import com.csi0n.searchjob.model.event.ShowChangeEvent;
import com.csi0n.searchjob.model.event.UserInfoEvent;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by csi0n on 4/25/16.
 */
@ContentView(R.layout.aty_update_documents)
public class UpdateDocumentsActivity extends BaseActivity {
    @ViewInject(value = R.id.iv_head)
    private ImageView mIvHead;
    @ViewInject(value = R.id.tv_uname)
    private TextView mTvUname;
    @ViewInject(value = R.id.tv_account)
    private TextView mTvAccount;
    @ViewInject(value = R.id.tv_sex)
    private TextView mTvSex;
    @ViewInject(value = R.id.tv_name)
    private TextView mTvName;
    @ViewInject(value = R.id.tv_code)
    private TextView mTvCode;
    @ViewInject(value = R.id.tv_sign)
    private TextView mTvSign;

    @Event(value = {R.id.rl_line_head, R.id.rl_line_uname, R.id.rl_line_account,R.id.rl_line_sex, R.id.rl_line_name, R.id.rl_line_code, R.id.rl_line_sign,R.id.btn_logout})
    private void onClick(View view) {
        if (mUpdateDocumentsController != null)
            mUpdateDocumentsController.onClick(view);
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title="更新资料";
        super.setActionBarRes(actionBarRes);
    }

    private UpdateDocumentsController mUpdateDocumentsController;

    @Override
    protected void initWidget() {
        mUpdateDocumentsController = new UpdateDocumentsController(this);
        mUpdateDocumentsController.initUpdateDocuments();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void changeHead(File file) {
        Picasso.with(aty).load(file).into(mIvHead, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                mIvHead.setImageResource(R.mipmap.ico_default_head_ic);
            }
        });
    }

    private void setUname(String uname) {
        mTvUname.setText(uname);
    }

    private void setAccount(String account) {
        mTvAccount.setText(account);
    }

    private void setSex(String sex) {
        if (sex.equals("1"))
            mTvSex.setText("男");
        else
            mTvSex.setText("女");
    }

    private void setName(String name) {
        mTvName.setText(name);
    }

    private void setCode(String code) {
        mTvCode.setText(code);
    }

    private void setSign(String sign) {
        mTvSign.setText(sign);
    }

    public void startChangeUname() {
        startActivity(aty, ChangeUnameActivity.class);
    }

    public void startChangeNameAndCode(ShowModel showModel){
        Bundle bundle=new Bundle();
        bundle.putSerializable(Config.MARK_CHANGE_NAME_AND_CODE_ACTIVITY,showModel);
        startActivity(aty,ChangeNameAndCodeActivity.class);
    }
    public void startChangeSign(){
        startActivity(aty,ChangeIntroActivity.class);
    }
    public void initWidget(ShowModel showModel) {
        Picasso.with(aty).load(com.csi0n.searchjob.lib.utils.Config.BASE_URL + showModel.getHead_ic()).into(mIvHead, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                mIvHead.setImageResource(R.mipmap.ico_default_head_ic);
            }
        });
        if (!TextUtils.isEmpty(showModel.getUname()))
            setUname(showModel.getUname());
        setAccount(showModel.getUsername());
        setSex(showModel.getSex());
        if (!TextUtils.isEmpty(showModel.getReal_name()))
            setName(showModel.getReal_name());
        if (!TextUtils.isEmpty(showModel.getReal_code()))
            setCode(showModel.getReal_code());
        if (!TextUtils.isEmpty(showModel.getIntro()))
            setSign(showModel.getIntro());
    }

    @Subscribe
    public void onEvent(HeadChangeEvent headChangeEvent) {
        changeHead(headChangeEvent.getHeadFile());
    }
    @Subscribe
    public void onEvent(UserInfoEvent userInfoEvent){
        if (!TextUtils.isEmpty(userInfoEvent.getUser().getUname()))
            setUname(userInfoEvent.getUser().getUname());
        if (!TextUtils.isEmpty(userInfoEvent.getUser().getSex()))
            setSex(userInfoEvent.getUser().getSex());
        if (!TextUtils.isEmpty(userInfoEvent.getUser().getIntro()))
            setSign(userInfoEvent.getUser().getIntro());
    }
    @Subscribe
    public void onEvent(ShowChangeEvent showChangeEvent){
        if (!TextUtils.isEmpty(showChangeEvent.getShow().getReal_name()))
            setName(showChangeEvent.getShow().getReal_name());
        if (!TextUtils.isEmpty(showChangeEvent.getShow().getReal_code()))
           setCode(showChangeEvent.getShow().getReal_code());
    }
}
