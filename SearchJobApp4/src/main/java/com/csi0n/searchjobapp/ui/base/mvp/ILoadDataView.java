package com.csi0n.searchjobapp.ui.base.mvp;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public interface ILoadDataView extends IView {
    int LOADING_TYPE_DEFAULT = 101;
    void showLoading();
    void showLoading(int loadingType);
    void hideLoading();
    void hideLoading(int loadingType);
    void showError(String message);
}
