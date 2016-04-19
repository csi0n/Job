package com.csi0n.searchjob.ui.widget.tabview.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.ui.widget.tabview.adpter.TextAdapter;

import java.util.List;

/**
 * Created by chqss on 2016/2/22 0022.
 */
public class ViewBackMoney extends RelativeLayout implements ViewBaseAction, TextAdapter.OnItemClickListener {
    private Context mContext;
    private ListView mList;
    private TextAdapter adapter;
    private OnSelectListener mOnSelectListener;

    public ViewBackMoney(Context context) {
        super(context);
        init(context);
    }

    public ViewBackMoney(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewBackMoney(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_tabview_distance, this, true);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_mid));
        mList = (ListView) findViewById(R.id.listView);
    }

    public void setAdapterData(List<String> strings) {
        adapter = new TextAdapter(mContext, strings, R.drawable.choose_item_right, R.drawable.choose_eara_item_selector);
        adapter.setTextSize(17);
        adapter.setOnItemClickListener(this);
        mList.setAdapter(adapter);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        public void getValue(int position, String showText);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void onItemClick(View view, int position) {
        if (mOnSelectListener != null) {
            mOnSelectListener.getValue(position, adapter.getItem(position));
        }
    }
}
