package com.csi0n.searchjob.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.pojo.model.ext.CompanyWorkDetailBModel;
import com.csi0n.searchjob.core.io.DbManager;
import com.csi0n.searchjob.database.dao.FuLi;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.next.tagview.TagCloudView;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailBListAdapter extends BaseAdapter {
    private Context mContext;
    public List<CompanyWorkDetailBModel> datas;

    public CompanyWorkDetailBListAdapter(Context mContext) {
        this.mContext = mContext;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CompanyWorkDetailBModel getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.view_adapter_company_work_detail_b_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.tvName.setText(getItem(position).job_type);
        List<String> tags2 = new ArrayList<>();
        if (getItem(position).sex==3)
            tags2.add("性别不限");
        else if (getItem(position).sex==2)
            tags2.add("女");
        else if (getItem(position).sex==1)
            tags2.add("男");
        if (TextUtils.isEmpty(getItem(position).age)) {
            tags2.add("年龄不限");
        } else {
            String[] ages = getItem(position).age.split(",");
            if (ages.length == 2) {
                tags2.add("男:"+ages[0]+"女:"+ages[1]);
            } else {
                tags2.add(getItem(position).age);
            }
        }
        String[] fu_lis = getItem(position).fuli.split(",");
        for (int i = 0; i < fu_lis.length; i++) {
                FuLi fuLi= DbManager.findFuLiByID(fu_lis[i]);
                if (fuLi != null)
                    if (fuLi.getName()!=null)
                        tags2.add(fuLi.getName());
        }
        holder.tagLine1.setTags(tags2);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tag_line_1)
        TagCloudView tagLine1;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
