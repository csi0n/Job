package com.csi0n.searchjob.controller;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.UserDynamicAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.ui.activity.UserDynamicActivity;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 2015/12/27 0027.
 */
public class UserDynamicController  extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private UserDynamicActivity mUserDynamicActivity;
    private UserDynamicAdapter adapter;

    public UserDynamicController(UserDynamicActivity userDynamicActivity) {
        this.mUserDynamicActivity = userDynamicActivity;
    }

    public void initUserDynamic() {
        mUserDynamicActivity.mBGARefreshLayout.setDelegate(this);
        mUserDynamicActivity.mBGARefreshLayout.setCustomHeaderView(getUserDynamicHeaderView(), true);
        mUserDynamicActivity.mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mUserDynamicActivity, true));
        adapter = new UserDynamicAdapter(mUserDynamicActivity);
        mUserDynamicActivity.mList.setAdapter(adapter);
    }

    private View getUserDynamicHeaderView() {
        View root = View.inflate(mUserDynamicActivity, R.layout.view_header_user_dynamic, null);
        TextView mTVAboutMe = (TextView) root.findViewById(R.id.tv_about_me);
        LinearLayout mTVSendNewDynamic = (LinearLayout) root.findViewById(R.id.tv_send_new_dynamic);
        if (!mUserDynamicActivity.isSelf) {
            mTVSendNewDynamic.setVisibility(View.GONE);
            mTVAboutMe.setText(mUserDynamicActivity.getString(R.string.str_friend_dynamic));
        } else {
            mTVAboutMe.setText(mUserDynamicActivity.getString(R.string.str_at_about_me));
            mTVSendNewDynamic.setOnClickListener(this);
        }

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send_new_dynamic:
                mUserDynamicActivity.startWriteDynamic();
                break;
            case R.id.right_btn:
                mUserDynamicActivity.startSetting();
            default:
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        bgaRefreshLayout.endRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        bgaRefreshLayout.endLoadingMore();
        return false;
    }
}
