package com.csi0n.searchjob.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.database.dao.KeyValue;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class SearchJobKeyAdapter extends BaseAdapter {
    private Context mContext;
    public List<KeyValue> datas;

    public SearchJobKeyAdapter(Context mContext) {
        this.mContext = mContext;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public KeyValue getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.view_adapter_item_search_job_key, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else
        holder=(ViewHolder)convertView.getTag();
        holder.tvName.setText(getItem(position).getValue());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
