package com.csi0n.searchjob.business.domain;

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

import java.io.File;

import rx.Observable;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public interface SearchJobDomain {
    Observable<GetConfigResponse> getConfig();
    Observable<GetLoginResponse> getLogin(String username,String password);
    Observable<GetCompanyJobMainResponse> getCompanyJobMain(int page,long city_id,long area_id,int money_back,int work_type,String fuli,String configVer);
    Observable<GetCheckTimeOutResponse> getCheckTimeOut(String token);
    Observable<GetSearchJobListByKeyResponse> getSearchJobListByKey(int page,String key,String configVer);
    Observable<GetSearchJobDetailAResponse> getSearchJobDetailA(int company_id);
    Observable<GetSearchJobDetailBResponse> getSearchJobDetailB(int page,int company_id);
    Observable<GetSearchJobDetailBHeaderResponse> getSearchJobDetailBHeader(int company_id);
    Observable<GetSearchJobDetailCResponse> getSearchJobDetailC(int page, int company_id);
    Observable<GetSearchJobDetailDResponse> getSearchJobDetailD(int page, int company_id);
    Observable<GetCompanyCommentResultResponse> getCompanyCommentResult(int company_id,String content,long reply_uid);
    Observable<GetChangeUserInfoResponse> getChangeUserInfoResponse(File head,String old_pass_number,String new_pass_number,String uname,String intro,String sex,String name,String code);
    Observable<GetMyCommentsResponse> getMyCommentsResponse(int page);
}
