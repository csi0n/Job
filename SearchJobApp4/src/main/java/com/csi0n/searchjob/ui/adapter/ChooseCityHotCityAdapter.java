package com.csi0n.searchjob.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.database.dao.City;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chqss on 2016/4/20 0020.
 */
public class ChooseCityHotCityAdapter extends BaseAdapter {
    private Context mContext;
    private List<City> hotCitys;

    public ChooseCityHotCityAdapter(Context context, List<City> hotCitys) {
        this.mContext = context;
        this.hotCitys = hotCitys;
    }

    @Override
    public int getCount() {
        return hotCitys.size();
    }

    @Override
    public City getItem(int position) {
        return hotCitys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null) {
            convertView = View.inflate(mContext, R.layout.view_choose_city_item_city, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.city.setText(getItem(position).getName());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.city)
        TextView city;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
