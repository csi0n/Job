package com.csi0n.searchjob.ui.companydeatil;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetCompanyCommentResultResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailCResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobDetailDResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.google.inject.Inject;

import rx.Observable;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailDPresenter extends BaseMvpPresenter<CompanyWorkDetailDPresenter.ICompanyWorkDetailDPresenter>{
    @Inject
    SearchJobDomain searchJobDomain;
    public Observable<GetSearchJobDetailDResponse> doGetSearchJobDetailD(int page, int company_id) {
        return searchJobDomain.getSearchJobDetailD(page,company_id);
    }
    public Observable<GetCompanyCommentResultResponse> doGetCompanyCommentResult(int company_id,String content,long reply_uid){
        return searchJobDomain.getCompanyCommentResult(company_id,content,reply_uid);
    }
    public interface ICompanyWorkDetailDPresenter extends IMvpView {

    }
}
