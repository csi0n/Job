package com.csi0n.searchjob.controller;

import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.MessageListAdapterItem;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.ui.activity.ChatActivity;
import com.csi0n.searchjob.ui.activity.MessageListActivity;
import com.csi0n.searchjob.utils.NativeImageLoader;
import com.csi0n.searchjob.utils.bean.ChatConversationCard;
import com.csi0n.searchjob.utils.bean.MessageListRemarkBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by csi0n on 2015/12/16 0016.
 */
public class MessageListController extends BaseController implements View.OnClickListener, AdapterView.OnItemLongClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, AdapterView.OnItemClickListener {
    private MessageListActivity mMessageListActivity;
    private MessageListAdapterItem adapter;
    private AlertView alertView;
    private DbManager db = x.getDb(Config.dbConfig);
    private List<ChatConversationCard> mDatas = new ArrayList<>();
    private double mDensity;
    private int mWidth;

    public MessageListController(MessageListActivity messageListActivity) {
        this.mMessageListActivity = messageListActivity;
        DisplayMetrics dm = new DisplayMetrics();
        mMessageListActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mWidth = dm.widthPixels;
    }

    public void initMessageList() {
        adapter = new MessageListAdapterItem(mMessageListActivity);
        mMessageListActivity.mList.setAdapter(adapter);
        refreshConvList();
    }

    public void refreshConvList() {
        try {
            mDatas = db.findAll(ChatConversationCard.class);
            adapter.refresh(mDatas);
        } catch (DbException e) {
            CLog.getInstance().eMessage(e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {
        alertView = new AlertView("选择操作", null, "取消", null, new String[]{"标记已读", "删除记录"}, mMessageListActivity, AlertView.Style.ActionSheet, new OnItemClickListener() {
            public void onItemClick(Object o, int position) {
                switch (position) {
                    case 0:
                        JMessageClient.getSingleConversation(adapter.getItem(i).getUsername()).resetUnreadCount();
                        break;
                    case 1:
                        try {
                            db.delete(ChatConversationCard.class, WhereBuilder.b().and("username", "=", adapter.getItem(i).getUsername()));
                        } catch (DbException e) {
                            CLog.show(e.getMessage());
                        }
                        JMessageClient.deleteSingleConversation(adapter.getItem(i).getUsername());
                        refreshConvList();
                        break;
                }
            }
        });
        alertView.show();
        return false;
    }

    public void onEvent(ConversationRefreshEvent conversationRefreshEvent) {
        Conversation conv = conversationRefreshEvent.getConversation();
        if (conv.getType() == ConversationType.single) {
            File file = conv.getAvatarFile();
            if (file != null) {
                loadAvatarAndRefresh(conv.getTargetId(), file.getAbsolutePath());
            }
            ChatConversationCard chatConversationCard = new ChatConversationCard();
            chatConversationCard.setKey_id(((UserInfo) conv.getTargetInfo()).getUserID());
            chatConversationCard.setType(1);
            chatConversationCard.setUsername(((UserInfo) conv.getTargetInfo()).getUserName());
            chatConversationCard.setRemark(((UserInfo) conv.getTargetInfo()).getNickname());
            final UserInfo my = JMessageClient.getMyInfo();
            MessageListRemarkBean messageListRemarkBean = null;
            try {
                messageListRemarkBean = db.selector(MessageListRemarkBean.class).where(WhereBuilder.b().and("uid", "=", String.valueOf(my.getUserID())).and("fid", "=", String.valueOf(((UserInfo) conv.getTargetInfo()).getUserID()))).findFirst();
            } catch (DbException e) {
                CLog.show(e.getMessage());
            }
            if (messageListRemarkBean != null) {
                CLog.getInstance().iMessage("get remark from database");
                chatConversationCard.setRemark(messageListRemarkBean.getRemark());
            } else {
                PostParams params = getDefaultPostParams(R.string.url_getMessageListRemark);
                CLog.getInstance().iMessage("get remark from internet");
                HttpPost post=new HttpPost(params, new ObjectHttpCallBack<MessageListRemarkBean>(MessageListRemarkBean.class) {
                    @Override
                    public void SuccessResult(MessageListRemarkBean result) throws JSONException {
                        result.setUid(String.valueOf(my.getUserID()));
                            try {
                                db.save(result);
                            } catch (DbException e) {
                                CLog.getInstance().iMessage("save database error:" + e.getMessage());
                            }
                    }
                });
                post.post();
            }
        }
    }

    public void onEventMainThread(MessageEvent event) {
        Message msg = event.getMessage();
        String targetID = msg.getTargetID();
        ConversationType convType = msg.getTargetType();
        Conversation conv = null;
        if (convType == ConversationType.single) {
            conv = JMessageClient.getSingleConversation(targetID);
        }
        if (conv != null && convType == ConversationType.single) {
            //如果缓存了头像，直接刷新会话列表
            if (NativeImageLoader.getInstance().getBitmapFromMemCache(targetID) != null) {
                refreshConvList();
                //没有头像，从Conversation拿
            } else {
                File file = conv.getAvatarFile();
                //拿到后缓存并刷新
                if (file != null) {
                    loadAvatarAndRefresh(targetID, file.getAbsolutePath());
                    //conversation中没有头像，直接刷新，SDK会在后台获得头像，拿到后会执行onEvent(ConversationRefreshEvent conversationRefreshEvent)
                } else
                    refreshConvList();
            }
        } else {
            refreshConvList();
        }
    }

    public void loadAvatarAndRefresh(String targetID, String path) {
        int size = (int) (50 * mDensity);
        NativeImageLoader.getInstance().putUserAvatar(targetID, path, size);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        refreshConvList();
        bgaRefreshLayout.endRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapter.getItem(i).getType()) {
            case 1:
                Intent intent = new Intent();
                intent.setClass(mMessageListActivity, ChatActivity.class);
                intent.putExtra(Config.MARK_CHAT_ACTIVITY_IS_GROUP, false);
                intent.putExtra(Config.MARK_CHAT_ACTIVITY_TARGET_ID, adapter.getItem(i).getUsername());
                intent.putExtra(Config.MARK_CHAT_ACTIVITY_TITLE_NAME, TextUtils.isEmpty(adapter.getItem(i).getRemark()) ? adapter.getItem(i).getUsername() : adapter.getItem(i).getRemark());
                mMessageListActivity.startActivity(intent);
                break;
            case 2:
                break;
            default:
                break;
        }
    }

}
