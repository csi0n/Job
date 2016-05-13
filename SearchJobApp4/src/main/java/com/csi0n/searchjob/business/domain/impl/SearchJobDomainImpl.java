package com.csi0n.searchjob.business.domain.impl;


import com.csi0n.searchjob.business.api.SearchJobApi;
import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.request.ext.GetChangeUserInfoRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetCheckTimeOutRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetCompanyCommentResultRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetCompanyJobMainRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetConfigRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetLoginRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetMyCommentsRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetSearchJobDetailARequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetSearchJobDetailBHeaderRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetSearchJobDetailBRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetSearchJobDetailCRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetSearchJobDetailDRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetSearchJobListByKeyRequest;
import com.csi0n.searchjob.business.pojo.response.ext.GetChangeUserInfoResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetCheckTimeOutResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetCompanyCommentResultResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetCompanyJobMainResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetLoginResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetMyCommentsResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailAResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailBHeaderResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailBResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailCResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailDResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobListByKeyResponse;
import com.csi0n.searchjob.core.net.NetWorkException;
import com.google.inject.Inject;

import java.io.File;

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
                } catch (NetWorkException e) {
                    return Observable.error(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<GetSearchJobDetailAResponse> getSearchJobDetailA(int company_id) {
        return Observable.just(new GetSearchJobDetailARequest(company_id)).flatMap(new Func1<GetSearchJobDetailARequest, Observable<GetSearchJobDetailAResponse>>() {
            @Override
            public Observable<GetSearchJobDetailAResponse> call(GetSearchJobDetailARequest getSearchJobDetailARequest) {
                try {
                    GetSearchJobDetailAResponse response = searchJobApi.getSearchJobDetailAResponse(getSearchJobDetailARequest);
                    return Observable.just(response);
                } catch (NetWorkException e) {
                    return Observable.error(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<GetSearchJobDetailBResponse> getSearchJobDetailB(int page, int company_id) {
        return Observable.just(new GetSearchJobDetailBRequest(page, company_id)).flatMap(new Func1<GetSearchJobDetailBRequest, Observable<GetSearchJobDetailBResponse>>() {
            @Override
            public Observable<GetSearchJobDetailBResponse> call(GetSearchJobDetailBRequest getSearchJobDetailBRequest) {
                try {
                    GetSearchJobDetailBResponse response = searchJobApi.getSearchJobDetailBResponse(getSearchJobDetailBRequest);
                    return Observable.just(response);
                } catch (NetWorkException e) {
                    return Observable.error(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<GetSearchJobDetailBHeaderResponse> getSearchJobDetailBHeader(final int company_id) {
        return Observable.just(new GetSearchJobDetailBHeaderRequest(company_id)).flatMap(new Func1<GetSearchJobDetailBHeaderRequest, Observable<GetSearchJobDetailBHeaderResponse>>() {
            @Override
            public Observable<GetSearchJobDetailBHeaderResponse> call(GetSearchJobDetailBHeaderRequest getSearchJobDetailBHeaderRequest) {
                try {
                    GetSearchJobDetailBHeaderResponse response = searchJobApi.getSearchJobDetailBHeaderResponse(getSearchJobDetailBHeaderRequest);
                    return Observable.just(response);
                }catch (NetWorkException e){
                    return Observable.error(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<GetSearchJobDetailCResponse> getSearchJobDetailC(int page, int company_id) {
        return Observable.just(new GetSearchJobDetailCRequest(page, company_id)).flatMap(new Func1<GetSearchJobDetailCRequest, Observable<GetSearchJobDetailCResponse>>() {
            @Override
            public Observable<GetSearchJobDetailCResponse> call(GetSearchJobDetailCRequest getSearchJobDetailCRequest) {
                try {
                    GetSearchJobDetailCResponse response = searchJobApi.getSearchJobDetailCResponse(getSearchJobDetailCRequest);
                    return Observable.just(response);
                } catch (NetWorkException e) {
                    return Observable.error(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<GetSearchJobDetailDResponse> getSearchJobDetailD(int page, int company_id) {
        return Observable.just(new GetSearchJobDetailDRequest(page,company_id)).flatMap(new Func1<GetSearchJobDetailDRequest, Observable<GetSearchJobDetailDResponse>>() {
            @Override
            public Observable<GetSearchJobDetailDResponse> call(GetSearchJobDetailDRequest getSearchJobDetailDRequest) {
               try {
                   GetSearchJobDetailDResponse response=searchJobApi.getSearchJobDetailDResponse(getSearchJobDetailDRequest);
                   return Observable.just(response);
               }catch (NetWorkException e){
                   return Observable.error(e);
               }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<GetCompanyCommentResultResponse> getCompanyCommentResult(int company_id, String content, long reply_uid) {
        return Observable.just(new GetCompanyCommentResultRequest(company_id,content,reply_uid)).flatMap(new Func1<GetCompanyCommentResultRequest, Observable<GetCompanyCommentResultResponse>>() {
            @Override
            public Observable<GetCompanyCommentResultResponse> call(GetCompanyCommentResultRequest getCompanyCommentResultRequest) {
                try {
                    GetCompanyCommentResultResponse response=searchJobApi.getCompanyCommentResultResponse(getCompanyCommentResultRequest);
                    return Observable.just(response);
                }catch (NetWorkException e){
                    return Observable.error(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<GetChangeUserInfoResponse> getChangeUserInfoResponse(File head, String old_pass_number, String new_pass_number, String uname, String intro, String sex, String name, String code) {
        return Observable.just(new GetChangeUserInfoRequest(head,old_pass_number,new_pass_number,uname,intro,sex,name,code)).flatMap(new Func1<GetChangeUserInfoRequest, Observable<GetChangeUserInfoResponse>>() {
            @Override
            public Observable<GetChangeUserInfoResponse> call(GetChangeUserInfoRequest getChangeUserInfoRequest) {
                try {
                    GetChangeUserInfoResponse response=searchJobApi.getChangeUserInfoResponse(getChangeUserInfoRequest);
                    return Observable.just(response);
                }catch (NetWorkException e){
                    return Observable.error(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<GetMyCommentsResponse> getMyCommentsResponse(int page) {
        return Observable.just(new GetMyCommentsRequest(page)).flatMap(new Func1<GetMyCommentsRequest, Observable<GetMyCommentsResponse>>() {
            @Override
            public Observable<GetMyCommentsResponse> call(GetMyCommentsRequest getMyCommentsRequest) {
                try {
                    GetMyCommentsResponse response=searchJobApi.getMyCommentsResponse(getMyCommentsRequest);
                    return Observable.just(response);
                }catch (NetWorkException e){
                    return Observable.error(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
