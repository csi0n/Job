package com.csi0n.searchjob.business.domain;

import com.csi0n.searchjob.business.pojo.response.ext.GetCompanyJobMainResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetLoginResponse;

import rx.Observable;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public interface SearchJobDomain {
    Observable<GetConfigResponse> getConfig();
    Observable<GetLoginResponse> getLogin(String username,String password);
    Observable<GetCompanyJobMainResponse> getCompanyJobMain(int page,String city_id,String area_id,String money_back,String work_type,String fuli);
}
