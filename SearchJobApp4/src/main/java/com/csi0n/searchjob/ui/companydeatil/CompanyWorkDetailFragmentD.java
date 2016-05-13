package com.csi0n.searchjob.ui.companydeatil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.event.ext.UserLoginEvent;
import com.csi0n.searchjob.business.pojo.response.ext.GetCompanyCommentResultResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailDResponse;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.ui.LoginActivity;
import com.csi0n.searchjob.ui.adapter.CompanyWorkDetailDListAdapter;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailFragmentD extends MvpFragment<CompanyWorkDetailDPresenter, CompanyWorkDetailDPresenter.ICompanyWorkDetailDPresenter> implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnKeyListener {

    @Bind(R.id.list)
    ListView mList;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout mRefreshLayout;
    @Bind(R.id.ll_view)
    LinearLayout llView;

    @OnItemClick(R.id.list)
    void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TEMP_REPLY_UID = adapter.datas.get(i).uid;
            setReplyHintContent("回复:" + adapter.datas.get(i).userInfo.uname);
    }

    Object loginViewHolder;

    int CURRENT_PAGE = 1;
    int TEMP_COUNT = 0;
    int company_id;
    boolean is_busy = false;
    long TEMP_REPLY_UID;

    CompanyWorkDetailDListAdapter adapter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.frag_company_work_detail_d;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLoginView();
        init();
        DoGetComments(CURRENT_PAGE);
    }

    void init() {
        company_id = mvpActivity.getBundle().getInt(Constants.MARK_COMPANY_WORK_DETAIL_COMPANY_ID);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mvpActivity, true));
        mRefreshLayout.setDelegate(this);
        adapter = new CompanyWorkDetailDListAdapter(mvpActivity);
        mList.setAdapter(adapter);
    }

    void setLoginView() {
        boolean isLogin = Constants.LOGIN_USER == null ? false : true;
        llView.removeAllViews();
        View viewLogin;
        if (isLogin) {
            viewLogin = View.inflate(mvpActivity, R.layout.view_company_work_detail_d_login_y, null);
            loginViewHolder = new ViewHolderY(viewLogin,this);
        } else {
            viewLogin = View.inflate(mvpActivity, R.layout.view_company_work_detail_d_login_n, null);
            loginViewHolder = new ViewHolderN(viewLogin,this);
        }
        viewLogin.setTag(loginViewHolder);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        llView.addView(viewLogin, params);
    }

    void DoPostComment(String content) {
        presenter.doGetCompanyCommentResult(company_id, content, TEMP_REPLY_UID).subscribe(new AdvancedSubscriber<GetCompanyCommentResultResponse>() {
            @Override
            public void onHandleSuccess(GetCompanyCommentResultResponse response) {
                super.onHandleSuccess(response);
                showToast("回复成功!");
                CURRENT_PAGE = 1;
                DoGetComments(CURRENT_PAGE);
            }
        });
    }

    void DoGetComments(final int page) {
        is_busy = true;
        presenter.doGetSearchJobDetailD(page, company_id).subscribe(new AdvancedSubscriber<GetSearchJobDetailDResponse>() {
            @Override
            public void onHandleSuccess(GetSearchJobDetailDResponse response) {
                super.onHandleSuccess(response);
                if (page == 1)
                    adapter.datas.clear();
                TEMP_COUNT = response.datas.length;
                adapter.datas.addAll(Arrays.asList(response.datas));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onHandleEmptyData() {
                super.onHandleEmptyData();
                TEMP_COUNT = 0;
            }

            @Override
            public void onHandleFinish() {
                super.onHandleFinish();
                is_busy = false;
                endRefresh();
            }
        });
    }

    void endRefresh() {
        mRefreshLayout.endRefreshing();
        mRefreshLayout.endLoadingMore();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            CURRENT_PAGE = 1;
            DoGetComments(CURRENT_PAGE);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!is_busy) {
            if (TEMP_COUNT >= Constants.DEFAULT_PAGE) {
                CURRENT_PAGE++;
                DoGetComments(CURRENT_PAGE);
                return true;
            } else
                return false;
        }
        return true;
    }

    @Override
    public void onEvent(Object object) {
        super.onEvent(object);
        if (object.getClass() == UserLoginEvent.class) {
            setLoginView();
        }
    }

    void startLoginActivity() {
        startActivity(LoginActivity.class);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            int count = getReplyContent().length();
            if (count == 0) {
                TEMP_REPLY_UID = 0;
                setReplyHintContent("请输入回复内容");
            }
        }
        return false;
    }

    void setReplyHintContent(String hit) {
        if (loginViewHolder.getClass() != ViewHolderY.class)
            return;
        try {
            ((ViewHolderY) loginViewHolder).editContent.setHint(hit);
        } catch (NullPointerException e) {
        }
    }

    String getReplyContent() {
        if (loginViewHolder.getClass() != ViewHolderY.class)
            return "";
        try {
            String content = ((ViewHolderY) loginViewHolder).editContent.getText().toString();
            if (TextUtils.isEmpty(content))
                return "";
            else
                return content;
        } catch (NullPointerException e) {
            return "";
        }
    }

    static class ViewHolderY {
        private CompanyWorkDetailFragmentD companyWorkDetailFragmentD;
        @Bind(R.id.edit_content)
        EditText editContent;
        @Bind(R.id.btn_reply)
        Button btnReply;
        @OnClick({R.id.btn_reply})void onClick(View view){
            switch (view.getId()){
                case R.id.btn_reply:
                    if (!TextUtils.isEmpty(companyWorkDetailFragmentD.getReplyContent()))
                        companyWorkDetailFragmentD.DoPostComment(companyWorkDetailFragmentD.getReplyContent());
                    else
                        companyWorkDetailFragmentD.showError("回复内容不能为空");
                    break;
                default:
                    break;
            }
        }

        ViewHolderY(View view,CompanyWorkDetailFragmentD companyWorkDetailFragmentD) {
            this.companyWorkDetailFragmentD=companyWorkDetailFragmentD;
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderN {
        private CompanyWorkDetailFragmentD companyWorkDetailFragmentD;
        @Bind(R.id.ll_main)
        LinearLayout llMain;
        @OnClick({R.id.ll_main})void onClick(View view){
            switch (view.getId()){
                case R.id.ll_main:
                    companyWorkDetailFragmentD.startLoginActivity();
                    break;
                default:
                    break;
            }
        }

        ViewHolderN(View view,CompanyWorkDetailFragmentD companyWorkDetailFragmentD) {
            this.companyWorkDetailFragmentD=companyWorkDetailFragmentD;
            ButterKnife.bind(this, view);
        }
    }
}
