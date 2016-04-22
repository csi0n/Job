package com.csi0n.searchjob.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.utils.TimeFormat;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.model.CompanyWorkDetailDListModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chqss on 2016/3/10 0010.
 */
public class CompanyWorkDetailDListAdapter extends BaseAdapter {
    private Context mContext;
    public List<CompanyWorkDetailDListModel.CompanyWorkDetailDModel> datas;

    public CompanyWorkDetailDListAdapter(Context context) {
        this.mContext = context;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CompanyWorkDetailDListModel.CompanyWorkDetailDModel getItem(int i) {
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
            holder = new ViewHolder ();
            view = View.inflate(mContext, R.layout.view_adapter_company_work_detail_d_list_item, null);
            holder.mRIhead = (RoundImageView) view.findViewById(R.id.ri_head);
            holder.mTVname = (TextView) view.findViewById(R.id.tv_name);
            holder.mTVcontent = (TextView) view.findViewById(R.id.tv_content);
            holder.mTVtime = (TextView) view.findViewById(R.id.tv_time);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();
        final ViewHolder finalHolder = holder;
        finalHolder.mTVcontent.setText(getItem(i).getContent());
        TimeFormat timeFormat=new TimeFormat(mContext,Long.valueOf(getItem(i).getAdd_time()));
        finalHolder.mTVtime.setText(timeFormat.getTime());
        addListener(holder,i);
        return view;
    }
private void  addListener(final ViewHolder holder,final int position){
    holder.mRIhead.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    });
}
    private class ViewHolder {
        RoundImageView mRIhead;
        TextView mTVname, mTVcontent, mTVtime;
    }
}
