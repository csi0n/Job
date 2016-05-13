package com.csi0n.searchjob.ui;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetChangeUserInfoResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.csi0n.searchjob.ui.base.mvp.IView;
import com.google.inject.Inject;

import rx.Observable;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public class UpdateDocumentsPresenter extends BaseMvpPresenter<UpdateDocumentsPresenter.iUpdateDocuments> {
    @Inject
    SearchJobDomain searchJobDomain;
    public Observable<GetChangeUserInfoResponse> doGetChanUserInfo(String sex){
        return searchJobDomain.getChangeUserInfoResponse(null,null,null,null,null,sex,null,null);
    }
    public interface iUpdateDocuments extends  IMvpView {

    }
}
