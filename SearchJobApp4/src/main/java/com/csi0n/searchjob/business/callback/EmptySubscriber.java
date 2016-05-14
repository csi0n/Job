package com.csi0n.searchjob.business.callback;

import android.text.TextUtils;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.app.App;
import com.csi0n.searchjob.business.pojo.response.BaseResponse;
import com.csi0n.searchjob.core.log.CLog;
import com.csi0n.searchjob.core.net.NetWorkException;
import com.csi0n.searchjob.ui.base.mvp.ILoadDataView;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.lang.ref.SoftReference;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chqss on 2016/5/3 0003.
 */
public class EmptySubscriber<T extends BaseResponse> extends SimpleSubscriber<T> {
    private SoftReference<ILoadDataView> loadDataViewSoftReference;

    public EmptySubscriber() {
        this(null);
    }

    public EmptySubscriber(ILoadDataView loadDataView) {
        if (loadDataView != null) {
            this.loadDataViewSoftReference = new SoftReference<>(loadDataView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (checkLoadDataView()) {
            loadDataViewSoftReference.get().loadstart();
            loadDataViewSoftReference.get().showLoading();
        }
    }

    @Override
    public void onHandleSuccess(T response) {
        CLog.i("response = " + response);
    }

    @Override
    public void onHandleFail(String message, Throwable throwable) {
        super.onHandleFail(message, throwable);
        if (message != null) { // 业务异常
            doHandleBusinessFail(message);
        } else if (throwable != null) { // 运行异常
            doHandleException(throwable);
        } else { // 未知异常
            CLog.i("AdvancedSubscriber.onHandleFail message = null, e = null");
        }
    }

    private void doHandleBusinessFail(String msg) {
        CLog.d(this + "....doHandleBusinessFail");
        if (TextUtils.isEmpty(msg)) {
            showToast("未知错误");
        } else {
            showToast(msg);
        }
    }

    private void doHandleException(Throwable throwable) {
        CLog.d(this + "....doHandleException");
        if (throwable != null) {
            CLog.e("AdvancedSubscriber.doHandleException throwable = " + throwable.getMessage(), throwable);
        }
        String toastText = null;
        if (throwable instanceof NetWorkException) {
            NetWorkException netWorkException = (NetWorkException) throwable;
            Throwable detailException = netWorkException.getDetailThrowable();
            if (detailException instanceof ConnectException) {
                toastText = "连接失败";
            } else if (detailException instanceof UnknownHostException) {
                toastText = "未知主机";
            } else if (detailException instanceof TimeoutException || detailException instanceof InterruptedIOException) {
                toastText = "超时";
            } else if (detailException instanceof JSONException) {
                toastText = "解析错误1";
            } else if (detailException instanceof JsonParseException) {
                toastText = "解析错误2";
            }
        }
        if (TextUtils.isEmpty(toastText)) {
            showToast(R.string.network_disable);
        } else {
            showToast("[" + toastText + "]");
        }
    }

    protected void showToast(int msg) {
        showToast(App.getInstance().getString(msg));
    }

    protected void showToast(String msg) {
        CLog.d("showToast = " + msg);
        if (checkLoadDataView()) {
            loadDataViewSoftReference.get().showError(msg);
        }
    }

    @Override
    public void onHandleFinish() {
        super.onHandleFinish();
        if (checkLoadDataView()) {
            if (isSuccess())
                loadDataViewSoftReference.get().hideLoading();
            loadDataViewSoftReference.get().loadfinish();
            loadDataViewSoftReference.clear();
            loadDataViewSoftReference = null;
        }
    }

    boolean checkLoadDataView() {
        return loadDataViewSoftReference != null && loadDataViewSoftReference.get() != null;
    }
}
