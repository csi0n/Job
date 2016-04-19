package com.csi0n.searchjob.controller;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.ExpressionAdapter;
import com.csi0n.searchjob.adapters.ExpressionPagerAdapter;
import com.csi0n.searchjob.adapters.MsgListAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.ui.activity.ChatActivity;
import com.csi0n.searchjob.ui.widget.ExpandGridView;
import com.csi0n.searchjob.utils.SmileUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.callback.GetGroupMembersCallback;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by csi0n on 2015/12/27 0027.
 */
public class ChatController extends BaseController {
    private ChatActivity mChatActivity;
    private Conversation mConv;
    private List<String> reslist;
    private InputMethodManager manager;
    private ClipboardManager clipboard;
    private final MyHandler myHandler = new MyHandler(this);

    public ChatController(ChatActivity chatActivity) {
        this.mChatActivity = chatActivity;
    }

    public int getGroupMembersCount() {
        if (mGroupInfo != null)
            return mGroupInfo.getGroupMembers().size();
        else return 1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_emoticons_checked:
                mChatActivity.mIv_emoticons_normal.setVisibility(View.VISIBLE);
                mChatActivity.mIv_emoticons_checked.setVisibility(View.INVISIBLE);
                mChatActivity.mBtnContainer.setVisibility(View.VISIBLE);
                mChatActivity.mEmojiIconContainer.setVisibility(View.GONE);
                mChatActivity.mMore.setVisibility(View.GONE);
                break;
            case R.id.iv_emoticons_normal:
                mChatActivity.mMore.setVisibility(View.VISIBLE);
                mChatActivity.mIv_emoticons_normal.setVisibility(View.INVISIBLE);
                mChatActivity.mIv_emoticons_checked.setVisibility(View.VISIBLE);
                mChatActivity.mBtnContainer.setVisibility(View.GONE);
                mChatActivity.mEmojiIconContainer.setVisibility(View.VISIBLE);
                hideKeyboard();
                break;
            case R.id.btn_more:
                toggleMore();
                break;
            case R.id.btn_set_mode_keyboard:
                setModeKeyboard(view);
                break;
            case R.id.btn_set_mode_voice:
                setModeVoice(view, mConv, mChatAdapter);
                break;
            case R.id.return_btn:
                mConv.resetUnreadCount();
                JMessageClient.exitConversaion();
                mChatActivity.finish();
                break;
            case R.id.btn_take_picture:
                takePhoto();
                break;
            case R.id.btn_picture:
                Intent intent = new Intent();
                if (mChatActivity.mIsGroup) {
                    intent.putExtra(Config.MARK_CHAT_ACTIVITY_GROUP_ID, mChatActivity.mGroupID);
                } else {
                    intent.putExtra(Config.MARK_CHAT_ACTIVITY_TARGET_ID, mChatActivity.mTargetID);
                }
                intent.putExtra(Config.MARK_CHAT_ACTIVITY_IS_GROUP, mChatActivity.mIsGroup);
                mChatActivity.StartPickPictureTotalActivity(intent);
                break;
            case R.id.btn_send:
                String msgContent = mChatActivity.mEditTextContent.getText().toString().trim();
                clearInput();
                mChatActivity.setToBottom();
                if (msgContent.equals("")) {
                    return;
                }
                TextContent content = new TextContent(msgContent);
                final Message msg = mConv.createSendMessage(content);
                msg.setOnSendCompleteCallback(new BasicCallback() {

                    @Override
                    public void gotResult(final int status, String desc) {
                        Log.i("ChatController", "send callback " + status + " desc " + desc);
                        if (status != 0) {
                            mChatActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //HandleResponseCode.onHandle(mContext, status, false);
                                }
                            });
                        }
                        // 发送成功或失败都要刷新一次
                        android.os.Message msg = myHandler.obtainMessage();
                        msg.what = UPDATE_CHAT_LISTVIEW;
                        Bundle bundle = new Bundle();
                        bundle.putString("desc", desc);
                        msg.setData(bundle);
                        msg.sendToTarget();
                    }
                });
                mChatAdapter.addMsgToList(msg);
                JMessageClient.sendMessage(msg);
                break;
            case R.id.right_btn:
                if (mChatActivity.mIsGroup) {
                    CLog.getInstance().iMessage("来自组群");
                } else {
                    mChatActivity.startShowUserInfo(String.valueOf(((UserInfo)mConv.getTargetInfo()).getUserID()));
                }
                break;
            default:
                break;
        }
    }

    public void clearInput() {
        mChatActivity.mEditTextContent.setText("");
    }

    private void takePhoto() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String dir = "sdcard/IM/pictures/";
            File destDir = new File(dir);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            File file = new File(dir, new DateFormat().format("yyyy_MMdd_hhmmss",
                    Calendar.getInstance(Locale.CHINA)) + ".jpg");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            setPhotoPath(file.getAbsolutePath());
            try {
                mChatActivity.startActivityForResult(intent, Config.REQUEST_CODE_TAKE_PHOTO);
            } catch (ActivityNotFoundException anf) {
                //Toast.makeText(mContext, mContext.getString(R.string.camera_not_prepared), Toast.LENGTH_SHORT).show();
            }
        } else {
            // Toast.makeText(mContext, mContext.getString(R.string.sdcard_not_exist_toast), Toast.LENGTH_SHORT).show();
        }
    }

    private void setPhotoPath(String path) {
        mPhotoPath = path;
    }

    public void setModeVoice(View view, Conversation conv, MsgListAdapter adapter) {
        hideKeyboard();
        mChatActivity.mEditTextLayout.setVisibility(View.GONE);
        mChatActivity.mMore.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        mChatActivity.mRecordVoice.initConv(conv, adapter);
        mChatActivity.mButtonSetModeKeyboard.setVisibility(View.VISIBLE);
        mChatActivity.mButtonSend.setVisibility(View.GONE);
        mChatActivity.mBtnMore.setVisibility(View.VISIBLE);
        mChatActivity.mButtonPressToSpeak.setVisibility(View.VISIBLE);
        mChatActivity.mIv_emoticons_normal.setVisibility(View.VISIBLE);
        mChatActivity.mIv_emoticons_checked.setVisibility(View.INVISIBLE);
        mChatActivity.mBtnContainer.setVisibility(View.VISIBLE);
        mChatActivity.mEmojiIconContainer.setVisibility(View.GONE);
    }

    public void setModeKeyboard(View view) {
        mChatActivity.mEditTextLayout.setVisibility(View.VISIBLE);
        mChatActivity.mMore.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        mChatActivity.mButtonSetModeVoice.setVisibility(View.VISIBLE);
        // mEditTextContent.setVisibility(View.VISIBLE);
        mChatActivity.getmEditTextContent().requestFocus();
        // buttonSend.setVisibility(View.VISIBLE);
        mChatActivity.mButtonPressToSpeak.setVisibility(View.GONE);
        if (TextUtils.isEmpty(mChatActivity.getmEditTextContent().getText())) {
            mChatActivity.mBtnMore.setVisibility(View.VISIBLE);
            mChatActivity.mButtonSend.setVisibility(View.GONE);
        } else {
            mChatActivity.mBtnMore.setVisibility(View.GONE);
            mChatActivity.mButtonSend.setVisibility(View.VISIBLE);
        }
    }

    public void toggleMore() {
        if (mChatActivity.mMore.getVisibility() == View.GONE) {
            hideKeyboard();
            mChatActivity.mMore.setVisibility(View.VISIBLE);
            mChatActivity.mBtnContainer.setVisibility(View.VISIBLE);
            mChatActivity.mEmojiIconContainer.setVisibility(View.GONE);
        } else {
            if (mChatActivity.mEmojiIconContainer.getVisibility() == View.VISIBLE) {
                mChatActivity.mEmojiIconContainer.setVisibility(View.GONE);
                mChatActivity.mBtnContainer.setVisibility(View.VISIBLE);
                mChatActivity.mIv_emoticons_normal.setVisibility(View.VISIBLE);
                mChatActivity.mIv_emoticons_checked.setVisibility(View.INVISIBLE);
            } else {
                mChatActivity.mMore.setVisibility(View.GONE);
            }
        }
    }

    private void hideKeyboard() {
        if (mChatActivity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (mChatActivity.getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(mChatActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public Conversation getConversation() {
        return mConv;
    }

    public GroupInfo getGroupInfo() {
        return mGroupInfo;
    }

    public void initChat() {
        Intent intent = mChatActivity.getIntent();
        mChatActivity.mTargetID = intent.getStringExtra(Config.MARK_CHAT_ACTIVITY_TARGET_ID);
        mChatActivity.mGroupID = intent.getLongExtra(Config.MARK_CHAT_ACTIVITY_GROUP_ID, 0);
        mChatActivity.mIsGroup = intent.getBooleanExtra(Config.MARK_CHAT_ACTIVITY_IS_GROUP, false);
        final boolean fromGroup = intent.getBooleanExtra(Config.MARK_CHAT_ACTIVITY_FROM_GROUP, false);
        if (mChatActivity.mIsGroup) {
            //判断是否从创建群组跳转过来
            if (fromGroup) {
                mChatActivity.setChatTitle(mChatActivity.getString(R.string.group), 1);
                mConv = JMessageClient.getGroupConversation(mChatActivity.mGroupID);
            } else {
                if (mChatActivity.mTargetID != null)
                    mChatActivity.mGroupID = Long.parseLong(mChatActivity.mTargetID);
                mConv = JMessageClient.getGroupConversation(mChatActivity.mGroupID);
                mChatActivity.mTVTitile.setText(mChatActivity.getString(R.string.group));
                //设置群聊聊天标题
                JMessageClient.getGroupInfo(mChatActivity.mGroupID, new GetGroupInfoCallback() {
                    @Override
                    public void gotResult(int status, String desc, GroupInfo groupInfo) {
                        if (status == 0) {
                            android.os.Message msg = myHandler.obtainMessage();
                            msg.obj = groupInfo;
                            msg.what = UPDATE_GROUP_INFO;
                            msg.sendToTarget();
                            if (!TextUtils.isEmpty(groupInfo.getGroupName())) {
                                mChatActivity.setChatTitle(groupInfo.getGroupName(), groupInfo.getGroupMembers().size());
                            } else {
                                CLog.getInstance().iMessage("GroupMember size: " + groupInfo.getGroupMembers().size());
                                mChatActivity.setChatTitle(mChatActivity.getString(R.string.group), groupInfo.getGroupMembers().size());
                            }
                        }
                    }
                });
                //判断自己如果不在群聊中，隐藏群聊详情按钮
                JMessageClient.getGroupMembers(mChatActivity.mGroupID, new GetGroupMembersCallback() {
                    @Override

                    public void gotResult(final int status, final String desc, final List<UserInfo> members) {
                        if (status == 0) {
                            List<String> userNames = new ArrayList<String>();
                            for (UserInfo info : members) {
                                userNames.add(info.getUserName());
                            }
                            //群主解散后，返回memberList为空
                            if (userNames.isEmpty()) {
                                mChatActivity.dismissRightBtn();
                                //判断自己如果不在memberList中，则隐藏聊天详情按钮
                            } else if (!userNames.contains(JMessageClient.getMyInfo().getUserName()))
                                mChatActivity.dismissRightBtn();
                            else mChatActivity.showRightBtn();
                        } else {
                            if (null == members || members.isEmpty()) {
                                mChatActivity.dismissRightBtn();
                            }
                            // HandleResponseCode.onHandle(mContext, status, false);
                        }
                    }
                });
            }
            //聊天信息标志改变
            mChatActivity.setGroupIcon();
        } else {
            // 用targetID得到会话
            Log.i("Tag", "targetID is " + mChatActivity.mTargetID);
            mConv = JMessageClient.getSingleConversation(mChatActivity.mTargetID);
            if (mConv != null) {
                mChatActivity.mTVTitile.setText(mConv.getTitle());
            }
            String remarkName = mChatActivity.getIntent().getStringExtra(Config.MARK_CHAT_ACTIVITY_TITLE_NAME);

            if (!TextUtils.isEmpty(remarkName))
                mChatActivity.mTVTitile.setText(remarkName);
        }

        // 如果之前沒有会话记录并且是群聊
        if (mConv == null && mChatActivity.mIsGroup) {
            mConv = Conversation.createConversation(ConversationType.group, mChatActivity.mGroupID);

            Log.i("ChatController", "create group success");
            // 是单聊
        } else if (mConv == null && !mChatActivity.mIsGroup) {
            mConv = Conversation.createConversation(ConversationType.single, mChatActivity.mTargetID);
            mChatActivity.mTVTitile.setText(mConv.getTitle());
        }
        if (mConv != null) {
            mConv.resetUnreadCount();
            if (mChatActivity.mIsGroup) {
                mChatAdapter = new MsgListAdapter(mChatActivity, mChatActivity.mGroupID, mGroupInfo);
            } else {
                mChatAdapter = new MsgListAdapter(mChatActivity, mChatActivity.mTargetID);
            }
            mChatActivity.setChatListAdapter(mChatAdapter);
        }
        // 滑动到底部
        mChatActivity.setToBottom();

        reslist = getExpressionRes(35);
        List<View> views = new ArrayList<View>();
        View gv1 = getGridChildView(1);
        View gv2 = getGridChildView(2);
        views.add(gv1);
        views.add(gv2);
        mChatActivity.setExpressionAdapter(new ExpressionPagerAdapter(views));
        mChatActivity.requestFocuseEditTextLayout();
        clipboard = (ClipboardManager) mChatActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        manager = (InputMethodManager) mChatActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        mChatActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private static final int UPDATE_GROUP_INFO = 1024;
    public static final int UPDATE_LAST_PAGE_LISTVIEW = 1025;
    public static final int UPDATE_CHAT_LISTVIEW = 1026;
    private GroupInfo mGroupInfo;
    private MsgListAdapter mChatAdapter;

    private static class MyHandler extends Handler {
        private final WeakReference<ChatController> mController;

        public MyHandler(ChatController controller) {
            mController = new WeakReference<ChatController>(controller);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            ChatController controller = mController.get();
            if (controller != null) {
                switch (msg.what) {
                    case UPDATE_GROUP_INFO:
                        controller.mGroupInfo = (GroupInfo) msg.obj;
                        controller.mChatAdapter.refreshGroupInfo(controller.mGroupInfo);
                        break;
                    case UPDATE_LAST_PAGE_LISTVIEW:
                        Log.i("Tag", "收到更新消息列表的消息");
                        controller.mChatAdapter.refresh();
                        //controller.mChatView.removeHeadView();
                        break;
                    case UPDATE_CHAT_LISTVIEW:
                        controller.mChatAdapter.refresh();
                        break;
                }
            }
        }
    }

    private String mPhotoPath = null;

    public void setAdapter(MsgListAdapter adapter) {
        mChatAdapter = adapter;
    }

    public String getPhotoPath() {
        return mPhotoPath;
    }

    public MsgListAdapter getAdapter() {
        return mChatAdapter;
    }

    public void refreshGroupInfo(GroupInfo groupInfo) {
        mGroupInfo = groupInfo;
        mChatAdapter.refreshGroupInfo(groupInfo);
    }

    public List<String> getExpressionRes(int getSum) {
        List<String> reslist = new ArrayList<String>();
        for (int x = 1; x <= getSum; x++) {
            String filename = "ee_" + x;
            reslist.add(filename);
        }
        return reslist;
    }

    private View getGridChildView(int i) {
        View view = View.inflate(mChatActivity, R.layout.expression_gridview, null);
        ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);
        List<String> list = new ArrayList<String>();
        if (i == 1) {
            List<String> list1 = reslist.subList(0, 20);
            list.addAll(list1);
        } else if (i == 2) {
            list.addAll(reslist.subList(20, reslist.size()));
        }
        list.add("delete_expression");
        final ExpressionAdapter expressionAdapter = new ExpressionAdapter(mChatActivity, 1, list);
        gv.setAdapter(expressionAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename = expressionAdapter.getItem(position);
                try {
                    // 文字输入框可见时，才可输入表情
                    // 按住说话可见，不让输入表情
                    if (mChatActivity.getmButtonSetModeKeyboard().getVisibility() != View.VISIBLE) {
                        if (filename != "delete_expression") { // 不是删除键，显示表情
                            // 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
                            Class clz = Class.forName("com.csi0n.im.utils.SmileUtils");
                            Field field = clz.getField(filename);
                            mChatActivity.getmEditTextContent().append(SmileUtils.getSmiledText(mChatActivity,
                                    (String) field.get(null)));
                        } else { // 删除文字或者表情
                            if (!TextUtils.isEmpty(mChatActivity.getmEditTextContent().getText())) {
                                int selectionStart = mChatActivity.getmEditTextContent().getSelectionStart();// 获取光标的位置
                                if (selectionStart > 0) {
                                    String body = mChatActivity.getmEditTextContent().getText().toString();
                                    String tempStr = body.substring(0, selectionStart);
                                    int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
                                    if (i != -1) {
                                        CharSequence cs = tempStr.substring(i, selectionStart);
                                        if (SmileUtils.containsKey(cs.toString()))
                                            mChatActivity.getmEditTextContent().getEditableText().delete(i, selectionStart);
                                        else
                                            mChatActivity.getmEditTextContent().getEditableText().delete(selectionStart - 1,
                                                    selectionStart);
                                    } else {
                                        mChatActivity.getmEditTextContent().getEditableText().delete(selectionStart - 1, selectionStart);
                                    }
                                }
                            }

                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        return view;
    }

    public void refresh() {
        mChatAdapter.refresh();
    }
}
