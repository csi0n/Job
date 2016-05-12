package com.csi0n.searchjob.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.core.io.DbManager;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.database.dao.KeyValue;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.ui.adapter.SearchJobKeyAdapter;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
import roboguice.inject.ContentView;

/**
 * Created by chqss on 2016/5/12 0012.
 */
@ContentView(R.layout.aty_search_job)
public class SearchJobActivity extends MvpActivity<SearchJobPresenter, SearchJobPresenter.ISearchJobPresenterView> {
    @Bind(R.id.edit_content)
    EditText editContent;
    @Bind(R.id.mList)
    ListView mList;

    @OnItemClick(R.id.mList)
    void onItemClick(int position) {
        if (position == adapter.datas.size()) {
            AlertView alertView = new AlertView("删除记录", "是否要删除记录?", "取消", new String[]{"确定"}, null, this, AlertView.Style.Alert, new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {
                    if (position != AlertView.CANCELPOSITION) {
                        adapter.datas.clear();
                        removeFooterView();
                        adapter.notifyDataSetChanged();
                        DbManager.clearValueByKey("search");
                    }
                }
            }).setCancelable(true);
            alertView.show();
        } else
            startShowSearchJobResult(adapter.datas.get(position).getValue());
    }

    SearchJobKeyAdapter adapter;

    @OnClick(value = {R.id.tv_search, R.id.tv_back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                String content = getContent();
                if (!TextUtils.isEmpty(content))
                    doSearch(content);
                else
                    showError("请输入关键字!");
                break;
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "搜索";
        super.setActionBarRes(actionBarRes);
    }

    private View mFootView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void doSearch(String vaule) {
        KeyValue keyValue = new KeyValue("search", vaule);
        DbManager.insertKeyValue(keyValue);
        startShowSearchJobResult(vaule);
    }

    void init() {
        mFootView = View.inflate(this, R.layout.view_cache_list_item_footer, null);
        adapter = new SearchJobKeyAdapter(this);
        List<KeyValue> searchKeys = DbManager.getAllKeyValueByKey("search");
        if (!searchKeys.isEmpty()) {
            adapter.datas = searchKeys;
            addFooterView();
        }
        mList.setAdapter(adapter);
    }

    void addFooterView() {
        mList.addFooterView(mFootView);
    }

    void removeFooterView() {
        mList.removeFooterView(mFootView);
    }

    String getContent() {
        return editContent.getText().toString();
    }

    void startShowSearchJobResult(String key) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.MARK_SHOW_SEARCH_JOB_ACTIVITY_KEY, key);
        skipActivityWithBundleWithOutExit(this, ShowSearchJobResultActivity.class, bundle);
    }
}
