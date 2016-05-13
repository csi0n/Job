package com.csi0n.searchjob.ui.companydeatil;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailAResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.google.inject.Inject;

import rx.Observable;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailAPresenter extends BaseMvpPresenter<CompanyWorkDetailAPresenter.ICompanyWorkDetailAPresenter> {
    @Inject
    SearchJobDomain searchJobDomain;

    public Observable<GetSearchJobDetailAResponse> doGetSearchJobDetailA(int company_id) {
        return searchJobDomain.getSearchJobDetailA(company_id);
    }

    public interface ICompanyWorkDetailAPresenter extends IMvpView {

    }
}
