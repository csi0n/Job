package com.csi0n.searchjob.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.SearchJobController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by chqss on 2016/4/19 0019.
 */
@ContentView(R.layout.frag_search_job)
public class SearchJobFragment extends BaseFragment {
    @ViewInject(value = R.id.tv_location)
    private TextView mTVLocation;

    @Event(value = {R.id.tv_location})
    private void onClick(View view) {
        mSearchJobController.onClick(view);
    }

    private SearchJobController mSearchJobController;
    @Override
    protected void initWidget() {
        mSearchJobController = new SearchJobController(this);
        mSearchJobController.initSearchJob();
    }
}
