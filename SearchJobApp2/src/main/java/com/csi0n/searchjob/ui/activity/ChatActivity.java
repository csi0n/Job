package com.csi0n.searchjob.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.MsgListAdapter;
import com.csi0n.searchjob.controller.ChatController;
import com.csi0n.searchjob.ui.widget.PasteEditText;
import com.csi0n.searchjob.ui.widget.RecordVoiceButton;
import com.csi0n.searchjob.utils.BitmapLoader;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import de.greenrobot.event.EventBus;

/**
 * Created by csi0n on 2015/12/27 0027.
 */
@ContentView(R.layout.aty_chat)
public class ChatActivity extends BaseActivity {
    private ChatController mChatController;
    private GroupNameChangedReceiver mReceiver;
    @ViewInject(value = R.id.title)
    public TextView mTVTitile;
    @ViewInject(value = R.id.right_btn)
    public ImageView mBtnRight;
    @ViewInject(value = R.id.return_btn)
    public ImageButton mBackLeft;
    @ViewInject(value = R.id.recording_container)
    public View mRecordingContainer;
    @ViewInject(value = R.id.mic_image)
    public ImageView mMicImage;
    @ViewInject(value = R.id.recording_hint)
    public TextView mRecordingHint;
    @ViewInject(value = R.id.list)
    public ListView mListView;
    @ViewInject(value = R.id.et_sendmessage)
    public PasteEditText mEditTextContent;
    @ViewInject(value = R.id.btn_set_mode_keyboard)
    public View mButtonSetModeKeyboard;
    @ViewInject(value = R.id.edittext_layout)
    public RelativeLayout mEditTextLayout;
    @ViewInject(value = R.id.btn_set_mode_voice)
    public View mButtonSetModeVoice;
    @ViewInject(value = R.id.btn_send)
    public View mButtonSend;
    @ViewInject(value = R.id.record_voice)
    public RecordVoiceButton mRecordVoice;
    @ViewInject(value = R.id.btn_press_to_speak)
    public View mButtonPressToSpeak;
    @ViewInject(value = R.id.vPager)
    public ViewPager mExpressionViewpager;
    @ViewInject(value = R.id.ll_face_container)
    public LinearLayout mEmojiIconContainer;
    @ViewInject(value = R.id.ll_btn_container)
    public LinearLayout mBtnContainer;
    @ViewInject(value = R.id.iv_emoticons_normal)
    public ImageView mIv_emoticons_normal;
    @ViewInject(value = R.id.iv_emoticons_checked)
    public ImageView mIv_emoticons_checked;
    @ViewInject(value = R.id.btn_more)
    public Button mBtnMore;
    @ViewInject(value = R.id.btn_take_picture)
    public ImageView mBtn_take_picture;
    @ViewInject(value = R.id.btn_picture)
    public ImageView mBtnChoosePic;
    @ViewInject(value = R.id.more)
    public View mMore;
    public String mTargetID;
    public long mGroupID;
    public boolean mIsGroup;
    protected BaseHandler mHandler;

    @Event(value = {R.id.btn_send, R.id.iv_emoticons_checked, R.id.iv_emoticons_normal, R.id.btn_more, R.id.btn_set_mode_keyboard, R.id.btn_set_mode_voice, R.id.return_btn, R.id.btn_take_picture, R.id.btn_picture, R.id.right_btn})
    private void onClick(View view) {
        if (mChatController != null)
            mChatController.onClick(view);
    }

    public class BaseHandler extends Handler {

        @Override
        public void handleMessage(android.os.Message msg) {
            handleMsg(msg);
        }
    }

    public void handleMsg(Message msg) {
        switch (msg.what) {
            case Config.MARK_CHAT_ACTIVITY_UPDATE_CHAT_LIST_VIEW:
                mChatController.getAdapter().refresh();
                break;
            case Config.MARK_CHAT_ACTIVITY_REFRESH_GROUP_NAME:
                if (mChatController.getConversation() != null) {
                    int num = msg.getData().getInt("membersCount");
                    setChatTitle(mChatController.getConversation().getTitle(), num);
                }
                break;
            case Config.MARK_CHAT_ACTIVITY_REFRESH_GROUP_NUM:
                int num = msg.getData().getInt("membersCount");
                setChatTitle(getString(R.string.group), num);
                break;
        }
    }

    public void dismissRecordDialog() {
        mRecordVoice.dismissDialog();
    }

    public void releaseRecorder() {
        mRecordVoice.releaseRecorder();
    }

    public void releaseMediaPlayer() {
        mChatController.getAdapter().releaseMediaPlayer();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    if (RecordVoiceButton.mIsPressed) {
                        dismissRecordDialog();
                        releaseRecorder();
                        RecordVoiceButton.mIsPressed = false;
                    }
                    if (mIsGroup) {
                        Conversation conv = JMessageClient.getGroupConversation(mGroupID);
                        conv.resetUnreadCount();
                    } else {
                        Conversation conv = JMessageClient.getSingleConversation(mTargetID);
                        conv.resetUnreadCount();
                    }
                    break;
                case KeyEvent.KEYCODE_MENU:
                    // 处理自己的逻辑
                    break;
                case KeyEvent.KEYCODE_ESCAPE:

                    break;
                default:
                    break;
            }
        }

        return super.dispatchKeyEvent(event);
    }

    /**
     * 释放资源
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
        unregisterReceiver(mReceiver);
        releaseMediaPlayer();
        releaseRecorder();
    }

    @Override
    protected void onPause() {
        RecordVoiceButton.mIsPressed = false;
        JMessageClient.exitConversaion();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mChatController.getAdapter().stopMediaPlayer();
        if (mChatController.getConversation() != null)
            mChatController.getConversation().resetUnreadCount();
        super.onStop();
    }

    @Override
    protected void onResume() {
        if (!RecordVoiceButton.mIsPressed)
            dismissRecordDialog();
        String targetID = getIntent().getStringExtra(Config.MARK_CHAT_ACTIVITY_TARGET_ID);
        boolean isGroup = getIntent().getBooleanExtra(Config.MARK_CHAT_ACTIVITY_IS_GROUP, false);
        if (isGroup) {
            try {
                long groupID = getIntent().getLongExtra(Config.MARK_CHAT_ACTIVITY_GROUP_ID, 0);
                if (groupID == 0) {
                    JMessageClient.enterGroupConversation(Long.parseLong(targetID));
                } else JMessageClient.enterGroupConversation(groupID);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        } else if (null != targetID) {
            JMessageClient.enterSingleConversaion(targetID);
        }
        boolean sendPicture = getIntent().getBooleanExtra(Config.MARK_CHAT_ACTIVITY_SEND_PICTURES, false);
        if (sendPicture) {
            handleImgRefresh(getIntent(), isGroup);
            getIntent().putExtra(Config.MARK_CHAT_ACTIVITY_SEND_PICTURES, false);
        }
        mChatController.refresh();
        mChatController.getAdapter().initMediaPlayer();
        super.onResume();
    }

    /**
     * 处理发送图片，刷新界面
     *
     * @param data    intent
     * @param isGroup 是否为群聊
     */
    private void handleImgRefresh(Intent data, boolean isGroup) {
        mTargetID = data.getStringExtra(Config.MARK_CHAT_ACTIVITY_TARGET_ID);
        long groupID = data.getLongExtra(Config.MARK_CHAT_ACTIVITY_GROUP_ID, 0);
        //判断是否在当前会话中发图片
        if (mTargetID != null) {
            if (mTargetID.equals(mTargetID)) {
                // 可能因为从其他界面回到聊天界面时，MsgListAdapter已经收到更新的消息了
                // 但是ListView没有刷新消息，要重新new Adapter, 并把这个Adapter传到ChatController
                // 保证ChatActivity和ChatController使用同一个Adapter
                if (isGroup) {
                    mChatController.setAdapter(new MsgListAdapter(this, groupID, mChatController.getGroupInfo()));
                } else {
                    mChatController.setAdapter(new MsgListAdapter(this, mTargetID));
                }
                // 重新绑定Adapter
                setChatListAdapter(mChatController.getAdapter());
                mChatController.getAdapter().setSendImg(data.getIntArrayExtra(Config.MARK_CHAT_ACTIVITY_MSG_IDS));
            }
        } else if (groupID != 0) {
            if (groupID == groupID) {
                mChatController.setAdapter(new MsgListAdapter(this, groupID, mChatController.getGroupInfo()));
                // 重新绑定Adapter
                setChatListAdapter(mChatController.getAdapter());
                mChatController.getAdapter().setSendImg(data.getIntArrayExtra(Config.MARK_CHAT_ACTIVITY_MSG_IDS));
            }
        }
    }

    /**
     * 用于处理拍照发送图片返回结果
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == Config.REQUEST_CODE_TAKE_PHOTO) {
            Conversation conv = mChatController.getConversation();
            try {
                String originPath = mChatController.getPhotoPath();
                Bitmap bitmap = BitmapLoader.getBitmapFromFile(originPath, 720, 1280);
                String thumbnailPath = BitmapLoader.saveBitmapToLocal(bitmap);
                File file = new File(thumbnailPath);
                ImageContent content = new ImageContent(file);
                cn.jpush.im.android.api.model.Message msg = conv.createSendMessage(content);
                boolean isGroup = getIntent().getBooleanExtra(Config.MARK_CHAT_ACTIVITY_IS_GROUP, false);
                Intent intent = new Intent();
                intent.putExtra(Config.MARK_CHAT_ACTIVITY_MSG_IDS, new int[]{msg.getId()});
                if (conv.getType() == ConversationType.group) {
                    intent.putExtra(Config.MARK_CHAT_ACTIVITY_GROUP_ID, Long.parseLong(conv.getTargetId()));
                } else {
                    intent.putExtra(Config.MARK_CHAT_ACTIVITY_TARGET_ID, msg.getTargetID());
                }
                handleImgRefresh(intent, isGroup);
//                mChatController.refresh();
            } catch (FileNotFoundException e) {
            } catch (NullPointerException e) {
            }
        }
    }

    public void onEvent(ConversationRefreshEvent conversationRefreshEvent) {
        mHandler.sendEmptyMessage(Config.MARK_CHAT_ACTIVITY_REFRESH_GROUP_NAME);
    }

    /**
     * 接收消息类事件
     *
     * @param event 消息事件
     */
    public void onEvent(MessageEvent event) {
        cn.jpush.im.android.api.model.Message msg = event.getMessage();
        //若为群聊相关事件，如添加、删除群成员
        if (msg.getContentType() == ContentType.eventNotification) {
            long groupID = Long.parseLong(event.getMessage().getTargetID());
            UserInfo myInfo = JMessageClient.getMyInfo();
            EventNotificationContent.EventNotificationType type = ((EventNotificationContent) msg.getContent()).getEventNotificationType();
            if (type.equals(EventNotificationContent.EventNotificationType.group_member_removed)) {
                //删除群成员事件
                List<String> userNames = ((EventNotificationContent) msg.getContent()).getUserNames();
                //群主删除了当前用户，则隐藏聊天详情按钮
                if (groupID == groupID) {
                    refreshGroupNum();
                    if (userNames.contains(myInfo.getNickname()) || userNames.contains(myInfo.getUserName())) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissRightBtn();
                            }
                        });
                    }
                }
            } else {
                //添加群成员事件
                List<String> userNames = ((EventNotificationContent) msg.getContent()).getUserNames();
                //群主把当前用户添加到群聊，则显示聊天详情按钮
                if (groupID == groupID) {
                    refreshGroupNum();
                    if (userNames.contains(myInfo.getNickname()) || userNames.contains(myInfo.getUserName())) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showRightBtn();
                            }
                        });
                    }
                }
            }
        }
        //刷新消息
        mHandler.sendEmptyMessage(Config.MARK_CHAT_ACTIVITY_UPDATE_CHAT_LIST_VIEW);
    }

    private void refreshGroupNum() {
        JMessageClient.getGroupInfo(mGroupID, new GetGroupInfoCallback() {
            @Override
            public void gotResult(int status, String desc, GroupInfo groupInfo) {
                if (status == 0) {
                    if (!TextUtils.isEmpty(groupInfo.getGroupName())) {
                        mChatController.refreshGroupInfo(groupInfo);
                        android.os.Message handleMessage = mHandler.obtainMessage();
                        handleMessage.what = Config.MARK_CHAT_ACTIVITY_REFRESH_GROUP_NAME;
                        Bundle bundle = new Bundle();
                        bundle.putInt("membersCount", groupInfo.getGroupMembers().size());
                        handleMessage.setData(bundle);
                        handleMessage.sendToTarget();
                    } else {
                        android.os.Message handleMessage = mHandler.obtainMessage();
                        handleMessage.what = Config.MARK_CHAT_ACTIVITY_REFRESH_GROUP_NUM;
                        Bundle bundle = new Bundle();
                        bundle.putInt("membersCount", groupInfo.getGroupMembers().size());
                        handleMessage.setData(bundle);
                        handleMessage.sendToTarget();
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JMessageClient.registerEventReceiver(this);
        EventBus.getDefault().register(this);
        mChatController = new ChatController(this);
        mChatController.initChat();
        initReceiver();
        mHandler = new BaseHandler();
    }

    @Override
    protected void initWidget() {
        mIv_emoticons_normal.setVisibility(View.VISIBLE);
        mIv_emoticons_checked.setVisibility(View.INVISIBLE);
        mEditTextLayout.setBackgroundResource(R.drawable.input_bar_bg_normal);
        mEditTextContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    mEditTextLayout.setBackgroundResource(R.drawable.input_bar_bg_active);
                } else {
                    mEditTextLayout.setBackgroundResource(R.drawable.input_bar_bg_normal);
                }
            }
        });
        mEditTextContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextLayout.setBackgroundResource(R.drawable.input_bar_bg_active);
                mMore.setVisibility(View.GONE);
                mIv_emoticons_normal.setVisibility(View.VISIBLE);
                mIv_emoticons_checked.setVisibility(View.INVISIBLE);
                mEmojiIconContainer.setVisibility(View.GONE);
                mBtnContainer.setVisibility(View.GONE);
            }
        });
        mEditTextContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mBtnMore.setVisibility(View.GONE);
                    mButtonSend.setVisibility(View.VISIBLE);
                } else {
                    mBtnMore.setVisibility(View.VISIBLE);
                    mButtonSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initReceiver() {
        mReceiver = new GroupNameChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.MARK_CHAT_ACTIVITY_UPDATE_GROUP_NAME_ACTION);
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mReceiver, filter);
    }

    private class GroupNameChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent data) {
            if (data != null) {
                mTargetID = data.getStringExtra(Config.MARK_CHAT_ACTIVITY_TARGET_ID);
                if (data.getAction().equals(
                        Config.MARK_CHAT_ACTIVITY_UPDATE_GROUP_NAME_ACTION)) {
                    mTVTitile.setText(data.getStringExtra("newGroupName") + "(" + mChatController.getGroupMembersCount() + ")");
                } else if (data.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                    mChatController.getAdapter().setAudioPlayByEarPhone(data.getIntExtra("state", 0));
                }
            }
        }
    }

    public void dismissRightBtn() {
        mBtnRight.setVisibility(View.GONE);
    }

    public void showRightBtn() {
        mBtnRight.setVisibility(View.VISIBLE);
    }

    public void setGroupIcon() {
        mBtnRight.setImageResource(R.mipmap.ico_group_chat_detail);
    }

    public void setChatListAdapter(BaseAdapter baseAdapter) {
        mListView.setAdapter(baseAdapter);
    }

    public void setToBottom() {
        mListView.post(new Runnable() {
            @Override
            public void run() {
                mListView.setSelection(mListView.getBottom());
            }
        });
    }

    public void setExpressionAdapter(PagerAdapter pagerAdapter) {
        mExpressionViewpager.setAdapter(pagerAdapter);
    }

    public void setChatTitle(String s1, int s2) {
        mTVTitile.setText(s1 + "(" + s2 + ")");
    }

    public void requestFocuseEditTextLayout() {
        mEditTextLayout.requestFocus();
    }

    public View getmButtonSetModeKeyboard() {
        return mButtonSetModeKeyboard;
    }

    public PasteEditText getmEditTextContent() {
        return mEditTextContent;
    }

    public void StartPickPictureTotalActivity(Intent intent) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, this.getString(R.string.sdcard_not_exist_toast), Toast.LENGTH_SHORT).show();
        } else {
            intent.setClass(this, PickPictureTotalActivity.class);
            startActivity(intent);
        }
    }

    public void startShowUserInfo(String uid) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Config.MARK_USER_INFO_ACTIVITY_IS_FROM_CAPTURE, true);
        bundle.putString(Config.MARK_USER_INFO_ACTIVITY_USER_ID, uid);
        skipActivityWithBundleWithOutExit(this, UserInforActivity.class, bundle);
    }
}
