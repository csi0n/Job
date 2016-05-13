package com.csi0n.searchjob.ui;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.google.inject.Inject;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailPresenter extends BaseMvpPresenter<CompanyWorkDetailPresenter.ICompanyWorkDetailView> {
    @Inject
    SearchJobDomain searchJobDomain;
    public interface ICompanyWorkDetailView extends IMvpView {

    }
}
