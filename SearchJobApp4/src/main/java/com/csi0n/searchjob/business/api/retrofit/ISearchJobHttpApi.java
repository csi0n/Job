package com.csi0n.searchjob.business.api.retrofit;

import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by chqss on 2016/5/3 0003.
 */
public interface ISearchJobHttpApi {
    @POST
    Call<GetConfigResponse> getConfigResponse(@Field("ver") String ver);
}
