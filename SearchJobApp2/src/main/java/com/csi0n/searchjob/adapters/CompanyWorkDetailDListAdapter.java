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
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.ui.activity.UserInforActivity;
import com.csi0n.searchjob.utils.TimeFormat;
import com.csi0n.searchjob.utils.bean.CompanyWorkDetailDListBean;
import com.csi0n.searchjob.utils.jpush.CDownloadAvatarCallback;
import com.csi0n.searchjob.utils.jpush.CGetUserInfoCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by chqss on 2016/3/10 0010.
 */
public class CompanyWorkDetailDListAdapter extends BaseAdapter {
    private Context mContext;
    public List<CompanyWorkDetailDListBean.CompanyWorkDetailD> datas;

    public CompanyWorkDetailDListAdapter(Context context) {
        this.mContext = context;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CompanyWorkDetailDListBean.CompanyWorkDetailD getItem(int i) {
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
            view = View.inflate(mContext, R.layout.view_adapter_company_work_d_list_item, null);
            holder.mRIhead = (RoundImageView) view.findViewById(R.id.ri_head);
            holder.mTVname = (TextView) view.findViewById(R.id.tv_name);
            holder.mTVcontent = (TextView) view.findViewById(R.id.tv_content);
            holder.mTVtime = (TextView) view.findViewById(R.id.tv_time);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();
        final ViewHolder finalHolder = holder;
        JMessageClient.getUserInfo(getItem(i).getUsername(), new CGetUserInfoCallback() {
            @Override
            protected void SuccessResult(UserInfo userInfo) {
                finalHolder.mTVname.setText(userInfo.getNickname());
                userInfo.getAvatarFileAsync(new CDownloadAvatarCallback() {
                    @Override
                    protected void SuccessResult(File file) {
                        Picasso.with(mContext).load(file).into(finalHolder.mRIhead);
                    }
                });
            }
        });
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
            Bundle bundle=new Bundle();
            bundle.putBoolean(Config.MARK_USER_INFO_ACTIVITY_IS_FROM_CAPTURE,true);
            bundle.putString(Config.MARK_USER_INFO_ACTIVITY_USER_ID,getItem(position).getUid());
            Intent intent=new Intent();
            intent.setClass(mContext, UserInforActivity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    });
}
    private class ViewHolder {
        RoundImageView mRIhead;
        TextView mTVname, mTVcontent, mTVtime;
    }
}
