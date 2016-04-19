package com.csi0n.searchjob.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by csi0n on 12/25/15.
 */
public class ScrollViewComplete extends ScrollView {
    private ScrollViewStatusListener scrollViewStatusListener;

    public interface ScrollViewStatusListener {
        void onScrollTOP();

        void onScrollBottom();

        void onOther();
    }

    public ScrollViewComplete(Context context) {
        super(context);
    }

    public ScrollViewComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewComplete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addScrollStatusListener(ScrollViewStatusListener scrollViewStatusListener) {
        this.scrollViewStatusListener = scrollViewStatusListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (scrollViewStatusListener!=null){
            if (t + getHeight() >= computeVerticalScrollRange()) {
                scrollViewStatusListener.onScrollBottom();
            } else if (getScrollY()==0){
                scrollViewStatusListener.onScrollTOP();
            }else {
                scrollViewStatusListener.onOther();
            }
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
