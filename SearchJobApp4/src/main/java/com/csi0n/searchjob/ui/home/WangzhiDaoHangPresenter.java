package com.csi0n.searchjob.ui.home;

import com.csi0n.searchjob.ui.base.mvp.IMvpView;
import com.csi0n.searchjob.business.domain.SearchJobDomain;
import com.csi0n.searchjob.ui.base.mvp.BaseMvpPresenter;
import com.google.inject.Inject;

/**
 * Created by chqss on 2016/5/1 0001.
 */
public class WangzhiDaoHangPresenter extends BaseMvpPresenter<WangzhiDaoHangPresenter.IWangzhiDaoHangView> {
    @Inject
    SearchJobDomain searchJobDomain;
    public interface IWangzhiDaoHangView extends IMvpView {

    }
}
