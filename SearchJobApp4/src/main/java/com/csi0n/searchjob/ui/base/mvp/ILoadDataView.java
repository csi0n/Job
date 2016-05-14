package com.csi0n.searchjob.ui.base.mvp;

import com.csi0n.searchjob.ui.widget.EmptyErrorType;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public interface ILoadDataView extends IView {
    void loadstart();
    void showLoading();
    void showLoading(EmptyErrorType Type);
    void hideLoading();
    void hideLoading(EmptyErrorType loadingType);
    void showError(String message);
    void loadfinish();
}
