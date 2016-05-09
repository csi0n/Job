package com.csi0n.searchjob.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.database.dao.City;
import com.csi0n.searchjob.ui.ChooseCityActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by csi0n on 5/7/16.
 */
public class ChooseCityListAdapter extends BaseAdapter {
    private ChooseCityActivity mChooseCityActivity;
    private List<City> list;
    private List<City> hotList;
    private List<String> hisCity;
    final int VIEW_TYPE = 5;

    public ChooseCityListAdapter(ChooseCityActivity mChooseCityActivity, List<City> list, List<City> hotList, List<String> hisCity) {
        this.mChooseCityActivity = mChooseCityActivity;
        this.list = list;
        this.hotList = hotList;
        this.hisCity = hisCity;
        mChooseCityActivity.alphaIndexer = new HashMap<String, Integer>();
        mChooseCityActivity.sections = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            // 当前汉语拼音首字母
            String currentStr = mChooseCityActivity.getAlpha(list.get(i).getPinyin());
            // 上一个汉语拼音首字母，如果不存在为" "
            String previewStr = (i - 1) >= 0 ? mChooseCityActivity.getAlpha(list.get(i - 1)
                    .getPinyin()) : " ";
            if (!previewStr.equals(currentStr)) {
                String name = mChooseCityActivity.getAlpha(list.get(i).getPinyin());
                mChooseCityActivity.alphaIndexer.put(name, i);
                mChooseCityActivity.sections[i] = name;
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
    public City getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
ViewHolder holder;
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final TextView city;
        int viewType = getItemViewType(position);
        if (viewType == 0) { // 定位
            convertView = View.inflate(mChooseCityActivity, R.layout.view_choose_city_frist_list_item, null);
            TextView locateHint = (TextView) convertView
                    .findViewById(R.id.locateHint);
            city = (TextView) convertView.findViewById(R.id.lng_city);
            city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mChooseCityActivity.locateProcess == 2) {
                        Toast.makeText(mChooseCityActivity.getApplicationContext(),
                                city.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    } else if (mChooseCityActivity.locateProcess == 3) {
                        mChooseCityActivity.locateProcess = 1;
                        mChooseCityActivity.personList.setAdapter(mChooseCityActivity.adapter);
                        mChooseCityActivity.adapter.notifyDataSetChanged();
                        mChooseCityActivity.mLocationClient.stop();
                        mChooseCityActivity.isNeedFresh = true;
                        mChooseCityActivity.InitLocation();
                        mChooseCityActivity.currentCity = "";
                        mChooseCityActivity.mLocationClient.start();
                    }
                }
            });
            ProgressBar pbLocate = (ProgressBar) convertView
                    .findViewById(R.id.pbLocate);
            if (mChooseCityActivity.locateProcess == 1) { // 正在定位
                locateHint.setText("正在定位");
                city.setVisibility(View.GONE);
                pbLocate.setVisibility(View.VISIBLE);
            } else if (mChooseCityActivity.locateProcess == 2) { // 定位成功
                locateHint.setText("当前定位城市");
                city.setVisibility(View.VISIBLE);
                city.setText(mChooseCityActivity.currentCity);
                mChooseCityActivity.mLocationClient.stop();
                pbLocate.setVisibility(View.GONE);
            } else if (mChooseCityActivity.locateProcess == 3) {
                locateHint.setText("未定位到城市,请选择");
                city.setVisibility(View.VISIBLE);
                city.setText("重新选择");
                pbLocate.setVisibility(View.GONE);
            }
        } else if (viewType == 1) { // 最近访问城市
            convertView = View.inflate(mChooseCityActivity, R.layout.view_choose_city_recent_city, null);
            GridView rencentCity = (GridView) convertView
                    .findViewById(R.id.recent_city);
            rencentCity
                    .setAdapter(new ChooseCityHitCityAdapter(mChooseCityActivity, this.hisCity));
            rencentCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(mChooseCityActivity.getApplicationContext(),
                            mChooseCityActivity.city_history.get(position), Toast.LENGTH_SHORT)
                            .show();
                }
            });
            TextView recentHint = (TextView) convertView
                    .findViewById(R.id.recentHint);
            recentHint.setText("最近访问的城市");
        } else if (viewType == 2) {
            convertView = View.inflate(mChooseCityActivity, R.layout.view_choose_city_recent_city, null);
            GridView hotCity = (GridView) convertView
                    .findViewById(R.id.recent_city);
            hotCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(mChooseCityActivity.getApplicationContext(),
                            mChooseCityActivity.city_hot.get(position).getName(),
                            Toast.LENGTH_SHORT).show();

                }
            });
            hotCity.setAdapter(new ChooseCityHotCityAdapter(mChooseCityActivity, this.hotList));
            TextView hotHint = (TextView) convertView
                    .findViewById(R.id.recentHint);
            hotHint.setText("热门城市");
        } else if (viewType == 3) {
            convertView = View.inflate(mChooseCityActivity, R.layout.view_choose_city_total_item, null);
        } else {
            if (convertView == null) {
                convertView = View.inflate(mChooseCityActivity, R.layout.view_choose_city_list_item, null);
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
                holder.name.setText(list.get(position).getName());
            }
        }
        return convertView;
    }


    private class ViewHolder {
        TextView alpha; // 首字母标题
        TextView name; // 城市名字
    }
}
