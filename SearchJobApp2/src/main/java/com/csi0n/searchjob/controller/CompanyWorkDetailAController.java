package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.AppContext;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.CompanyWorkAAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.ui.fragment.CompanyWorkDetailFragmentA;
import com.csi0n.searchjob.ui.widget.timeandaddresschoose.ChangeBirthDialog;
import com.csi0n.searchjob.utils.bean.CompanyWorkDetailMainBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/2/23 0023.
 */
public class CompanyWorkDetailAController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private CompanyWorkDetailFragmentA mCompanyWorkDetailFragmentA;
    private CompanyWorkAAdapter adapter;
    private ChangeBirthDialog mChangeBirthDialog;
    public CompanyWorkDetailAController(CompanyWorkDetailFragmentA CompanyWorkDetailFragmentA) {
        this.mCompanyWorkDetailFragmentA = CompanyWorkDetailFragmentA;
    }

    public void initCompany() {
        adapter = new CompanyWorkAAdapter(mCompanyWorkDetailFragmentA.aty);
        mCompanyWorkDetailFragmentA.setAdapter(adapter);
        getDetailMain();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                mChangeBirthDialog = new ChangeBirthDialog(mCompanyWorkDetailFragmentA.aty);
                Calendar c = Calendar.getInstance();
                mChangeBirthDialog.setDate(c.get(Calendar.YEAR), Calendar.MONTH, Calendar.DAY_OF_MONTH);
                mChangeBirthDialog.show();
                mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {
                    @Override
                    public void onClick(String year, String month, String day) {
                        try {
                            Date epoch = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(String.format("%s/%s/%s", day, month, year));
                            CLog.getInstance().iMessage(String.valueOf(epoch.getTime()));
                        } catch (ParseException e) {
                            CLog.show("时间转化失败！");
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    private void getDetailMain() {
        PostParams params = getDefaultPostParams(R.string.url_getSearchJobDetail);
        params.put("job_id", mCompanyWorkDetailFragmentA.getCompanyJobBean().getJob_id());
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<CompanyWorkDetailMainBean>(CompanyWorkDetailMainBean.class) {
            @Override
            public void SuccessResult(CompanyWorkDetailMainBean result) throws JSONException {
                mCompanyWorkDetailFragmentA.setCompanyInforTop(result);
                if (result.getToday_money_back() != null) {
                    adapter.todayMoneyBackEntityList = result.getToday_money_back();
                    adapter.notifyDataSetChanged();
                } else {
                    CLog.getInstance().iMessage("jinliren not put money");
                }
            }
        });
        post.post();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        bgaRefreshLayout.endRefreshing();
        bgaRefreshLayout.endLoadingMore();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        return false;
    }
}
