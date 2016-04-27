package com.csi0n.searchjob.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.utils.Config;
import com.csi0n.searchjob.lib.utils.TimeFormat;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.model.MyCommentsListModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chqss on 2016/4/27 0027.
 */
public class MyCommentsAdapter extends BaseAdapter {
    private Context mContext;
    public List<MyCommentsListModel.MyCommentsModel> datas;

    public MyCommentsAdapter(Context mContext) {
        this.mContext = mContext;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public MyCommentsListModel.MyCommentsModel getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.view_adapter_my_comments_list_item, null);
            holder.mRIUserHead = (RoundImageView) convertView.findViewById(R.id.ri_user_head);
            holder.mRICompanyImage = (RoundImageView) convertView.findViewById(R.id.ri_company_image);
            holder.mTvUname = (TextView) convertView.findViewById(R.id.tv_uname);
            holder.mTvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.mTvAddTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.mTvCompanyName = (TextView) convertView.findViewById(R.id.tv_company_name);
            holder.mTvCompanyIntro = (TextView) convertView.findViewById(R.id.tv_company_intro);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        final ViewHolder finalHolder = holder;
        Picasso.with(mContext).load(Config.BASE_URL + com.csi0n.searchjob.Config.LOGIN_USER.getHead_ic()).into(holder.mRIUserHead, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                finalHolder.mRIUserHead.setImageResource(R.mipmap.ico_default_head_ic);
            }
        });
        Picasso.with(mContext).load(Config.BASE_URL + getItem(position).getImage()).into(holder.mRICompanyImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                finalHolder.mRICompanyImage.setImageResource(R.mipmap.ico_default_head_ic);
            }
        });

        finalHolder.mTvUname.setText(com.csi0n.searchjob.Config.LOGIN_USER.getUname());
        finalHolder.mTvContent.setText(getItem(position).getContent());
        TimeFormat timeFormat = new TimeFormat(mContext, getItem(position).getAdd_time());
        finalHolder.mTvAddTime.setText(timeFormat.getTime());
        finalHolder.mTvCompanyName.setText(getItem(position).getName());
        finalHolder.mTvCompanyIntro.setText(getItem(position).getIntro());

        return convertView;
    }

    private class ViewHolder {
        RoundImageView mRIUserHead, mRICompanyImage;
        TextView mTvUname, mTvContent, mTvAddTime, mTvCompanyName, mTvCompanyIntro;
    }
}
