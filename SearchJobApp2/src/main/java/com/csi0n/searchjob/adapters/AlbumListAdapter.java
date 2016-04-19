package com.csi0n.searchjob.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.ui.widget.SizeImageView;
import com.csi0n.searchjob.utils.NativeImageLoader;
import com.csi0n.searchjob.utils.bean.ImageBean;

import java.util.List;

/**
 * Created by csi0n on 2015/9/26 0026.
 */
public class AlbumListAdapter extends BaseAdapter {
    private List<ImageBean> list;
    private Point mPoint = new Point(0, 0);//用来封装ImageView的宽和高的对象
    private ListView mListView;
    protected LayoutInflater mInflater;
    private double mDensity;

    public AlbumListAdapter(Context context, List<ImageBean> list, ListView listView) {
        this.list = list;
        this.mListView = listView;
        mInflater = LayoutInflater.from(context);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
    }

    @Override
    public int getCount() {
        if (list.size() > 0)
            return list.size();
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        ImageBean mImageBean = list.get(position);
        String path = mImageBean.getTopImagePath();
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_pick_picture_total_list, null);
            viewHolder.mImageView = (SizeImageView) convertView.findViewById(R.id.group_image);
            viewHolder.mTextViewTitle = (TextView) convertView.findViewById(R.id.group_title);
            viewHolder.mTextViewCounts = (TextView) convertView.findViewById(R.id.group_count);

            //用来监听ImageView的宽和高
            viewHolder.mImageView.setOnMeasureListener(new SizeImageView.OnMeasureListener() {

                @Override
                public void onMeasureSize(int width, int height) {
                    mPoint.set(width, height);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mImageView.setImageResource(R.mipmap.ico_friends_sends_pictures_no);
        }

        viewHolder.mTextViewTitle.setText(mImageBean.getFolderName());
        viewHolder.mTextViewCounts.setText("(" + Integer.toString(mImageBean.getImageCounts()) + ")");
        //给ImageView设置路径Tag,这是异步加载图片的小技巧
        viewHolder.mImageView.setTag(path);
        //利用NativeImageLoader类加载本地图片
        Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, (int) (80 * mDensity), new NativeImageLoader.NativeImageCallBack() {

            @Override
            public void onImageLoader(Bitmap bitmap, String path) {
                ImageView mImageView = (ImageView) mListView.findViewWithTag(path);
                if (bitmap != null && mImageView != null) {
                    mImageView.setImageBitmap(bitmap);
                }
            }
        });

        if (bitmap != null) {
            viewHolder.mImageView.setImageBitmap(bitmap);
        } else {
            viewHolder.mImageView.setImageResource(R.mipmap.ico_friends_sends_pictures_no);
        }
        return convertView;
    }

    public static class ViewHolder {
        public SizeImageView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewCounts;
    }
}
