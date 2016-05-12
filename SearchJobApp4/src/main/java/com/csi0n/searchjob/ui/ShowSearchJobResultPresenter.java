package com.csi0n.searchjob.ui;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetSearchJobListByKeyResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.google.inject.Inject;

import rx.Observable;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class ShowSearchJobResultPresenter extends BaseMvpPresenter<ShowSearchJobResultPresenter.ISearchJobPresenterView> {
    @Inject
    SearchJobDomain searchJobDomain;
    public Observable<GetSearchJobListByKeyResponse> getSearchJobListByKey(int page,String token,String configVer) {
        return searchJobDomain.getSearchJobListByKey(page,token,configVer);
    }
    public interface ISearchJobPresenterView extends IMvpView {

    }
}
