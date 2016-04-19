package com.csi0n.searchjob.enterpriseapp.controller;

import android.view.View;
import android.widget.AdapterView;

import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.adapter.SendHistoryAdapter;
import com.csi0n.searchjob.enterpriseapp.ui.activity.MySendHistoryActivity;
import com.csi0n.searchjob.enterpriseapp.utils.bean.JobListBean;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.Config;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.widget.EmptyLayout;

import org.json.JSONException;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 3/27/16.
 */
public class MySendHistoryController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener,AdapterView.OnItemClickListener {
        private MySendHistoryActivity mMySendHistoryActivity;
    private SendHistoryAdapter adapter;
    private boolean is_busy = false;
    private int CURRENT_PAGE = 1;
    private int TEMP_COUNT = 0;

    public MySendHistoryController(MySendHistoryActivity mySendHistoryActivity) {
        this.mMySendHistoryActivity = mySendHistoryActivity;
    }

    public void initMySendHistory() {
        adapter = new SendHistoryAdapter(mMySendHistoryActivity);
        mMySendHistoryActivity.setAdapter(adapter);
        do_get_job_list(CURRENT_PAGE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.empty_layout:
                do_get_job_list(CURRENT_PAGE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            CURRENT_PAGE = 1;
            do_get_job_list(CURRENT_PAGE);
        } else {
            CLog.show(R.string.str_thread_busy_please_wait_last_task_complete);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT >= Config.DEFAULT_PAGE) {
                CURRENT_PAGE++;
                do_get_job_list(CURRENT_PAGE);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private void do_get_job_list(final int page) {
        mMySendHistoryActivity.setEmptyLayoutErrorType(EmptyLayout.NETWORK_LOADING);
        PostParams params = getDefaultPostParams(R.string.url_getJobList);
        params.put("page", String.valueOf(page));
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<JobListBean>(JobListBean.class) {
            @Override
            public void SuccessResult(JobListBean result) throws JSONException {
                TEMP_COUNT = result.getData().size();
                if (page == 1)
                    adapter.datas.clear();
                adapter.datas.addAll(result.getData());
                adapter.notifyDataSetChanged();
                mMySendHistoryActivity.setEmptyLayoutErrorType(EmptyLayout.HIDE_LAYOUT);
            }

            @Override
            public void ErrorResult(int code, String str) {
                super.ErrorResult(code, str);
                TEMP_COUNT = 0;
                mMySendHistoryActivity.setEmptyLayoutErrorType(EmptyLayout.NODATA);
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mMySendHistoryActivity.endRefresh();
                is_busy = false;
            }
        });
        post.post();
    }
}
