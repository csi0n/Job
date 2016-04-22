package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.CompanyWorkDetailBListAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.model.CompanyWorkDetailBHeaderModel;
import com.csi0n.searchjob.model.CompanyWorkDetailBListModel;
import com.csi0n.searchjob.ui.fragment.CompanyWorkDetailFragmentB;

import org.json.JSONException;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/3/6 0006.
 */
public class CompanyWorkDetailBController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private CompanyWorkDetailFragmentB mCompanyWorkDetailFragmentB;
    private CompanyWorkDetailBListAdapter adapter;
    private boolean is_busy = false;
    private int CURRENT_PAGE = 1;
    private int TEMP_COUNT = 0;

    public CompanyWorkDetailBController(CompanyWorkDetailFragmentB companyWorkDetailFragmentB) {
        this.mCompanyWorkDetailFragmentB = companyWorkDetailFragmentB;
    }

    public void initCompanyWorkDetailB() {
        adapter = new CompanyWorkDetailBListAdapter(mCompanyWorkDetailFragmentB);
        mCompanyWorkDetailFragmentB.setAdapter(adapter);
        getHeadFromNet();
    }

    private void getHeadFromNet() {
       PostParams params = getDefaultPostParams(R.string.url_searchJobDetailBHeader);
        params.put("company_id", String.valueOf(mCompanyWorkDetailFragmentB.getCompanyJobBean().getId()));
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<CompanyWorkDetailBHeaderModel>(CompanyWorkDetailBHeaderModel.class) {
            @Override
            public void SuccessResult(CompanyWorkDetailBHeaderModel result) throws JSONException {
                mCompanyWorkDetailFragmentB.setHeadView(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }

            @Override
            public void onFinished() {
                super.onFinished();
                getJob(CURRENT_PAGE);
            }

        });
        post.post();
    }

    private void getJob(final int page) {
        is_busy = true;
        PostParams params = getDefaultPostParams(R.string.url_searchJobDetailB);
        params.put("company_id", String.valueOf(mCompanyWorkDetailFragmentB.getCompanyJobBean().getId()));
        params.put("page", String.valueOf(page));
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<CompanyWorkDetailBListModel>(CompanyWorkDetailBListModel.class) {
            @Override
            public void SuccessResult(CompanyWorkDetailBListModel result) throws JSONException {
                if (page == 1)
                    adapter.datas.clear();
                TEMP_COUNT = result.getData().size();
                adapter.datas.addAll(result.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mCompanyWorkDetailFragmentB.endRefresh();
                is_busy = false;
            }
        });
        post.post();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            CURRENT_PAGE = 1;
            getJob(CURRENT_PAGE);
        } else {
            CLog.show(R.string.str_thread_busy_please_wait_last_task_complete);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT >= Config.DEFAULT_PAGE) {
                CURRENT_PAGE++;
                getJob(CURRENT_PAGE);
                return false;
            } else {
                return true;
            }
        } else {
            CLog.show(R.string.str_thread_busy_please_wait_last_task_complete);
            return true;
        }
    }
}
