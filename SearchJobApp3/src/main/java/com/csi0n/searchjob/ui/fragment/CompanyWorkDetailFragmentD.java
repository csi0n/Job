package com.csi0n.searchjob.ui.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.CompanyWorkDetailDController;
import com.csi0n.searchjob.lib.widget.EmptyLayout;
import com.csi0n.searchjob.model.CompanyJobListModel;
import com.csi0n.searchjob.model.UserModel;
import com.csi0n.searchjob.model.event.UserLoginEvent;
import com.csi0n.searchjob.ui.activity.LoginActivity;
import com.csi0n.searchjob.utils.BGANormalRefreshViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/2/23 0023.
 */
@ContentView(R.layout.frag_company_work_detail_d)
public class CompanyWorkDetailFragmentD extends BaseFragment {
    @ViewInject(value = R.id.refreshLayout)
    private BGARefreshLayout mBGARefreshLayout;
    @ViewInject(value = R.id.list)
    private ListView mList;
    @ViewInject(value = R.id.ll_view)
    private LinearLayout mLLView;
    @ViewInject(value = R.id.empty_layout)
    private EmptyLayout mEmptyLayout;
    private LinearLayout mLLLoginVN;
    private EditText mContent;
    private Button mReply;
    private CompanyWorkDetailDController mCompanyWorkDetailDController;
    private CompanyJobListModel.CompanyJobModel companyJobBean;

    @Override
    protected void initWidget() {
        Bundle bundle = aty.getIntent().getExtras();
        if (bundle != null)
            companyJobBean = (CompanyJobListModel.CompanyJobModel) bundle.getSerializable(Config.MARK_COMAPNY_WORK_DETAIL_ACTIVITY_COMPANY_DATA);
        mCompanyWorkDetailDController = new CompanyWorkDetailDController(this);
        mCompanyWorkDetailDController.initCompanyWorkDetailD();
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(aty, true));
        mBGARefreshLayout.setDelegate(mCompanyWorkDetailDController);
        mEmptyLayout.setOnLayoutClickListener(mCompanyWorkDetailDController);
        mList.setOnItemClickListener(mCompanyWorkDetailDController);
        EventBus.getDefault().register(this);
    }

    public void setLoginView(boolean isLogin) {
        mLLView.removeAllViews();
        View viewLogin = null;
        if (isLogin) {
            viewLogin = View.inflate(aty, R.layout.view_company_work_detail_d_login_y, null);
            findLoginViewY(viewLogin);
        } else {
            viewLogin = View.inflate(aty, R.layout.view_company_work_detail_d_login_n, null);
            findLoginViewN(viewLogin);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLLView.addView(viewLogin, params);
    }

    public void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }

    private void findLoginViewY(View yView) {
        mContent = (EditText) yView.findViewById(R.id.edit_content);
        mContent.setOnKeyListener(mCompanyWorkDetailDController);
        mReply = (Button) yView.findViewById(R.id.btn_reply);
        mReply.setOnClickListener(mCompanyWorkDetailDController);
    }

    private void findLoginViewN(View nView) {
        mLLLoginVN = (LinearLayout) nView.findViewById(R.id.ll_main);
        mLLLoginVN.setOnClickListener(mCompanyWorkDetailDController);
    }

    public CompanyJobListModel.CompanyJobModel getCompanyJobBean() {
        return companyJobBean;
    }

    public void setAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
    }

    public String getReplyContent() {
        if (mContent != null)
            return mContent.getText().toString();
        else
            return "";
    }
    public void setReplayContent(String string){
        if (mContent!=null)
            mContent.setText(string);
    }

    public void setReplyHintContent(String string) {
        if (mContent != null)
            mContent.setHint("回复:" + string);
    }

    public void startLoginActivity() {
        startActivity(LoginActivity.class);
    }

    @Subscribe
    public void onEvent(UserLoginEvent u) {
        mLLView.removeAllViews();
        View viewLogin = View.inflate(aty, R.layout.view_company_work_detail_d_login_y, null);
        findLoginViewY(viewLogin);
        mLLView.addView(viewLogin);
    }

    public void setErrorLayout(int type) {
        mEmptyLayout.setErrorType(type);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
