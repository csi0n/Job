package com.csi0n.searchjob.ui.base.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.csi0n.searchjob.app.InjectHelp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class BaseMvpPresenter<V extends IMvpView> implements IMvpPresenter {
    protected ILoadDataView loadDataView;
    protected V view;
    protected Context context;
    private EventBus eventBus;

    public BaseMvpPresenter() {
        super();
    }
    public void initMvpPresenter(ILoadDataView loadDataView,V view){
        this.loadDataView=loadDataView;
        this.view=view;
        this.context=loadDataView.getContext();
        InjectHelp.injectMembersWithoutViews(this);
    }
    public void initInActivity(Bundle saveInstanceState, Intent activityIntent){

    }
    public void initInFragment(Bundle savedInstanceState,Bundle fragmentArguments){

    }
    @Override
    public void create(Bundle savedInstanceState) {
        registerEventBusListener(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destory() {
        unregisterEventBusListener(this);
        this.loadDataView = null;
        this.view = null;
        this.context = null;
        this.eventBus = null;
    }
    /**
     * 所有子类通过此方法获取EventBus，这样子类可以通过复写此方法获取自己的EventBus
     *
     * @return eventBus
     */
    public EventBus getEventBus() {
        if (eventBus == null) {
            eventBus = EventBus.getDefault();
        }
        return eventBus;
    }
    public void registerEventBusListener(Object object) {
        if (getEventBus() != null) {
            try {
                getEventBus().register(object);
            } catch (EventBusException eventBusException) {
                // 如果object没有任何onEvent等订阅，会导致EventBusException，此处try-catch防止崩溃
            }
        }
    }

    public void unregisterEventBusListener(Object object) {
        if (getEventBus() != null) {
            getEventBus().unregister(object);
        }
    }
    @Subscribe
    public void onEvent(Object object){

    }
}
