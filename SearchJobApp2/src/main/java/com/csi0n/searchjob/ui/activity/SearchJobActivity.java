package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.SearchJobActivityController;
import com.csi0n.searchjob.ui.widget.timeandaddresschoose.ChangeAddressDialog;

import org.xutils.DbManager;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by csi0n on 2/20/16.
 */
@ContentView(R.layout.aty_search_job)
public class SearchJobActivity extends BaseActivity {
    @ViewInject(value = R.id.edit_content)
    private EditText mContent;
    @ViewInject(value = R.id.mList)
    private ListView mList;
    @Event(value = {R.id.tv_search})
    private void onClick(View view) {
        if (mSearchJobActivityController != null)
            mSearchJobActivityController.onClick(view);
    }
    private View mFootView;
    private SearchJobActivityController mSearchJobActivityController;
    @Override
    protected void initWidget() {
        mFootView = View.inflate(this, R.layout.view_cache_list_item_footer, null);
mSearchJobActivityController=new SearchJobActivityController(this);
        mSearchJobActivityController.initSearchJob();
        mList.setOnItemClickListener(mSearchJobActivityController);
    }
    public void addFooterView() {
        mList.addFooterView(mFootView);
    }
    public void removeFooterView(){
        mList.removeFooterView(mFootView);
    }
    public void setAdapter(BaseAdapter adapter){
        mList.setAdapter(adapter);
    }
    public String getContent(){
        return mContent.getText().toString();
    }
    public void startShowSearchJobResult(String key){
        Bundle bundle=new Bundle();
        bundle.putString(Config.MARK_SHOW_SEARCH_JOB_RESULT_KEY,key);
        skipActivityWithBundle(aty,ShowSearchJobResultActivity.class,bundle);
    }
}
