package com.csi0n.searchjob.enterpriseapp.controller;

import android.view.View;
import android.widget.AdapterView;
import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.adapter.CommentsAdapter;
import com.csi0n.searchjob.enterpriseapp.ui.activity.CommentsActivity;
import com.csi0n.searchjob.enterpriseapp.utils.bean.CommentsListBean;
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
 * Created by csi0n on 3/29/16.
 */
public class CommentsController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, AdapterView.OnItemClickListener, View.OnClickListener {
    private CommentsActivity mCommentsActivity;
    private CommentsAdapter adapter;
    private boolean is_busy = false;
    private int CURRENT_PAGE = 1;
    private int TEMP_COUNT = 0;

    public CommentsController(CommentsActivity commentsActivity) {
        this.mCommentsActivity = commentsActivity;
    }

    public void initComments() {
        adapter = new CommentsAdapter(mCommentsActivity);
        mCommentsActivity.setAdapter(adapter);
        do_get_comments(CURRENT_PAGE);
    }

    private void do_get_comments(final int page) {
        mCommentsActivity.setEmptyLayoutErrorType(EmptyLayout.NETWORK_LOADING);
        PostParams params = getDefaultPostParams(R.string.url_getComments);
        params.put("page", String.valueOf(page));
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<CommentsListBean>(CommentsListBean.class) {
            @Override
            public void SuccessResult(CommentsListBean result) throws JSONException {
                TEMP_COUNT = result.getData().size();
                if (page == 1)
                    adapter.datas.clear();
                adapter.datas.addAll(result.getData());
                adapter.notifyDataSetChanged();
                mCommentsActivity.setEmptyLayoutErrorType(EmptyLayout.HIDE_LAYOUT);
            }



            @Override
            public void ErrorResult(int code, String str) {
                super.ErrorResult(code, str);
                TEMP_COUNT = 0;
                mCommentsActivity.setEmptyLayoutErrorType(EmptyLayout.NODATA);
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mCommentsActivity.endRefresh();
                is_busy = false;
            }
        });
        post.post();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.empty_layout:
                do_get_comments(CURRENT_PAGE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            CURRENT_PAGE = 1;
            do_get_comments(CURRENT_PAGE);
        } else {
            CLog.show(R.string.str_thread_busy_please_wait_last_task_complete);
        }
    }
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT >= Config.DEFAULT_PAGE) {
                CURRENT_PAGE++;
                do_get_comments(CURRENT_PAGE);
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
}
