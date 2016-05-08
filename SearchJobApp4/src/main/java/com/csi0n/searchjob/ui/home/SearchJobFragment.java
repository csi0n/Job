package com.csi0n.searchjob.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
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
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;

/**
 * Created by chqss on 2016/5/1 0001.
 */
public class SearchJobFragment extends MvpFragment<SearchJobPresenter, SearchJobPresenter.ISearchJobView> {
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
                    Area are = new Area(area.id, area.area,area.pinyin, area.city_id);
                    DbManager.insertArea(are);
                }
                City city = new City(cityAndAreaItem.city.id, cityAndAreaItem.city.city,cityAndAreaItem.city.pinyin);
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

    private interface IUpdateConfigStatus {
        void success();

        void failed();
    }
}
