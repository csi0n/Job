package com.csi0n.searchjob.controller;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.CompanyWorkDetailDListAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.bean.BaseStatusBean;
import com.csi0n.searchjob.lib.widget.EmptyLayout;
import com.csi0n.searchjob.model.CompanyWorkDetailDListModel;
import com.csi0n.searchjob.ui.fragment.CompanyWorkDetailFragmentD;

import org.json.JSONException;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/3/10 0010.
 */
public class CompanyWorkDetailDController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener, AdapterView.OnItemClickListener, View.OnKeyListener {
    private CompanyWorkDetailFragmentD mCompanyWorkDetailFragmentD;
    private CompanyWorkDetailDListAdapter adapter;
    private boolean is_busy = false;
    private int CURRENT_PAGE = 1;
    private int TEMP_COUNT = 0;
    private String TEMP_REPLY_UID = null;

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
                mCompanyWorkDetailFragmentD.setErrorLayout(EmptyLayout.HIDE_LAYOUT);
                adapter.datas.addAll(result.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void EmptyData(BaseStatusBean<CompanyWorkDetailDListModel> b) throws JSONException {
                mCompanyWorkDetailFragmentD.setErrorLayout(EmptyLayout.NO_COMMENTS);
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
                mCompanyWorkDetailFragmentD.startLoginActivity();
                break;
            case R.id.btn_reply:
                if (TextUtils.isEmpty(mCompanyWorkDetailFragmentD.getReplyContent())) {
                    CLog.show("请输入回复内容!");
                } else {
                    do_insert_comments(String.valueOf(mCompanyWorkDetailFragmentD.getCompanyJobBean().getId()), mCompanyWorkDetailFragmentD.getReplyContent(), TEMP_REPLY_UID);
                }
                break;
            case R.id.empty_layout:
                getDataFromNet(CURRENT_PAGE);
                break;
            default:
                break;
        }
    }

    private void do_insert_comments(String company_id, String content, String reply_uid) {
        PostParams params = getDefaultPostParams(R.string.url_comments_insert);
        params.put("company_id", company_id);
        params.put("content", content);
        if (!TextUtils.isEmpty(reply_uid))
            params.put("reply_uid", reply_uid);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyLayout>(EmptyLayout.class) {
            @Override
            public void SuccessResult(EmptyLayout result) throws JSONException {
                CLog.show("回复成功!");
                mCompanyWorkDetailFragmentD.setReplayContent("");
                getDataFromNet(CURRENT_PAGE);
            }
        });
        post.post();
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

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_DEL) {
            int count = mCompanyWorkDetailFragmentD.getReplyContent().length();
            if (count == 0) {
                TEMP_REPLY_UID = null;
                mCompanyWorkDetailFragmentD.setReplyHintContent("请输入回复内容");
            }
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TEMP_REPLY_UID = adapter.datas.get(i).getUid();
        mCompanyWorkDetailFragmentD.setReplyHintContent(adapter.datas.get(i).getUser_info().getUname());
    }
}
