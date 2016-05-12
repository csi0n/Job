package com.csi0n.searchjob.business.domain.impl;


import com.csi0n.searchjob.business.api.SearchJobApi;
import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.request.ext.GetCheckTimeOutRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetCompanyJobMainRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetConfigRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetLoginRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetSearchJobListByKeyRequest;
import com.csi0n.searchjob.business.pojo.response.ext.GetCheckTimeOutResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetCompanyJobMainResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetLoginResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobListByKeyResponse;
import com.csi0n.searchjob.core.net.NetWorkException;
import com.google.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class SearchJobDomainImpl implements SearchJobDomain {
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

    @Override
    public Observable<GetCompanyJobMainResponse> getCompanyJobMain(int page, long city_id, long area_id, int money_back, int work_type, String fuli,String configVer) {
        return Observable.just(new GetCompanyJobMainRequest(page, area_id, city_id, money_back, work_type, fuli,configVer)).flatMap(new Func1<GetCompanyJobMainRequest, Observable<GetCompanyJobMainResponse>>() {
            @Override
            public Observable<GetCompanyJobMainResponse> call(GetCompanyJobMainRequest getCompanyJobMainRequest) {
                try {
                    GetCompanyJobMainResponse response = searchJobApi.getSearchJobListResponse(getCompanyJobMainRequest);
                    return Observable.just(response);
                } catch (NetWorkException e) {
                    return Observable.error(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<GetCheckTimeOutResponse> getCheckTimeOut(String token) {
        return Observable.just(new GetCheckTimeOutRequest(token)).flatMap(new Func1<GetCheckTimeOutRequest, Observable<GetCheckTimeOutResponse>>() {
            @Override
            public Observable<GetCheckTimeOutResponse> call(GetCheckTimeOutRequest getCheckTimeOutRequest) {
                try {
                    GetCheckTimeOutResponse response=searchJobApi.getCheckTimeOutResponse(getCheckTimeOutRequest);
                    return Observable.just(response);
                }catch (NetWorkException e){
                    return Observable.error(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<GetSearchJobListByKeyResponse> getSearchJobListByKey(int page, String key,String configVer) {
        return Observable.just(new GetSearchJobListByKeyRequest(page,key,configVer)).flatMap(new Func1<GetSearchJobListByKeyRequest, Observable<GetSearchJobListByKeyResponse>>() {
            @Override
            public Observable<GetSearchJobListByKeyResponse> call(GetSearchJobListByKeyRequest getSearchJobListByKeyRequest) {
                try{
                    GetSearchJobListByKeyResponse response=searchJobApi.getSearchJobListByKeyResponse(getSearchJobListByKeyRequest);
                    return Observable.just(response);
                }catch (NetWorkException e){
                    return Observable.error(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
