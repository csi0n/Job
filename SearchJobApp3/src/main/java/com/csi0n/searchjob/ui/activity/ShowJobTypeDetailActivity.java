package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ShowJobTypeDetailController;
import com.csi0n.searchjob.model.CompanyWorkDetailAModel;
import com.csi0n.searchjob.model.FuliModel;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import me.next.tagview.TagCloudView;

/**
 * Created by chqss on 2016/4/22 0022.
 */
@ContentView(R.layout.aty_show_job_type_detail)
public class ShowJobTypeDetailActivity extends BaseActivity {
    @ViewInject(value = R.id.tv_gang_wei_bo)
    private TextView mGangWei;
    @ViewInject(value = R.id.tv_use_type)
    private  TextView mTVUseType;
    @ViewInject(value = R.id.tv_gongzi)
    private TextView mGongZi;
    @ViewInject(value = R.id.tv_work_time)
    private TextView mWorkTime;
    @ViewInject(value = R.id.tv_sheba)
    private TextView mSheBao;
    @ViewInject(value = R.id.tv_location)
    private TextView mLocation;
    @ViewInject(value = R.id.tag)
    private TagCloudView mTag;
    @ViewInject(value = R.id.tv_work_xuqiu)
    private TextView mWorkXuQiu;
    @ViewInject(value = R.id.tv_degree)
    private TextView mDegree;
    @ViewInject(value = R.id.tv_work_life)
    private TextView mWorkLife;
    @ViewInject(value = R.id.tv_more_info)
    private TextView mMoreInfo;
    @ViewInject(value = R.id.tv_work_info)
    private TextView mWorkInfo;
    private ShowJobTypeDetailController mShowJobTypeDetailController;
    private String JobId;

    @Override
    protected void initWidget() {
        Bundle bundle=getBundle();
        if (bundle==null)
            finish();
        JobId=bundle.getString(Config.MARK_SHOW_SEARCH_JOB_TYPE_ACTIVITY);
        mShowJobTypeDetailController = new ShowJobTypeDetailController(this);
        mShowJobTypeDetailController.initShowJobType();
    }
    public void setCompanyInforTop(DbManager dbManager, CompanyWorkDetailAModel companyInforTop) {
        mGangWei.setText(companyInforTop.getJob_type());
        mTVUseType.setText(companyInforTop.getUse_type());
        mGongZi.setText(companyInforTop.getGongzi());
        mWorkTime.setText(companyInforTop.getWork_time());
        mSheBao.setText(Integer.valueOf(companyInforTop.getShebao()) == 0 ? "无" : "按照国家规定缴纳社保.");
        mLocation.setText(companyInforTop.getWork_location());
        mDegree.setText(companyInforTop.getDegree_wanted());
        mWorkLife.setText(companyInforTop.getWork_life());
        mMoreInfo.setText(companyInforTop.getMore_infor());
        mWorkInfo.setText(companyInforTop.getWork_infor());
        try {
            List<String> stringList = new ArrayList<>();
            String[] fulis = companyInforTop.getFuli().split(",");
            for (int i = 0; i < fulis.length; i++) {
                FuliModel fuliBean = dbManager.selector(FuliModel.class).where("id", "=", fulis[i]).findFirst();
                if (fuliBean != null)
                    stringList.add(fuliBean.getName());
            }
            mTag.setTags(stringList);
        } catch (DbException e) {
        }
        String[] ages = companyInforTop.getAge().split(",");
        if (ages.length == 2) {
            mWorkXuQiu.setText(ages[0] + " " + ages[1]);
        } else {
            mWorkXuQiu.setText(companyInforTop.getAge());
        }
    }
    public String getJobId() {
        return JobId;
    }
}
