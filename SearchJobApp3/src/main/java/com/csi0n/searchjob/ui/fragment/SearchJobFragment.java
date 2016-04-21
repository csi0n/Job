package com.csi0n.searchjob.ui.fragment;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.SearchJobController;
import com.csi0n.searchjob.lib.widget.EmptyLayout;
import com.csi0n.searchjob.model.AreaModel;
import com.csi0n.searchjob.model.CityModel;
import com.csi0n.searchjob.model.FuliModel;
import com.csi0n.searchjob.model.JobTypeModel;
import com.csi0n.searchjob.ui.activity.ChooseCityActivity;
import com.csi0n.searchjob.ui.widget.tabview.view.ExpandTabView;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewAreaList;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewBackMoney;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewJob;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewWelfare;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/4/19 0019.
 */
@ContentView(R.layout.frag_search_job)
public class SearchJobFragment extends BaseFragment {
    @ViewInject(value = R.id.tv_location)
    private TextView mLocation;
    @ViewInject(value = R.id.expandtab_view)
    private ExpandTabView mExpandTabView;
    @ViewInject(value = R.id.refreshLayout)
    private BGARefreshLayout mBGARefreshLayout;
    @ViewInject(value = R.id.empty_layout)
    private EmptyLayout mEmptyLayout;
    @ViewInject(value = R.id.list)
    private ListView mList;

    @Event(value = {R.id.tv_location, R.id.empty_layout})
    private void onClick(View view) {
        mSearchJobController.onClick(view);
    }
    private SearchJobController mSearchJobController;

    private ViewAreaList viewAreaList;
    private ViewBackMoney viewBackMoney;
    private ViewJob viewJob;
    private ViewWelfare viewWelfare;

    private ArrayList<View> mViewArray = new ArrayList<View>();
    @Override
    protected void initWidget() {
        mSearchJobController = new SearchJobController(this);
        mSearchJobController.initSearchJob();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void initTop(DbManager db, CityModel cityBean, List<JobTypeModel> jobTypeBeenList, List<FuliModel> fuliBeanList) throws DbException {
        ArrayList<String> mTextArray = new ArrayList<String>();
        viewAreaList = new ViewAreaList(aty);
        mLocation.setText(cityBean.getCity());
        final AreaModel area = new AreaModel();
        area.setArea("区域不限");
        viewAreaList.areaDatas.add(area);
        viewAreaList.areaDatas.addAll(cityBean.getAreaList(db));
        viewAreaList.setAdapterChange();
        mViewArray.add(viewAreaList);
        mTextArray.add(viewAreaList.areaDatas.get(0).getArea());
        viewBackMoney = new ViewBackMoney(aty);

        List<String> backmoney = new ArrayList<>();
        backmoney.add("返现不限");
        backmoney.add("男返");
        backmoney.add("女返");
        viewBackMoney.setAdapterData(backmoney);
        mViewArray.add(viewBackMoney);
        mTextArray.add(backmoney.get(0));

        viewJob = new ViewJob(aty);
        JobTypeModel jobTypeBean = new JobTypeModel();
        jobTypeBean.setName("工作不限");
        viewJob.jobDatas.add(jobTypeBean);
        viewJob.jobDatas.addAll(jobTypeBeenList);
        viewJob.setAdapterChange();
        mViewArray.add(viewJob);
        mTextArray.add(viewJob.jobDatas.get(0).getName());

        viewWelfare = new ViewWelfare(aty);
        FuliModel fuliBean = new FuliModel();
        fuliBean.setName("福利不限");
        viewWelfare.fuliBeanList.add(fuliBean);
        viewWelfare.fuliBeanList.addAll(fuliBeanList);
        viewWelfare.setAdapterChange();
        mViewArray.add(viewWelfare);
        mTextArray.add(viewWelfare.fuliBeanList.get(0).getName());

        mExpandTabView.setValue(mTextArray, mViewArray);
        mExpandTabView.setTitle(viewAreaList.areaDatas.get(0).getArea(), 0);
        mExpandTabView.setTitle(backmoney.get(0), 1);
        mExpandTabView.setTitle(viewJob.jobDatas.get(0).getName(), 2);
        mExpandTabView.setTitle(viewWelfare.fuliBeanList.get(0).getName(), 3);
        viewAreaList.setOnSelectListener(new ViewAreaList.OnSelectListener() {
            @Override
            public void getValue(int position, AreaModel showText) {
                onRefresh(viewAreaList, -1, showText.getArea(), showText, null, null);
            }
        });
        viewBackMoney.setOnSelectListener(new ViewBackMoney.OnSelectListener() {
            @Override
            public void getValue(int position, String showText) {
                onRefresh(viewBackMoney, -1, showText, null, null, null);
            }
        });
        viewJob.setOnSelectListener(new ViewJob.OnSelectListener() {
            @Override
            public void getValue(int position, JobTypeModel showText) {
                onRefresh(viewJob, -1, showText.getName(), null, showText, null);
            }
        });
        viewWelfare.setOnSelectListener(new ViewWelfare.OnSelectListener() {
            @Override
            public void getValue(List<FuliModel> datas) {
                onRefresh(viewWelfare, -1, null, null, null, datas);
            }
        });
    }

    public void setExpandTabDefault() {
        mExpandTabView.setTitle("区域不限", 0);
        mExpandTabView.setTitle("返现不限", 1);
        mExpandTabView.setTitle("工作不限", 2);
        mExpandTabView.setTitle("福利不限", 3);

    }

    public void setListAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
    }

    @Subscribe
    public void onEvent(CityModel city) {
        mLocation.setText(city.getCity());
        setExpandTabDefault();
        mSearchJobController.onEvent(city);
    }

    public void addAreaDatas(List<AreaModel> areaModelList) {
        viewAreaList.areaDatas.clear();
        viewAreaList.areaDatas.addAll(areaModelList);
        viewAreaList.setAdapterChange();
    }

    private void onRefresh(View view, int positio, String showText, AreaModel area, JobTypeModel jobTypeBean, List<FuliModel> strings) {
        mExpandTabView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !mExpandTabView.getTitle(position).equals(showText)) {
            if (showText != null)
                mExpandTabView.setTitle(showText, position);
            mSearchJobController.onRefresh(positio, area, jobTypeBean, strings);
        }
    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }

    public void serEmptyLayoutType(int type) {
        mEmptyLayout.setErrorType(type);
    }

    public void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }

    public void startChooseCityActivity() {
        startActivity(ChooseCityActivity.class);
    }

}
