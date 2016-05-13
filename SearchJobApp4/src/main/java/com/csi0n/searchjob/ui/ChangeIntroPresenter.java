package com.csi0n.searchjob.ui;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetChangeUserInfoResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.google.inject.Inject;

import rx.Observable;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public class ChangeIntroPresenter extends BaseMvpPresenter<ChangeIntroPresenter.IChangeIntro> {
    @Inject
    SearchJobDomain searchJobDomain;
    public Observable<GetChangeUserInfoResponse> doGetChangeUserInfo(String intro){
        return searchJobDomain.getChangeUserInfoResponse(null,null,null,null,intro,null,null,null);
    }
    public interface IChangeIntro extends  IMvpView {

    }
}
