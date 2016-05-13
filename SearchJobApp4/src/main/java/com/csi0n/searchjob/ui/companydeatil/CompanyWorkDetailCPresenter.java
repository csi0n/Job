package com.csi0n.searchjob.ui.companydeatil;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailBResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailCResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.google.inject.Inject;

import rx.Observable;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailCPresenter extends BaseMvpPresenter<CompanyWorkDetailCPresenter.ICompanyWorkDetailCPresenter>{
    @Inject
    SearchJobDomain searchJobDomain;
    public Observable<GetSearchJobDetailCResponse> doGetSearchJobDetailC(int page, int company_id) {
        return searchJobDomain.getSearchJobDetailC(page,company_id);
    }
    public interface ICompanyWorkDetailCPresenter extends IMvpView {

    }
}
