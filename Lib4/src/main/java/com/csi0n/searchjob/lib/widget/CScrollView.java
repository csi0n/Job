package com.csi0n.searchjob.lib.widget;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;


/**
 * Created by csi0n on 11/6/15.
 */
public class CScrollView extends ScrollView {
    private static final float MOVE_FACTOR = 0.5f; // 移动因子,手指移动100px,那么View就只移动50px
    private static final int ANIM_TIME = 300; // 松开手指后, 界面回到正常位置需要的动画时间
    private float startY;// 手指按下时的Y值, 用于在移动时计算移动距离,如果按下时不能上拉和下拉，
    // 会在手指移动时更新为当前手指的Y值
    private View contentView; // ScrollView的唯一内容控件
    private final Rect originalRect = new Rect();// 用于记录正常的布局位置
    private boolean canPullDown = false; // 是否可以继续下拉
    private boolean canPullUp = false; // 是否可以继续上拉
    private boolean isMoved = false; // 记录是否移动了布局

    public CScrollView(Context context) {
        super(context);
    }

    public CScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
        }
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        onFinishInflate();
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        onFinishInflate();
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        onFinishInflate();
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (contentView == null)
            return;
        originalRect.set(contentView.getLeft(), contentView.getTop(),
                contentView.getRight(), contentView.getBottom());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (contentView == null) {
            return super.dispatchTouchEvent(ev);
        }
        // 手指是否移动到了当前ScrollView控件之外
        boolean isTouchOutOfScrollView = ev.getY() >= this.getHeight()
                || ev.getY() <= 0;
        if (isTouchOutOfScrollView) { // 如果移动到了当前ScrollView控件之外
            if (isMoved) {// 如果当前contentView已经被移动, 首先把布局移到原位置
                boundBack();
            }
            return true;
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 判断是否可以上拉和下拉
                canPullDown = isCanPullDown();
                canPullUp = isCanPullUp();
                // 记录按下时的Y值
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                boundBack();
                if (canPullDown && (ev.getY() - startY) > 200 && pullListener != null) {
                    // 调用监听器
                    pullListener.onPull();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 在移动的过程中， 既没有滚动到可以上拉的程度， 也没有滚动到可以下拉的程度
                if (!canPullDown && !canPullUp) {
                    startY = ev.getY();
                    canPullDown = isCanPullDown();
                    canPullUp = isCanPullUp();
                    break;
                }
                // 计算手指移动的距离
                float nowY = ev.getY();
                int deltaY = (int) (nowY - startY);

                // 是否应该移动布局
                boolean shouldMove = (canPullDown && deltaY > 0) // 可以下拉， 并且手指向下移动
                        || (canPullUp && deltaY < 0) // 可以上拉， 并且手指向上移动
                        || (canPullUp && canPullDown); // 既可以上拉也可以下拉（这种情况出现在ScrollView包裹的控件比ScrollView还小）
                if (canPullDown && deltaY > 0) { // 关闭下拉
                    shouldMove = false;
                }

                if (shouldMove) {
                    // 计算偏移量
                    int offset = (int) (deltaY * MOVE_FACTOR);
                    // 随着手指的移动而移动布局
                    contentView.layout(originalRect.left,
                            originalRect.top + offset, originalRect.right,
                            originalRect.bottom + offset);
                    isMoved = true; // 记录移动了布局
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void boundBack() {
        if (!isMoved) {
            return; // 如果没有移动布局， 则跳过执行
        }
        // 开启动画
        TranslateAnimation anim = new TranslateAnimation(0, 0,
                contentView.getTop(), originalRect.top);
        anim.setDuration(ANIM_TIME);
        contentView.startAnimation(anim);
        // 设置回到正常的布局位置
        contentView.layout(originalRect.left, originalRect.top,
                originalRect.right, originalRect.bottom);
        // 将标志位设回false
        canPullDown = false;
        canPullUp = false;
        isMoved = false;
    }

    private boolean isCanPullDown() {
        return getScrollY() == 0
                || contentView.getHeight() < getHeight() + getScrollY();
    }

    private boolean isCanPullUp() {
        return contentView.getHeight() <= getHeight() + getScrollY();
    }

    public OnViewTopPull pullListener;

    public void setOnViewTopPullListener(OnViewTopPull l) {
        this.pullListener = l;
    }

    public interface OnViewTopPull {
        void onPull();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollViewListener != null)
            mScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
    }

    public void addScrollerListener(ScrollViewListener mScrollViewListener) {
        this.mScrollViewListener = mScrollViewListener;
}

    private ScrollViewListener mScrollViewListener;

    public interface ScrollViewListener {
        void onScrollChanged(CScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}