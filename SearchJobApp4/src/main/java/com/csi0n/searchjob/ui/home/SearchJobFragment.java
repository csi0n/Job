package com.csi0n.searchjob.ui.home;

import android.os.Bundle;
import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.core.io.SharePreferenceManager;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;
/**
 * Created by chqss on 2016/5/1 0001.
 */
public class SearchJobFragment extends MvpFragment<SearchJobPresenter,SearchJobPresenter.ISearchJobView> {
    @Override
    protected int getFragmentLayout() {
        return R.layout.frag_search_job;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        if (SharePreferenceManager.getFlagIsFirstStartSearchJobFragment()) {
            presenter.doGetConfig().subscribe(new AdvancedSubscriber<GetConfigResponse>(){
                @Override
                public void onHandleSuccess(GetConfigResponse response) {
                    super.onHandleSuccess(response);

                }
            });
        }
    }
}
