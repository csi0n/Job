package com.csi0n.searchjob.ui.base.mvp;

import android.os.Bundle;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public interface IMvpPresenter {
    void create(Bundle savedInstanceState);
    void start();
    void resume();
    void pause();
    void stop();
    void destory();
}
