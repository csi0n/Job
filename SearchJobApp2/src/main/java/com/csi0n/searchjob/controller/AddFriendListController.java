package com.csi0n.searchjob.controller;

import android.view.View;
import android.widget.AdapterView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.AddFriendListAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.ui.activity.AddFriendListActivity;
import com.csi0n.searchjob.utils.bean.AddFriendListBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 2015/12/20 0020.
 */
public class AddFriendListController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, AdapterView.OnItemClickListener {
    private AddFriendListActivity mAddFriendListActivity;
    private AddFriendListAdapter adapter;
    private int CURRENT_PAGE = 1;
    private int TEMP_COUNT = 0;
    private boolean is_busy = false;

    public AddFriendListController(AddFriendListActivity addFriendListActivity) {
        this.mAddFriendListActivity = addFriendListActivity;
    }

    public void initAddFriendList() {
        adapter = new AddFriendListAdapter(mAddFriendListActivity);
        mAddFriendListActivity.setAdapter(adapter);
        getAddFriendListFromNet(CURRENT_PAGE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }
    }

    private void getAddFriendListFromNet(final int page) {
        is_busy = true;
        PostParams params = getDefaultPostParams(R.string.url_getAddFriendList);
        params.put("page", String.valueOf(page));
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<AddFriendListBean>(AddFriendListBean.class) {
            @Override
            public void SuccessResult(AddFriendListBean result) throws JSONException {
                if (page == 1) {
                    adapter.mAddFriends.clear();
                }
                adapter.mAddFriends.addAll(result.getData());
                adapter.notifyDataSetChanged();
                TEMP_COUNT = result.getData().size();
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mAddFriendListActivity.endRefresh();
                is_busy = false;
            }
        });

        post.post();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            CURRENT_PAGE = 1;
            getAddFriendListFromNet(CURRENT_PAGE);
        } else {
            CLog.show(R.string.str_thread_busy_please_wait_last_task_complete);
        }

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT >= Config.DEFAULT_PAGE) {
                CURRENT_PAGE++;
                getAddFriendListFromNet(CURRENT_PAGE);
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
