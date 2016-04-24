package com.csi0n.searchjob.adapters;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.model.CompanyJobListModel;
import com.csi0n.searchjob.model.FuliModel;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;
import me.next.tagview.TagCloudView;

/**
 * Created by chqss on 2016/2/22 0022.
 */
public class SearchJobFragmentAdapter extends BaseAdapter {
    private Context mContext;
    public List<CompanyJobListModel.CompanyJobModel> datas;
    private DbManager db = x.getDb(Config.dbConfig);

    public SearchJobFragmentAdapter(Context context) {
        this.mContext = context;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CompanyJobListModel.CompanyJobModel getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int positon, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.view_adapter_item_search_job_fragment, null);
            holder = new ViewHolder();
            holder.mTVname = (TextView) view.findViewById(R.id.tv_name);
            holder.mTVbackmoney = (TextView) view.findViewById(R.id.tv_back_money);
            holder.mTAGline1 = (TagCloudView) view.findViewById(R.id.tag_line_1);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        List<String> tags2 = new ArrayList<>();
        tags2.add(String.format("%s-%s", getItem(positon).getCity(), getItem(positon).getArea()));
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
                FuliModel fuliBean = db.selector(FuliModel.class).where("id", "=", fu_lis[i]).findFirst();
                if (fuliBean != null)
                    if (fuliBean.getName()!=null)
                        tags2.add(fuliBean.getName());
            } catch (DbException e) {
            }
        }
        holder.mTVname.setText(getItem(positon).getCompany_name());
        holder.mTAGline1.setTags(tags2);
        int money;
        money = (getItem(positon).getMoney_back_male() + getItem(positon).getMoney_back_famale()) / 2;
        holder.mTVbackmoney.setText(money > 0 ? String.valueOf(money) + "元" : "");
        return view;
    }

    private class ViewHolder {
        TextView mTVname, mTVbackmoney;
        TagCloudView mTAGline1;
    }
}
