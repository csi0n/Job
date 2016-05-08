package com.csi0n.searchjob.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.core.io.DbManager;
import com.csi0n.searchjob.database.dao.City;
import com.csi0n.searchjob.ui.adapter.ChooseCityListAdapter;
import com.csi0n.searchjob.ui.adapter.ChooseCityResultAdapter;
import com.csi0n.searchjob.ui.base.BaseActivity;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.csi0n.searchjob.ui.widget.LetterListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import roboguice.inject.ContentView;

/**
 * Created by csi0n on 5/7/16.
 */
@ContentView(value = R.layout.aty_choose_city)
public class ChooseCityActivity extends MvpActivity implements LetterListView.OnTouchingLetterChangedListener, BDLocationListener {
    @Bind(value = R.id.list_view)
    public ListView personList;
    @Bind(R.id.search_result)
    public ListView resultList;
    @Bind(R.id.sh)
    public EditText sh;
    @Bind(R.id.tv_noresult)
    public TextView tv_noresult;
    @Bind(R.id.MyLetterListView01)
    public LetterListView letterListView;
    public LocationClient mLocationClient;
    public BaseAdapter adapter;
    private ChooseCityResultAdapter resultListAdapter;
    private ArrayList<City> allCity_lists; // 所有城市列表
    public ArrayList<City> city_hot;
    private ArrayList<City> city_result;
    public ArrayList<String> city_history;
    public HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    public String[] sections;// 存放存在的汉语拼音首字母
    public int locateProcess = 1; // 记录当前定位的状态 正在定位-定位成功-定位失败
    public boolean isNeedFresh;
    @OnItemClick(value = R.id.list_view)
    void onItemClick(int position) {
        if (position >= 4) {
            EventBus.getDefault().post(allCity_lists.get(position));
            finish();
        }
    }

    @OnItemClick(value = R.id.search_result)
    void onItemClick2(int position) {
        EventBus.getDefault().post(city_result.get(position));
        finish();
    }

    @OnTextChanged(value = R.id.sh)
    void onTextChanged(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            letterListView.setVisibility(View.VISIBLE);
            personList.setVisibility(View.VISIBLE);
            resultList.setVisibility(View.GONE);
            tv_noresult.setVisibility(View.GONE);
        } else {
            city_result.clear();
            letterListView.setVisibility(View.GONE);
            personList.setVisibility(View.GONE);
            searchCityByKey(text.toString());
            if (city_result.size() <= 0) {
                tv_noresult.setVisibility(View.VISIBLE);
                resultList.setVisibility(View.GONE);
            } else {
                tv_noresult.setVisibility(View.GONE);
                resultList.setVisibility(View.VISIBLE);
                resultListAdapter.notifyDataSetChanged();
            }
        }
    }
    public String currentCity; // 用于保存定位到的城市

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChooseCity();
    }

    private void initChooseCity() {
        allCity_lists = new ArrayList<>();
        city_hot = new ArrayList<>();
        city_result = new ArrayList<>();
        city_history = new ArrayList<String>();
        letterListView.setOnTouchingLetterChangedListener(this);
        alphaIndexer = new HashMap<String, Integer>();
        isNeedFresh = true;
        locateProcess = 1;
        personList.setAdapter(adapter);
        resultListAdapter = new ChooseCityResultAdapter(this, city_result);
        resultList.setAdapter(resultListAdapter);
        cityInit();
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(this);
        InitLocation();
        mLocationClient.start();
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "请选择城市";
        super.setActionBarRes(actionBarRes);
    }

    private void searchCityByKey(String key) {
        city_result.addAll(DbManager.searchCityByPinYin(key));
        Collections.sort(city_result, comparator);
    }

    /**
     * a-z排序
     */
    @SuppressWarnings("rawtypes")
    private Comparator comparator = new Comparator<City>() {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    private void cityInit() {
        // 当前定位城市
        City city = new City(1024l, "定位", "dingwei");
        allCity_lists.add(city);
        City city1 = new City(1024l, "最近", "zuijin");
        allCity_lists.add(city1);
        City city2 = new City(1024l, "热门", "remen");
        allCity_lists.add(city2);
        City city3 = new City(1024l, "全部", "quanbu");
        allCity_lists.add(city3);
        allCity_lists.addAll(getCityListFromLocal());
        hotCityInit();
        setAdapter(allCity_lists, city_hot, city_history);
    }

    private List<City> getCityListFromLocal() {
        return DbManager.getAllCity();
    }

    public void hotCityInit() {
        List<City> cities = getCityListFromLocal();
        for (int i = 0; i < (cities.size() >= 3 ? 3 : cities.size()); i++) {
            city_hot.add(cities.get(i));
        }
    }

    private void setAdapter(List<City> list, List<City> hotList,
                            List<String> hisCity) {
        adapter = new ChooseCityListAdapter(this, list, hotList, hisCity);
        personList.setAdapter(adapter);
    }

    public void InitLocation() {
        // 设置定位参数
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(10000); // 10分钟扫描1次
        // 需要地址信息，设置为其他任何值（string类型，且不能为null）时，都表示无地址信息。
        option.setAddrType("all");
        // 设置是否返回POI的电话和地址等详细信息。默认值为false，即不返回POI的电话和地址信息。
        option.setPoiExtraInfo(true);
        // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setProdName("通过GPS定位我当前的位置");
        // 禁用启用缓存定位数据
        option.disableCache(true);
        // 设置最多可返回的POI个数，默认值为3。由于POI查询比较耗费流量，设置最多返回的POI个数，以便节省流量。
        option.setPoiNumber(3);
        // 设置定位方式的优先级。
        // 当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。如果gps不可用，再发起网络请求，进行定位。
        option.setPriority(LocationClientOption.GpsFirst);
        mLocationClient.setLocOption(option);
    }

    // 获得汉语拼音首字母
    public String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else if (str.equals("0")) {
            return "定位";
        } else if (str.equals("1")) {
            return "最近";
        } else if (str.equals("2")) {
            return "热门";
        } else if (str.equals("3")) {
            return "全部";
        } else {
            return "#";
        }
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        if (alphaIndexer.get(s) != null) {
            int position = alphaIndexer.get(s);
            personList.setSelection(position);
        }
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (!isNeedFresh) {
            return;
        }
        isNeedFresh = false;
        if (bdLocation.getCity() == null) {
            locateProcess = 3; // 定位失败
            if (adapter != null) {
                personList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                isNeedFresh = true;
            }
            return;
        }
        currentCity = bdLocation.getCity().substring(0, bdLocation.getCity().length() - 1);
        locateProcess = 2; // 定位成功
        personList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onReceivePoi(BDLocation bdLocation) {

    }
}
