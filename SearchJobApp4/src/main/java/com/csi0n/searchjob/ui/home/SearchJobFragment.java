package com.csi0n.searchjob.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.event.ext.ChooseCityEvent;
import com.csi0n.searchjob.business.pojo.model.ext.AreaModel;
import com.csi0n.searchjob.business.pojo.model.ext.CityAndAreaListModel;
import com.csi0n.searchjob.business.pojo.model.ext.FuliModel;
import com.csi0n.searchjob.business.pojo.model.ext.JobTypeModel;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.core.io.DbManager;
import com.csi0n.searchjob.core.io.SharePreferenceManager;
import com.csi0n.searchjob.core.log.CLog;
import com.csi0n.searchjob.database.dao.Area;
import com.csi0n.searchjob.database.dao.City;
import com.csi0n.searchjob.database.dao.FuLi;
import com.csi0n.searchjob.database.dao.JobType;
import com.csi0n.searchjob.ui.ChooseCityActivity;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;
import com.csi0n.searchjob.ui.widget.tabview.view.ExpandTabView;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewAreaList;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewBackMoney;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewJob;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewWelfare;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/5/1 0001.
 */
public class SearchJobFragment extends MvpFragment<SearchJobPresenter, SearchJobPresenter.ISearchJobView>implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @Bind(R.id.tv_location)
    TextView mLocation;
    @Bind(value = R.id.expandtab_view)
    private ExpandTabView mExpandTabView;
    @Bind(value = R.id.refreshLayout)
    private BGARefreshLayout mBGARefreshLayout;
    @Bind(value = R.id.list)
    private ListView mList;
    private ViewAreaList viewAreaList;
    private ViewBackMoney viewBackMoney;
    private ViewJob viewJob;
    private ViewWelfare viewWelfare;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    @OnClick({R.id.tv_location}) void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                startChooseCity();
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

    private void init() {
        SharePreferenceManager.setFlagIsFirstStartSearchJobFragment(true);
        SharePreferenceManager.setFlagLocalConfigVersion("0");
        if (SharePreferenceManager.getFlagIsFirstStartSearchJobFragment()) {
            updateLocalConfig(new IUpdateConfigStatus() {
                @Override
                public void success() {
                    CLog.i("Config Update Success!");
                }

                @Override
                public void failed() {
                    showError("配置文件更新出错,无法继续!");
                }
            });
        }
    }

    public void saveConfig(GetConfigResponse response) {
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

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
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
            //mSearchJobController.onRefresh(positio, area, jobTypeBean, strings);
        }
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
    @Subscribe
    public void onEvent(ChooseCityEvent object) {

    }
}
