package com.csi0n.searchjob.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.utils.SharePreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by chqss on 2016/4/19 0019.
 */
public class SearchJobReceiver  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        CLog.getInstance().iMessage("[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            CLog.getInstance().iMessage("[MyReceiver] 接收Registration Id:" + regId);
            //send the Registration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            CLog.getInstance().iMessage("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            CLog.getInstance().iMessage("[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            CLog.getInstance().iMessage("[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            CLog.getInstance().iMessage("[MyReceiver] 用户点击打开了通知");
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            CLog.getInstance().iMessage("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            CLog.getInstance().iMessage("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            CLog.getInstance().iMessage("[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }
    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    CLog.getInstance().iMessage("这个接收到的消息没有内容");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    CLog.getInstance().iMessage("printBundle json error");
                }
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String data = bundle.getString(JPushInterface.EXTRA_EXTRA);
        CLog.getInstance().iMessage("message:"+data);
        try {
            JSONObject jsonObject=new JSONObject(data);
            if (jsonObject.getInt("status")== Config.CODE_NOTICE_CONFIG_CHANGE){
                SharePreferenceManager.init(context,Config.IM_CONFIGS);
                SharePreferenceManager.setFlagIsFirstStartSearchJobFragment(true);
                CLog.getInstance().iMessage("修改用户配置文件成功!");
            }
        } catch (JSONException e) {
            CLog.getInstance().iMessage("processCustomMessage json error");
        }
        CLog.getInstance().iMessage("processCustomMessage");
    }
}
