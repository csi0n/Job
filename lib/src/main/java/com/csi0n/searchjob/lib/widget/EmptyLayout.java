package com.csi0n.searchjob.lib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csi0n.searchjob.lib.R;
import com.csi0n.searchjob.lib.utils.SystemUtils;
import com.victor.loading.book.BookLoading;

/**
 * Created by csi0n on 3/28/16.
 */
public class EmptyLayout extends LinearLayout implements View.OnClickListener {
    public static final int NETWORK_ERROR = 1; // 网络错误
    public static final int NETWORK_LOADING = 2; // 加载中
    public static final int NODATA = 3; // 没有数据
    public static final int HIDE_LAYOUT = 4; // 隐藏
    public static final int NO_FRIEND_CHAT = 5;
    public static final int NO_COMMENTS=7;
    public static final int NO_JINGLIREN=8;
    public static final int DATA_ERROR=6;
    private int mErrorState = NETWORK_LOADING;
    private OnClickListener listener;
    private boolean clickEnable = true;
    private String strNoDataContent = "";
    private TextView tv;
    public ImageView img;
    private BookLoading animProgress;

    public EmptyLayout(Context context) {
        super(context);
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.view_error_layout, null);
        img = (ImageView) view.findViewById(R.id.img_error_layout);
        tv = (TextView) view.findViewById(R.id.tv_error_layout);
        animProgress = (BookLoading) view.findViewById(R.id.animProgress);
        animProgress.start();
        setBackgroundColor(-1);
        setOnClickListener(this);
        setErrorType(NETWORK_LOADING);

        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickEnable) {
                    if (listener != null)
                        listener.onClick(v);
                }
            }
        });
        this.addView(view);
    }

    @Override
    public void onClick(View v) {
        if (clickEnable && listener != null) {
            listener.onClick(v);
        }
    }

    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == NETWORK_LOADING;
    }

    public void setErrorMessage(String msg) {
        tv.setText(msg);
    }

    public void setErrorImag(int imgResource) {
        try {
            img.setImageResource(imgResource);
        } catch (Exception e) {
        }
    }

    public void setNoDataContent(String noDataContent) {
        strNoDataContent = noDataContent;
    }

    public void setOnLayoutClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setTvNoDataContent() {
        if (TextUtils.isEmpty(strNoDataContent)) {
            tv.setText("没有数据啊亲，点击重试!");
        } else {
            tv.setText(strNoDataContent);
        }
    }


    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case DATA_ERROR:
            case NETWORK_ERROR:
                mErrorState = NETWORK_ERROR;
                tv.setText("没有网络啊~");
                if (SystemUtils.isWiFi(getContext())) {
                    img.setBackgroundResource(R.mipmap.page_icon_network);
                } else {
                    img.setBackgroundResource(R.mipmap.page_icon_failed);
                }
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                clickEnable = true;
                break;
            case NETWORK_LOADING:
                mErrorState = NETWORK_LOADING;
                animProgress.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                tv.setText("加载中…");
                clickEnable = false;
                break;
            case NODATA:
                mErrorState = NODATA;
                img.setBackgroundResource(R.mipmap.page_icon_empty);
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            case NO_COMMENTS:
                mErrorState = NODATA;
                img.setBackgroundResource(R.mipmap.bg_empty_comments);
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                tv.setText("快发表第一个评论吧!");
                clickEnable = true;
                break;
            case NO_JINGLIREN:
                mErrorState = NODATA;
                img.setBackgroundResource(R.mipmap.bg_empty_jinliren);
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                tv.setText("此公司还没有经理人!!");
                clickEnable = true;
                break;
            case NO_FRIEND_CHAT:
                tv.setText("找个人去聊天吧!");
                mErrorState = NO_FRIEND_CHAT;
                img.setBackgroundResource(R.mipmap.ico_empty_chat_list);
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                clickEnable = true;
                break;
            case HIDE_LAYOUT:
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE) {
            mErrorState = HIDE_LAYOUT;
        }
        super.setVisibility(visibility);
    }
}
