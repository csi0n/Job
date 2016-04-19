package com.csi0n.searchjob.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.utils.bean.City;
import com.csi0n.searchjob.utils.bean.CityBean;

import java.util.ArrayList;

/**
 * Created by csi0n on 12/17/15.
 */
public class ChooseCityResultAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<City> results = new ArrayList<>();

    public ChooseCityResultAdapter(Context context, ArrayList<City> results) {
        inflater = LayoutInflater.from(context);
        this.results = results;
    }

    @Override
    public int getCount() {
        return results.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_choose_city_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView
                    .findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(results.get(position).getCity());
        return convertView;
    }

    class ViewHolder {
        TextView name;
    }
}
