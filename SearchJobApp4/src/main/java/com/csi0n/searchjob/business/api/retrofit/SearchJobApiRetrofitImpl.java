package com.csi0n.searchjob.business.api.retrofit;

import com.csi0n.searchjob.business.api.SearchJobApi;
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
import com.csi0n.searchjob.core.log.CLog;
import com.csi0n.searchjob.core.net.NetWorkException;
import com.csi0n.searchjob.core.string.Constants;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

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
        return new RetrofitAdapter<GetCheckTimeOutResponse>(){
            @Override
            GetCheckTimeOutResponse call() throws Exception {
                return httpApi.getCheckTimeOutResponse(getCheckTimeOutRequest.token).execute().body();
            }
        }.get();
    }

    @Override
    public GetSearchJobListByKeyResponse getSearchJobListByKeyResponse(final GetSearchJobListByKeyRequest getSearchJobListByKeyRequest) throws NetWorkException {
        return new RetrofitAdapter<GetSearchJobListByKeyResponse>(){
            @Override
            GetSearchJobListByKeyResponse call() throws Exception {
                return httpApi.getSearchJobListByKeyResponse(getSearchJobListByKeyRequest.page,getSearchJobListByKeyRequest.key,getSearchJobListByKeyRequest.configVer).execute().body();
            }
        }.get();
    }
}
