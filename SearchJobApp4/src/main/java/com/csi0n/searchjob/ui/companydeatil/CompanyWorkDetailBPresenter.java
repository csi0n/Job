package com.csi0n.searchjob.ui.companydeatil;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailBHeaderResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailBResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.google.inject.Inject;

import rx.Observable;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailBPresenter extends BaseMvpPresenter<CompanyWorkDetailBPresenter.ICompanyWorkDetailBPresenter>{
    @Inject
    SearchJobDomain searchJobDomain;
    public Observable<GetSearchJobDetailBResponse> doGetSearchJobDetailB(int page,int company_id) {
        return searchJobDomain.getSearchJobDetailB(page,company_id);
    }
    public Observable<GetSearchJobDetailBHeaderResponse> doGetSearchJobDetailBHeader(int company_id){
        return searchJobDomain.getSearchJobDetailBHeader(company_id);
    }
    public interface ICompanyWorkDetailBPresenter extends IMvpView {

    }
}
