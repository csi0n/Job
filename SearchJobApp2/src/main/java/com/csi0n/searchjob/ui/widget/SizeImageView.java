package com.csi0n.searchjob.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by csi0n on 2015/9/26 0026.
 */
public class SizeImageView extends ImageView {
    private OnMeasureListener onMeasureListener;

    public void setOnMeasureListener(OnMeasureListener onMeasureListener) {
        this.onMeasureListener = onMeasureListener;
    }

    public SizeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SizeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //将图片测量的大小回调到onMeasureSize()方法中
        if(onMeasureListener != null){
            onMeasureListener.onMeasureSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public interface OnMeasureListener{
        void onMeasureSize(int width, int height);
    }
}
