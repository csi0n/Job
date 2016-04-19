package com.csi0n.searchjob.ui.activity;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ContactsController;
import com.csi0n.searchjob.lib.widget.EmptyLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 2/21/16.
 */
@ContentView(R.layout.aty_list)
public class ContactsActivity extends BaseActivity {
    private ContactsController mContactsController;
    @ViewInject(value = R.id.refreshLayout)
    private BGARefreshLayout mBGARefreshLayout;
    @ViewInject(value = R.id.list)
    private ListView mList;
    @ViewInject(value = R.id.empty_layout)
    private EmptyLayout mEmptyLayout;

    @Event(value = {R.id.empty_layout})
    private void onClick(View view) {
        if (mContactsController != null)
            mContactsController.onClick(view);
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "通讯录";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mContactsController = new ContactsController(this);
        mContactsController.initContacts();
        mBGARefreshLayout.setDelegate(mContactsController);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
    }

    public void setAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
    }

    public void endRefresh() {
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }
    public void setEmptyLayout(int id){
        mEmptyLayout.setErrorType(id);
    }
}
