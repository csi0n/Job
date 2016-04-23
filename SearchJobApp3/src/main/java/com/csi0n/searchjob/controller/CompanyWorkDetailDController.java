package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.CompanyWorkDetailDListAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.model.CompanyWorkDetailDListModel;
import com.csi0n.searchjob.ui.fragment.CompanyWorkDetailFragmentD;

import org.json.JSONException;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/3/10 0010.
 */
public class CompanyWorkDetailDController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private CompanyWorkDetailFragmentD mCompanyWorkDetailFragmentD;
    private CompanyWorkDetailDListAdapter adapter;
    private boolean is_busy = false;
    private int CURRENT_PAGE = 1;
    private int TEMP_COUNT = 0;

    public CompanyWorkDetailDController(CompanyWorkDetailFragmentD companyWorkDetailFragmentD) {
        this.mCompanyWorkDetailFragmentD = companyWorkDetailFragmentD;
    }

    public void initCompanyWorkDetailD() {
        if (Config.LOGIN_USER != null)
            mCompanyWorkDetailFragmentD.setLoginView(true);
        else
            mCompanyWorkDetailFragmentD.setLoginView(false);
        adapter = new CompanyWorkDetailDListAdapter(mCompanyWorkDetailFragmentD.aty);
        mCompanyWorkDetailFragmentD.setAdapter(adapter);
        getDataFromNet(CURRENT_PAGE);
    }

    private void getDataFromNet(final int page) {
        is_busy = true;
        PostParams params = getDefaultPostParams(R.string.url_searchJobDetailD);
        params.put("company_id", String.valueOf(mCompanyWorkDetailFragmentD.getCompanyJobBean().getId()));
        params.put("page", String.valueOf(page));
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<CompanyWorkDetailDListModel>(CompanyWorkDetailDListModel.class) {
            @Override
            public void SuccessResult(CompanyWorkDetailDListModel result) throws JSONException {
                TEMP_COUNT = result.getData().size();
                if (page == 1)
                    adapter.datas.clear();
                adapter.datas.addAll(result.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mCompanyWorkDetailFragmentD.endRefresh();
                is_busy = false;
            }
        });
        post.post();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main:
                break;
            case R.id.btn_reply:
                break;
            default:
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            CURRENT_PAGE = 1;
            getDataFromNet(CURRENT_PAGE);
        } else {
            CLog.show(R.string.str_thread_busy_please_wait_last_task_complete);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT >= Config.DEFAULT_PAGE) {
                CURRENT_PAGE++;
                getDataFromNet(CURRENT_PAGE);
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
