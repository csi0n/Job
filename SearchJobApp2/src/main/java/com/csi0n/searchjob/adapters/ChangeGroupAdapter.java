package com.csi0n.searchjob.adapters;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.ui.activity.ChangeGroupListActivity;
import com.csi0n.searchjob.utils.bean.GroupListBean;
import com.csi0n.searchjob.utils.bean.Show;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chqss on 2016/1/31 0031.
 */
public class ChangeGroupAdapter extends BaseAdapter {
    private ChangeGroupListActivity mChangeGroupListActivity;
    public List<GroupListBean.Group> mGroups;
    private Show.FriendInfo mFriendInfo;
    private int index = -1;

    public ChangeGroupAdapter(ChangeGroupListActivity mChangeGroupListActivity, Show.FriendInfo friendInfo) {
        this.mChangeGroupListActivity = mChangeGroupListActivity;
        this.mGroups = new ArrayList<>();
        this.mFriendInfo = friendInfo;
        for (int i = 0; i < mGroups.size(); i++) {
            if (friendInfo.getGroup_info().getId().equals(mGroups.get(i).getId()))
                index = i;
        }
    }

    @Override
    public int getCount() {
        return mGroups.size();
    }

    @Override
    public GroupListBean.Group getItem(int position) {
        return mGroups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mChangeGroupListActivity,R.layout.view_adapter_group_list_item,null);
            holder = new ViewHolder();
            holder.m_tv_group_name = (TextView) convertView.findViewById(R.id.tv_group_name);
            holder.m_iv_group_check = (CheckBox) convertView.findViewById(R.id.iv_group_check);
            holder.m_rt_main_view = (RelativeLayout) convertView.findViewById(R.id.main_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.m_tv_group_name.setText(getItem(position).getName());
        holder.m_iv_group_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    index = position;
                    notifyDataSetChanged();
                }
            }
        });
        if (index == position)
            holder.m_iv_group_check.setChecked(true);
        else
            holder.m_iv_group_check.setChecked(false);
        return convertView;
    }

    public int getIndex() {
        return index;
    }

    private class ViewHolder {
        TextView m_tv_group_name;
        CheckBox m_iv_group_check;
        RelativeLayout m_rt_main_view;
    }
}
