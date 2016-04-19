package com.csi0n.searchjob.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.ui.activity.UserInforActivity;
import com.csi0n.searchjob.utils.bean.CompanyWorkDetailCListBean;
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
 * Created by chqss on 2016/3/7 0007.
 */
public class CompanyWorkDetailCListAdapter extends BaseAdapter {
    public List<CompanyWorkDetailCListBean.CompanyWorkDetailCBean> datas;
    private Context mContext;
private AlertView mAlertView;
    public CompanyWorkDetailCListAdapter(Context context) {
        this.mContext = context;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CompanyWorkDetailCListBean.CompanyWorkDetailCBean getItem(int i) {
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
            holder = new ViewHolder();
            view = View.inflate(mContext, R.layout.view_adapter_company_work_detail_c_item, null);
            holder.mRIhead = (RoundImageView) view.findViewById(R.id.ri_head);
            holder.mTVname = (TextView) view.findViewById(R.id.tv_name);
            holder.mTVintro = (TextView) view.findViewById(R.id.tv_intro);
            holder.mIVfollow = (ImageView) view.findViewById(R.id.iv_follow);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();
        final ViewHolder finalHolder = holder;
        JMessageClient.getUserInfo(getItem(i).getUsername(), new CGetUserInfoCallback() {
            @Override
            protected void SuccessResult(UserInfo userInfo) {
                if (!TextUtils.isEmpty(userInfo.getSignature()))
                    finalHolder.mTVintro.setText(userInfo.getSignature());
                userInfo.getAvatarFileAsync(new CDownloadAvatarCallback() {
                    @Override
                    protected void SuccessResult(File file) {
                        Picasso.with(mContext).load(file).into(finalHolder.mRIhead);
                    }
                });
            }
        });
        holder.mTVname.setText(getItem(i).getReal_name());
        if (getItem(i).getIs_follow()==0)
            holder.mIVfollow.setImageResource(R.mipmap.ico_follow_n);
        else if (getItem(i).getIs_follow()==1)
            holder.mIVfollow.setImageResource(R.mipmap.ico_follow_y);
        addListener(i,holder);
        return view;
    }
    private void addListener(final int position,final ViewHolder holder){
      holder.mIVfollow.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (getItem(position).getIs_follow()==0){
                  mAlertView = new AlertView("关注经理人", "是否关注该经理人？", "取消", new String[]{"确定"}, null, mContext, AlertView.Style.Alert, new OnItemClickListener() {
                      @Override
                      public void onItemClick(Object o, final int p) {
                          if (p != AlertView.CANCELPOSITION) {
                              PostParams params= BaseController.getStaticDefaultPostParams(R.string.url_followJinglirenOption);
                              params.put("fid",getItem(position).getUid());
                              params.put("option",String.valueOf(0));
                              HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
                                  @Override
                                  public void SuccessResult(EmptyBean result) throws JSONException {
                                      CLog.show("关注成功!");
                                      getItem(position).setIs_follow(1);
                                      notifyDataSetChanged();
                                  }
                              });
                              post.post();
                          }
                      }
                  }).setCancelable(true);
                  mAlertView.show();
              }else if (getItem(position).getIs_follow()==1){
                  mAlertView = new AlertView("取消关注经理人", "是否取消关注该经理人？", "取消", new String[]{"确定"}, null, mContext, AlertView.Style.Alert, new OnItemClickListener() {
                      @Override
                      public void onItemClick(Object o, int p) {
                          if (p!= AlertView.CANCELPOSITION) {
                              PostParams params= BaseController.getStaticDefaultPostParams(R.string.url_followJinglirenOption);
                              params.put("fid",getItem(position).getUid());
                              params.put("option",String.valueOf(1));
                              HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
                                  @Override
                                  public void SuccessResult(EmptyBean result) throws JSONException {
                                      CLog.show("取消关注成功!");
                                      getItem(position).setIs_follow(0);
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
        TextView mTVname, mTVintro;
        ImageView mIVfollow;
    }
}
