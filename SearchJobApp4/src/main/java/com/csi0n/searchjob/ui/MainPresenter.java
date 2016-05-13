package com.csi0n.searchjob.ui;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetChangeUserInfoResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetCheckTimeOutResponse;
import com.csi0n.searchjob.business.pojo.response.ext.GetLoginResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.google.inject.Inject;

import java.io.File;

import rx.Observable;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class MainPresenter extends BaseMvpPresenter<MainPresenter.IMainView> {
    @Inject
    SearchJobDomain searchJobDomain;
    public Observable<GetCheckTimeOutResponse> doCheckTimeOut(String token) {
        return searchJobDomain.getCheckTimeOut(token);
    }
    public Observable<GetChangeUserInfoResponse> doGetChangeUserInfo(File head){
        return searchJobDomain.getChangeUserInfoResponse(head,null,null,null,null,null,null,null);
    }
    public interface IMainView extends IMvpView {

    }
}
