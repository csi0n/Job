package com.csi0n.searchjob.business.api;

import com.csi0n.searchjob.business.pojo.request.ext.GetConfigRequest;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.core.net.NetWorkException;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public interface SearchJobApi {
    GetConfigResponse getConfigResponse(GetConfigRequest getConfigRequest)throws NetWorkException;
}
