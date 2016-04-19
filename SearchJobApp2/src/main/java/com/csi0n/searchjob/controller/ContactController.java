package com.csi0n.searchjob.controller;

import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.ContactsDefaultAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.ui.fragment.ContactFragment;
import com.csi0n.searchjob.utils.bean.FriendListBean;
import com.csi0n.searchjob.utils.bean.UserBean;
import com.csi0n.searchjob.utils.jpush.CGetUserInfoCallback;
import org.json.JSONException;
import org.xutils.ex.DbException;
import java.util.ArrayList;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
/**
 * Created by chqss on 2016/1/30 0030.
 */
public class ContactController extends BaseController implements ExpandableListView.OnChildClickListener {
    private ContactFragment mContactFragment;
    private ContactsDefaultAdapter adapter;

    public ContactController(ContactFragment contactFragment) {
        this.mContactFragment = contactFragment;
    }

    public void initContact() {
        adapter = new ContactsDefaultAdapter(mContactFragment.getActivity(), null, mContactFragment.mList);
        mContactFragment.addHeadView();
        mContactFragment.mList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        insertTop();
        emptyData();
        getFriendListFromNet();
    }

    public void refreshContact() {
        getFriendListFromNet();
    }

    public void emptyData() {
        for (int i = 0; i < 1; i++) {
            FriendListBean.FriendBean friend = new FriendListBean.FriendBean();
            if (i == 0)
                friend.setShowBottom(true);
            friend.setNormal(true);
            friend.setId("1");
            friend.setName("我的好友");
            ArrayList<UserBean> users = new ArrayList<>();
            UserBean user = new UserBean();
            user.setUsername("测试");
            user.setUid("841506740");
            user.setLogin_status("1");
            user.setStatus("0");
            users.add(user);
            friend.setFriends_data(users);
            adapter.groupList.add(friend);
            adapter.notifyDataSetChanged();
        }
    }

    private void insertTop() {
        FriendListBean.FriendBean friend1 = new FriendListBean.FriendBean();
        friend1.setNormal(false);
        friend1.setId("0x2");
        friend1.setName("手机通讯录");
        ArrayList<UserBean> users1 = new ArrayList<>();
        UserBean user1 = new UserBean();
        user1.setUsername("测试");
        user1.setNormal(false);
        user1.setUid("841506740");
        user1.setLogin_status("1");
        user1.setStatus("0");
        users1.add(user1);
        friend1.setFriends_data(users1);
        adapter.groupList.add(friend1);
        FriendListBean.FriendBean friend3 = new FriendListBean.FriendBean();
        friend3.setNormal(false);
        friend3.setId("0x4");
        friend3.setName("消息");
        ArrayList<UserBean> users3 = new ArrayList<>();
        UserBean user3 = new UserBean();
        user3.setNormal(false);
        user3.setUsername("测试");
        user3.setUid("841506740");
        user3.setLogin_status("1");
        user3.setStatus("0");
        users3.add(user3);
        friend3.setFriends_data(users3);
        adapter.groupList.add(friend3);
    }

    private void getFriendListFromNet() {
        PostParams params = getDefaultPostParams(R.string.url_getFriends);
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<FriendListBean>(FriendListBean.class) {
            @Override
            public void SuccessResult(FriendListBean result) throws JSONException {
                adapter.groupList.clear();
                mContactFragment.removeHeadView();
                mContactFragment.addHeadView();
                insertTop();
                adapter.notifyDataSetChanged();
                for (int i=0;i<result.getData().size();i++){
                    if (i == 0)
                        result.getData().get(i).setShowBottom(true);
                    result.getData().get(i).setNormal(true);
                    adapter.groupList.add(result.getData().get(i));
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void ErrorResult(int code, String str) {
                super.ErrorResult(code, str);
                adapter.groupList.clear();
                mContactFragment.removeHeadView();
                mContactFragment.addHeadView();
                insertTop();
                emptyData();
                adapter.notifyDataSetChanged();
            }
        });
        post.post();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_head_ic:
                mContactFragment.startFeedBack();
                break;
            case R.id.ll_community:
                mContactFragment.startQRScan();
                break;
            case R.id.ll_yun_bak:
                mContactFragment.startContacts();
                break;
            case R.id.ll_near_by:
                mContactFragment.startNearBy();
                break;
            case R.id.ll_dynamic:
                mContactFragment.startUserDynamic();
                break;
            case R.id.tv_add:
                mContactFragment.showMenuPopWindow();
                break;
            case R.id.btn_add_friends:
                mContactFragment.startAddFriend();
                break;
            case R.id.btn_add_friends_list:
                mContactFragment.startAddFriendList();
                break;
            case R.id.btn_message_list:
                mContactFragment.startMessage();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        final UserBean userBean = adapter.groupList.get(i).getFriends_data().get(i1);
        if (userBean.getUsername().equals(JMessageClient.getMyInfo().getUserName())) {
            CLog.show("不能跟自己聊天!");
        } else {
            if (TextUtils.isEmpty(userBean.getGroup_data().getRemark())) {
                JMessageClient.getUserInfo(userBean.getUsername(), new CGetUserInfoCallback() {
                    @Override
                    protected void SuccessResult(UserInfo userInfo) {
                        try {
                            mContactFragment.saveCard(userInfo.getUserID(), 1, userInfo.getNickname(), userInfo.getUserName());
                        } catch (DbException e) {
                            CLog.show("数据库操作失败:" + e.getMessage());
                        }
                        mContactFragment.startChat(userInfo.getUserName(), userInfo.getNickname());
                    }
                });
            } else {
                try {
                    mContactFragment.saveCard(Float.valueOf(userBean.getUid()), 1, userBean.getGroup_data().getRemark(), userBean.getUsername());
                } catch (DbException e) {
                    CLog.show("数据库操作失败:" + e.getMessage());
                }
                mContactFragment.startChat(userBean.getUsername(), userBean.getGroup_data().getRemark());
            }
        }
        return true;
    }
}
