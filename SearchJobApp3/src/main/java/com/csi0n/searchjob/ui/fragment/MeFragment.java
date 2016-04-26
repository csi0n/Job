package com.csi0n.searchjob.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.MeController;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.model.UserModel;
import com.csi0n.searchjob.model.event.HeadChangeEvent;
import com.csi0n.searchjob.model.event.UserInfoEvent;
import com.csi0n.searchjob.ui.activity.ChangeIntroActivity;
import com.csi0n.searchjob.ui.activity.ChangePasswordActivity;
import com.csi0n.searchjob.ui.activity.ChangeUnameActivity;
import com.csi0n.searchjob.ui.activity.MyCommentsActivity;
import com.csi0n.searchjob.ui.activity.UpdateDocumentsActivity;
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
@ContentView(R.layout.frag_me)
public class MeFragment extends BaseFragment {
    private MeController mMeController;
    @ViewInject(value = R.id.ri_head)
    private RoundImageView mRiHead;
    @ViewInject(value = R.id.tv_uname)
    private TextView mTvName;
    @ViewInject(value = R.id.tv_intro)
    private TextView mTvIntro;

    @Event(value = {R.id.ri_head, R.id.tv_uname, R.id.tv_intro, R.id.btn_update_document, R.id.btn_comments, R.id.btn_change_password})
    private void onClick(View view) {
        if (mMeController != null)
            mMeController.onClick(view);
    }

    public void setUname(String uname) {
        mTvName.setText(uname);
    }

    public void setIntro(String intro) {
        mTvIntro.setText(intro);
    }

    private void changeHead(File Headfile) {
        Picasso.with(aty).load(Headfile).into(mRiHead, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                mRiHead.setImageResource(R.mipmap.ico_default_head_ic);
            }
        });
    }

    @Override
    protected void initWidget() {
        mMeController = new MeController(this);
        mMeController.initMe();
        EventBus.getDefault().register(this);
    }

    public void initViewText(UserModel userModel) {
        if (!TextUtils.isEmpty(userModel.getHead_ic()))
            Picasso.with(aty).load(com.csi0n.searchjob.lib.utils.Config.BASE_URL + userModel.getHead_ic()).into(mRiHead, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    mRiHead.setImageResource(R.mipmap.ico_default_head_ic);
                }
            });
        else
            mRiHead.setImageResource(R.mipmap.ico_default_head_ic);
        if (TextUtils.isEmpty(userModel.getUname()))
            setUname("未设置");
        else
            setUname(userModel.getUname());
        if (TextUtils.isEmpty(userModel.getIntro()))
            setIntro("这个人很懒什么都没有留下！");
        else
            setIntro(userModel.getIntro());
    }

    public void startChangePassword() {
        startActivity(ChangePasswordActivity.class);
    }

    public void startChangeIntro() {
        startActivity(ChangeIntroActivity.class);
    }

    public void startChangeUname() {
        startActivity(ChangeUnameActivity.class);
    }

    public void startUpdateDocument() {
        startActivity(UpdateDocumentsActivity.class);
    }

    public void startComments() {
        startActivity(MyCommentsActivity.class);
    }

    @Subscribe
    public void onEvent(HeadChangeEvent headChangeEvent) {
        changeHead(headChangeEvent.getHeadFile());
    }

    @Subscribe
    public void onEvent(UserInfoEvent userInfoEvent) {
        if (TextUtils.isEmpty(userInfoEvent.getUser().getUname()))
            setUname("未设置");
        else
            setUname(userInfoEvent.getUser().getUname());
        if (TextUtils.isEmpty(userInfoEvent.getUser().getIntro()))
            setIntro("这个人很懒什么都没有留下！");
        else
            setIntro(userInfoEvent.getUser().getIntro());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
