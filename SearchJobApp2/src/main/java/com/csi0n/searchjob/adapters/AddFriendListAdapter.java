package com.csi0n.searchjob.adapters;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.StringUtils;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.ui.activity.AddFriendListActivity;
import com.csi0n.searchjob.utils.ProgressLoading;
import com.csi0n.searchjob.utils.bean.AddFriendListBean;
import com.csi0n.searchjob.utils.bean.EmptyBean;
import com.csi0n.searchjob.utils.jpush.CDownloadAvatarCallback;
import com.csi0n.searchjob.utils.jpush.CGetUserInfoCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by chqss on 2016/2/1 0001.
 */
public class AddFriendListAdapter extends BaseAdapter {
    private AddFriendListActivity mAddFriendListActivity;
    public ArrayList<AddFriendListBean.AddFriend> mAddFriends;
    private ProgressLoading loading;

    public AddFriendListAdapter(AddFriendListActivity showAddFriendListActivity) {
        this.mAddFriendListActivity = showAddFriendListActivity;
        this.mAddFriends = new ArrayList<>();
    }

    @Override
    public int getCount() {
        if (mAddFriends != null)
            return mAddFriends.size();
        else
            return 0;
    }

    @Override
    public AddFriendListBean.AddFriend getItem(int i) {
        return mAddFriends.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(mAddFriendListActivity, R.layout.view_adapter_add_friend_item, null);
            holder.m_tv_uname = (TextView) view.findViewById(R.id.tv_uname);
            holder.m_tv_info = (TextView) view.findViewById(R.id.tv_info);
            holder.m_ri_head = (RoundImageView) view.findViewById(R.id.ri_head);
            holder.m_tv_add = (TextView) view.findViewById(R.id.tv_add);
            holder.m_tv_account = (TextView) view.findViewById(R.id.tv_account);
            holder.ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (getItem(i).getHandle() == 0) {
            holder.m_tv_add.setTextColor(StringUtils.getColor(R.color.theme_color));
            holder.m_tv_add.setText("添加");
        } else if (getItem(i).getHandle() == 1) {
            holder.m_tv_add.setTextColor(StringUtils.getColor(R.color.normal_gray));
            holder.m_tv_add.setText("已添加");
        }
        final ViewHolder holder1 = holder;
        JMessageClient.getUserInfo(getItem(i).getUser_data().getUsername(), new CGetUserInfoCallback() {
            @Override
            protected void SuccessResult(UserInfo userInfo) {
                userInfo.getAvatarFileAsync(new CDownloadAvatarCallback() {
                    @Override
                    protected void SuccessResult(File file) {
                        Picasso.with(mAddFriendListActivity).load(file).into(holder1.m_ri_head);
                    }
                });
                holder1.m_tv_account.setText("账户:" + userInfo.getUserName());
                holder1.m_tv_uname.setText(userInfo.getNickname());
                String info = TextUtils.isEmpty(userInfo.getSignature()) ? "这个人很懒什么都没写！" : userInfo.getSignature();
                if (info.length() > 12)
                    info = info.substring(0, 12) + "...";
                holder1.m_tv_info.setText(info);
                AddListeners(holder1, i, userInfo);
            }
        });
        return view;
    }

    private void AddListeners(final ViewHolder holder, final int position, final UserInfo userInfo) {
        holder.m_tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getItem(position).getHandle() == 0) {
                    do_apply(position, getItem(position).getId(), userInfo.getNickname());
                }
            }
        });
    }

    private void do_apply(final int position, String id, String remark) {
        loading = new ProgressLoading(mAddFriendListActivity, "批准中...");
        loading.show();
        PostParams params = BaseController.getStaticDefaultPostParams(R.string.url_handleFriendApply);
        params.put("id", id);
        params.put("mark", "1");
        params.put("remark", remark);
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
    @Override
    public void SuccessResult(EmptyBean result) throws JSONException {
        getItem(position).setHandle(1);
        notifyDataSetChanged();
        CLog.show("操作成功!");
    }

    @Override
    public void onFinished() {
        super.onFinished();
        if (loading.isShowing())
            loading.dismiss();
    }
});

        post.post();
    }

    private class ViewHolder {

        RoundImageView m_ri_head;
        LinearLayout ll_main;
        TextView m_tv_uname, m_tv_info, m_tv_add, m_tv_account;
    }
}
