package com.csi0n.searchjob.ui.home;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetCompanyJobMainResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetConfigResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.google.inject.Inject;

import rx.Observable;

/**
 * Created by chqss on 2016/5/1 0001.
 */
public class SearchJobPresenter extends BaseMvpPresenter<SearchJobPresenter.ISearchJobView> {
    @Inject
    SearchJobDomain searchJobDomain;
    public Observable<GetCompanyJobMainResponse> getSearJobList(int page,long city_id,long area_id,int money_back,int work_type,String fuli,String configVer){
        return searchJobDomain.getCompanyJobMain(page,city_id,area_id,money_back,work_type,fuli,configVer);
    }
    public Observable<GetConfigResponse> doGetConfig() {
        return searchJobDomain.getConfig();
    }
    public interface ISearchJobView extends IMvpView {
    }
}
