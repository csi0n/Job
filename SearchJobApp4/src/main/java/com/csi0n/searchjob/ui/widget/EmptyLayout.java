package com.csi0n.searchjob.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.core.system.SystemUtils;
import com.csi0n.searchjob.ui.base.mvp.ILoadDataView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by csi0n on 3/28/16.
 */
public class EmptyLayout extends LinearLayout implements View.OnClickListener, ILoadDataView {
    EmptyErrorType mErrorState = EmptyErrorType.NETWORK_LOADING;
    EmptyErrorType mShowError = EmptyErrorType.NETWORK_LOADING;
    OnClickListener listener;
    boolean clickEnable = true;
    String strNoDataContent = "";
    ViewHolder holder;
    boolean showError = true;

    public EmptyLayout(Context context) {
        super(context);
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //mShowError = EmptyErrorType.values()[attrs.getAttributeIntValue(R.styleable.EmptyLayout_ErrorType,0 )];
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.view_error_layout, null);
        holder = new ViewHolder(view);
        setBackgroundColor(-1);
        setOnClickListener(this);
        setErrorType(EmptyErrorType.NETWORK_LOADING);
        holder.imgErrorLayout.setOnClickListener(new OnClickListener() {
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
        mErrorState = EmptyErrorType.HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public void setTvNoDataContent() {
        if (TextUtils.isEmpty(strNoDataContent)) {
            holder.tvErrorLayout.setText("没有数据啊亲，点击重试!");
        } else {
            holder.tvErrorLayout.setText(strNoDataContent);
        }
    }

    public void setShowError(EmptyErrorType type) {
        mShowError = type;
    }


    public void setErrorType(EmptyErrorType i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case DATA_ERROR:
            case NETWORK_ERROR:
                mErrorState = EmptyErrorType.NETWORK_ERROR;
                holder.tvErrorLayout.setText("没有网络啊~");
                if (SystemUtils.isWiFi(getContext())) {
                    holder.imgErrorLayout.setBackgroundResource(R.mipmap.page_icon_network);
                } else {
                    holder.imgErrorLayout.setBackgroundResource(R.mipmap.page_icon_failed);
                }
                holder.imgErrorLayout.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.GONE);
                clickEnable = true;
                break;
            case NETWORK_LOADING:
                mErrorState = EmptyErrorType.NETWORK_LOADING;
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.imgErrorLayout.setVisibility(View.GONE);
                holder.tvErrorLayout.setText("加载中…");
                clickEnable = false;
                break;
            case NODATA:
                mErrorState = EmptyErrorType.NODATA;
                holder.imgErrorLayout.setBackgroundResource(R.mipmap.page_icon_empty);
                holder.imgErrorLayout.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            case NO_COMMENTS:
                mErrorState = EmptyErrorType.NO_COMMENTS;
                holder.imgErrorLayout.setBackgroundResource(R.mipmap.page_empty_comments);
                holder.imgErrorLayout.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.GONE);
                holder.tvErrorLayout.setText("快发表第一个评论吧!");
                clickEnable = true;
                break;
            case NO_JINGLIREN:
                mErrorState = EmptyErrorType.NO_JINGLIREN;
                holder.imgErrorLayout.setBackgroundResource(R.mipmap.page_empty_jinliren);
                holder.imgErrorLayout.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.GONE);
                holder.tvErrorLayout.setText("此公司还没有经理人!!");
                clickEnable = true;
                break;
            case NO_FRIEND_CHAT:
                holder.tvErrorLayout.setText("找个人去聊天吧!");
                mErrorState = EmptyErrorType.NO_FRIEND_CHAT;
                holder.imgErrorLayout.setBackgroundResource(R.mipmap.page_empty_chat_list);
                holder.imgErrorLayout.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.GONE);
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
            mErrorState = EmptyErrorType.HIDE_LAYOUT;
        }
        super.setVisibility(visibility);
    }

    public void reSetCount() {
        showError = true;
    }


    @Override
    public void loadstart() {

    }

    @Override
    public void showLoading() {
        showLoading(EmptyErrorType.NETWORK_LOADING);
    }

    @Override
    public void showLoading(EmptyErrorType Type) {
        if (showError)
            setErrorType(Type);
    }

    @Override
    public void hideLoading() {
        hideLoading(EmptyErrorType.HIDE_LAYOUT);
    }

    @Override
    public void hideLoading(EmptyErrorType Type) {
        if (showError)
            setErrorType(Type);
    }

    @Override
    public void showError(String message) {
        if (showError)
            setErrorType(mShowError);
    }

    @Override
    public void loadfinish() {
        showError=false;
    }


    static class ViewHolder {
        @Bind(R.id.img_error_layout)
        ImageView imgErrorLayout;
        @Bind(R.id.progressBar)
        ProgressBar progressBar;
        @Bind(R.id.tv_error_layout)
        TextView tvErrorLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
