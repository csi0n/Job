package com.csi0n.searchjob.business.domain;

import com.csi0n.searchjob.business.pojo.response.ext.GetCheckTimeOutResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetCompanyJobMainResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetLoginResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobListByKeyResponse;

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

}
