package com.csi0n.searchjob.business.api.retrofit;

import com.csi0n.searchjob.business.api.SearchJobApi;
import com.csi0n.searchjob.business.pojo.request.ext.GetChangeUserInfoRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetCheckTimeOutRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetCheckUserAppVerRequest;
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
import com.csi0n.searchjob.business.pojo.response.ext.GetCheckUserAppVerResponse;
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
import com.csi0n.searchjob.core.io.FileUtils;
import com.csi0n.searchjob.core.log.CLog;
import com.csi0n.searchjob.core.net.NetWorkException;
import com.csi0n.searchjob.core.string.Constants;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class SearchJobApiRetrofitImpl implements SearchJobApi {
    ISearchJobHttpApi httpApi;

    public SearchJobApiRetrofitImpl() {
        OkHttpClient.Builder builder= new OkHttpClient.Builder();
        int cacheSize=1000*1024*1024;//1000Mib
        Cache cache=new Cache(FileUtils.getSaveFolder(Constants.cacheFolder),cacheSize);
        builder.cache(cache);
        builder.readTimeout(15, TimeUnit.MINUTES);
        builder.connectTimeout(15,TimeUnit.MINUTES);
        builder.writeTimeout(15, TimeUnit.MINUTES);
        builder.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request=chain.request();
                Response response=chain.proceed(request);
                CLog.i("Interceptor:request = %s, response = %s", request, response);
                return response;
            }
        });
        httpApi=new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ISearchJobHttpApi.class);
    }

    @Override
    public GetConfigResponse getConfigResponse(final GetConfigRequest getConfigRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.getConfigResponse request = " + getConfigRequest);
        return new RetrofitAdapter<GetConfigResponse>() {
            @Override
            GetConfigResponse call() throws Exception {
                return httpApi.getConfigResponse(getConfigRequest.version).execute().body();
            }
        }.get();
    }

    @Override
    public GetLoginResponse getLoginResponse(final GetLoginRequest getLoginRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.getLoginResponse request = " + getLoginRequest);
        return new RetrofitAdapter<GetLoginResponse>() {
            @Override
            GetLoginResponse call() throws Exception {
                return httpApi.getLoginResponse(getLoginRequest.username,getLoginRequest.password).execute().body();
            }
        }.get();
    }

    @Override
    public GetCompanyJobMainResponse getSearchJobListResponse(final GetCompanyJobMainRequest getCompanyJobMainRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.getSearchJobListResponse request = " + getCompanyJobMainRequest);
        return new RetrofitAdapter<GetCompanyJobMainResponse>() {
            @Override
            GetCompanyJobMainResponse call() throws Exception {
                return httpApi.getSearchJobListResponse(getCompanyJobMainRequest.page,getCompanyJobMainRequest.city_id,getCompanyJobMainRequest.area_id,getCompanyJobMainRequest.money_back,getCompanyJobMainRequest.work_type,getCompanyJobMainRequest.fuli,getCompanyJobMainRequest.configVer).execute().body();
            }
        }.get();
    }

    @Override
    public GetCheckTimeOutResponse getCheckTimeOutResponse(final GetCheckTimeOutRequest getCheckTimeOutRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.getCheckTimeOutResponse request = " + getCheckTimeOutRequest);
        return new RetrofitAdapter<GetCheckTimeOutResponse>(){
            @Override
            GetCheckTimeOutResponse call() throws Exception {
                return httpApi.getCheckTimeOutResponse(getCheckTimeOutRequest.token).execute().body();
            }
        }.get();
    }

    @Override
    public GetSearchJobListByKeyResponse getSearchJobListByKeyResponse(final GetSearchJobListByKeyRequest getSearchJobListByKeyRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.GetSearchJobListByKeyRequest request = " + getSearchJobListByKeyRequest);
        return new RetrofitAdapter<GetSearchJobListByKeyResponse>(){
            @Override
            GetSearchJobListByKeyResponse call() throws Exception {
                return httpApi.getSearchJobListByKeyResponse(getSearchJobListByKeyRequest.page,getSearchJobListByKeyRequest.key,getSearchJobListByKeyRequest.configVer).execute().body();
            }
        }.get();
    }

    @Override
    public GetSearchJobDetailAResponse getSearchJobDetailAResponse(final GetSearchJobDetailARequest getSearchJobDetailARequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.GetSearchJobDetailARequest request = " + getSearchJobDetailARequest);
        return new RetrofitAdapter<GetSearchJobDetailAResponse>() {
            @Override
            GetSearchJobDetailAResponse call() throws Exception {
                return httpApi.getSearchJobDetailAResponse(getSearchJobDetailARequest.company_id).execute().body();
            }
        }.get();
    }

    @Override
    public GetSearchJobDetailBResponse getSearchJobDetailBResponse(final GetSearchJobDetailBRequest getSearchJobDetailBRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.getSearchJobDetailBResponse request = " + getSearchJobDetailBRequest);
        return new RetrofitAdapter<GetSearchJobDetailBResponse>(){
            @Override
            GetSearchJobDetailBResponse call() throws Exception {
                return httpApi.getSearchJobDetailBResponse(getSearchJobDetailBRequest.page,getSearchJobDetailBRequest.company_id).execute().body();
            }
        }.get();
    }

    @Override
    public GetSearchJobDetailBHeaderResponse getSearchJobDetailBHeaderResponse(final GetSearchJobDetailBHeaderRequest getSearchJobDetailBHeaderRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.getSearchJobDetailBHeaderResponse request = " + getSearchJobDetailBHeaderRequest);
        return new RetrofitAdapter<GetSearchJobDetailBHeaderResponse>(){
            @Override
            GetSearchJobDetailBHeaderResponse call() throws Exception {
                return httpApi.getSearchJobDetailBHeaderResponse(getSearchJobDetailBHeaderRequest.company_id).execute().body();
            }
        }.get();
    }

    @Override
    public GetSearchJobDetailCResponse getSearchJobDetailCResponse(final GetSearchJobDetailCRequest getSearchJobDetailCRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.getSearchJobDetailCResponse request = " + getSearchJobDetailCRequest);
        return new RetrofitAdapter<GetSearchJobDetailCResponse>() {
            @Override
            GetSearchJobDetailCResponse call() throws Exception {
                return httpApi.getSearchJobDetailCResponse(getSearchJobDetailCRequest.page,getSearchJobDetailCRequest.company_id).execute().body();
            }
        }.get();
    }

    @Override
    public GetSearchJobDetailDResponse getSearchJobDetailDResponse(final GetSearchJobDetailDRequest getSearchJobDetailDRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.getSearchJobDetailDResponse request = " + getSearchJobDetailDRequest);
        return new RetrofitAdapter<GetSearchJobDetailDResponse>() {
            @Override
            GetSearchJobDetailDResponse call() throws Exception {
                return httpApi.getSearchJobDetailDResponse(getSearchJobDetailDRequest.page,getSearchJobDetailDRequest.company_id).execute().body();
            }
        }.get();
    }

    @Override
    public GetCompanyCommentResultResponse getCompanyCommentResultResponse(final GetCompanyCommentResultRequest getCompanyCommentResultRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.getCompanyCommentResultResponse request = " + getCompanyCommentResultRequest);
        return new RetrofitAdapter<GetCompanyCommentResultResponse>() {
            @Override
            GetCompanyCommentResultResponse call() throws Exception {
                return httpApi.getCompanyCommentResultResponse(getCompanyCommentResultRequest.company_id,getCompanyCommentResultRequest.content,getCompanyCommentResultRequest.reply_uid,getCompanyCommentResultRequest.token).execute().body();
            }
        }.get();
    }

    @Override
    public GetChangeUserInfoResponse getChangeUserInfoResponse(final GetChangeUserInfoRequest getChangeUserInfoRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.getChangeUserInfoResponse request = " + getChangeUserInfoRequest);
        return new RetrofitAdapter<GetChangeUserInfoResponse>() {
            @Override
            GetChangeUserInfoResponse call() throws Exception {
                return httpApi.getChangeUserInfoResponse(getChangeUserInfoRequest.old_pass_word,getChangeUserInfoRequest.new_pass_word,getChangeUserInfoRequest.uname,getChangeUserInfoRequest.intro,getChangeUserInfoRequest.sex,getChangeUserInfoRequest.name,getChangeUserInfoRequest.code,getChangeUserInfoRequest.token).execute().body();
            }
        }.get();
    }

    @Override
    public GetChangeUserInfoResponse getChangeUserInfoHeadResponse(final GetChangeUserInfoRequest getChangeUserInfoRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.getChangeUserInfoHeadResponse request = " + getChangeUserInfoRequest);
        return new RetrofitAdapter<GetChangeUserInfoResponse>() {
            @Override
            GetChangeUserInfoResponse call() throws Exception {
                return httpApi.getChangeUserInfoHead(getChangeUserInfoRequest.head,getChangeUserInfoRequest.token).execute().body();
            }
        }.get();
    }

    @Override
    public GetMyCommentsResponse getMyCommentsResponse(final GetMyCommentsRequest getMyCommentsRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.GetMyCommentsRequest request = " + getMyCommentsRequest);
        return new RetrofitAdapter<GetMyCommentsResponse>() {
            @Override
            GetMyCommentsResponse call() throws Exception {
                return httpApi.getMyCommentsResponse(getMyCommentsRequest.page,getMyCommentsRequest.token).execute().body();
            }
        }.get();
    }

    @Override
    public GetCheckUserAppVerResponse getCheckUserAppVerResponse(final GetCheckUserAppVerRequest getCheckUserAppVerRequest) throws NetWorkException {
        CLog.i("SearchJobApiRetrofitImpl.GetCheckUserAppVerResponse request = " + getCheckUserAppVerRequest);
        return new RetrofitAdapter<GetCheckUserAppVerResponse>() {
            @Override
            GetCheckUserAppVerResponse call() throws Exception {
                return httpApi.getCheckUserAppVerResponse(getCheckUserAppVerRequest.version).execute().body();
            }
        }.get();
    }
}
