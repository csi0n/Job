package com.csi0n.searchjob.ui.home;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.app.App;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.model.ext.AreaModel;
import com.csi0n.searchjob.business.pojo.model.ext.CityAndAreaListModel;
import com.csi0n.searchjob.business.pojo.model.ext.FuliModel;
import com.csi0n.searchjob.business.pojo.model.ext.JobTypeModel;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.core.io.SharePreferenceManager;
import com.csi0n.searchjob.database.dao.Area;
import com.csi0n.searchjob.database.dao.AreaDao;
import com.csi0n.searchjob.database.dao.City;
import com.csi0n.searchjob.database.dao.CityDao;
import com.csi0n.searchjob.database.dao.DaoSession;
import com.csi0n.searchjob.database.dao.FuLi;
import com.csi0n.searchjob.database.dao.FuLiDao;
import com.csi0n.searchjob.database.dao.JobType;
import com.csi0n.searchjob.database.dao.JobTypeDao;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;
/**
 * Created by chqss on 2016/5/1 0001.
 */
public class SearchJobFragment extends MvpFragment<SearchJobPresenter,SearchJobPresenter.ISearchJobView> {
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
       /* SharePreferenceManager.setFlagIsFirstStartSearchJobFragment(true);
        SharePreferenceManager.setFlagLocalConfigVersion("0");*/
        if (SharePreferenceManager.getFlagIsFirstStartSearchJobFragment()) {
            presenter.doGetConfig().subscribe(new AdvancedSubscriber<GetConfigResponse>(){
                @Override
                public void onHandleSuccess(GetConfigResponse response) {
                    super.onHandleSuccess(response);
                    saveConfig(response);
                    SharePreferenceManager.setFlagIsFirstStartSearchJobFragment(false);
                    SharePreferenceManager.setFlagLocalConfigVersion(response.version);
                }
            });
        }
    }

    public void saveConfig(GetConfigResponse response) {
        if (!SharePreferenceManager.getFlagLocalConfigVersion().equals(response.version)) {
            dropAllTable();
            createAllTable();
            for (CityAndAreaListModel cityAndAreaItem : response.cityAndAreaLists) {
                for (AreaModel area : cityAndAreaItem.area) {
                    Area are = new Area(area.id, area.area, area.city_id);
                    getDaoSession().getAreaDao().insert(are);
                }
                City city = new City(cityAndAreaItem.city.id, cityAndAreaItem.city.city);
                getDaoSession().getCityDao().insert(city);
            }
            for (FuliModel fuli_model : response.fuLis) {
                FuLi fuli = new FuLi(fuli_model.id, fuli_model.name);
                getDaoSession().getFuLiDao().insert(fuli);
            }
            for (JobTypeModel job_type_model : response.jobTypes) {
                JobType job_type = new JobType(job_type_model.id, job_type_model.name);
                getDaoSession().getJobTypeDao().insert(job_type);
            }
        }
    }

    public void dropAllTable() {
        AreaDao.dropTable(getDb(), true);
        CityDao.dropTable(getDb(), true);
        FuLiDao.dropTable(getDb(), true);
        JobTypeDao.dropTable(getDb(), true);
    }

    public void createAllTable() {
        AreaDao.createTable(getDb(),true);
        CityDao.createTable(getDb(),true);
        FuLiDao.createTable(getDb(),true);
        JobTypeDao.createTable(getDb(),true);
    }
    public DaoSession getDaoSession() {
        return ((App) getActivity().getApplicationContext()).getDaoSession();
    }

    public SQLiteDatabase getDb() {
        return ((App) getActivity().getApplicationContext()).getDb();
    }
}
