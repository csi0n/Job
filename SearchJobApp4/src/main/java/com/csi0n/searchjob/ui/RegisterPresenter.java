package com.csi0n.searchjob.ui;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.google.inject.Inject;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class RegisterPresenter extends BaseMvpPresenter<RegisterPresenter.IRegisterView> {
    @Inject
    SearchJobDomain searchJobDomain;
    public interface IRegisterView extends IMvpView {

    }
}
