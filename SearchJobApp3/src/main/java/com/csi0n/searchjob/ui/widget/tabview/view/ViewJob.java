package com.csi0n.searchjob.ui.widget.tabview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.JobTypeAdapter;
import com.csi0n.searchjob.model.JobTypeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chqss on 2016/2/22 0022.
 */
public class ViewJob extends RelativeLayout implements ViewBaseAction, JobTypeAdapter.OnItemClickListener {
    private Context mContext;
    private ListView mList;
    private JobTypeAdapter adapter;
    private OnSelectListener mOnSelectListener;
    public List<JobTypeModel> jobDatas = new ArrayList<>();

    public ViewJob(Context context) {
        super(context);
        init(context);
    }

    public ViewJob(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewJob(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_tabview_distance, this, true);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_mid));
        mList = (ListView) findViewById(R.id.listView);
        adapter = new JobTypeAdapter(mContext, jobDatas, R.drawable.choose_item_right, R.drawable.choose_eara_item_selector);
        adapter.setTextSize(17);
        adapter.setOnItemClickListener(this);
        mList.setAdapter(adapter);
    }

    public void setAdapterChange() {
        adapter.notifyDataSetChanged();
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mOnSelectListener != null) {
            mOnSelectListener.getValue(position, adapter.getItem(position));
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    public interface OnSelectListener {
        public void getValue(int position, JobTypeModel datas);

    }

}
