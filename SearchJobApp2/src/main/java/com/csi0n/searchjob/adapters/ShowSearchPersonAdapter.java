package com.csi0n.searchjob.adapters;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.ui.activity.ShowSearchUserResultActivity;
import com.csi0n.searchjob.utils.bean.UserBean;
import com.csi0n.searchjob.utils.jpush.CDownloadAvatarCallback;
import com.csi0n.searchjob.utils.jpush.CGetUserInfoCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by csi0n on 2015/11/26 0026.
 */
public class ShowSearchPersonAdapter extends BaseAdapter {
    private ShowSearchUserResultActivity mShowSearchFriendResultActivity;
    public ArrayList<UserBean> users;

    public ShowSearchPersonAdapter(ShowSearchUserResultActivity showSearchFriendResultActivity, ArrayList<UserBean> users) {
        mShowSearchFriendResultActivity = showSearchFriendResultActivity;
        if (users == null)
            this.users = new ArrayList<>();
    }

    @Override
    public int getCount() {
        if (users != null)
            return users.size();
        else
            return 0;
    }

    @Override
    public UserBean getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(mShowSearchFriendResultActivity, R.layout.view_adapter_item_show_search_person, null);
            holder.m_ri_head = (RoundImageView) view.findViewById(R.id.ri_head);
            holder.m_tv_uname = (TextView) view.findViewById(R.id.tv_uname);
            holder.m_tv_account = (TextView) view.findViewById(R.id.tv_account);
            holder.m_tv_info = (TextView) view.findViewById(R.id.tv_info);
            holder.m_iv_sex = (ImageView) view.findViewById(R.id.iv_sex);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final ViewHolder finalHolder = holder;
        JMessageClient.getUserInfo(getItem(i).getUsername(), new CGetUserInfoCallback() {
            @Override
            protected void SuccessResult(UserInfo userInfo) {
                finalHolder.m_tv_uname.setText(userInfo.getNickname());
                finalHolder.m_tv_account.setText("账户:" + userInfo.getUserName());
                finalHolder.m_tv_info.setText(TextUtils.isEmpty(userInfo.getSignature()) ? "这个人很懒，什么都没留下!" : userInfo.getSignature());
                finalHolder.m_iv_sex.setImageResource(userInfo.getGender() == UserInfo.Gender.female ? R.mipmap.ico_small_female_checked : R.mipmap.ico_small_male_checked);
                userInfo.getAvatarFileAsync(new CDownloadAvatarCallback() {
                    @Override
                    protected void SuccessResult(File file) {
                        Picasso.with(mShowSearchFriendResultActivity).load(file).into(finalHolder.m_ri_head);
                    }
                });
            }
        });
        return view;
    }

    private class ViewHolder {
        RoundImageView m_ri_head;
        TextView m_tv_uname, m_tv_info, m_tv_account;
        ImageView m_iv_sex;
    }
}
