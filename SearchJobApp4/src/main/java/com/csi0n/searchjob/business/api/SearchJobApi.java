package com.csi0n.searchjob.business.api;

import com.csi0n.searchjob.business.pojo.request.ext.GetCompanyJobMainRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetConfigRequest;
import com.csi0n.searchjob.business.pojo.request.ext.GetLoginRequest;
import com.csi0n.searchjob.business.pojo.response.ext.GetCompanyJobMainResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetLoginResponse;
import com.csi0n.searchjob.core.net.NetWorkException;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public interface SearchJobApi {
    GetConfigResponse getConfigResponse(GetConfigRequest getConfigRequest)throws NetWorkException;

    GetLoginResponse getLoginResponse(GetLoginRequest getLoginRequest) throws NetWorkException;

    GetCompanyJobMainResponse getSearchJobListResponse(GetCompanyJobMainRequest getCompanyJobMainRequest)throws NetWorkException;
}
