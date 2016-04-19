package com.csi0n.searchjob.controller;

import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.ui.activity.UserInforActivity;
import com.csi0n.searchjob.utils.bean.EmptyBean;
import com.csi0n.searchjob.utils.bean.Show;
import com.csi0n.searchjob.utils.jpush.CDownloadAvatarCallback;
import com.csi0n.searchjob.utils.jpush.CGetUserInfoCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by csi0n on 2015/12/22 0022.
 */
public class UserInfoController extends BaseController {
    private UserInforActivity mUserInforActivity;
    private Show show;

    public UserInfoController(UserInforActivity userInforActivity) {
        this.mUserInforActivity = userInforActivity;
    }

    public void initUserInfor() {
        if (mUserInforActivity.isFromCapture()) {
            getUserInfoFromNet();
        } else {
            mUserInforActivity.setAccount(mUserInforActivity.getUSER_DATA().getUsername());
            JMessageClient.getUserInfo(mUserInforActivity.getUSER_DATA().getUsername(), new CGetUserInfoCallback() {
                @Override
                protected void SuccessResult(UserInfo userInfo) {
                    mUserInforActivity.setUname(TextUtils.isEmpty(userInfo.getNickname()) ? userInfo.getUserName() : userInfo.getNickname());
                    mUserInforActivity.setAddress(TextUtils.isEmpty(userInfo.getAddress()) ? "江苏 无锡" : userInfo.getAddress());
                    mUserInforActivity.setSign(TextUtils.isEmpty(userInfo.getSignature()) ? "这个人很懒，什么都没有留下..." : userInfo.getSignature());
                    userInfo.getAvatarFileAsync(new CDownloadAvatarCallback() {
                        @Override
                        protected void SuccessResult(File file) {
                            mUserInforActivity.setHeadFile(file);
                        }
                    });
                }
            });
            getUserInfoFromNet();
        }
    }

    @Override
    public void onClick(View view) {
        if (show == null) {
            CLog.getInstance().showError("请等待初始化完成!");
            return;
        }
        switch (view.getId()) {
            case R.id.tv_back:
                mUserInforActivity.finish();
                break;
            case R.id.rl_person_dynamic:
                mUserInforActivity.startUserDynamic();
                break;
            case R.id.rl_change_remark:
                mUserInforActivity.startChangeRemark(show.getFriend_info().getData());
                break;
            case R.id.rl_change_group_list:
                mUserInforActivity.startChangeGroupList(show.getFriend_info());
                break;
            case R.id.bt_send_message:
                JMessageClient.getUserInfo(show.getUser().getUsername(), new CGetUserInfoCallback() {
                    @Override
                    protected void SuccessResult(UserInfo userInfo) {
                        mUserInforActivity.startChat(show.getUser().getUsername(), TextUtils.isEmpty(show.getFriend_info().getData().getRemark()) ? userInfo.getNickname() : show.getFriend_info().getData().getRemark());
                    }
                });
                break;
            case R.id.bt_add_friend:
                addFriend();
                break;
            case R.id.bt_del:
                delFriend();
                break;
            default:
                break;
        }
    }

    private void getUserInfoFromNet() {
        PostParams params = getDefaultPostParams(R.string.url_show);
        String fid;
        if (mUserInforActivity.isFromCapture())
            fid = mUserInforActivity.getUSER_ID();
        else
            fid = mUserInforActivity.getUSER_DATA().getUid();
        params.put("fid", fid);
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<Show>(Show.class) {
            @Override
            public void SuccessResult(Show show) throws JSONException {
                if (show.getFriend_info() != null) {
                    if (show.getFriend_info().getFollowing().equals("1"))
                        mUserInforActivity.IsFriend(true, show);
                    else
                        mUserInforActivity.IsFriend(false, show);
                    if (mUserInforActivity.isFromCapture()) {
                        mUserInforActivity.setAccount(show.getUser().getUsername());
                        JMessageClient.getUserInfo(show.getUser().getUsername(), new CGetUserInfoCallback() {
                            @Override
                            protected void SuccessResult(UserInfo userInfo) {
                                mUserInforActivity.setUname(userInfo.getNickname());
                                mUserInforActivity.setAddress(TextUtils.isEmpty(userInfo.getAddress()) ? "江苏 无锡" : userInfo.getAddress());
                                mUserInforActivity.setSign(TextUtils.isEmpty(userInfo.getSignature()) ? "这个人很懒，什么都没有留下..." : userInfo.getSignature());
                                userInfo.getAvatarFileAsync(new CDownloadAvatarCallback() {
                                    @Override
                                    protected void SuccessResult(File file) {
                                        mUserInforActivity.setHeadFile(file);
                                    }
                                });
                            }
                        });
                    }
                }else {
                    mUserInforActivity.IsFriend(false, show);
                }
            }
        });
        post.post();
    }

    private void addFriend() {
        PostParams params = getDefaultPostParams(R.string.url_addFriend);
        params.put("fid", mUserInforActivity.getUSER_DATA().getUid());
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {
                CLog.getInstance().showError("添加成功请等待对方批准!");
                mUserInforActivity.finish();
            }
        });
        post.post();
    }

    private void delFriend() {
        PostParams params = getDefaultPostParams(R.string.url_handleFriendApply);
        params.put("mark", "2");
        params.put("id", mUserInforActivity.getUSER_DATA().getUid());
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {
                CLog.getInstance().showError("删除成功!!");
                mUserInforActivity.finish();
            }
        });
        post.post();
    }
}
