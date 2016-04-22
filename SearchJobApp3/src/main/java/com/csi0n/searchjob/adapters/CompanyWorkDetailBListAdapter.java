package com.csi0n.searchjob.adapters;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.model.CompanyWorkDetailBListModel;
import com.csi0n.searchjob.ui.fragment.CompanyWorkDetailFragmentB;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import me.next.tagview.TagCloudView;

/**
 * Created by chqss on 2016/3/6 0006.
 */
public class CompanyWorkDetailBListAdapter extends BaseAdapter {
    private CompanyWorkDetailFragmentB mCompanyWorkDetailFragmentB;
    public List<CompanyWorkDetailBListModel.CompanyWorkDetailBean> datas;
    private DbManager db = x.getDb(Config.dbConfig);

    public CompanyWorkDetailBListAdapter(CompanyWorkDetailFragmentB mCompanyWorkDetailFragmentB) {
        this.mCompanyWorkDetailFragmentB = mCompanyWorkDetailFragmentB;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CompanyWorkDetailBListModel.CompanyWorkDetailBean getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int positon, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(mCompanyWorkDetailFragmentB.aty, R.layout.view_adapter_company_work_detail_b_list_item, null);
            holder.mTVName = (TextView) view.findViewById(R.id.tv_name);
            holder.mTag = (TagCloudView) view.findViewById(R.id.tag_line_1);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();
        holder.mTVName.setText(getItem(positon).getJob_type());
        List<String> tags2 = new ArrayList<>();
        if (getItem(positon).getSex()==3)
            tags2.add("性别不限");
        else if (getItem(positon).getSex()==2)
            tags2.add("女");
        else if (getItem(positon).getSex()==1)
            tags2.add("男");
        if (TextUtils.isEmpty(getItem(positon).getAge())) {
            tags2.add("年龄不限");
        } else {
            String[] ages = getItem(positon).getAge().split(",");
            if (ages.length == 2) {
                tags2.add("男:"+ages[0]+"女:"+ages[1]);
            } else {
                tags2.add(getItem(positon).getAge());
            }
        }
        String[] fu_lis = getItem(positon).getFuli().split(",");
        for (int i = 0; i < fu_lis.length; i++) {
            try {
                com.csi0n.searchjob.lib.utils.bean.FuliBean fuliBean = db.selector(com.csi0n.searchjob.lib.utils.bean.FuliBean.class).where("id", "=", fu_lis[i]).findFirst();
                if (fuliBean != null)
                    if (fuliBean.getName()!=null)
                        tags2.add(fuliBean.getName());
            } catch (DbException e) {
            }
        }
        holder.mTag.setTags(tags2);
        return view;
    }

    private class ViewHolder {
        TextView mTVName;
        TagCloudView mTag;
    }
}
