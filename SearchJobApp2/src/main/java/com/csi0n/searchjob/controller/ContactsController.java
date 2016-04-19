package com.csi0n.searchjob.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.provider.ContactsContract;
import android.view.View;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.ContactsAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.widget.EmptyLayout;
import com.csi0n.searchjob.ui.activity.ContactsActivity;
import com.csi0n.searchjob.utils.bean.PhoneNumberInfo;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 2/21/16.
 */
public class ContactsController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private ContactsActivity mContactsActivity;
    private int CURRENT_PAGE = 1;
    private ContactsAdapter adapter;
    private int TEMP_COUNT = 0;

    public ContactsController(ContactsActivity mContactsActivity) {
        this.mContactsActivity = mContactsActivity;
    }

    public void initContacts() {
        adapter = new ContactsAdapter(mContactsActivity);
        mContactsActivity.setAdapter(adapter);
        refresh();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.empty_layout:

                refresh();
                break;
            default:
                break;
        }
    }

    public void refresh() {
        CURRENT_PAGE = 1;
        getNumber(mContactsActivity, CURRENT_PAGE);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        refresh();
        mContactsActivity.endRefresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        if (TEMP_COUNT >= Config.DEFAULT_PAGE) {
            return true;
        } else {
            CURRENT_PAGE++;
            getNumber(mContactsActivity, CURRENT_PAGE);
            mContactsActivity.endRefresh();
            return false;
        }
    }

    public void getNumber(Context context, int page) {
        if (page==1)
            adapter.datas.clear();
        mContactsActivity.setEmptyLayout(EmptyLayout.NETWORK_LOADING);
        try {
            page--;
            int start = page * Config.DEFAULT_PAGE;
            int end = page * Config.DEFAULT_PAGE + Config.DEFAULT_PAGE;
            Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, "random() desc limit " + start + "," + end);
            String phoneNumber; //获取号码
            String phoneName; //获取名字
            TEMP_COUNT = cursor.getCount();
            while (cursor.moveToNext()) {
                phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                PhoneNumberInfo phoneInfo = new PhoneNumberInfo(phoneName, phoneNumber);
                adapter.datas.add(phoneInfo);
            }
            adapter.notifyDataSetChanged();
            mContactsActivity.setEmptyLayout(EmptyLayout.HIDE_LAYOUT);
        } catch (SQLiteException e) {
            e.printStackTrace();
            CLog.show("没发现通讯录!");
        }
    }
}
