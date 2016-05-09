package com.csi0n.searchjob.business.api.retrofit;

import com.csi0n.searchjob.business.pojo.response.ext.GetCompanyJobMainResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetLoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by chqss on 2016/5/3 0003.
 */
public interface ISearchJobHttpApi {
    @FormUrlEncoded
    @POST("api.php/User/Public/config")
    Call<GetConfigResponse> getConfigResponse(@Field("ver") String ver);
    @FormUrlEncoded
    @POST("api.php/User/Oauth/authorize")
    Call<GetLoginResponse> getLoginResponse(@Field("username")String username,@Field("password")String password);
    @FormUrlEncoded
    @POST("api.php/User/Public/searchJobList")
    Call<GetCompanyJobMainResponse> getSearchJobListResponse(@Field("page")int page,@Field("city_id")String city_id,@Field("area_id")String area_id,@Field("money_back")String money_back,@Field("work_type")String work_type,@Field("fuli")String fuli);
}
