package com.csi0n.searchjob.ui.widget.tabview.adpter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.model.FuliModel;

import java.util.ArrayList;
import java.util.List;

public class CheckAdapter extends BaseAdapter {
    private Context mContext;
    private List<FuliModel> mListData;
    private int normalDrawbleId;
    private Drawable selectedDrawble;
    public List<FuliModel> listData;

    public CheckAdapter(Context context) {
        mContext = context;
        listData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public FuliModel getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.view_adapter_item_check_string, null);
            holder.mtvname = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mck = (CheckBox) convertView.findViewById(R.id.ck_check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mtvname.setText(getItem(position).getName());
        holder.mck.setChecked(getItem(position).isCheck() ? true : false);
        holder.mck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    getItem(position).setCheck(true);
                else
                    getItem(position).setCheck(false);
            }
        });
        return convertView;
    }


    private class ViewHolder {
        TextView mtvname;
        CheckBox mck;
    }
}
