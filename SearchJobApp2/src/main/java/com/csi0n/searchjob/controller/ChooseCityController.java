package com.csi0n.searchjob.controller;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.ChooseCityListAdapter;
import com.csi0n.searchjob.adapters.ChooseCityResultAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.ui.activity.ChooseCityActivity;
import com.csi0n.searchjob.ui.widget.LetterListView;
import com.csi0n.searchjob.utils.ProgressLoading;
import com.csi0n.searchjob.utils.bean.City;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

/**
 * Created by csi0n on 12/17/15.
 */
public class ChooseCityController extends BaseController implements BDLocationListener, LetterListView.OnTouchingLetterChangedListener {
    private ProgressLoading loading;
    private ChooseCityActivity mChooseCityActivity;

    private ArrayList<City> allCity_lists; // 所有城市列表
    public ArrayList<City> city_hot;
    private ArrayList<City> city_result;
    public ArrayList<String> city_history;
    public BaseAdapter adapter;
    private ChooseCityResultAdapter resultListAdapter;
    public int locateProcess = 1; // 记录当前定位的状态 正在定位-定位成功-定位失败
    public HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    public String[] sections;// 存放存在的汉语拼音首字母
    public boolean isNeedFresh;
    public LocationClient mLocationClient;
    public String currentCity; // 用于保存定位到的城市
    private DbManager db = x.getDb(Config.dbConfig);

    public ChooseCityController(ChooseCityActivity chooseCityActivity) {
        this.mChooseCityActivity = chooseCityActivity;
    }

    public void initChooseCity() {
        allCity_lists = new ArrayList<>();
        city_hot = new ArrayList<>();
        city_result = new ArrayList<>();
        city_history = new ArrayList<String>();
        mChooseCityActivity.sh.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString() == null || "".equals(s.toString())) {
                    mChooseCityActivity.letterListView.setVisibility(View.VISIBLE);
                    mChooseCityActivity.personList.setVisibility(View.VISIBLE);
                    mChooseCityActivity.resultList.setVisibility(View.GONE);
                    mChooseCityActivity.tv_noresult.setVisibility(View.GONE);
                } else {
                    city_result.clear();
                    mChooseCityActivity.letterListView.setVisibility(View.GONE);
                    mChooseCityActivity.personList.setVisibility(View.GONE);
                    try {
                        getResultCityList(s.toString());
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    if (city_result.size() <= 0) {
                        mChooseCityActivity.tv_noresult.setVisibility(View.VISIBLE);
                        mChooseCityActivity.resultList.setVisibility(View.GONE);
                    } else {
                        mChooseCityActivity.tv_noresult.setVisibility(View.GONE);
                        mChooseCityActivity.resultList.setVisibility(View.VISIBLE);
                        resultListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mChooseCityActivity.letterListView.setOnTouchingLetterChangedListener(this);
        alphaIndexer = new HashMap<String, Integer>();
        isNeedFresh = true;
        mChooseCityActivity.personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position >= 4) {
                    EventBus.getDefault().post(allCity_lists.get(position));
                    mChooseCityActivity.finish();
                }
            }
        });
        locateProcess = 1;
        mChooseCityActivity.personList.setAdapter(adapter);
        resultListAdapter = new ChooseCityResultAdapter(mChooseCityActivity, city_result);
        mChooseCityActivity.resultList.setAdapter(resultListAdapter);
        mChooseCityActivity.resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(city_result.get(position));
                mChooseCityActivity.finish();
            }
        });
        cityInit();

        //hisCityInit();最近访问的城市

        mLocationClient = new LocationClient(mChooseCityActivity.getApplicationContext());
        mLocationClient.registerLocationListener(this);
        InitLocation();
        mLocationClient.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                mChooseCityActivity.finish();
                break;
        }
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

    private void cityInit() {
        // 当前定位城市
        City city = new City();
        city.setCity("定位");
        city.setPinyin("dingwei");
        allCity_lists.add(city);
        City city1 = new City();
        city1.setCity("最近");
        city1.setPinyin("zuijin");// 最近访问的城市
        allCity_lists.add(city1);
        City city2 = new City();
        city2.setCity("热门");
        city2.setPinyin("remeng");// 热门城市
        allCity_lists.add(city2);
        City city3 = new City();
        city3.setCity("全部");
        city3.setPinyin("quanbu");// 全部城市
        allCity_lists.add(city3);
        try {
            allCity_lists.addAll(getCityListFromLocal());
            hotCityInit();
            setAdapter(allCity_lists, city_hot, city_history);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 热门城市
     */
    public void hotCityInit() throws DbException {
        List<City> cities = db.selector(City.class).findAll();
        for (int i = 0; i < (cities.size() >= 3 ? 3 : cities.size()); i++) {
            city_hot.add(cities.get(i));
        }
    }

    private List<City> getCityListFromLocal() throws DbException {
        return db.selector(City.class).findAll();
    }

    private void getResultCityList(String keyword) throws DbException {
        List<City> cities = db.selector(City.class).where("pinyin", "LIKE", "%" + keyword + "%").findAll();
        city_result.addAll(cities);
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

    private void setAdapter(List<City> list, List<City> hotList,
                            List<String> hisCity) {
        adapter = new ChooseCityListAdapter(this, mChooseCityActivity, list, hotList, hisCity);
        mChooseCityActivity.personList.setAdapter(adapter);
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
                mChooseCityActivity.personList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                isNeedFresh = true;
            }
            return;
        }
        currentCity = bdLocation.getCity().substring(0,
                bdLocation.getCity().length() - 1);
        locateProcess = 2; // 定位成功
        mChooseCityActivity.personList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onReceivePoi(BDLocation bdLocation) {

    }

    public void onStop() {
        mLocationClient.stop();
    }

    private boolean isScroll = false;

    @Override
    public void onTouchingLetterChanged(final String s) {
        if (alphaIndexer.get(s) != null) {
            int position = alphaIndexer.get(s);
            mChooseCityActivity.personList.setSelection(position);
        }
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
}
