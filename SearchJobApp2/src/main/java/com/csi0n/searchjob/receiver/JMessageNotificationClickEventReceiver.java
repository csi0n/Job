package com.csi0n.searchjob.receiver;

import android.content.Context;

import com.csi0n.searchjob.lib.utils.CLog;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by csi0n on 2015/12/14 0014.
 */
public class JMessageNotificationClickEventReceiver {
    private Context mContext;
    public JMessageNotificationClickEventReceiver(Context context) {
        mContext = context;
        JMessageClient.registerEventReceiver(this);
    }

    public void onEvent(NotificationClickEvent notificationClickEvent) {
        CLog.getInstance().iMessage("[onEvent] NotificationClickEvent !!!!");
        if (null == notificationClickEvent) {
            return;
        }
        Message msg = notificationClickEvent.getMessage();
        if (msg != null){
            String targetID = msg.getTargetID();
            ConversationType type = msg.getTargetType();
            Conversation conv;
            if (type.equals(ConversationType.single)){
                conv = JMessageClient.getSingleConversation(targetID);
            }else conv = JMessageClient.getGroupConversation(Long.parseLong(targetID));
            conv.resetUnreadCount();
            CLog.getInstance().iMessage("Conversation unread msg reset");
       /*     Intent notificationIntent = new Intent(mContext, ChatActivity.class);
//        notificationIntent.setAction(Intent.ACTION_MAIN);
            notificationIntent.putExtra("targetID", targetID);
            if (ConversationType.group == type) {
                notificationIntent.putExtra("isGroup", true);
            } else {
                notificationIntent.putExtra("isGroup", false);
            }
            notificationIntent.putExtra("fromGroup", false);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(notificationIntent);*/
        }
    }
}
