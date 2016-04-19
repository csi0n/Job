package com.csi0n.searchjob.lib.utils;
import android.content.Context;
import android.text.TextUtils;
import com.csi0n.searchjob.lib.utils.bean.UpdateBean;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import org.json.JSONException;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by csi0n on 2016/1/1 0001.
 */
public class CheckUpdate {
    private String APP_NAME = "search_job";
    private File folder;
    private AlertView mAlertView;
    private int url;
    private Context mContext;


    public CheckUpdate(Context context,String saveFolder,String saveAppName,int url) {
        this.mContext = context;
        final File folder = FileUtils.getSaveFolder(saveFolder);
        this.folder = folder;
        this.APP_NAME=saveAppName;
        this.url=url;
    }

    public void start() {
        PostParams params = new PostParams(StringUtils.getString(url));
        params.put("ver", StringUtils.getVersion(x.app()));
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<UpdateBean>(UpdateBean.class) {
            @Override
            public void SuccessResult(final UpdateBean result) throws JSONException {
                CLog.getInstance().iMessage("下载地址:" + result.getUrl());
                mAlertView = new AlertView("发现新版本", result.getInfo(), "取消", new String[]{"确定"}, null, mContext, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position != AlertView.CANCELPOSITION) {
                            if (!TextUtils.isEmpty(result.getUrl())) {
                                download(result.getUrl());
                            } else {
                                CLog.show("下载地址为空");
                            }
                        }
                    }
                }).setCancelable(true);
                mAlertView.show();
            }
        });
        post.post();
    }

    private void download(String url) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                FileUtils.installApk(x.app(), result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                CLog.show("开始下载!");
                File tempFile = new File(folder + "/" + APP_NAME + ".apk.tmp");
                if (tempFile.exists()) {
                    tempFile.delete();
                }
                File tempFile2 = new File(folder + "/" + APP_NAME + ".apk");
                if (tempFile2.exists()) {
                    tempFile2.delete();
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
            }
        });
    }
}
