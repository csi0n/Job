package com.csi0n.searchjob.controller;

import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.ui.activity.SearchUserActivity;

/**
 * Created by csi0n on 2015/12/20 0020.
 */
public class SearchUserController extends BaseController {
    private SearchUserActivity mSearchUserActivity;

    public SearchUserController(SearchUserActivity searchUserActivity) {
        this.mSearchUserActivity = searchUserActivity;
    }

    public void initModule() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_person_id:
                if (TextUtils.isEmpty(mSearchUserActivity.getSearchKey()))
                    CLog.getInstance().showError( "请输入搜索内容!");
                else
                    mSearchUserActivity.startShowSearchResult(mSearchUserActivity.getSearchKey());
                break;
            default:
                break;
        }
    }
}
