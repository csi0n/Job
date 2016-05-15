package com.csi0n.searchjob.core.io;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by chqss on 2016/5/15 0015.
 */
public class ProgressHelper {
    /**
     * 包装OkHttpClient，用于下载文件的回调
     * @param client 待包装的OkHttpClient
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient，使用clone方法返回
     */
    public static OkHttpClient addProgressResponseListener(OkHttpClient client, final ProgressListener progressListener){
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());
                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
            }
        };
        return client.newBuilder()
                .addInterceptor(interceptor)
                .build();
    }
}
