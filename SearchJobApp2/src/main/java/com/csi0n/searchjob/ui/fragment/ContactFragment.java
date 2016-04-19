package com.csi0n.searchjob.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ContactController;
import com.csi0n.searchjob.ui.activity.AddFriendListActivity;
import com.csi0n.searchjob.ui.activity.ChatActivity;
import com.csi0n.searchjob.ui.activity.ContactsActivity;
import com.csi0n.searchjob.ui.activity.MessageListActivity;
import com.csi0n.searchjob.ui.activity.MyQunActivity;
import com.csi0n.searchjob.ui.activity.NearByPersonActivity;
import com.csi0n.searchjob.ui.activity.SearchUserActivity;
import com.csi0n.searchjob.ui.activity.SettingAndFeedActivity;
import com.csi0n.searchjob.ui.activity.UserDynamicActivity;
import com.csi0n.searchjob.ui.widget.IphoneTreeView;
import com.csi0n.searchjob.ui.widget.qr.ui.CaptureActivity;
import com.csi0n.searchjob.utils.bean.ChatConversationCard;
import com.csi0n.searchjob.utils.events.UserFriendChangeEvent;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.greenrobot.event.EventBus;

/**
 * Created by chqss on 2016/1/30 0030.
 */
@ContentView(R.layout.frag_contact)
public class ContactFragment extends BaseFragment {
    @ViewInject(R.id.list)
    public IphoneTreeView mList;
    @ViewInject(value = R.id.tv_add)
    private TextView mTVAdd;
    private ContactController mContact2Controller;
    private LinearLayout mAddFriend, mAddFriendList, mMessageList;
    private PopupWindow mMenuPopWindow;
    private View mMenuView, mListHead;
    private DbManager db = x.getDb(Config.dbConfig);

    @Event(value = {R.id.img_head_ic, R.id.tv_add})
    private void onClick(View view) {
        if (mContact2Controller != null)
            mContact2Controller.onClick(view);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initWidget() {
        mContact2Controller = new ContactController(this);
        mContact2Controller.initContact();
        EventBus.getDefault().register(this);
        mList.setOnChildClickListener(mContact2Controller);
        mMenuView = aty.getLayoutInflater().inflate(R.layout.view_message_list_drop_down_menu, null);
        mAddFriend = (LinearLayout) mMenuView.findViewById(R.id.btn_add_friends);
        mAddFriendList = (LinearLayout) mMenuView.findViewById(R.id.btn_add_friends_list);
        mMessageList = (LinearLayout) mMenuView.findViewById(R.id.btn_message_list);
        FixonClick(mAddFriend);
        FixonClick(mAddFriendList);
        FixonClick(mMessageList);
        mMenuPopWindow = new PopupWindow(mMenuView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        mMenuPopWindow.setOnDismissListener(onDismissListener);
    }

    private void FixonClick(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContact2Controller != null)
                    mContact2Controller.onClick(view);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMenuPopWindow.isShowing()) {
            mMenuPopWindow.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void showMenuPopWindow() {
        mMenuPopWindow.setTouchable(true);
        mMenuPopWindow.setOutsideTouchable(true);
        mMenuPopWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        if (mMenuPopWindow.isShowing()) {
            mMenuPopWindow.dismiss();
        } else {
            mMenuPopWindow.showAsDropDown(mTVAdd, 0, 0);
            backgroundAlpha(100);
        }
    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    };

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = aty.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        aty.getWindow().setAttributes(lp);
    }

    public void addHeadView() {
        mListHead = View.inflate(getActivity(), R.layout.view_contact2_head, null);
        FixonClick(mListHead.findViewById(R.id.ll_yun_bak));
        FixonClick(mListHead.findViewById(R.id.ll_community));
        FixonClick(mListHead.findViewById(R.id.ll_dynamic));
        FixonClick(mListHead.findViewById(R.id.ll_near_by));
        mList.addHeaderView(mListHead);
    }

    public void onEvent(UserFriendChangeEvent userFriendChangeEvent) {
        mContact2Controller.refreshContact();
    }

    public void removeHeadView() {
        mList.removeHeaderView(mListHead);
    }

    public void saveCard(float key_id, int type, String remake, String username) throws DbException {
        ChatConversationCard chatConversationCard = new ChatConversationCard();
        chatConversationCard.setKey_id(key_id);
        chatConversationCard.setType(type);
        if (remake != null)
            chatConversationCard.setRemark(remake);
        chatConversationCard.setUsername(username);
        db.saveOrUpdate(chatConversationCard);
    }

    public void startChat(final String targetID, final String remark) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ChatActivity.class);
        intent.putExtra(Config.MARK_CHAT_ACTIVITY_IS_GROUP, false);
        intent.putExtra(Config.MARK_CHAT_ACTIVITY_TARGET_ID, targetID);
        intent.putExtra(Config.MARK_CHAT_ACTIVITY_TITLE_NAME, remark);
        startActivity(intent);
    }

    public void startContacts() {
        startActivity(ContactsActivity.class);
    }

    public void startMessage() {
        startActivity(MessageListActivity.class);
    }

    public void startAddFriend() {
        startActivity(SearchUserActivity.class);
    }

    public void startQRScan() {
        startActivity(CaptureActivity.class);
    }

    public void startAddFriendList() {
        startActivity(AddFriendListActivity.class);
    }

    public void startFeedBack() {
        startActivity(SettingAndFeedActivity.class);
    }

    public void startNearBy() {
        startActivity(NearByPersonActivity.class);
    }

    public void startUserDynamic() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), UserDynamicActivity.class);
        intent.putExtra(Config.MARK_USER_DYNAMIC_ACTIVITY_IS_SELF, true);
        startActivity(intent);
    }

    public void startMyQun() {
        startActivity(MyQunActivity.class);
    }
}
