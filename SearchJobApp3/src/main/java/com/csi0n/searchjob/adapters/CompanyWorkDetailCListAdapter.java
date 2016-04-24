package com.csi0n.searchjob.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.Config;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.bean.EmptyModel;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.model.CompanyWorkDetailCListModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chqss on 2016/3/7 0007.
 */
public class CompanyWorkDetailCListAdapter extends BaseAdapter {
    public List<CompanyWorkDetailCListModel.CompanyWorkDetailCModel> datas;
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
    public CompanyWorkDetailCListModel.CompanyWorkDetailCModel getItem(int i) {
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
            view = View.inflate(mContext, R.layout.view_adapter_company_work_detail_c_list_item, null);
            holder.mRIhead = (RoundImageView) view.findViewById(R.id.ri_head);
            holder.mTVname = (TextView) view.findViewById(R.id.tv_name);
            holder.mTVintro = (TextView) view.findViewById(R.id.tv_intro);
            holder.mIVfollow = (ImageView) view.findViewById(R.id.iv_follow);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();
        if (TextUtils.isEmpty(getItem(i).getHead_ic()))
            holder.mRIhead.setImageResource(R.mipmap.ico_default_head_ic);
        else {
            final ViewHolder finalHolder = holder;
            Picasso.with(mContext).load(Config.BASE_URL + getItem(i).getHead_ic()).into(holder.mRIhead, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    finalHolder.mRIhead.setImageResource(R.mipmap.ico_default_head_ic);
                }
            });
        }
        holder.mTVname.setText(getItem(i).getReal_name());
        if (!TextUtils.isEmpty(getItem(i).getIntro()))
            holder.mTVintro.setText(getItem(i).getIntro());
        if (getItem(i).getIs_follow() == 0)
            holder.mIVfollow.setImageResource(R.mipmap.ico_follow_n);
        else if (getItem(i).getIs_follow() == 1)
            holder.mIVfollow.setImageResource(R.mipmap.ico_follow_y);
        addListener(i, holder);
        return view;
    }

    private void addListener(final int position, final ViewHolder holder) {
        holder.mIVfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getItem(position).getIs_follow() == 0) {
                    mAlertView = new AlertView("关注经理人", "是否关注该经理人？", "取消", new String[]{"确定"}, null, mContext, AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, final int p) {
                            if (p != AlertView.CANCELPOSITION) {
                                PostParams params = BaseController.getStaticDefaultPostParams(R.string.url_followJinglirenOption);
                                params.put("fid", getItem(position).getUid());
                                params.put("option", String.valueOf(0));
                                HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyModel>(EmptyModel.class) {
                                    @Override
                                    public void SuccessResult(EmptyModel result) throws JSONException {
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
                } else if (getItem(position).getIs_follow() == 1) {
                    mAlertView = new AlertView("取消关注经理人", "是否取消关注该经理人？", "取消", new String[]{"确定"}, null, mContext, AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int p) {
                            if (p != AlertView.CANCELPOSITION) {
                                PostParams params = BaseController.getStaticDefaultPostParams(R.string.url_followJinglirenOption);
                                params.put("fid", getItem(position).getUid());
                                params.put("option", String.valueOf(1));
                                HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyModel>(EmptyModel.class) {
                                    @Override
                                    public void SuccessResult(EmptyModel result) throws JSONException {
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

            }
        });
    }

    private class ViewHolder {
        RoundImageView mRIhead;
        TextView mTVname, mTVintro;
        ImageView mIVfollow;
    }
}
