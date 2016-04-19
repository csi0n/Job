package com.csi0n.searchjob.ui.widget;

import android.widget.GridLayout;
import android.widget.GridView;

/**
 * Created by csi0n on 2015/12/17 0017.
 */
public class NoScrollGridView extends GridView {
    public NoScrollGridView(android.content.Context context,
                      android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
