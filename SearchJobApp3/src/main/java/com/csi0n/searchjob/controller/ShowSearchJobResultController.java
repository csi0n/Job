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
import com.csi0n.searchjob.model.CompanyJobListModel;
import com.csi0n.searchjob.ui.activity.ShowSearchJobResultActivity;

import org.json.JSONException;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/3/25 0025.
 */
public class ShowSearchJobResultController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener, AdapterView.OnItemClickListener {
    private ShowSearchJobResultActivity mShowSearchJobResultActivity;
    private SearchJobFragmentAdapter adapter;
    private int CURRENT_PAGE = 1;
    private int TEMP_COUNT = 0;
    private boolean is_busy = false;

    public ShowSearchJobResultController(ShowSearchJobResultActivity showSearchJobResultActivity) {
        this.mShowSearchJobResultActivity = showSearchJobResultActivity;
    }

    public void initShowSearchJobResult() {
        adapter = new SearchJobFragmentAdapter(mShowSearchJobResultActivity);
        mShowSearchJobResultActivity.setAdapter(adapter);
        getSearchResult(CURRENT_PAGE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.empty_layout:
                getSearchResult(CURRENT_PAGE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            CURRENT_PAGE = 1;
            getSearchResult(CURRENT_PAGE);
        } else {
            CLog.show(R.string.str_thread_busy_please_wait_last_task_complete);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT >= Config.DEFAULT_PAGE) {
                CURRENT_PAGE++;
                getSearchResult(CURRENT_PAGE);
                return false;
            } else {
                return true;
            }
        } else {
            CLog.show(R.string.str_thread_busy_please_wait_last_task_complete);
            return true;
        }
    }

    private void getSearchResult(final int page) {
        is_busy = true;
        mShowSearchJobResultActivity.setEmptyLayoutType(EmptyLayout.NETWORK_LOADING);
        PostParams params = getDefaultPostParams(R.string.url_searchJobList);
        params.put("page", String.valueOf(page));
        params.put("key", mShowSearchJobResultActivity.getSEARCH_KEY());
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<CompanyJobListModel>(CompanyJobListModel.class) {
            @Override
            public void SuccessResult(CompanyJobListModel result) throws JSONException {
                if (page == 1) {
                    adapter.datas.clear();
                    mShowSearchJobResultActivity.setEmptyLayoutType(EmptyLayout.HIDE_LAYOUT);
                }
                adapter.datas.addAll(result.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void EmptyData(BaseStatusBean<CompanyJobListModel> b) throws JSONException {
                super.EmptyData(b);
                if (page == 1)
                    mShowSearchJobResultActivity.setEmptyLayoutType(EmptyLayout.NODATA);
            }

            @Override
            public void ErrorResult(int code, String str) {
                super.ErrorResult(code, str);
                mShowSearchJobResultActivity.setEmptyLayoutType(EmptyLayout.DATA_ERROR);
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mShowSearchJobResultActivity.endRefresh();
                is_busy = false;
            }
        });
        post.post();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mShowSearchJobResultActivity.startCompanyWorkDetail(adapter.datas.get(i));
    }
}
