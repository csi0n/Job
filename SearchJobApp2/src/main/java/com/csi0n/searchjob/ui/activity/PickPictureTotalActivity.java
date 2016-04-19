package com.csi0n.searchjob.ui.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.AlbumListAdapter;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.utils.SortPictureList;
import com.csi0n.searchjob.utils.bean.ImageBean;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by csi0n on 2015/12/30 0030.
 */
@ContentView(R.layout.aty_pick_picture_total)
public class PickPictureTotalActivity extends BaseActivity {
    private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
    private List<ImageBean> list = new ArrayList<ImageBean>();
    private final static int SCAN_OK = 1;
    private final static int SCAN_ERROR = 2;
    private ProgressDialog mProgressDialog;
    //	private PickPictureTotalAdapter adapter;
//	private GridView mGroupGridView;
    private AlbumListAdapter adapter;
    @ViewInject(value = R.id.pick_picture_total_list_view)
    private ListView mListView;
    private Intent mIntent;
    private final MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<PickPictureTotalActivity> mActivity;

        public MyHandler(PickPictureTotalActivity activity) {
            mActivity = new WeakReference<PickPictureTotalActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PickPictureTotalActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case SCAN_OK:
                        //关闭进度条
                        activity.mProgressDialog.dismiss();
                        activity.adapter = new AlbumListAdapter(activity, activity.list = activity.subGroupOfImage(activity.mGruopMap), activity.mListView);
                        activity.mListView.setAdapter(activity.adapter);
                        break;
                    case SCAN_ERROR:
                        activity.mProgressDialog.dismiss();
                        CLog.getInstance().showError(activity.getString(R.string.sdcard_not_prepare_toast));
                        break;
                }
            }
        }
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title=getString(R.string.choose_album_title);
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getImages();
        mIntent = this.getIntent();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                List<String> childList = mGruopMap.get(list.get(position).getFolderName());
                mIntent.setClass(PickPictureTotalActivity.this, PickPictureActivity.class);
                mIntent.putStringArrayListExtra("data", (ArrayList<String>) childList);
                startActivity(mIntent);
            }
        });
    }

    @Override
    protected void initWidget() {

    }

    @Override
    protected void onPause() {
        mProgressDialog.dismiss();
        super.onPause();
    }
    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    private void getImages() {
        //显示进度条
        mProgressDialog = ProgressDialog.show(this, null, PickPictureTotalActivity.this.getString(R.string.loading));
        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = PickPictureTotalActivity.this.getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                if (mCursor == null || mCursor.getCount() == 0) {
                    myHandler.sendEmptyMessage(SCAN_ERROR);
                } else {
                    while (mCursor.moveToNext()) {
                        //获取图片的路径
                        String path = mCursor.getString(mCursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));

                        try {
                            //获取该图片的父路径名
                            String parentName = new File(path).getParentFile().getName();
                            //根据父路径名将图片放入到mGruopMap中
                            if (!mGruopMap.containsKey(parentName)) {
                                List<String> chileList = new ArrayList<String>();
                                chileList.add(path);
                                mGruopMap.put(parentName, chileList);
                            } else {
                                mGruopMap.get(parentName).add(path);
                            }
                        } catch (Exception e) {
                        }
                    }
                    mCursor.close();
                    //通知Handler扫描图片完成
                    myHandler.sendEmptyMessage(SCAN_OK);
                }
            }
        }).start();

    }


    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     *
     * @param mGruopMap
     * @return
     */
    private List<ImageBean> subGroupOfImage(HashMap<String, List<String>> mGruopMap) {
        if (mGruopMap.size() == 0) {
            return null;
        }
        List<ImageBean> list = new ArrayList<ImageBean>();

        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            ImageBean mImageBean = new ImageBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();
            SortPictureList sortList = new SortPictureList();
            Collections.sort(value, sortList);
            mImageBean.setFolderName(key);
            mImageBean.setImageCounts(value.size());
            mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片

            list.add(mImageBean);
        }

        return list;

    }
}
