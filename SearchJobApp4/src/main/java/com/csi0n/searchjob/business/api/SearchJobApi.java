package com.csi0n.searchjob.business.api;

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
import com.csi0n.searchjob.core.net.NetWorkException;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public interface SearchJobApi {
    GetConfigResponse getConfigResponse(GetConfigRequest getConfigRequest)throws NetWorkException;

    GetLoginResponse getLoginResponse(GetLoginRequest getLoginRequest) throws NetWorkException;

    GetCompanyJobMainResponse getSearchJobListResponse(GetCompanyJobMainRequest getCompanyJobMainRequest)throws NetWorkException;

    GetCheckTimeOutResponse getCheckTimeOutResponse(GetCheckTimeOutRequest getCheckTimeOutRequest) throws NetWorkException;

    GetSearchJobListByKeyResponse getSearchJobListByKeyResponse(GetSearchJobListByKeyRequest getSearchJobListByKeyRequest)throws NetWorkException;

    GetSearchJobDetailAResponse getSearchJobDetailAResponse(GetSearchJobDetailARequest getSearchJobDetailARequest)throws NetWorkException;

    GetSearchJobDetailBResponse getSearchJobDetailBResponse(GetSearchJobDetailBRequest getSearchJobDetailBRequest)throws NetWorkException;

    GetSearchJobDetailBHeaderResponse getSearchJobDetailBHeaderResponse(GetSearchJobDetailBHeaderRequest getSearchJobDetailBHeaderRequest)throws NetWorkException;

    GetSearchJobDetailCResponse getSearchJobDetailCResponse(GetSearchJobDetailCRequest getSearchJobDetailCRequest)throws NetWorkException;

    GetSearchJobDetailDResponse getSearchJobDetailDResponse(GetSearchJobDetailDRequest getSearchJobDetailDRequest)throws NetWorkException;

    GetCompanyCommentResultResponse getCompanyCommentResultResponse(GetCompanyCommentResultRequest getCompanyCommentResultRequest)throws NetWorkException;

    GetChangeUserInfoResponse getChangeUserInfoResponse(GetChangeUserInfoRequest getChangeUserInfoRequest)throws NetWorkException;

    GetChangeUserInfoResponse getChangeUserInfoHeadResponse(GetChangeUserInfoRequest getChangeUserInfoRequest)throws NetWorkException;

    GetMyCommentsResponse getMyCommentsResponse(GetMyCommentsRequest getMyCommentsRequest)throws NetWorkException;

    GetCheckUserAppVerResponse getCheckUserAppVerResponse(GetCheckUserAppVerRequest getCheckUserAppVerRequest)throws NetWorkException;
}
