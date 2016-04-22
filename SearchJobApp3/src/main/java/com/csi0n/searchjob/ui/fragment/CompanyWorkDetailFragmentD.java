package com.csi0n.searchjob.ui.fragment;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.CompanyWorkDetailDController;
import com.csi0n.searchjob.model.CompanyJobListModel;
import com.csi0n.searchjob.utils.BGANormalRefreshViewHolder;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by chqss on 2016/2/23 0023.
 */
@ContentView(R.layout.frag_list)
public class CompanyWorkDetailFragmentD extends BaseFragment {
    @ViewInject(value = R.id.refreshLayout)
    private BGARefreshLayout mBGARefreshLayout;
    @ViewInject(value = R.id.list)
    private ListView mList;
    private CompanyWorkDetailDController mCompanyWorkDetailDController;
    private CompanyJobListModel.CompanyJobModel companyJobBean;
    @Override
    protected void initWidget() {
        Bundle bundle = aty.getIntent().getExtras();
        if (bundle != null)
            companyJobBean = (CompanyJobListModel.CompanyJobModel) bundle.getSerializable(Config.MARK_COMAPNY_WORK_DETAIL_ACTIVITY_COMPANY_DATA);
        mCompanyWorkDetailDController = new CompanyWorkDetailDController(this);
        mCompanyWorkDetailDController.initCompanyWorkDetailD();
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(aty, false));
        mBGARefreshLayout.setDelegate(mCompanyWorkDetailDController);
    }
    public void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }

    public CompanyJobListModel.CompanyJobModel getCompanyJobBean() {
        return companyJobBean;
    }

    public void setAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
    }
}
