package com.csi0n.searchjob.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.utils.bean.SearchJobKeyCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chqss on 2016/3/23 0023.
 */
public class SearchJobKeyAdapter extends BaseAdapter {
    private Context mContext;
    public List<SearchJobKeyCache> datas;
    public SearchJobKeyAdapter(Context context) {
        this.mContext=context;
        datas=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public SearchJobKeyCache getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int positon, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null){
            view=View.inflate(mContext, R.layout.view_adapter_item_search_job_key,null);
            holder=new ViewHolder();
            holder.mName=(TextView)view.findViewById(R.id.tv_name);
            view.setTag(holder);
        }else
        holder=(ViewHolder)view.getTag();
        holder.mName.setText(getItem(positon).getKey_name());
        addListener(holder,positon);
        return view;
    }
    private void addListener(final ViewHolder holder,final int position){

    }
    private class ViewHolder{
        private TextView mName;
    }
}
