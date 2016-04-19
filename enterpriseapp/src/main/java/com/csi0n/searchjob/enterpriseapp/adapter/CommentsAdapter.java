package com.csi0n.searchjob.enterpriseapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.utils.bean.CommentsListBean;
import com.csi0n.searchjob.lib.utils.TimeFormat;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csi0n on 3/29/16.
 */
public class CommentsAdapter extends BaseAdapter {

    private Context mContext;
    public List<CommentsListBean.CommentsBean> datas;

    public CommentsAdapter(Context context) {
        this.mContext = context;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CommentsListBean.CommentsBean getItem(int i) {
        return datas.get(i);
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
            view = View.inflate(mContext, R.layout.adapter_comments_item, null);
            holder.mTVname = (TextView) view.findViewById(R.id.tv_name);
            holder.mTVtime = (TextView) view.findViewById(R.id.tv_time);
            holder.mTVcontent = (TextView) view.findViewById(R.id.tv_content);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();
        holder.mTVname.setText(getItem(i).getUsername());
        TimeFormat timeFormat=new TimeFormat(mContext,Long.valueOf(getItem(i).getAdd_time()));
        holder.mTVtime.setText(timeFormat.getTime());
        return view;
    }

    private class ViewHolder {
        TextView mTVname, mTVcontent, mTVtime;
    }
}
