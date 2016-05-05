package com.csi0n.searchjob.business.domain.impl;


import com.csi0n.searchjob.business.api.SearchJobApi;
import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.request.ext.GetConfigRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetLoginRequest;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetLoginResponse;
import com.csi0n.searchjob.core.net.NetWorkException;
import com.google.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class SearchJobDomainImpl  implements SearchJobDomain{
    @Inject
    SearchJobApi searchJobApi;

    @Override
    public Observable<GetConfigResponse> getConfig() {
        return Observable.just(new GetConfigRequest()).flatMap(new Func1<GetConfigRequest, Observable<GetConfigResponse>>() {
            @Override
            public Observable<GetConfigResponse> call(GetConfigRequest getConfigRequest) {
                try {
                    GetConfigResponse response = searchJobApi.getConfigResponse(getConfigRequest);
                    return Observable.just(response);
                } catch (NetWorkException e) {
                    return Observable.error(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<GetLoginResponse> getLogin(String username, String password) {
        return Observable.just(new GetLoginRequest(username, password)).flatMap(new Func1<GetLoginRequest, Observable<GetLoginResponse>>() {
            @Override
            public Observable<GetLoginResponse> call(GetLoginRequest getLoginRequest) {
                try {
                    GetLoginResponse response = searchJobApi.getLoginResponse(getLoginRequest);
                    return Observable.just(response);
                } catch (NetWorkException e) {
                    return Observable.error(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
