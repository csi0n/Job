package com.csi0n.searchjob.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.event.ext.ChoosePicHeadEvent;
import com.csi0n.searchjob.business.pojo.event.ext.UserInfoChangeEvent;
import com.csi0n.searchjob.business.pojo.model.ext.MyModel;
import com.csi0n.searchjob.business.pojo.response.ext.GetChangeUserInfoResponse;
import com.csi0n.searchjob.core.io.SharePreferenceManager;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.lib.AppManager;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import roboguice.inject.ContentView;

/**
 * Created by chqss on 2016/5/13 0013.
 */
@ContentView(R.layout.aty_update_documents)
public class UpdateDocumentsActivity extends MvpActivity<UpdateDocumentsPresenter, UpdateDocumentsPresenter.iUpdateDocuments> {
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.tv_uname)
    TextView tvUname;
    @Bind(R.id.tv_account)
    TextView tvAccount;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.tv_sign)
    TextView tvSign;

    AlertView alertView;
    @OnClick({R.id.rl_line_head,R.id.rl_line_uname,R.id.rl_line_sex,R.id.rl_line_name,R.id.rl_line_code,R.id.rl_line_sign,R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_line_head:
                alertView = new AlertView("上传头像", null, "取消", null,
                        new String[]{"拍照", "从相册中选择"},
                        this, AlertView.Style.ActionSheet, new OnItemClickListener() {
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
            case R.id.rl_line_uname:
                startChangeUname();
                break;
            case R.id.rl_line_sex:
                alertView = new AlertView("请选择性别", null, "取消", null,
                        new String[]{"男", "女"},
                        this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:
                                DoPostChangeSex("0");
                                break;
                            case 1:
                                DoPostChangeSex("1");
                                break;
                        }
                    }
                });
                alertView.show();
                break;
            case R.id.rl_line_name:
            case R.id.rl_line_code:
                startChangeNameAndCode();
                break;
            case R.id.rl_line_sign:
                startChangeIntro();
                break;
            case R.id.btn_logout:
                Constants.LOGIN_USER=null;
                SharePreferenceManager.setKeyCachedToken("");
                AppManager.getAppManager().restartApplication(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "更新资料";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init() {
        MyModel my = Constants.LOGIN_USER;
        setHead(Constants.BaseUrl + my.head_ic);
        setUname(my.uname);
        setAccount(my.username);
        setSex(my.sex);
        setName(my.real_name);
        setCode(my.real_code);
        setSign(my.intro);
    }

    void setHead(String url) {
        Picasso.with(this).load(url).into(ivHead, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                ivHead.setImageResource(R.mipmap.ico_default_head_ic);
            }
        });
    }

    void setHead(File file) {
        Picasso.with(this).load(file).into(ivHead, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                ivHead.setImageResource(R.mipmap.ico_default_head_ic);
            }
        });
    }

    void setUname(String uname) {
        tvUname.setText(uname);
    }

    void setAccount(String account) {
        tvAccount.setText(account);
    }

    void setSex(String sex) {
        if (sex.equals("1"))
            tvSex.setText("男");
        else
            tvSex.setText("女");
    }

    void setName(String name) {
        tvName.setText(name);
    }

    void setCode(String code) {
        tvCode.setText(code);
    }

    void setSign(String sign) {
        tvSign.setText(sign);
    }
    void startChangeUname(){
        startActivity(this,ChangeUnameActivity.class);
    }
    void startChangeNameAndCode(){
        startActivity(this,ChangeNameAndCodeActivity.class);
    }
    void startChangeIntro(){
        startActivity(this,ChangeIntroActivity.class);
    }

    void DoPostChangeSex(String sex){
        presenter.doGetChanUserInfo(sex).subscribe(new AdvancedSubscriber<GetChangeUserInfoResponse>(){
            @Override
            public void onHandleSuccess(GetChangeUserInfoResponse response) {
                super.onHandleSuccess(response);
                showToast("修改成功");
            }

            @Override
            public void onHandleFail(String message, Throwable throwable) {
                super.onHandleFail(message, throwable);
                showError(message);
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
}
