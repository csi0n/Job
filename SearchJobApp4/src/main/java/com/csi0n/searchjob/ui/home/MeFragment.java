package com.csi0n.searchjob.ui.home;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.pojo.event.ext.ChoosePicHeadEvent;
import com.csi0n.searchjob.business.pojo.event.ext.MainFragmentSkipEvent;
import com.csi0n.searchjob.business.pojo.event.ext.UserInfoChangeEvent;
import com.csi0n.searchjob.business.pojo.model.ext.MyModel;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.ui.ChangePasswordActivity;
import com.csi0n.searchjob.ui.MyCommentsActivity;
import com.csi0n.searchjob.ui.UpdateDocumentsActivity;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by chqss on 2016/5/1 0001.
 */
public class MeFragment extends MvpFragment<MePresenter,MePresenter.IMeView> {
    @Bind(value = R.id.ri_head)
    RoundImageView RiHead;
    @Bind(value = R.id.tv_uname)
    TextView mTvUname;
    @Bind(value = R.id.tv_intro)
    TextView mTvIntro;


    AlertView alertView;

    @OnClick(value = {R.id.ri_head,R.id.btn_update_document,R.id.btn_comments,R.id.btn_change_password})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ri_head:
                alertView = new AlertView("上传头像", null, "取消", null, new String[]{"拍照", "从相册中选择"}, mvpActivity, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:
                                EventBus.getDefault().post(new ChoosePicHeadEvent(true));
                                break;
                            case 1:
                                EventBus.getDefault().post(new ChoosePicHeadEvent(false));
                                break;
                        }
                    }
                });
                alertView.show();
                break;
            case R.id.btn_update_document:
                startUpdateDocument();
                break;
            case R.id.btn_comments:
                startComments();
                break;
            case R.id.btn_change_password:
                startChangePassword();
                break;
            default:
                break;
        }
    }
    @Override
    protected int getFragmentLayout() {
        return R.layout.frag_me;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(Constants.LOGIN_USER);
    }
    private void initView(MyModel my){
        if (my==null)
            EventBus.getDefault().post(new MainFragmentSkipEvent(Constants.MainSkipTYPE.SEARCHJOB));
        else{
            Picasso.with(mvpActivity).load(Constants.BaseUrl+my.head_ic).into(RiHead, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    RiHead.setImageResource(R.mipmap.ico_default_head_ic);
                }
            });
            mTvUname.setText(my.uname);
            if (!TextUtils.isEmpty(my.intro))
                mTvIntro.setText(my.intro);
        }

    }

    void setHead(File file) {
        Picasso.with(mvpActivity).load(file).into(RiHead, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                RiHead.setImageResource(R.mipmap.ico_default_head_ic);
            }
        });
    }

    @Override
    public void onEvent(Object object) {
        super.onEvent(object);
        if (object.getClass() == UserInfoChangeEvent.class) {
            UserInfoChangeEvent userInfoChangeEvent = ((UserInfoChangeEvent) object);
            switch (userInfoChangeEvent.type) {
                case HEAD:
                    setHead((File) userInfoChangeEvent.object);
                    break;
                default:
                    break;
            }
        }
    }
    void startUpdateDocument(){
        startActivity(UpdateDocumentsActivity.class);
    }
    void startComments() {
        startActivity(MyCommentsActivity.class);
    }
    void startChangePassword(){
        startActivity(ChangePasswordActivity.class);
    }
}
