package com.csi0n.searchjob.business.api.retrofit;

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

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    Call<GetCompanyJobMainResponse> getSearchJobListResponse(@Field("page")int page,@Field("city_id")long city_id,@Field("area_id")long area_id,@Field("money_back")int money_back,@Field("work_type")int work_type,@Field("fuli")String fuli,@Field("ver")String ver);
    @FormUrlEncoded
    @POST("api.php/User/Oauth/checkTimeOut")
    Call<GetCheckTimeOutResponse> getCheckTimeOutResponse(@Field("token")String token);
    @FormUrlEncoded
    @POST("api.php/User/Public/searchJobList")
    Call<GetSearchJobListByKeyResponse> getSearchJobListByKeyResponse(@Field("page")int page,@Field("key")String key,@Field("ver")String ver);
    @FormUrlEncoded
    @POST("api.php/User/Public/searchJobDetailA")
    Call<GetSearchJobDetailAResponse> getSearchJobDetailAResponse(@Field("company_id")int company_id);
    @FormUrlEncoded
    @POST("api.php/User/Public/searchJobDetailB")
    Call<GetSearchJobDetailBResponse> getSearchJobDetailBResponse(@Field("page")int page,@Field("company_id")int company_id);
    @FormUrlEncoded
    @POST("api.php/User/Public/searchJobDetailBHeader")
    Call<GetSearchJobDetailBHeaderResponse> getSearchJobDetailBHeaderResponse(@Field("company_id")int company_id);
    @FormUrlEncoded
    @POST("api.php/User/Public/searchJobDetailC")
    Call<GetSearchJobDetailCResponse> getSearchJobDetailCResponse(@Field("page")int page, @Field("company_id")int company_id);
    @FormUrlEncoded
    @POST("api.php/User/Public/searchJobDetailD")
    Call<GetSearchJobDetailDResponse> getSearchJobDetailDResponse(@Field("page")int page, @Field("company_id")int company_id);
    @FormUrlEncoded
    @POST("api.php/User/Comments/insert")
    Call<GetCompanyCommentResultResponse> getCompanyCommentResultResponse(@Field("company_id")int company_id,@Field("content")String content,@Field("reply_uid")long reply_uid,@Field("token")String token);
    @FormUrlEncoded
    @Multipart
    @POST("api.php/User/User/changeInfo")
    Call<GetChangeUserInfoResponse> getChangeUserInfoResponse(@Part("head")RequestBody requestBody,@Field("old_pass_number")String old_pass_number,@Field("new_pass_number")String new_pass_number,@Field("uname")String uname,@Field("intro")String intro,@Field("sex")String sex,@Field("name")String name,@Field("code")String code,@Field("token")String token);
    @FormUrlEncoded
    @POST("api.php/User/Comments/lists")
    Call<GetMyCommentsResponse> getMyCommentsResponse(@Field("page")int page,@Field("token")String token);
}
