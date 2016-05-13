package com.csi0n.searchjob.ui.home;

import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.business.pojo.response.ext.GetChangeUserInfoResponse;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.google.inject.Inject;

import java.io.File;

import rx.Observable;

/**
 * Created by chqss on 2016/5/1 0001.
 */
public class MePresenter extends BaseMvpPresenter<MePresenter.IMeView> {
    @Inject
    SearchJobDomain searchJobDomain;
    public interface IMeView extends IMvpView {

    }
}
