package com.csi0n.searchjob.core.net;

import com.csi0n.searchjob.core.io.FileUtils;
import com.csi0n.searchjob.core.io.ProgressHelper;
import com.csi0n.searchjob.core.io.UIProgressListener;
import com.csi0n.searchjob.core.log.CLog;
import com.csi0n.searchjob.core.string.Constants;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

/**
 * Created by chqss on 2016/5/14 0014.
 */
public class DownLoadFileUtils {
    private OkHttpClient client = new OkHttpClient();
    private String url;
    private UIProgressListener uiProgressListener;
    private Request request;

    public DownLoadFileUtils(String url, UIProgressListener uiProgressListener) {
        this.url = url;
        this.uiProgressListener = uiProgressListener;
        request = new Request.Builder()
                .url(url)
                .build();
    }

    public void start() {
        ProgressHelper.addProgressResponseListener(client, uiProgressListener).newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                CLog.e("TAG", "error ", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                for (int i = 0; i < response.headers().size(); i++) {
                    CLog.e("header-----name:"+response.headers().name(i)+"value:"+response.headers().value(i));
                }
                CLog.e(response.body().contentType().toString());
                File downloadFile=FileUtils.getSaveFile(Constants.saveFolder,"111.zip");
                BufferedSink sink= Okio.buffer(Okio.sink(downloadFile));
                sink.writeAll(response.body().source());
                sink.close();
            }
        });
    }

}
