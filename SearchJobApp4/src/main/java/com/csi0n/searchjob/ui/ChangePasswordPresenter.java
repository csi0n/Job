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
public class ChangePasswordPresenter extends BaseMvpPresenter<ChangePasswordPresenter.IChangePassword> {
    @Inject
    SearchJobDomain searchJobDomain;
    public Observable<GetChangeUserInfoResponse> doGetChangeUserInfo(String old_pass_number,String new_pass_number){
        return searchJobDomain.getChangeUserInfoResponse(old_pass_number,new_pass_number,null,null,null,null,null);
    }
    public interface IChangePassword extends IMvpView{

    }
}
