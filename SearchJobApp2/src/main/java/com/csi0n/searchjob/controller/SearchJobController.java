package com.csi0n.searchjob.controller;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.SearchJobFragmentAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.bean.BaseStatusBean;
import com.csi0n.searchjob.lib.widget.EmptyLayout;
import com.csi0n.searchjob.ui.fragment.SearchJobFragment;
import com.csi0n.searchjob.utils.ProgressLoading;
import com.csi0n.searchjob.utils.SharePreferenceManager;
import com.csi0n.searchjob.utils.bean.Area;
import com.csi0n.searchjob.utils.bean.City;
import com.csi0n.searchjob.utils.bean.CityBean;
import com.csi0n.searchjob.utils.bean.CompanyJobListBean;
import com.csi0n.searchjob.utils.bean.FuliBean;
import com.csi0n.searchjob.utils.bean.JobTypeBean;
import com.csi0n.searchjob.utils.bean.SearchJonFragmentConfigBean;

import org.json.JSONException;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 2015/12/16 0016.
 */
public class SearchJobController extends BaseController implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, AdapterView.OnItemClickListener {
    private SearchJobFragment mSearchJobFragment;
    private SearchJobFragmentAdapter adapter;
    private int CURRENT_PAGE = 1;
    private int DEFAULT_CITY_ID = -1;
    private int DEFAULT_MONEY_BACK = -1;/*1.男返,2.女返,-1.全反*/
    private Area DEFAULT_AREA = null;
    private JobTypeBean DEFAULT_JOB_TYPE = null;
    private List<FuliBean> DEFAULT_FU_LI = null;
    private int TEMP_COUNT = 0;
    private boolean is_busy = false;
    private DbManager db = x.getDb(Config.dbConfig);
    private ProgressLoading loading;

    public SearchJobController(SearchJobFragment searchJobFragment) {
        this.mSearchJobFragment = searchJobFragment;
    }

    public void initSearchJob() {
        adapter = new SearchJobFragmentAdapter(mSearchJobFragment.aty);
        mSearchJobFragment.setAdapter(adapter);
        mSearchJobFragment.setEmptyLayout(EmptyLayout.HIDE_LAYOUT);
        if (SharePreferenceManager.getFlagIsFirstStartSearchJobFragment()) {
            getFragmentConfigFromNet();
        } else {
            try {
                City city = db.selector(City.class).findFirst();
                DEFAULT_CITY_ID = city.getId();
                List<Area> areas = db.selector(Area.class).where("city_id", "=", city.getId()).findAll();
                CityBean cityBean = new CityBean();
                cityBean.setCity(city);
                cityBean.setArea(areas);
                List<JobTypeBean> jobTypeBeanList = db.selector(JobTypeBean.class).findAll();
                List<FuliBean> fuliBeanList = db.selector(FuliBean.class).findAll();
                mSearchJobFragment.initTop(cityBean, jobTypeBeanList, fuliBeanList);
            } catch (DbException e) {
            }
            getJobList(CURRENT_PAGE, DEFAULT_AREA == null ? -1 : DEFAULT_AREA.getId(), DEFAULT_CITY_ID, DEFAULT_MONEY_BACK, DEFAULT_JOB_TYPE == null ? -1 : DEFAULT_JOB_TYPE.getId(), DEFAULT_FU_LI);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                mSearchJobFragment.startChooseLocation();
                break;
            case R.id.ll_search_job:
            case R.id.tv_search_job:
mSearchJobFragment.startSearchJob();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            CURRENT_PAGE = 1;
            getJobList(CURRENT_PAGE, DEFAULT_AREA == null ? -1 : DEFAULT_AREA.getId(), DEFAULT_CITY_ID, DEFAULT_MONEY_BACK, DEFAULT_JOB_TYPE == null ? -1 : DEFAULT_JOB_TYPE.getId(), DEFAULT_FU_LI);
        } else {
            CLog.show(R.string.str_thread_busy_please_wait_last_task_complete);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT >= Config.DEFAULT_PAGE) {
                CURRENT_PAGE++;
                getJobList(CURRENT_PAGE, DEFAULT_AREA == null ? -1 : DEFAULT_AREA.getId(), DEFAULT_CITY_ID, DEFAULT_MONEY_BACK, DEFAULT_JOB_TYPE == null ? -1 : DEFAULT_JOB_TYPE.getId(), DEFAULT_FU_LI);
                return false;
            } else {
                return true;
            }
        } else {
            CLog.show(R.string.str_thread_busy_please_wait_last_task_complete);
            return true;
        }
    }

    private void getFragmentConfigFromNet() {
        loading = new ProgressLoading(mSearchJobFragment.aty, "初始化...");
        loading.show();
        PostParams params = getDefaultPostParams(R.string.url_getDefaultSearchJobFragmentConfig);
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<SearchJonFragmentConfigBean>(SearchJonFragmentConfigBean.class) {
            @Override
            public void SuccessResult(final SearchJonFragmentConfigBean result) throws JSONException {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            List<CityBean> cityBeenList = result.getCity();
                            for (int i = 0; i < cityBeenList.size(); i++) {
                                CityBean cityBean = cityBeenList.get(i);
                                City city = db.selector(City.class).where("id", "=", cityBean.getCity().getId()).findFirst();
                                if (city != null) {
                                    List<Area> areas = db.selector(Area.class).where("city_id", "=", city.getId()).findAll();
                                    if (areas != null && areas.size() > 0) {
                                        CLog.getInstance().iMessage("area data is exist");
                                    } else {
                                        for (int j = 0; j < cityBean.getArea().size(); j++) {
                                            db.save(cityBean.getArea().get(j));
                                        }
                                        CLog.getInstance().iMessage("area data save complete");
                                    }
                                } else {
                                    city = cityBean.getCity();
                                    db.save(city);
                                    CLog.getInstance().iMessage("city data save complete");
                                    List<Area> areas = db.selector(Area.class).where("city_id", "=", city.getId()).findAll();
                                    if (areas != null && areas.size() > 0) {
                                        CLog.getInstance().iMessage("area data is exist");
                                    } else {
                                        for (int j = 0; j < cityBean.getArea().size(); j++) {
                                            db.save(cityBean.getArea().get(j));
                                        }
                                        CLog.getInstance().iMessage("area data save complete");
                                    }
                                }
                            }
                            List<FuliBean> fuliBeanList = result.getFu_li();
                            for (int i = 0; i < fuliBeanList.size(); i++) {
                                FuliBean fuliBean = fuliBeanList.get(i);
                                FuliBean fuliBean_temp = db.selector(FuliBean.class).where("id", "=", fuliBean.getId()).findFirst();
                                if (fuliBean_temp == null) {
                                    db.save(fuliBean);
                                }
                            }
                            List<JobTypeBean> jobTypeBeanList = result.getJob_type();
                            for (int i = 0; i < jobTypeBeanList.size(); i++) {
                                JobTypeBean jobTypeBean = jobTypeBeanList.get(i);
                                JobTypeBean jobTypeBean_temp = db.selector(JobTypeBean.class).where("id", "=", jobTypeBean.getId()).findFirst();
                                if (jobTypeBean_temp == null) {
                                    db.save(jobTypeBean);
                                }
                            }
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        try {
                            City city = db.selector(City.class).findFirst();
                            List<Area> areas = db.selector(Area.class).where("city_id", "=", city.getId()).findAll();
                            CityBean cityBean = new CityBean();
                            cityBean.setCity(city);
                            cityBean.setArea(areas);
                            List<JobTypeBean> jobTypeBeanList = db.selector(JobTypeBean.class).findAll();
                            List<FuliBean> fuliBeanList = db.selector(FuliBean.class).findAll();
                            mSearchJobFragment.initTop(cityBean, jobTypeBeanList, fuliBeanList);
                            SharePreferenceManager.setFlagIsFirstStartSearchJobFragment(false);
                            getJobList(CURRENT_PAGE, DEFAULT_AREA == null ? -1 : DEFAULT_AREA.getId(), DEFAULT_CITY_ID, DEFAULT_MONEY_BACK, DEFAULT_JOB_TYPE == null ? -1 : DEFAULT_JOB_TYPE.getId(), DEFAULT_FU_LI);
                        } catch (DbException e) {

                        }
                        if (loading.isShowing())
                            loading.dismiss();
                    }
                }.execute(null, null, null, null, null);
            }
        });
        post.post();
    }

    public void onRefresh(int positon, Area area, JobTypeBean jobTypeBean, List<FuliBean> strings) {
        if (positon != -1)
            DEFAULT_MONEY_BACK = positon;
        if (area != null) {
            if (area.getId() > 0)
                DEFAULT_AREA = area;
            else
                DEFAULT_CITY_ID = area.getCity_id();
        }
        if (jobTypeBean != null)
            DEFAULT_JOB_TYPE = jobTypeBean;
        if (strings != null)
            DEFAULT_FU_LI = strings;
        getJobList(CURRENT_PAGE, DEFAULT_AREA == null ? -1 : DEFAULT_AREA.getId(), DEFAULT_CITY_ID, DEFAULT_MONEY_BACK, DEFAULT_JOB_TYPE == null ? -1 : DEFAULT_JOB_TYPE.getId(), DEFAULT_FU_LI);
    }

    public void onEvent(City city) {
        CURRENT_PAGE = 1;
        DEFAULT_MONEY_BACK = -1;
        DEFAULT_AREA = null;
        DEFAULT_JOB_TYPE = null;
        DEFAULT_FU_LI = null;
        DEFAULT_CITY_ID = city.getId();
        getJobList(CURRENT_PAGE, DEFAULT_AREA == null ? -1 : DEFAULT_AREA.getId(), DEFAULT_CITY_ID, DEFAULT_MONEY_BACK, DEFAULT_JOB_TYPE == null ? -1 : DEFAULT_JOB_TYPE.getId(), DEFAULT_FU_LI);
    }

    private void getJobList(final int page, final int area_id, final int city_id, final int money_back, final int work_type, final List<FuliBean> fuliBeanList) {
        is_busy = true;
        PostParams params = getDefaultPostParams(R.string.url_getSearchJobList);
        params.put("page", String.valueOf(page));
        if (area_id > 0)
            params.put("area_id", String.valueOf(area_id));
        if (city_id > 0)
            params.put("city_id", String.valueOf(city_id));
        if (money_back > 0)
            params.put("money_back", String.valueOf(money_back));
        if (work_type > 0)
            params.put("work_type", String.valueOf(work_type));
        if (fuliBeanList != null) {
            for (int i = 0; i < fuliBeanList.size(); i++) {

            }
        }
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<CompanyJobListBean>(CompanyJobListBean.class) {
            @Override
            public void SuccessResult(CompanyJobListBean result) throws JSONException {
                if (page == 1)
                    adapter.datas.clear();
                adapter.datas.addAll(result.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void EmptyData(BaseStatusBean<CompanyJobListBean> b) throws JSONException {
                super.EmptyData(b);
                adapter.datas.clear();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mSearchJobFragment.endRefresh();
                is_busy = false;
            }
        });
        post.post();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mSearchJobFragment.hasHeader())
            mSearchJobFragment.startCompanyWorkDetail(adapter.datas.get(i - 1));
        else
            mSearchJobFragment.startCompanyWorkDetail(adapter.datas.get(i));
    }
}
