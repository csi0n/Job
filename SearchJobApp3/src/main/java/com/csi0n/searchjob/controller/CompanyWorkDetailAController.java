package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.CompanyWorkDetailAListAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.widget.timeandaddresschoose.ChangeBirthDialog;
import com.csi0n.searchjob.model.CompanyWorkDetailAModel;
import com.csi0n.searchjob.ui.fragment.CompanyWorkDetailFragmentA;

import org.json.JSONException;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/2/23 0023.
 */
public class CompanyWorkDetailAController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private CompanyWorkDetailFragmentA mCompanyWorkDetailFragmentA;
    private CompanyWorkDetailAListAdapter adapter;
    private ChangeBirthDialog mChangeBirthDialog;
    public CompanyWorkDetailAController(CompanyWorkDetailFragmentA CompanyWorkDetailFragmentA) {
        this.mCompanyWorkDetailFragmentA = CompanyWorkDetailFragmentA;
    }
    public void initCompany() {
        adapter = new CompanyWorkDetailAListAdapter(mCompanyWorkDetailFragmentA.aty);
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
        PostParams params = getDefaultPostParams(R.string.url_searchJobDetailA);
        params.put("job_id", mCompanyWorkDetailFragmentA.getCompanyJobBean().getJob_id());
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<CompanyWorkDetailAModel>(CompanyWorkDetailAModel.class) {
            @Override
            public void SuccessResult(CompanyWorkDetailAModel result) throws JSONException {
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
        mCompanyWorkDetailFragmentA.endRefersh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        return false;
    }
}
