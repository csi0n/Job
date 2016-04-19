package com.csi0n.searchjob.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.ui.activity.UserDynamicDetailActivity;

/**
 * Created by csi0n on 2015/12/27 0027.
 */
public class UserDynamicAdapter extends BaseAdapter {
    private Context mContext;

    public UserDynamicAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int i) {
        return null;
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
            view = View.inflate(mContext, R.layout.view_adapter_user_dynamic_item, null);
            holder.mHead = (RoundImageView) view.findViewById(R.id.imgHead);
            holder.mTVUname = (TextView) view.findViewById(R.id.tvName);
            holder.mTVContent = (TextView) view.findViewById(R.id.tvContent);
            holder.mTVCtime = (TextView) view.findViewById(R.id.tvDate);
            holder.bg = (LinearLayout) view.findViewById(R.id.contacts_items);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        AddListener(holder, i);
        return view;
    }

    private void AddListener(final ViewHolder holder, final int position) {
        holder.bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(mContext, UserDynamicDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    private class ViewHolder {
        private RoundImageView mHead;
        private TextView mTVUname, mTVContent, mTVCtime;
        private LinearLayout bg;
    }
}
