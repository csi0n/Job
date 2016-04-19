package com.csi0n.searchjob.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.SearchJobController;
import com.csi0n.searchjob.lib.widget.EmptyLayout;
import com.csi0n.searchjob.ui.activity.ChooseCityActivity;
import com.csi0n.searchjob.ui.activity.CompanyWorkDetailActivity;
import com.csi0n.searchjob.ui.activity.SearchJobActivity;
import com.csi0n.searchjob.ui.widget.tabview.view.ExpandTabView;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewAreaList;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewBackMoney;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewJob;
import com.csi0n.searchjob.ui.widget.tabview.view.ViewWelfare;
import com.csi0n.searchjob.utils.BGANormalRefreshViewHolder;
import com.csi0n.searchjob.utils.bean.Area;
import com.csi0n.searchjob.utils.bean.City;
import com.csi0n.searchjob.utils.bean.CityBean;

import com.csi0n.searchjob.utils.bean.CompanyJobListBean;
import com.csi0n.searchjob.utils.bean.FuliBean;
import com.csi0n.searchjob.utils.bean.JobTypeBean;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.EventBus;

/**
 * Created by csi0n on 2015/12/14 0014.
 * 搜工作页面
 */
@ContentView(R.layout.frag_search_job)
public class SearchJobFragment extends BaseFragment {
    @ViewInject(value = R.id.expandtab_view)
    private ExpandTabView mExpandTabView;
    @ViewInject(value = R.id.empty_layout)
    private EmptyLayout mEmptyLayout;
    @ViewInject(value = R.id.refreshLayout)
    private BGARefreshLayout mBGARefreshLayout;
    @ViewInject(value = R.id.list)
    private ListView mList;
    @ViewInject(value = R.id.tv_location)
    private TextView mLocation;
    private List<View> views;
    private List<String> strings;

    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewAreaList viewAreaList;
    private ViewBackMoney viewBackMoney;
    private ViewJob viewJob;
    private ViewWelfare viewWelfare;
    private SearchJobController mSearchJobController;
    private ConvenientBanner mConvenientBanner;
    private View mListHeadView;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private DbManager db = x.getDb(Config.dbConfig);

    @Event(value = {R.id.tv_location, R.id.ll_search_job,R.id.tv_search_job})
    private void onClick(View view) {
        if (mSearchJobController != null)
            mSearchJobController.onClick(view);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initWidget() {
        views = new ArrayList<>();
        strings = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            views.add(getPagerView());
        }
        mList.addHeaderView(getHeaderView(views, strings));
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mList.removeHeaderView(mListHeadView);
            }
        }, 8 * 1000);
        mSearchJobController = new SearchJobController(this);
        mSearchJobController.initSearchJob();
        mBGARefreshLayout.setDelegate(mSearchJobController);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(aty, false));
        mList.setOnItemClickListener(mSearchJobController);
        EventBus.getDefault().register(this);
    }

    public void initTop(CityBean cityBean, List<JobTypeBean> jobTypeBeenList, List<FuliBean> fuliBeanList) {
        ArrayList<String> mTextArray = new ArrayList<String>();
        viewAreaList = new ViewAreaList(aty);
        mLocation.setText(cityBean.getCity().getCity());
        final Area area = new Area();
        area.setArea("区域不限");
        viewAreaList.areaDatas.add(area);
        for (int i = 0; i < cityBean.getArea().size(); i++) {
            viewAreaList.areaDatas.add(cityBean.getArea().get(i));
        }
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
        JobTypeBean jobTypeBean = new JobTypeBean();
        jobTypeBean.setName("工作不限");
        viewJob.jobDatas.add(jobTypeBean);
        for (int i = 0; i < jobTypeBeenList.size(); i++) {
            viewJob.jobDatas.add(jobTypeBeenList.get(i));
        }
        viewJob.setAdapterChange();
        mViewArray.add(viewJob);
        mTextArray.add(viewJob.jobDatas.get(0).getName());

        viewWelfare = new ViewWelfare(aty);
        FuliBean fuliBean = new FuliBean();
        fuliBean.setName("福利不限");
        viewWelfare.fuliBeanList.add(fuliBean);
        for (int i = 0; i < fuliBeanList.size(); i++) {
            viewWelfare.fuliBeanList.add(fuliBeanList.get(i));
        }
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
            public void getValue(int position, Area showText) {
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
            public void getValue(int position, JobTypeBean showText) {
                onRefresh(viewJob, -1, showText.getName(), null, showText, null);
            }
        });
        viewWelfare.setOnSelectListener(new ViewWelfare.OnSelectListener() {
            @Override
            public void getValue(List<FuliBean> datas) {
                onRefresh(viewWelfare, -1, null, null, null, datas);
            }
        });
    }

    private void onRefresh(View view, int positio, String showText, Area area, JobTypeBean jobTypeBean, List<FuliBean> strings) {
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

    private View getPagerView() {
        ImageView imageView = new ImageView(aty);
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return imageView;
    }

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    public View getHeaderView(List<View> views, List<String> strings) {
        localImages.add(R.mipmap.ic_default_adimage);
        mListHeadView = View.inflate(aty, R.layout.view_adapter_header_search_job_fragment, null);
        mListHeadView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.removeHeaderView(mListHeadView);
            }
        });
        mConvenientBanner = (ConvenientBanner) mListHeadView.findViewById(R.id.convenientBanner);
        mConvenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        return mListHeadView;
    }

    public void onEvent(City city) {
        try {
            mLocation.setText(city.getCity());
            List<Area> areas = db.selector(Area.class).where("city_id", "=", city.getId()).findAll();
            if (areas == null)
                return;
            viewAreaList.areaDatas.clear();
            Area area = new Area();
            area.setArea("区域不限");
            viewAreaList.areaDatas.add(area);
            viewAreaList.areaDatas.addAll(areas);
            viewAreaList.setAdapterChange();
            mExpandTabView.setTitle(viewAreaList.areaDatas.get(0).getArea(), 0);
            mExpandTabView.setTitle("返现不限", 1);
            mExpandTabView.setTitle(viewJob.jobDatas.get(0).getName(), 2);
            mExpandTabView.setTitle(viewWelfare.fuliBeanList.get(0).getName(), 3);
            mSearchJobController.onEvent(city);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }

    public boolean hasHeader() {
        if (mList.getHeaderViewsCount() == 0)
            return false;
        else
            return true;
    }

    public void setAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
    }

    public void setEmptyLayout(int id) {
        mEmptyLayout.setErrorType(id);
    }

    public void startChooseLocation() {
        startActivity(ChooseCityActivity.class);
    }

    public void startSearchJob(){
        startActivity(SearchJobActivity.class);
    }
    public void startCompanyWorkDetail(CompanyJobListBean.CompanyJobBean companyJobBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.MARK_COMAPNY_WORK_DETAIL_ACTIVITY_COMPANY_DATA, companyJobBean);
        aty.skipActivityWithBundleWithOutExit(aty, CompanyWorkDetailActivity.class, bundle);
    }
}
