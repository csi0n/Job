package com.csi0n.searchjob.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.pojo.model.ext.MyCommentModel;
import com.csi0n.searchjob.business.pojo.model.ext.MyModel;
import com.csi0n.searchjob.core.string.Constants;
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
public class MyCommentsAdapter extends BaseAdapter {
    private Context mContent;

    public List<MyCommentModel> datas;

    public MyCommentsAdapter(Context mContent) {
        this.mContent = mContent;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public MyCommentModel getItem(int position) {
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
            convertView = View.inflate(mContent, R.layout.view_adapter_my_comments_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        final ViewHolder finalHolder = holder;
        MyModel my=Constants.LOGIN_USER;
        Picasso.with(mContent).load(Constants.BaseUrl +my.head_ic).into(holder.riUserHead, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                finalHolder.riUserHead.setImageResource(R.mipmap.ico_default_head_ic);
            }
        });
        Picasso.with(mContent).load(Constants.BaseUrl + getItem(position).image).into(holder.riCompanyImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                finalHolder.riCompanyImage.setImageResource(R.mipmap.ico_default_head_ic);
            }
        });
        finalHolder.tvUname.setText(my.uname);
        finalHolder.tvContent.setText(getItem(position).content);
        /*TimeFormat timeFormat = new TimeFormat(mContext, getItem(position).getAdd_time());
        finalHolder.mTvAddTime.setText(timeFormat.getTime());*/
        finalHolder.tvCompanyName.setText(getItem(position).name);
        finalHolder.tvCompanyIntro.setText(getItem(position).intro);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.ri_user_head)
        RoundImageView riUserHead;
        @Bind(R.id.tv_uname)
        TextView tvUname;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.ri_company_image)
        RoundImageView riCompanyImage;
        @Bind(R.id.tv_company_name)
        TextView tvCompanyName;
        @Bind(R.id.tv_company_intro)
        TextView tvCompanyIntro;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
