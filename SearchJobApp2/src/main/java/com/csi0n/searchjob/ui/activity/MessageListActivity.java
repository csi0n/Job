package com.csi0n.searchjob.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ListView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.MessageListController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.utils.bean.MessageListRemarkBean;
import com.csi0n.searchjob.utils.bean.ReMarkChange;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageEvent;

/**
 * Created by csi0n on 2016/1/3 0003.
 * 消息列表
 */
@ContentView(R.layout.aty_list)
public class MessageListActivity extends BaseActivity {
    @ViewInject(R.id.refreshLayout)
    public BGARefreshLayout mBGARefreshLayout;
    @ViewInject(R.id.list)
    public ListView mList;
    private MessageListController mMessageListController;
    private MessageReceive messageReceive;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = getString(R.string.str_message);
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mMessageListController = new MessageListController(this);
        mMessageListController.initMessageList();
        mBGARefreshLayout.setDelegate(mMessageListController);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(x.app(), true));
        mList.setOnItemLongClickListener(mMessageListController);
        mList.setOnItemClickListener(mMessageListController);
        JMessageClient.registerEventReceiver(this);
        registerMessageReceive();
    }

    @Override
    protected void onResume() {
        mMessageListController.refreshConvList();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        unregisterReceiver(messageReceive);
        super.onDestroy();

    }

    public void onEvent(ConversationRefreshEvent conversationRefreshEvent) {
        if (mMessageListController != null)
            mMessageListController.onEvent(conversationRefreshEvent);
    }

    public void onEventMainThread(MessageEvent event) {
        if (mMessageListController != null)
            mMessageListController.onEventMainThread(event);
    }

    private void registerMessageReceive() {
        messageReceive = new MessageReceive();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Config.MARK_MESSAGE_LIST_ACTIVITY_REMARK_CHANGE);
        registerReceiver(messageReceive, filter);
    }

    private class MessageReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Config.MARK_MESSAGE_LIST_ACTIVITY_REMARK_CHANGE.equals(intent.getAction())) {
                CLog.getInstance().iMessage("change user remark");
                ReMarkChange reMarkChange = (ReMarkChange) intent.getSerializableExtra(Config.MARK_MESSAGE_LIST_ACTIVITY_REMARK_CHANGE_DATA);
                if (reMarkChange != null)
                    try {
                        final DbManager dbManager = x.getDb(Config.dbConfig);
                        MessageListRemarkBean messageListRemark = dbManager.selector(MessageListRemarkBean.class).where(WhereBuilder.b().and("uid", "=", reMarkChange.getUid()).and("fid", "=", reMarkChange.getFid())).findFirst();
                        if (messageListRemark != null) {
                            messageListRemark.setRemark(reMarkChange.getRemark());
                            dbManager.update(messageListRemark);
                        }
                    } catch (DbException e) {
                        CLog.getInstance().iMessage("database error:" + e.getMessage());
                    }
            }
        }
    }

}
