package com.csi0n.searchjob.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.pojo.model.ext.CompanyJobModel;
import com.csi0n.searchjob.core.io.DbManager;
import com.csi0n.searchjob.database.dao.FuLi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.next.tagview.TagCloudView;

/**
 * Created by csi0n on 5/8/16.
 */
public class SearchJobFragmentAdapter extends BaseAdapter {
    private Context mContext;
    public List<CompanyJobModel> datas;

    public SearchJobFragmentAdapter(Context mContext) {
        this.mContext = mContext;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CompanyJobModel getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int positon, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            view = View.inflate(mContext, R.layout.view_adapter_item_search_job_fragment, null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else
            holder=(ViewHolder)view.getTag();
        List<String> tags2 = new ArrayList<>();
        tags2.add(String.format("%s-%s", getItem(positon).city, getItem(positon).area));
        if (getItem(positon).sex==3)
            tags2.add("性别不限");
        else if (getItem(positon).sex==2)
            tags2.add("女");
        else if (getItem(positon).sex==1)
            tags2.add("男");
        if (TextUtils.isEmpty(getItem(positon).age)) {
            tags2.add("年龄不限");
        } else {
            String[] ages = getItem(positon).age.split(",");
            if (ages.length == 2) {
                tags2.add("男:"+ages[0]+"女:"+ages[1]);
            } else {
                tags2.add(getItem(positon).age);
            }
        }
        String[] fu_lis = getItem(positon).fuli.split(",");
        for (int i = 0; i < fu_lis.length; i++) {
            FuLi fuLi= DbManager.getFuLiByID(Long.valueOf(fu_lis[i]));
                if (fuLi != null)
                    if (fuLi.getName()!=null)
                        tags2.add(fuLi.getName());
        }
        holder.tvName.setText(getItem(positon).company_name);
        holder.tagLine1.setTags(tags2);
        int money;
        money = (getItem(positon).money_back_male + getItem(positon).money_back_famale) / 2;
        holder.tvBackMoney.setText(money > 0 ? String.valueOf(money) + "元" : "");
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_back_money)
        TextView tvBackMoney;
        @Bind(R.id.tag_line_1)
        TagCloudView tagLine1;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
