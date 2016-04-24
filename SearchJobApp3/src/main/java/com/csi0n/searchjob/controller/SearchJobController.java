package com.csi0n.searchjob.controller;

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
import com.csi0n.searchjob.lib.widget.ProgressLoading;
import com.csi0n.searchjob.model.AreaModel;
import com.csi0n.searchjob.model.CityModel;
import com.csi0n.searchjob.model.CompanyJobListModel;
import com.csi0n.searchjob.model.ConfigModel;
import com.csi0n.searchjob.model.FuliModel;
import com.csi0n.searchjob.model.JobTypeModel;
import com.csi0n.searchjob.ui.fragment.SearchJobFragment;
import com.csi0n.searchjob.utils.DownLoadConfig;
import com.csi0n.searchjob.utils.SharePreferenceManager;

import org.json.JSONException;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/4/20 0020.
 */
public class SearchJobController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, AdapterView.OnItemClickListener {
    private SearchJobFragment mSearchJobFragment;
    private ProgressLoading loading;
    private DbManager db = x.getDb(Config.dbConfig);
    private SearchJobFragmentAdapter adapter;
    private boolean is_busy = false;
    private int CURRENT_PAGE = 1;
    private int TEMP_COUNT = 0;
    private int AREA_ID = -1;
    private int CITY_ID = -1;
    private int MONEY_BACK = -1;
    private int WORK_TYPE = -1;
    private List<FuliModel> FULI_LIST = null;

    public SearchJobController(SearchJobFragment mSearchJobFragment) {
        this.mSearchJobFragment = mSearchJobFragment;
    }

    public void initSearchJob() {
        adapter = new SearchJobFragmentAdapter(mSearchJobFragment.aty);
        mSearchJobFragment.setListAdapter(adapter);
        if (SharePreferenceManager.getFlagIsFirstStartSearchJobFragment()) {
            loading = new ProgressLoading(mSearchJobFragment.aty, "正在下载配置文件...");
            loading.show();
            DownLoadConfig downLoadConfig = new DownLoadConfig(new DownLoadConfig.I_DownLoadConfig() {
                @Override
                public void onSuccess(ConfigModel result) {
                    try {
                        CityModel city = db.selector(CityModel.class).findFirst();
                        List<JobTypeModel> jobTypeBeanList = db.selector(JobTypeModel.class).findAll();
                        List<FuliModel> fuliBeanList = db.selector(FuliModel.class).findAll();
                        mSearchJobFragment.initTop(db, city, jobTypeBeanList, fuliBeanList);
                        CITY_ID = city.getId();
                        SharePreferenceManager.setFlagIsFirstStartSearchJobFragment(false);
                        getJobList(CURRENT_PAGE);
                    } catch (DbException e) {
                        onError(1, "数据库操作失败!");
                    }
                }

                @Override
                public void onError(int code, String str) {

                }

                @Override
                public void onFinish() {
                    if (loading.isShowing())
                        loading.dismiss();
                }
            });
            downLoadConfig.download();
        } else {
        }

    }

    public void onEvent(CityModel cityModel) {
        try {
            mSearchJobFragment.addAreaDatas(cityModel.getAreaList(db));
            setDefaultPostDatas(cityModel.getId());
            CURRENT_PAGE = 1;
            getJobList(CURRENT_PAGE);
        } catch (DbException e) {
            CLog.show("获取区域信息出错!");
        }
    }

    private void setDefaultPostDatas(int city_id) {
        CITY_ID = city_id;
        AREA_ID = -1;
        MONEY_BACK = -1;
        WORK_TYPE = -1;
        FULI_LIST = null;
    }

    public void onRefresh(int positon, AreaModel area, JobTypeModel jobTypeBean, List<FuliModel> strings) {
        if (positon != -1)
            MONEY_BACK = positon;
        if (area != null) {
            if (area.getId() != -1)
                AREA_ID = area.getId();
            else
                CITY_ID = area.getCity_id();
        }
        if (jobTypeBean != null)
            WORK_TYPE = jobTypeBean.getId();
        if (strings != null)
            FULI_LIST = strings;
        CURRENT_PAGE = 1;
        getJobList(CURRENT_PAGE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                mSearchJobFragment.startChooseCityActivity();
                break;
            case R.id.empty_layout:
                getJobList(CURRENT_PAGE);
                break;
            case R.id.ll_search_job:
                mSearchJobFragment.startSearchJobActivity();
                break;
            default:
                break;
        }
    }

    private void getJobList(final int page) {
        is_busy = true;
        PostParams params = getDefaultPostParams(R.string.url_searchJobList);
        params.put("page", String.valueOf(page));
        if (AREA_ID != -1)
            params.put("area_id", String.valueOf(AREA_ID));
        if (CITY_ID != -1)
            params.put("city_id", String.valueOf(CITY_ID));
        if (MONEY_BACK != -1)
            params.put("money_back", String.valueOf(MONEY_BACK));
        if (WORK_TYPE != -1)
            params.put("work_type", String.valueOf(WORK_TYPE));
        if (FULI_LIST != null && FULI_LIST.size() > 0) {
            int k = 0;
            String fuliTemp = null;
            for (int i = 0; i < FULI_LIST.size(); i++) {
                if (k == 0)
                    fuliTemp = String.valueOf(FULI_LIST.get(i).getId());
                else
                    fuliTemp = "," + FULI_LIST.get(i).getId();
                k++;
            }
            params.put("fuli", fuliTemp);
        }
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<CompanyJobListModel>(CompanyJobListModel.class) {
            @Override
            public void SuccessResult(CompanyJobListModel result) throws JSONException {
                if (page == 1)
                    adapter.datas.clear();
                mSearchJobFragment.serEmptyLayoutType(EmptyLayout.HIDE_LAYOUT);
                TEMP_COUNT = result.getData().size();
                adapter.datas.addAll(result.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void ErrorResult(int code, String str) {
                super.ErrorResult(code, str);
                TEMP_COUNT = 0;
                mSearchJobFragment.serEmptyLayoutType(EmptyLayout.NETWORK_ERROR);
            }

            @Override
            public void EmptyData(BaseStatusBean<CompanyJobListModel> b) throws JSONException {
                super.EmptyData(b);
                TEMP_COUNT = 0;
                if (page == 1)
                    mSearchJobFragment.serEmptyLayoutType(EmptyLayout.NODATA);
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
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            CURRENT_PAGE = 1;
            getJobList(CURRENT_PAGE);
        } else {
            CLog.show(R.string.str_thread_busy_please_wait_last_task_complete);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT >= Config.DEFAULT_PAGE) {
                CURRENT_PAGE++;
                getJobList(CURRENT_PAGE);
                return false;
            } else {
                return true;
            }
        } else {
            CLog.show(R.string.str_thread_busy_please_wait_last_task_complete);
            return true;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mSearchJobFragment.startCompanyWorkDetail(adapter.datas.get(position));
    }
}
