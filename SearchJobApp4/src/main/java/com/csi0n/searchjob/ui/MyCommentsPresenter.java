package com.csi0n.searchjob.ui;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetMyCommentsResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.google.inject.Inject;

import rx.Observable;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public class MyCommentsPresenter extends BaseMvpPresenter<MyCommentsPresenter.IMyComments> {
    @Inject
    SearchJobDomain searchJobDomain;
    public Observable<GetMyCommentsResponse> getMyComments(int page){
        return searchJobDomain.getMyCommentsResponse(page);
    }
    public interface IMyComments extends IMvpView{

    }
}
