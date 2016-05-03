package com.csi0n.searchjob.ui.widget.tabview.adpter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.pojo.model.ext.JobTypeModel;

import java.util.List;

/**
 * Created by chqss on 2016/3/2 0002.
 */
public class JobTypeAdapter extends BaseAdapter {
    private Context mContext;
    private List<JobTypeModel> mListData;
    private int selectedPos = -1;
    private String selectedText = "";
    private int normalDrawbleId;
    private Drawable selectedDrawble;
    private float textSize;
    private View.OnClickListener onClickListener;
    private OnItemClickListener mOnItemClickListener;

    public JobTypeAdapter(Context context, List<JobTypeModel> listData, int sId, int nId) {
        mContext = context;
        mListData = listData;
        selectedDrawble = mContext.getResources().getDrawable(sId);
        normalDrawbleId = nId;
        init();
    }

    private void init() {
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPos = (Integer) view.getTag();
                setSelectedPosition(selectedPos);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, selectedPos);
                }
            }
        };
    }

    /**
     * 设置选中的position,并通知列表刷新
     */
    public void setSelectedPosition(int pos) {
        if (mListData != null && pos < mListData.size()) {
            selectedPos = pos;
            selectedText = mListData.get(pos).name;
            notifyDataSetChanged();
        }
    }

    /**
     * 设置选中的position,但不通知刷新
     */
    public void setSelectedPositionNoNotify(int pos) {
        selectedPos = pos;
        if (mListData != null && pos < mListData.size()) {
            selectedText = mListData.get(pos).name;
        }
    }

    /**
     * 获取选中的position
     */
    public int getSelectedPosition() {
        if (mListData != null && selectedPos < mListData.size()) {
            return selectedPos;
        }
        return -1;
    }

    /**
     * 设置列表字体大小
     */
    public void setTextSize(float tSize) {
        textSize = tSize;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public JobTypeModel getItem(int i) {
        return mListData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.view_adapter_jobtype_choose_item, parent, false);
        } else {
            view = (TextView) convertView;
        }
        view.setTag(position);
        String mString = "";
        if (mListData != null) {
            if (position < mListData.size()) {
                mString = mListData.get(position).name;
            }
        }
        view.setText(mString);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        if (selectedText != null && selectedText.equals(mString)) {
            view.setBackgroundDrawable(selectedDrawble);//设置选中的背景图片
        } else {
            view.setBackgroundDrawable(mContext.getResources().getDrawable(normalDrawbleId));//设置未选中状态背景图片
        }
        view.setPadding(20, 0, 0, 0);
        view.setOnClickListener(onClickListener);
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    /**
     * 重新定义菜单选项单击接口
     */
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

}
