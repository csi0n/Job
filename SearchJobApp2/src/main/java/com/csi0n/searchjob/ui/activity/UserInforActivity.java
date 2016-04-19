package com.csi0n.searchjob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.UserInfoController;
import com.csi0n.searchjob.utils.bean.Show;
import com.csi0n.searchjob.utils.bean.UserBean;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by csi0n on 2015/12/22 0022.
 * params:用户USER_ID
 */
@ContentView(R.layout.aty_user_info)
public class UserInforActivity extends BaseActivity {
    private UserInfoController mUserInfoController;
    @ViewInject(value = R.id.rl_person_dynamic)
    private RelativeLayout mRTPersonDynamic;
    @ViewInject(value = R.id.iv_head)
    private ImageView mIVHead;
    @ViewInject(value = R.id.tv_uname)
    private TextView mTVUname;
    @ViewInject(value = R.id.tv_account)
    private TextView mTVAccount;
    @ViewInject(value = R.id.tv_address)
    private TextView mTVAddress;
    @ViewInject(value = R.id.tv_sign)
    private TextView mTVSign;
    @ViewInject(value = R.id.ll_change_group)
    private LinearLayout mLLChangeGroup;
    @ViewInject(value = R.id.ll_remark)
    private LinearLayout mLLRemark;
    private UserBean USER_DATA;
    private String USER_ID;
    private boolean isFromCapture = false;

    @Event(value = {R.id.rl_person_dynamic, R.id.rl_change_remark, R.id.rl_change_group_list, R.id.bt_add_friend, R.id.bt_send_message, R.id.bt_del})
    private void onClick(View view) {
        if (mUserInfoController != null)
            mUserInfoController.onClick(view);
    }

    @Override
    protected void initWidget() {
        Bundle bundle = getBundle();
        if (bundle != null) {
            isFromCapture = bundle.getBoolean(Config.MARK_USER_INFO_ACTIVITY_IS_FROM_CAPTURE);
            if (isFromCapture)
                USER_ID = bundle.getString(Config.MARK_USER_INFO_ACTIVITY_USER_ID);
            else
                USER_DATA = (UserBean) bundle.getSerializable(Config.MARK_USER_INFO_ACTIVITY_USER_DATA);
        } else
            finish();
        mUserInfoController = new UserInfoController(this);
        mUserInfoController.initUserInfor();
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = getString(R.string.str_user_info);
        super.setActionBarRes(actionBarRes);
    }

    public void startUserDynamic() {
        startActivity(this, UserDynamicActivity.class);
    }

    public void startChangeRemark(final Show.FriendInfo.FriendData friendData) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.MARK_CHANGE_REMARK_ACTIVITY_FRIEND_DATA, friendData);
        skipActivityWithBundle(this, ChangeRemarkActivity.class, bundle);
    }

    public void startChangeGroupList(Show.FriendInfo friendInfo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.MARK_CHANGE_GROUP_LIST_ACTIVITY_FRIEND_INFO, friendInfo);
        skipActivityWithBundle(this, ChangeGroupListActivity.class, bundle);
    }

    public void startChat(String username, String title_name) {
        Intent intent = new Intent();
        intent.setClass(this, ChatActivity.class);
        intent.putExtra(Config.MARK_CHAT_ACTIVITY_IS_GROUP, false);
        intent.putExtra(Config.MARK_CHAT_ACTIVITY_TARGET_ID, username);
        intent.putExtra(Config.MARK_CHAT_ACTIVITY_TITLE_NAME, title_name);
        startActivity(intent);

    }

    public void setHeadFile(File file) {
        Picasso.with(this).load(file).into(mIVHead);
    }

    public void setUname(String uname) {
        mTVUname.setText(uname);
    }

    public void setAccount(String account) {
        mTVAccount.setText(account);
    }

    public void setAddress(String address) {
        mTVAddress.setText(address);
    }

    public void setSign(String sign) {
        mTVSign.setText(sign);
    }

    public UserBean getUSER_DATA() {
        return USER_DATA;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public boolean isFromCapture() {
        return isFromCapture;
    }

    public void IsFriend(boolean isfriend,Show show) {
        if (isfriend) {
            findViewById(R.id.ll_add_friend).setVisibility(View.GONE);
            if (show.getLast_dynamic()!=null){

            }else {
                findViewById(R.id.rl_person_dynamic).setVisibility(View.GONE);
            }
        } else {
            mLLChangeGroup.setVisibility(View.GONE);
            mLLRemark.setVisibility(View.GONE);
            mRTPersonDynamic.setVisibility(View.GONE);
            findViewById(R.id.ll_send_message).setVisibility(View.GONE);
            findViewById(R.id.ll_del).setVisibility(View.GONE);
            findViewById(R.id.rl_person_dynamic).setVisibility(View.GONE);
        }
    }
}
