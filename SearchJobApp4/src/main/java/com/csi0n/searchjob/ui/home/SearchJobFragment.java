package com.csi0n.searchjob.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.event.ext.ChooseCityEvent;
import com.csi0n.searchjob.business.pojo.model.ext.AreaModel;
import com.csi0n.searchjob.business.pojo.model.ext.CityAndAreaListModel;
import com.csi0n.searchjob.business.pojo.model.ext.FuliModel;
import com.csi0n.searchjob.business.pojo.model.ext.JobTypeModel;
import com.csi0n.searchjob.business.pojo.response.ext.GetCompanyJobMainResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.core.io.DbManager;
import com.csi0n.searchjob.core.io.SharePreferenceManager;
import com.csi0n.searchjob.core.log.CLog;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.database.dao.Area;
import com.csi0n.searchjob.database.dao.City;
import com.csi0n.searchjob.database.dao.FuLi;
import com.csi0n.searchjob.database.dao.JobType;
import com.csi0n.searchjob.ui.ChooseCityActivity;
import com.csi0n.searchjob.ui.SearchJobActivity;
import com.csi0n.searchjob.ui.adapter.SearchJobFragmentAdapter;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;
import com.csi0n.searchjob.ui.widget.tabview.view.ExpandTabView;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewAreaList;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewBackMoney;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewJob;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewWelfare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/5/1 0001.
 */
public class SearchJobFragment extends MvpFragment<SearchJobPresenter, SearchJobPresenter.ISearchJobView>implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @Bind(R.id.tv_location)
    TextView mLocation;
    @Bind(value = R.id.expandtab_view)
    ExpandTabView mExpandTabView;
    @Bind(value = R.id.refreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @Bind(value = R.id.list)
    ListView mList;
    private ViewAreaList viewAreaList;
    private ViewBackMoney viewBackMoney;
    private ViewJob viewJob;
    private ViewWelfare viewWelfare;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private boolean is_busy = false;
    private int CURRENT_PAGE = 1;
    private int TEMP_COUNT = 0;
    private long AREA_ID = -1;
    private long CITY_ID = -1;
    private int MONEY_BACK = -1;
    private int WORK_TYPE = -1;
    private List<FuLi> FULI_LIST = null;
    private SearchJobFragmentAdapter adapter;

    @OnClick({R.id.tv_location,R.id.tv_search_job}) void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                startChooseCity();
                break;
            case R.id.tv_search_job:
                startSearchJobActivity();
                break;
            default:
                break;
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.frag_search_job;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    void init() {
        mBGARefreshLayout.setDelegate(this);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mvpActivity, true));
        adapter = new SearchJobFragmentAdapter(mvpActivity);
        mList.setAdapter(adapter);
        SharePreferenceManager.setFlagIsFirstStartSearchJobFragment(true);
        SharePreferenceManager.setFlagLocalConfigVersion("0");
        if (SharePreferenceManager.getFlagIsFirstStartSearchJobFragment()) {
            updateLocalConfig(new IUpdateConfigStatus() {
                @Override
                public void success() {
                    CLog.i("Config Update Success!");
                    initTop(DbManager.getTopCity(), DbManager.getAllJobType(), DbManager.getAllFuLi());
                    getJobList(CURRENT_PAGE);
                }

                @Override
                public void failed() {
                    showError("配置文件更新出错,无法继续!");
                }
            });
        }
    }

    void saveConfig(GetConfigResponse response) {
        if (TextUtils.isEmpty(response.version))
            return;
        if (!SharePreferenceManager.getFlagLocalConfigVersion().equals(response.version)) {
            DbManager.dropAllTable();
            DbManager.createAllTable();
            for (CityAndAreaListModel cityAndAreaItem : response.cityAndAreaLists) {
                for (AreaModel area : cityAndAreaItem.area) {
                    Area are = new Area(area.id, area.area, area.pinyin, area.city_id);
                    DbManager.insertArea(are);
                }
                City city = new City(cityAndAreaItem.city.id, cityAndAreaItem.city.city, cityAndAreaItem.city.pinyin);
                DbManager.insertCity(city);
            }
            for (FuliModel fuli_model : response.fuLis) {
                FuLi fuli = new FuLi(fuli_model.id, fuli_model.name);
                DbManager.insertFuLi(fuli);
            }
            for (JobTypeModel job_type_model : response.jobTypes) {
                JobType job_type = new JobType(job_type_model.id, job_type_model.name);
                DbManager.insertJobType(job_type);
            }
        }
    }

    void getJobList(final int page) {
        String fuliTemp = null;
        if (FULI_LIST != null && FULI_LIST.size() > 0) {
            for (int i = 0; i < FULI_LIST.size(); i++) {
                if (i == 0)
                    fuliTemp = String.valueOf(FULI_LIST.get(i).getFid());
                else
                    fuliTemp = fuliTemp + "," + FULI_LIST.get(i).getFid();
            }
        }
        presenter.getSearJobList(page, CITY_ID, AREA_ID, MONEY_BACK, WORK_TYPE, fuliTemp, SharePreferenceManager.getFlagLocalConfigVersion()).subscribe(new AdvancedSubscriber<GetCompanyJobMainResponse>() {
            @Override
            public void onHandleSuccess(GetCompanyJobMainResponse response) {
                super.onHandleSuccess(response);
                if (page == 1)
                    adapter.datas.clear();
                TEMP_COUNT = response.companyJobs.length;
                adapter.datas.addAll(Arrays.asList(response.companyJobs));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onHandleFail(String message, Throwable throwable) {
                super.onHandleFail(message, throwable);
                TEMP_COUNT = 0;
            }

            @Override
            public void onHandleEmptyData() {
                super.onHandleEmptyData();
                TEMP_COUNT = 0;
                if (page == 1) {
                    adapter.datas.clear();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onHandleFinish() {
                super.onHandleFinish();
                endRefresh();
            }
        });
    }
    void updateLocalConfig(final IUpdateConfigStatus iUpdateConfigStatus) {
        presenter.doGetConfig().subscribe(new AdvancedSubscriber<GetConfigResponse>() {
                                              @Override
                                              public void onHandleSuccess(GetConfigResponse response) {
                                                  super.onHandleSuccess(response);
                                                  saveConfig(response);
                                                  SharePreferenceManager.setFlagIsFirstStartSearchJobFragment(false);
                                                  SharePreferenceManager.setFlagLocalConfigVersion(response.version);
                                                  iUpdateConfigStatus.success();
                                              }

                                              @Override
                                              public void onHandleFail(String message, Throwable throwable) {
                                                  super.onHandleFail(message, throwable);
                                                  iUpdateConfigStatus.failed();
                                              }
                                          }
        );
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            CURRENT_PAGE = 1;
            getJobList(CURRENT_PAGE);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT >= Constants.DEFAULT_PAGE) {
                CURRENT_PAGE++;
                getJobList(CURRENT_PAGE);
                return false;
            }
        }
        return true;
    }

    void endRefresh(){
        mBGARefreshLayout.endLoadingMore();
        mBGARefreshLayout.endRefreshing();
    }
    private interface IUpdateConfigStatus {
        void success();

        void failed();
    }
     void initTop(City cityBean, List<JobType> jobTypeBeenList, List<FuLi> fuliBeanList){
        ArrayList<String> mTextArray = new ArrayList<String>();
        viewAreaList = new ViewAreaList(mvpActivity);
        mLocation.setText(cityBean.getName());
        final Area area = new Area();
         area.setAid(Long.valueOf(-1));
         area.setCid(Long.valueOf(-1));
        area.setName("区域不限");
        viewAreaList.areaDatas.add(area);
        viewAreaList.areaDatas.addAll(cityBean.getAreas());
        viewAreaList.setAdapterChange();
        mViewArray.add(viewAreaList);
        mTextArray.add(viewAreaList.areaDatas.get(0).getName());
        viewBackMoney = new ViewBackMoney(mvpActivity);

        List<String> backmoney = new ArrayList<>();
        backmoney.add("返现不限");
        backmoney.add("男返");
        backmoney.add("女返");
        viewBackMoney.setAdapterData(backmoney);
        mViewArray.add(viewBackMoney);
        mTextArray.add(backmoney.get(0));

        viewJob = new ViewJob(mvpActivity);
        JobType jobTypeBean = new JobType();
        jobTypeBean.setJid(-1);
        jobTypeBean.setName("工作不限");
        viewJob.jobDatas.add(jobTypeBean);
        viewJob.jobDatas.addAll(jobTypeBeenList);
        viewJob.setAdapterChange();
        mViewArray.add(viewJob);
        mTextArray.add(viewJob.jobDatas.get(0).getName());

        viewWelfare = new ViewWelfare(mvpActivity);
        viewWelfare.fuliBeanList.addAll(fuliBeanList);
        viewWelfare.setAdapterChange();
        mViewArray.add(viewWelfare);
        mTextArray.add(viewWelfare.fuliBeanList.get(0).getName());

        mExpandTabView.setValue(mTextArray, mViewArray);
        mExpandTabView.setTitle(viewAreaList.areaDatas.get(0).getName(), 0);
        mExpandTabView.setTitle(backmoney.get(0), 1);
        mExpandTabView.setTitle(viewJob.jobDatas.get(0).getName(), 2);
        mExpandTabView.setTitle("福利不限", 3);
        viewAreaList.setOnSelectListener(new ViewAreaList.OnSelectListener() {
            @Override
            public void getValue(int position, Area showText) {
                onRefresh(viewAreaList, -1, showText.getName(), showText, null, null);
            }
        });
        viewBackMoney.setOnSelectListener(new ViewBackMoney.OnSelectListener() {
            @Override
            public void getValue(int position, String showText) {
                onRefresh(viewBackMoney, position, showText, null, null, null);
            }
        });
        viewJob.setOnSelectListener(new ViewJob.OnSelectListener() {
            @Override
            public void getValue(int position, JobType showText) {
                onRefresh(viewJob, -1, showText.getName(), null, showText, null);
            }
        });
        viewWelfare.setOnSelectListener(new ViewWelfare.OnSelectListener() {
            @Override
            public void getValue(List<FuLi> datas) {
                if (datas.size() == 0)
                    onRefresh(viewWelfare, -1, "福利不限", null, null, datas);
                else
                    onRefresh(viewWelfare, -1, null, null, null, datas);
            }
        });
    }
     void onRefresh(View view, int positio, String showText, Area area, JobType jobTypeBean, List<FuLi> strings) {
        mExpandTabView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !mExpandTabView.getTitle(position).equals(showText)) {
            if (showText != null)
                mExpandTabView.setTitle(showText, position);
            onRefresh(positio, area, jobTypeBean, strings);
        }
     }

    void onRefresh(int positon, Area area, JobType jobTypeBean, List<FuLi> strings) {
        if (positon >= 0)
            MONEY_BACK = positon;
        if (area != null) {
            AREA_ID = -1;
            CITY_ID = -1;
            if (area.getAid() != -1)
                AREA_ID = area.getAid();
            else
                CITY_ID = area.getCid();
        }
        if (jobTypeBean != null && jobTypeBean.getJid() != -1)
            WORK_TYPE = jobTypeBean.getJid();
        if (strings != null)
            FULI_LIST = strings;
        CURRENT_PAGE = 1;
        getJobList(CURRENT_PAGE);
    }
     int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }
     void startChooseCity(){
        startActivity(ChooseCityActivity.class);
    }

    @Override
    public void onEvent(Object object) {
        super.onEvent(object);
        if (object.getClass() == ChooseCityEvent.class) {
            ChooseCityEvent chooseCityEvent = ((ChooseCityEvent) object);
            setLocationText(chooseCityEvent.chooseCity.getName());
            resetAreaDatas(chooseCityEvent.chooseCity.getAreas());
            setDefaultPostDatas(chooseCityEvent.chooseCity.getCid().intValue());
            CURRENT_PAGE = 1;
            getJobList(CURRENT_PAGE);
        }
    }

    void setDefaultPostDatas(int city_id) {
        CITY_ID = city_id;
        AREA_ID = -1;
        MONEY_BACK = -1;
        WORK_TYPE = -1;
        FULI_LIST = null;
    }

    void resetAreaDatas(List<Area> areaModelList) {
        viewAreaList.areaDatas.clear();
        viewAreaList.areaDatas.addAll(areaModelList);
        viewAreaList.setAdapterChange();
    }

    void setLocationText(String location) {
        mLocation.setText(location);
    }
    void startSearchJobActivity(){
        startActivity(SearchJobActivity.class);
    }
}
