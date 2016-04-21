package com.csi0n.searchjob.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ChooseCityController;
import com.csi0n.searchjob.model.CityModel;
import com.csi0n.searchjob.ui.activity.ChooseCityActivity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by chqss on 2016/4/20 0020.
 */
public class ChooseCityListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<CityModel> list;
    private List<CityModel> hotList;
    private List<String> hisCity;
    final int VIEW_TYPE = 5;
    private ChooseCityController mChooseCityController;
    private ChooseCityActivity mChooseCityActivity;

    public ChooseCityListAdapter(ChooseCityController chooseCityController, ChooseCityActivity chooseCityActivity, List<CityModel> list,
                                 List<CityModel> hotList, List<String> hisCity) {
        this.inflater = LayoutInflater.from(chooseCityActivity);
        this.list = list;
        this.hotList = hotList;
        this.hisCity = hisCity;
        this.mChooseCityController = chooseCityController;
        this.mChooseCityActivity = chooseCityActivity;
        mChooseCityController.alphaIndexer = new HashMap<String, Integer>();
        mChooseCityController.sections = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            // 当前汉语拼音首字母
            String currentStr = mChooseCityController.getAlpha(list.get(i).getPinyin());
            // 上一个汉语拼音首字母，如果不存在为" "
            String previewStr = (i - 1) >= 0 ? mChooseCityController.getAlpha(list.get(i - 1)
                    .getPinyin()) : " ";
            if (!previewStr.equals(currentStr)) {
                String name = mChooseCityController.getAlpha(list.get(i).getPinyin());
                mChooseCityController.alphaIndexer.put(name, i);
                mChooseCityController.sections[i] = name;
            }
        }
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public int getItemViewType(int position) {
        return position < 4 ? position : 4;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView city;
        int viewType = getItemViewType(position);
        if (viewType == 0) { // 定位
            convertView = inflater.inflate(R.layout.view_choose_city_frist_list_item, null);
            TextView locateHint = (TextView) convertView
                    .findViewById(R.id.locateHint);
            city = (TextView) convertView.findViewById(R.id.lng_city);
            city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mChooseCityController.locateProcess == 2) {
                        Toast.makeText(mChooseCityActivity.getApplicationContext(),
                                city.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    } else if (mChooseCityController.locateProcess == 3) {
                        mChooseCityController.locateProcess = 1;
                        mChooseCityActivity.personList.setAdapter(mChooseCityController.adapter);
                        mChooseCityController.adapter.notifyDataSetChanged();
                        mChooseCityController.mLocationClient.stop();
                        mChooseCityController.isNeedFresh = true;
                        mChooseCityController.InitLocation();
                        mChooseCityController.currentCity = "";
                        mChooseCityController.mLocationClient.start();
                    }
                }
            });
            ProgressBar pbLocate = (ProgressBar) convertView
                    .findViewById(R.id.pbLocate);
            if (mChooseCityController.locateProcess == 1) { // 正在定位
                locateHint.setText("正在定位");
                city.setVisibility(View.GONE);
                pbLocate.setVisibility(View.VISIBLE);
            } else if (mChooseCityController.locateProcess == 2) { // 定位成功
                locateHint.setText("当前定位城市");
                city.setVisibility(View.VISIBLE);
                city.setText(mChooseCityController.currentCity);
                mChooseCityController.mLocationClient.stop();
                pbLocate.setVisibility(View.GONE);
            } else if (mChooseCityController.locateProcess == 3) {
                locateHint.setText("未定位到城市,请选择");
                city.setVisibility(View.VISIBLE);
                city.setText("重新选择");
                pbLocate.setVisibility(View.GONE);
            }
        } else if (viewType == 1) { // 最近访问城市
            convertView = inflater.inflate(R.layout.view_choose_city_recent_city, null);
            GridView rencentCity = (GridView) convertView
                    .findViewById(R.id.recent_city);
            rencentCity
                    .setAdapter(new ChooseCityHitCityAdapter(mChooseCityActivity, this.hisCity));
            rencentCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(mChooseCityActivity.getApplicationContext(),
                            mChooseCityController.city_history.get(position), Toast.LENGTH_SHORT)
                            .show();

                }
            });
            TextView recentHint = (TextView) convertView
                    .findViewById(R.id.recentHint);
            recentHint.setText("最近访问的城市");
        } else if (viewType == 2) {
            convertView = inflater.inflate(R.layout.view_choose_city_recent_city, null);
            GridView hotCity = (GridView) convertView
                    .findViewById(R.id.recent_city);
            hotCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Toast.makeText(mChooseCityActivity.getApplicationContext(),
                            mChooseCityController.city_hot.get(position).getCity(),
                            Toast.LENGTH_SHORT).show();

                }
            });
            hotCity.setAdapter(new ChooseCityHotCityAdapter(mChooseCityActivity, this.hotList));
            TextView hotHint = (TextView) convertView
                    .findViewById(R.id.recentHint);
            hotHint.setText("热门城市");
        } else if (viewType == 3) {
            convertView = inflater.inflate(R.layout.view_choose_city_total_item, null);
        } else {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.view_choose_city_list_item, null);
                holder = new ViewHolder();
                holder.alpha = (TextView) convertView
                        .findViewById(R.id.alpha);
                holder.name = (TextView) convertView
                        .findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position >= 1) {
                holder.name.setText(list.get(position).getCity());
            }
        }
        return convertView;
    }

    private class ViewHolder {
        TextView alpha; // 首字母标题
        TextView name; // 城市名字
    }
}
