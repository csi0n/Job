package com.csi0n.searchjob.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.pojo.model.ext.CompanyWorkDetailDModel;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.core.string.TimeUtils;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public class CompanyWorkDetailDListAdapter extends BaseAdapter {
    private Context mContext;
    public List<CompanyWorkDetailDModel> datas;

    public CompanyWorkDetailDListAdapter(Context mContext) {
        this.mContext = mContext;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CompanyWorkDetailDModel getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.view_adapter_company_work_detail_d_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        final ViewHolder finalHolder = holder;
        if (getItem(position).userInfo != null) {
            if (TextUtils.isEmpty(getItem(position).userInfo.head_ic))
                holder.riHead.setImageResource(R.mipmap.ico_default_head_ic);
            else {
                Picasso.with(mContext).load(Constants.BaseUrl + getItem(position).userInfo.head_ic).into(holder.riHead, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        finalHolder.riHead.setImageResource(R.mipmap.ico_default_head_ic);
                    }
                });
            }
        }
        finalHolder.tvName.setText(getItem(position).userInfo.uname);
        finalHolder.tvContent.setText(getItem(position).content);
        finalHolder.tvTime.setText(TimeUtils.getDistanceTime(getItem(position).addTime));
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.ri_head)
        RoundImageView riHead;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
