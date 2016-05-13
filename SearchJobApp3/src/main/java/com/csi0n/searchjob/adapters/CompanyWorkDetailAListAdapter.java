package com.csi0n.searchjob.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.bean.EmptyModel;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.model.CompanyWorkDetailAModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by csi0n on 2016/2/23 0023.
 */
public class CompanyWorkDetailAListAdapter extends BaseAdapter {
    private Context mContext;
    public List<CompanyWorkDetailAModel.TodayMoneyBackEntity> todayMoneyBackEntityList;
private AlertView mAlertView;
    public CompanyWorkDetailAListAdapter(Context context) {
        this.mContext = context;
        todayMoneyBackEntityList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return todayMoneyBackEntityList.size();
    }

    @Override
    public CompanyWorkDetailAModel.TodayMoneyBackEntity getItem(int i) {
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
            view = View.inflate(mContext, R.layout.view_adapter_company_work_detail_a_list_item, null);
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
                                HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyModel>(EmptyModel.class) {
                                    @Override
                                    public void SuccessResult(EmptyModel result) throws JSONException {
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
                                HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyModel>(EmptyModel.class) {
                                    @Override
                                    public void SuccessResult(EmptyModel result) throws JSONException {
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

            }
        });

    }

    private class ViewHolder {
        ImageView mHead, mFollow;
        TextView mName, mPhone, mNBack, mNVBack;
    }
}
