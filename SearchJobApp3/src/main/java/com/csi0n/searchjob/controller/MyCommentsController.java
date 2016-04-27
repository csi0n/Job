package com.csi0n.searchjob.controller;

import android.view.View;
import android.widget.AdapterView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.MyCommentsAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.bean.BaseStatusBean;
import com.csi0n.searchjob.lib.widget.EmptyLayout;
import com.csi0n.searchjob.model.MyCommentsListModel;
import com.csi0n.searchjob.ui.activity.MyCommentsActivity;

import org.json.JSONException;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 4/25/16.
 */
public class MyCommentsController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener, AdapterView.OnItemClickListener {
    private MyCommentsActivity mMyCommentsActivity;
    private MyCommentsAdapter adapter;
    private int TEMP_COUNT = 0;
    private boolean is_busy;
    private int CURRENT_PAGE = 1;
    public MyCommentsController(MyCommentsActivity mMyCommentsActivity) {
        this.mMyCommentsActivity = mMyCommentsActivity;
    }
public void initMyComments(){
    adapter = new MyCommentsAdapter(mMyCommentsActivity);
    mMyCommentsActivity.setAdapter(adapter);
    do_get_comments(CURRENT_PAGE);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void do_get_comments(final int page) {
        is_busy = true;
        PostParams params = getDefaultPostParams(R.string.url_comments_list);
        params.put("page", String.valueOf(page));
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<MyCommentsListModel>(MyCommentsListModel.class) {
            @Override
            public void SuccessResult(MyCommentsListModel result) throws JSONException {
                TEMP_COUNT = result.getData().size();
                if (page == 1)
                    adapter.datas.clear();
                mMyCommentsActivity.setErrorLayout(EmptyLayout.HIDE_LAYOUT);
                adapter.datas.addAll(result.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void EmptyData(BaseStatusBean<MyCommentsListModel> b) throws JSONException {
                super.EmptyData(b);
                mMyCommentsActivity.setErrorLayout(EmptyLayout.NO_COMMENTS);
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mMyCommentsActivity.endRefresh();
                is_busy = false;
            }
        });
        post.post();

    }
}
