package com.csi0n.searchjob.controller;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.SearchJobKeyAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.model.SearchJobKeyCacheModel;
import com.csi0n.searchjob.ui.activity.SearchJobActivity;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by chqss on 2016/3/23 0023.
 */
public class SearchJobActivityController extends BaseController implements AdapterView.OnItemClickListener {
    private SearchJobActivity mSearchJobActivity;
    private DbManager db = x.getDb(Config.dbConfig);
    private String WHAT = "SearchJobActivityController";
    private SearchJobKeyAdapter adapter;
    private AlertView alertView;

    public SearchJobActivityController(SearchJobActivity searchJobActivity) {
        this.mSearchJobActivity = searchJobActivity;
    }

    public void initSearchJob() {
        adapter = new SearchJobKeyAdapter(mSearchJobActivity);
        try {
            List<SearchJobKeyCacheModel> data = db.selector(SearchJobKeyCacheModel.class).where("what", "=", WHAT).findAll();
            if (data != null && data.size() > 0) {
                adapter.datas = data;
                mSearchJobActivity.addFooterView();
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
        mSearchJobActivity.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                String key = mSearchJobActivity.getContent();
                if (TextUtils.isEmpty(key)) {
                    CLog.show("请输入搜索关键字!");
                    break;
                }
                try {
                    List<SearchJobKeyCacheModel> temp = db.selector(SearchJobKeyCacheModel.class).where("key_name", "=", key).findAll();
                    if (temp != null && temp.size() > 0)
                        db.delete(SearchJobKeyCacheModel.class, WhereBuilder.b().and("key_name", "=", key));
                    SearchJobKeyCacheModel searchJobKeyCache = new SearchJobKeyCacheModel();
                    searchJobKeyCache.setKey_name(mSearchJobActivity.getContent());
                    searchJobKeyCache.setWhat(WHAT);
                    db.save(searchJobKeyCache);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                mSearchJobActivity.startShowSearchJobResult(key);
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {
        final int cache_size = adapter.datas.size();
        if (i == cache_size) {
            alertView = new AlertView("删除记录", "是否要删除记录?", "取消", new String[]{"确定"}, null, mSearchJobActivity, AlertView.Style.Alert, new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {
                    if (position != AlertView.CANCELPOSITION) {
                        try {
                            adapter.datas.clear();
                            mSearchJobActivity.removeFooterView();
                            adapter.notifyDataSetChanged();
                            db.dropTable(SearchJobKeyCacheModel.class);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).setCancelable(true);
            alertView.show();
        } else
            mSearchJobActivity.startShowSearchJobResult(adapter.getItem(i).getKey_name());
    }
}
