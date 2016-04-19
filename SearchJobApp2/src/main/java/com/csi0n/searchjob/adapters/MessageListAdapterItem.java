package com.csi0n.searchjob.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.utils.NativeImageLoader;
import com.csi0n.searchjob.utils.TimeFormat;
import com.csi0n.searchjob.utils.bean.ChatConversationCard;
import com.csi0n.searchjob.utils.bean.MessageListRemarkBean;

import org.json.JSONException;
import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by csi0n on 12/23/15.
 */
public class MessageListAdapterItem extends BaseAdapter {
    public List<ChatConversationCard> mDatas;
    private Context mContext;

    public MessageListAdapterItem(Context context) {
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        if (mDatas != null && mDatas.size() > 0)
            return mDatas.size();
        else
            return 0;
    }

    @Override
    public ChatConversationCard getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.view_adapter_message_list_item, null);
            holder.mRIHead = (RoundImageView) convertView.findViewById(R.id.iv_head);
            holder.mTVTitle = (TextView) convertView.findViewById(R.id.tv_uname);
            holder.mTVContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.mTVCtime = (TextView) convertView.findViewById(R.id.tv_ctime);
            holder.mTVCount = (TextView) convertView.findViewById(R.id.txt_count);
            holder.mBGLine = (LinearLayout) convertView.findViewById(R.id.bgaline);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (getItem(position).getType() == 1) {
            final Conversation conversation = JMessageClient.getSingleConversation(getItem(position).getUsername());
            Message lastMsg = conversation.getLatestMessage();
            if (lastMsg != null) {
                TimeFormat timeFormat = new TimeFormat(mContext, lastMsg.getCreateTime());
                holder.mTVCtime.setText(timeFormat.getTime());
                // 按照最后一条消息的消息类型进行处理
                switch (lastMsg.getContentType()) {
                    case image:
                        holder.mTVContent.setText(mContext.getString(R.string.type_picture));
                        break;
                    case voice:
                        holder.mTVContent.setText(mContext.getString(R.string.type_voice));
                        break;
                    case location:
                        holder.mTVContent.setText(mContext.getString(R.string.type_location));
                        break;
                    case eventNotification:
                        holder.mTVContent.setText(mContext.getString(R.string.group_notification));
                        break;
                    case custom:
                        CustomContent content = (CustomContent) lastMsg.getContent();
                        Boolean isBlackListHint = content.getBooleanValue("blackList");
                        if (isBlackListHint != null && isBlackListHint) {
                            holder.mTVContent.setText(mContext.getString(R.string.server_803008));
                        } else holder.mTVContent.setText(mContext.getString(R.string.type_custom));
                        break;
                    default:
                        holder.mTVContent.setText(((TextContent) lastMsg.getContent()).getText());
                }
            } else {
                TimeFormat timeFormat = new TimeFormat(mContext, conversation.getLastMsgDate());
                holder.mTVCtime.setText(timeFormat.getTime());
                holder.mTVContent.setText("");
            }
            if (conversation.getType().equals(ConversationType.single)) {
                holder.mTVTitle.setText(conversation.getTitle());
                Bitmap bitmap = NativeImageLoader.getInstance().getBitmapFromMemCache(((UserInfo) conversation.getTargetInfo()).getUserName());
                UserInfo userInfo = (UserInfo) conversation.getTargetInfo();
                try {
                    showRemarkSingle(holder, String.valueOf(JMessageClient.getMyInfo().getUserID()), String.valueOf(userInfo.getUserID()));
                } catch (DbException e) {
                    CLog.getInstance().iMessage("database error:" + e.getMessage());
                }
                if (!TextUtils.isEmpty(userInfo.getNoteText()))
                    holder.mTVTitle.setText(userInfo.getNoteText());
                if (bitmap != null)
                    holder.mRIHead.setImageBitmap(bitmap);
                else holder.mRIHead.setImageResource(R.mipmap.ic_launcher);
            } else {
                holder.mRIHead.setImageResource(R.mipmap.ico_group_down);
                holder.mTVTitle.setText(conversation.getTitle());
            }
            if (conversation.getUnReadMsgCnt() > 0) {
                if (conversation.getUnReadMsgCnt() < 100)
                    holder.mTVCount.setText(String.valueOf(conversation.getUnReadMsgCnt()));
                else holder.mTVCount.setText("99");
            } else {
                holder.mTVCount.setVisibility(View.GONE);
            }
        }
        return convertView;
    }


    private void showRemarkSingle(final ViewHolder holder, final String uid, final String fid) throws DbException {
        final DbManager db = x.getDb(Config.dbConfig);
        MessageListRemarkBean messageListRemarkBean = db.selector(MessageListRemarkBean.class).where(WhereBuilder.b().and("uid", "=", uid).and("fid", "=", fid)).findFirst();
        if (messageListRemarkBean != null) {
            CLog.getInstance().iMessage("get remark from database");
            holder.mTVTitle.setText(messageListRemarkBean.getRemark());
        } else {
            PostParams params = BaseController.getStaticDefaultPostParams(R.string.url_getMessageListRemark);
            params.put("fid", fid);
            CLog.getInstance().iMessage("get remark from internet");
HttpPost post=new HttpPost(params, new ObjectHttpCallBack<MessageListRemarkBean>(MessageListRemarkBean.class) {
    @Override
    public void SuccessResult(MessageListRemarkBean result) throws JSONException {
        result.setUid(uid);
        if (result.getRemark() != null) {
            holder.mTVTitle.setText(result.getRemark());
        }
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

    public void refresh(List<ChatConversationCard> data) {
        if (mDatas != null && mDatas.size() > 0) {
            mDatas.clear();
            this.mDatas = data;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder {
        private RoundImageView mRIHead;
        private TextView mTVTitle, mTVContent, mTVCtime, mTVCount;
        private LinearLayout mBGLine;
    }
}
