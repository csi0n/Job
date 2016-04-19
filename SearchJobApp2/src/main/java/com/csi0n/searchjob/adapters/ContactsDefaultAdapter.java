package com.csi0n.searchjob.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.ui.widget.IphoneTreeView;
import com.csi0n.searchjob.utils.bean.FriendListBean;
import com.csi0n.searchjob.utils.bean.UserBean;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadAvatarCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by csi0n on 15/9/22.
 */
public class ContactsDefaultAdapter extends BaseExpandableListAdapter implements IphoneTreeView.IphoneTreeHeaderAdapter {
    private Context mContext;
    public ArrayList<FriendListBean.FriendBean> groupList;
    private IphoneTreeView mIphoneTreeView;
    private HashMap<Integer, Integer> groupStatusMap;

    @SuppressLint("UseSparseArrays")
    public ContactsDefaultAdapter(Context context, ArrayList<FriendListBean.FriendBean> groupList, IphoneTreeView mIphoneTreeView) {
        this.mContext = context;
        if (groupList == null)
            this.groupList = new ArrayList<>();
        else
            this.groupList = groupList;
        this.mIphoneTreeView = mIphoneTreeView;
        groupStatusMap = new HashMap<Integer, Integer>();
    }

    public UserBean getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).getFriends_data().get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        if (groupPosition != -1) {
            if (groupList.get(groupPosition).getFriends_data() != null)
                return groupList.get(groupPosition).getFriends_data().size();
            else
                return 0;
        } else {
            return 0;
        }
    }

    /*计算在线人数*/
    public int getChildrenOnlineCount(int groupPosition) {
        int count = 0;
        try {
            List<UserBean> childList = groupList.get(groupPosition).getFriends_data();
            for (int i = 0; i < childList.size(); i++) {
                UserBean child = childList.get(i);
                if (child.getLogin_status().equals("1")) {
                    count++;
                }
            }
        } catch (NullPointerException e) {
            return count;
        }
        return count;
    }

    public FriendListBean.FriendBean getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    public int getGroupCount() {
        return groupList.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_adapter_contact_default_child_item, null);
            holder.mRLNormal = (RelativeLayout) convertView.findViewById(R.id.rl_normal);
            holder.mNormalHead = (RoundImageView) convertView.findViewById(R.id.ri_normal_head);
            holder.mNormalName = (TextView) convertView.findViewById(R.id.tv_normal_uname);
            holder.mNormalSign = (TextView) convertView.findViewById(R.id.tv_normal_sign);
            holder.mRLDevices = (RelativeLayout) convertView.findViewById(R.id.rl_device);
            holder.mDeviceHead = (RoundImageView) convertView.findViewById(R.id.tv_device_icon);
            holder.mDeviceName = (TextView) convertView.findViewById(R.id.tv_device_uname);
            holder.mDeviceSign = (TextView) convertView.findViewById(R.id.tv_device_sign);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        final ChildHolder finalHolder = holder;
        final UserBean child = getChild(groupPosition, childPosition);
        if (getGroup(groupPosition).isNormal()) {
            if (holder.mRLDevices.getVisibility() == View.VISIBLE)
                holder.mRLDevices.setVisibility(View.GONE);
            if (holder.mRLNormal.getVisibility() == View.GONE)
                holder.mRLNormal.setVisibility(View.VISIBLE);
            holder.mNormalName.setText(child.getUsername());
            if (child.getGroup_data()!=null){
                if (!TextUtils.isEmpty(child.getGroup_data().getRemark()))
                    holder.mNormalName.setText(child.getGroup_data().getRemark());
            }
            JMessageClient.getUserInfo(child.getUsername(), new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (i == Config.JMESSAGE_SUCCESS_CODE) {
                        if (!TextUtils.isEmpty(userInfo.getSignature()))
                            finalHolder.mNormalSign.setText(userInfo.getSignature());
                        userInfo.getAvatarFileAsync(new DownloadAvatarCallback() {
                            @Override
                            public void gotResult(int i, String s, File file) {
                                if (i == Config.JMESSAGE_SUCCESS_CODE)
                                    Picasso.with(mContext).load(file).into(finalHolder.mNormalHead);
                                else
                                    CLog.getInstance().iMessage("get user head_ico fail");
                            }
                        });
                    } else {
                        CLog.getInstance().showJMessageClientError( i, s);
                    }
                }
            });
        } else {
            if (holder.mRLNormal.getVisibility() == View.VISIBLE)
                holder.mRLNormal.setVisibility(View.GONE);
            if (holder.mRLDevices.getVisibility() == View.GONE)
                holder.mRLDevices.setVisibility(View.VISIBLE);
            finalHolder.mDeviceName.setText(child.getUsername());
            if (!TextUtils.isEmpty(child.getRemark()))
                finalHolder.mDeviceSign.setText(child.getRemark());
        }
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_adapter_contact_group_item, null);
            holder = new GroupHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.group_name);
            holder.onLineView = (TextView) convertView.findViewById(R.id.online_count);
            holder.iconView = (ImageView) convertView.findViewById(R.id.group_indicator);
            holder.mLLBottom = (LinearLayout) convertView.findViewById(R.id.ll_bottom);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        if (isExpanded) {
            holder.iconView.setImageResource(R.mipmap.ico_group_down);
        } else {
            holder.iconView.setImageResource(R.mipmap.ico_group_right);
        }
        holder.nameView.setText(groupList.get(groupPosition).getName());
        holder.onLineView.setText(getChildrenOnlineCount(groupPosition) + "/" + getChildrenCount(groupPosition));
        if (getGroup(groupPosition).isShowBottom()) {
            if (holder.mLLBottom.getVisibility() == View.GONE)
                holder.mLLBottom.setVisibility(View.VISIBLE);
        } else {
            if (holder.mLLBottom.getVisibility() == View.VISIBLE)
                holder.mLLBottom.setVisibility(View.GONE);
        }
        return convertView;

    }

    @Override
    public int getTreeHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            //mSearchView.setVisibility(View.GONE);
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1 && !mIphoneTreeView.isGroupExpanded(groupPosition)) {
            //mSearchView.setVisibility(View.VISIBLE);
            return PINNED_HEADER_GONE;
        } else {
            //mSearchView.setVisibility(View.GONE);
            return PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configureTreeHeader(View header, int groupPosition, int childPosition, int alpha) {
        if (groupPosition != -1) {
            ((TextView) header.findViewById(R.id.group_name)).setText(groupList.get(groupPosition).getName());
            ((TextView) header.findViewById(R.id.online_count)).setText(getChildrenOnlineCount(groupPosition) + "/" + getChildrenCount(groupPosition));
        }
    }

    @Override
    public void onHeadViewClick(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getHeadViewClickStatus(int groupPosition) {
        if (groupStatusMap.containsKey(groupPosition)) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }

    class GroupHolder {
        LinearLayout mLLBottom;
        TextView nameView;
        TextView onLineView;
        ImageView iconView;
    }

    class ChildHolder {
        RelativeLayout mRLNormal, mRLDevices;
        RoundImageView mNormalHead;
        TextView mNormalSign, mNormalName;
        RoundImageView mDeviceHead;
        TextView mDeviceName, mDeviceSign;
    }
}
