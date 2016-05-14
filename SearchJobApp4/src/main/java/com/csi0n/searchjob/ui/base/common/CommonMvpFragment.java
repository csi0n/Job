package com.csi0n.searchjob.ui.base.common;
import android.os.Bundle;
import android.view.View;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public abstract class CommonMvpFragment<P extends BaseMvpPresenter<V>,V extends IMvpView> extends MvpFragment<P,V> implements ICommonFragment{
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(savedInstanceState);
    }
    protected abstract void init(Bundle savedInstanceState);
}
