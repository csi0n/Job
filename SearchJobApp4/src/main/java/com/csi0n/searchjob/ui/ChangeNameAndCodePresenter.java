package com.csi0n.searchjob.ui;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetChangeUserInfoResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.google.inject.Inject;

import rx.Observable;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public class ChangeNameAndCodePresenter extends BaseMvpPresenter<ChangeNameAndCodePresenter.IChangeNameAndCode> {
    @Inject
    SearchJobDomain searchJobDomain;
    public Observable<GetChangeUserInfoResponse> doGetChangeUserInfo(String name,String code){
        return searchJobDomain.getChangeUserInfoResponse(null,null,null,null,null,name,code);
    }
    public interface IChangeNameAndCode extends IMvpPresenter, IMvpView {

    }
}
