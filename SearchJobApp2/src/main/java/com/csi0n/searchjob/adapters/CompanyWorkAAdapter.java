package com.csi0n.searchjob.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.ui.activity.UserInforActivity;
import com.csi0n.searchjob.utils.bean.CompanyWorkDetailMainBean;
import com.csi0n.searchjob.utils.bean.EmptyBean;
import com.csi0n.searchjob.utils.jpush.CDownloadAvatarCallback;
import com.csi0n.searchjob.utils.jpush.CGetUserInfoCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by csi0n on 2016/2/23 0023.
 */
public class CompanyWorkAAdapter extends BaseAdapter {
    private Context mContext;
    public List<CompanyWorkDetailMainBean.TodayMoneyBackEntity> todayMoneyBackEntityList;
private AlertView mAlertView;
    public CompanyWorkAAdapter(Context context) {
        this.mContext = context;
        todayMoneyBackEntityList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return todayMoneyBackEntityList.size();
    }

    @Override
    public CompanyWorkDetailMainBean.TodayMoneyBackEntity getItem(int i) {
        return todayMoneyBackEntityList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(mContext, R.layout.view_adapter_company_work_a_item, null);
            holder.mHead = (ImageView) view.findViewById(R.id.iv_head);
            holder.mName = (TextView) view.findViewById(R.id.id_name);
            holder.mPhone = (TextView) view.findViewById(R.id.tv_phone);
            holder.mNBack = (TextView) view.findViewById(R.id.tv_money_back_male);
            holder.mNVBack = (TextView) view.findViewById(R.id.tv_money_back_female);
            holder.mFollow = (ImageView) view.findViewById(R.id.iv_follow);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        JMessageClient.getUserInfo(getItem(i).getJingliren().getUsername(), new CGetUserInfoCallback() {
            @Override
            protected void SuccessResult(UserInfo userInfo) {
                userInfo.getAvatarFileAsync(new CDownloadAvatarCallback() {
                    @Override
                    protected void SuccessResult(File file) {
                        Picasso.with(mContext).load(file).into(holder.mHead);
                    }
                });
            }
        });
        if (getItem(i).getJingliren().getIs_follow()==0)
            holder.mFollow.setImageResource(R.mipmap.ico_follow_n);
        else if (getItem(i).getJingliren().getIs_follow()==1)
            holder.mFollow.setImageResource(R.mipmap.ico_follow_y);
        holder.mName.setText(getItem(i).getJingliren().getReal_name());
        holder.mPhone.setText(getItem(i).getJingliren().getPhone());
        holder.mNBack.setText(getItem(i).getMoney_back_male());
        holder.mNVBack.setText(getItem(i).getMoney_back_famale());
        addListener(i,holder);
        return view;
    }
    private void addListener(final int position,final ViewHolder holder){
        holder.mFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getItem(position).getJingliren().getIs_follow()==0){
                    mAlertView = new AlertView("关注经理人", "是否关注该经理人？", "取消", new String[]{"确定"}, null, mContext, AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, final int p) {
                            if (p != AlertView.CANCELPOSITION) {
                                PostParams params= BaseController.getStaticDefaultPostParams(R.string.url_followJinglirenOption);
                                params.put("fid",getItem(position).getJingliren().getUid());
                                params.put("option",String.valueOf(0));
                                HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
                                    @Override
                                    public void SuccessResult(EmptyBean result) throws JSONException {
                                        CLog.show("关注成功!");
                                        getItem(position).getJingliren().setIs_follow(1);
                                        notifyDataSetChanged();
                                    }
                                });
                                post.post();
                            }
                        }
                    }).setCancelable(true);
                    mAlertView.show();
                }else if (getItem(position).getJingliren().getIs_follow()==1){
                    mAlertView = new AlertView("取消关注经理人", "是否取消关注该经理人？", "取消", new String[]{"确定"}, null, mContext, AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int p) {
                            if (p!= AlertView.CANCELPOSITION) {
                                PostParams params= BaseController.getStaticDefaultPostParams(R.string.url_followJinglirenOption);
                                params.put("fid",getItem(position).getJingliren().getUid());
                                params.put("option",String.valueOf(1));
                                HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
                                    @Override
                                    public void SuccessResult(EmptyBean result) throws JSONException {
                                        CLog.show("取消关注成功!");
                                        getItem(position).getJingliren().setIs_follow(1);
                                        notifyDataSetChanged();
                                    }
                                });
                                post.post();
                            }
                        }
                    }).setCancelable(true);
                    mAlertView.show();
                }
            }
        });
        holder.mHead.setOnClickListener(new View.OnClickListener() {
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
        ImageView mHead, mFollow;
        TextView mName, mPhone, mNBack, mNVBack;
    }
}
