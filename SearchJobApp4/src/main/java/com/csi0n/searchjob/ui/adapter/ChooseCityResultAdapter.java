package com.csi0n.searchjob.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.database.dao.City;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by csi0n on 5/7/16.
 */
public class ChooseCityResultAdapter extends BaseAdapter {
    private Context mContext;

    private ArrayList<City> cityArrayList;

    public ChooseCityResultAdapter(Context mContext, ArrayList<City> cities) {
        this.mContext = mContext;
        this.cityArrayList = cities;
    }

    @Override
    public int getCount() {
        return cityArrayList.size();
    }

    @Override
    public City getItem(int i) {
        return cityArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.view_choose_city_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(view);
        } else
            holder = (ViewHolder) view.getTag();
        holder.name.setText(getItem(i).getName());
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.name)
        TextView name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
