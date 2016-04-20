package com.csi0n.searchjob.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.model.CityModel;

import java.util.List;

/**
 * Created by chqss on 2016/4/20 0020.
 */
public class ChooseCityHotCityAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<CityModel> hotCitys;

    public ChooseCityHotCityAdapter(Context context, List<CityModel> hotCitys) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.hotCitys = hotCitys;
    }

    @Override
    public int getCount() {
        return hotCitys.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.view_choose_city_item_city, null);
        TextView city = (TextView) convertView.findViewById(R.id.city);
        city.setText(hotCitys.get(position).getCity());
        return convertView;
    }
}
