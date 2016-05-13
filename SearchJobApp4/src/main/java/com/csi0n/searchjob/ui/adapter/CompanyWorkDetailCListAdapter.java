package com.csi0n.searchjob.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.pojo.model.ext.CompanyWorkDetailCModel;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailCListAdapter extends BaseAdapter {
    private Context mContext;

    public List<CompanyWorkDetailCModel> datas;

    public CompanyWorkDetailCListAdapter(Context mContext) {
        this.mContext = mContext;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CompanyWorkDetailCModel getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.view_adapter_company_work_detail_c_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        if (TextUtils.isEmpty(getItem(position).headIc))
            holder.riHead.setImageResource(R.mipmap.ico_default_head_ic);
        else {
            final ViewHolder finalHolder = holder;
            Picasso.with(mContext).load(Constants.BaseUrl + getItem(position).headIc).into(holder.riHead, new Callback() {
                @Override
                public void onSuccess() {
                }
                @Override
                public void onError() {
                    finalHolder.riHead.setImageResource(R.mipmap.ico_default_head_ic);
                }
            });
        }
        holder.tvName.setText(getItem(position).realName);
        if (!TextUtils.isEmpty(getItem(position).intro))
            holder.tvIntro.setText(getItem(position).intro);
        if (getItem(position).isFollow)
            holder.ivFollow.setImageResource(R.mipmap.ico_follow_y);
        else
            holder.ivFollow.setImageResource(R.mipmap.ico_follow_n);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.ri_head)
        RoundImageView riHead;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_intro)
        TextView tvIntro;
        @Bind(R.id.iv_follow)
        ImageView ivFollow;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
