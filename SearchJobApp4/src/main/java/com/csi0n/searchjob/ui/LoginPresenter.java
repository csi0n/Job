package com.csi0n.searchjob.ui;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetLoginResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.google.inject.Inject;

import rx.Observable;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class LoginPresenter extends BaseMvpPresenter<LoginPresenter.ILoginView> {
    @Inject
    SearchJobDomain searchJobDomain;
    public Observable<GetLoginResponse> doGetLogin(String username,String password) {
        return searchJobDomain.getLogin(username,password);
    }
    public interface ILoginView extends IMvpView {

    }
}
