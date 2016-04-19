package com.csi0n.searchjob.controller;

import android.view.View;
import android.widget.AdapterView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.ShowSearchPersonAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.bean.BaseStatusBean;
import com.csi0n.searchjob.lib.widget.EmptyLayout;
import com.csi0n.searchjob.ui.activity.ShowSearchUserResultActivity;
import com.csi0n.searchjob.utils.bean.UserListBean;

import org.json.JSONException;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 2015/12/27 0027.
 */
public class ShowSearchUserResultController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, AdapterView.OnItemClickListener,View.OnClickListener {
    private ShowSearchUserResultActivity mShowSearchUserResultActivity;
    private ShowSearchPersonAdapter adapter;

    private int CURRENT_PAGE = 1;
    private int PAGE_COUNT = 0;
    private boolean IS_WORKING = false;

    public ShowSearchUserResultController(ShowSearchUserResultActivity showSearchUserResultActivity) {
        this.mShowSearchUserResultActivity = showSearchUserResultActivity;
    }

    public void initShowSearchUser() {
        adapter = new ShowSearchPersonAdapter(mShowSearchUserResultActivity, null);
        mShowSearchUserResultActivity.getList().setAdapter(adapter);
        getDataFromNet(CURRENT_PAGE);
    }

    private void getDataFromNet(final int page) {
        IS_WORKING = true;
        mShowSearchUserResultActivity.setEmptyLayoutType(EmptyLayout.NETWORK_LOADING);
        PostParams params = getDefaultPostParams(R.string.url_getUserByLike);
        params.put("key", mShowSearchUserResultActivity.getKey());
        params.put("page", String.valueOf(page));
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<UserListBean>(UserListBean.class) {
            @Override
            public void SuccessResult(UserListBean result) throws JSONException {
                if (page == 1) {
                    adapter.users.clear();
                    adapter.notifyDataSetChanged();
                }
                adapter.users.addAll(result.getData());
                PAGE_COUNT = result.getData().size();
                mShowSearchUserResultActivity.setEmptyLayoutType(EmptyLayout.HIDE_LAYOUT);
            }

            @Override
            public void ErrorResult(int code, String str) {
                super.ErrorResult(code, str);
mShowSearchUserResultActivity.setEmptyLayoutType(EmptyLayout.NODATA);
            }

            @Override
            public void EmptyData(BaseStatusBean<UserListBean> b) throws JSONException {
                super.EmptyData(b);
                mShowSearchUserResultActivity.setEmptyLayoutType(EmptyLayout.NODATA);
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mShowSearchUserResultActivity.setStopRefresh();
                mShowSearchUserResultActivity.setStopLoadMore();
                IS_WORKING = false;
            }
        });

        post.post();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.empty_layout:
                getDataFromNet(CURRENT_PAGE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        if (IS_WORKING)
            CLog.getInstance().showError("请等待上一个任务完成!");
        else {
            CURRENT_PAGE = 1;
            getDataFromNet(CURRENT_PAGE);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        if (PAGE_COUNT == Config.DEFAULT_PAGE) {
            CURRENT_PAGE++;
            getDataFromNet(CURRENT_PAGE);
            return true;
        } else {
            CLog.show("没有更多数据了！");
            return false;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mShowSearchUserResultActivity.startShowUserInfor(adapter.users.get(i));
    }
}
