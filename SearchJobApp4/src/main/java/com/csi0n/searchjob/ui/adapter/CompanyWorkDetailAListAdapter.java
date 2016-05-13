package com.csi0n.searchjob.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.pojo.model.ext.MoneyBackModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailAListAdapter extends BaseAdapter {
    private Context mContext;

    public List<MoneyBackModel> datas;

    public CompanyWorkDetailAListAdapter(Context mContext) {
        this.mContext = mContext;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public MoneyBackModel getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.view_adapter_company_work_detail_a_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        if (getItem(position).jingliren.is_follow)
            holder.ivFollow.setImageResource(R.mipmap.ico_follow_y);
        else
            holder.ivFollow.setImageResource(R.mipmap.ico_follow_n);
        holder.idName.setText(getItem(position).jingliren.real_name);
        holder.tvPhone.setText(getItem(position).jingliren.phone);
        holder.tvMoneyBackFemale.setText(getItem(position).money_back_male);
        holder.tvMoneyBackMale.setText(getItem(position).money_back_famale);
        return convertView;
    }
    static class ViewHolder {
        @Bind(R.id.iv_head)
        ImageView ivHead;
        @Bind(R.id.id_name)
        TextView idName;
        @Bind(R.id.tv_phone)
        TextView tvPhone;
        @Bind(R.id.tv_money_back_female)
        TextView tvMoneyBackFemale;
        @Bind(R.id.tv_money_back_male)
        TextView tvMoneyBackMale;
        @Bind(R.id.iv_follow)
        ImageView ivFollow;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
