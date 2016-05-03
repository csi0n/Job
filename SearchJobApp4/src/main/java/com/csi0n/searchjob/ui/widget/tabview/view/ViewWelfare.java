package com.csi0n.searchjob.ui.widget.tabview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.pojo.model.ext.FuliModel;
import com.csi0n.searchjob.ui.widget.tabview.adpter.CheckAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chqss on 2016/2/22 0022.
 */
public class ViewWelfare extends RelativeLayout implements ViewBaseAction, View.OnClickListener {
    private Context mContext;
    private ListView mList;
    private Button mBtn;
    private CheckAdapter adapter;
    private OnSelectListener mOnSelectListener;
    public List<FuliModel> fuliBeanList = new ArrayList<>();
    private List<FuliModel> temp_fuli = new ArrayList<>();

    public ViewWelfare(Context context) {
        super(context);
        init(context);
    }

    public ViewWelfare(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewWelfare(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_tabview_welfare, this, true);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_mid));
        mList = (ListView) findViewById(R.id.listView);
        mBtn = (Button) findViewById(R.id.submit);
        mBtn.setOnClickListener(this);
        adapter = new CheckAdapter(mContext);
        adapter.listData = fuliBeanList;
        mList.setAdapter(adapter);
    }

    public void setAdapterChange() {
        adapter.notifyDataSetChanged();
    }


    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (mOnSelectListener != null) {
                    temp_fuli.clear();
                    for (int i = 0; i < adapter.listData.size(); i++) {
                        if (adapter.listData.get(i).isCheck())
                            temp_fuli.add(adapter.listData.get(i));
                    }
                    mOnSelectListener.getValue(temp_fuli);
                }
                break;
            default:
                break;
        }
    }

    public interface OnSelectListener {
        public void getValue(List<FuliModel> datas);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
}
