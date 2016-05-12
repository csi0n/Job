package com.csi0n.searchjob.ui.base.mvp;

import android.os.Bundle;

import com.csi0n.searchjob.business.pojo.event.ext.MainFragmentSkipEvent;
import com.csi0n.searchjob.core.log.CLog;
import com.csi0n.searchjob.ui.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public abstract class MvpActivity<P extends BaseMvpPresenter<V>, V extends IMvpView> extends BaseActivity {
    protected P presenter;
    protected V view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeMvpInit(savedInstanceState);
        onMvpInit();
        if (presenter != null) {
            //注册Activity
            presenter.initMvpPresenter(this, view);
            presenter.registerEventBusListener(this);
            presenter.create(savedInstanceState);
            presenter.initInActivity(savedInstanceState, getIntent());
        }
    }

    /**
     * 在初始化mvp前，做些事情
     *
     * @param savedInstanceState savedInstanceState
     */
    protected void beforeMvpInit(Bundle savedInstanceState) {

    }

    private void onMvpInit() {
        try {
            initPresenterAndView();
        } catch (Exception e) {
            // 防止子类未使用泛型所可能产生的意外错误
            CLog.w("onMvpInit fail, e = " + e);
        }
    }

    /**
     * 通过反射获取{@link P}和{@link V}
     */
    protected void initPresenterAndView() {
        MvpHelper<P, V> mvpHelper = new MvpHelper<>(this);
        view = getViewInstance();
        Class<P> pClass = mvpHelper.getPresenterClass();
        if (pClass != null) {
            try {
                presenter = pClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        CLog.d("view = " + view);
        CLog.d("presenter = " + presenter);
    }

    /**
     * 返回实现{@link V}的实例，默认是当前Activity
     *
     * @return {@link V}的实例
     */
    protected V getViewInstance() {
        try {
            Class<V> vClass = new MvpHelper<P, V>(this).getViewClass();
            if (vClass != null && vClass.isInstance(this)) {
                return (V) this;
            }
        } catch (Exception e) {
            CLog.w(e.toString());
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null)
            presenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null)
            presenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null)
            presenter.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.stop();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.unregisterEventBusListener(this);
            presenter.destory();
        }
        presenter = null;
        view = null;
        super.onDestroy();
    }
    @Subscribe
    public void onEvent(Object object){

    }
}
