package com.csi0n.searchjobapp.ui;

import com.csi0n.searchjobapp.business.domain.SearchJobDomain;
import com.csi0n.searchjobapp.ui.base.mvp.BaseMvpPresenter;
import com.csi0n.searchjobapp.ui.base.mvp.IMvpView;
import com.google.inject.Inject;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class MainPresenter extends BaseMvpPresenter<MainPresenter.IMainView>{
    @Inject
    SearchJobDomain searchJobDomain;
    public interface IMainView extends IMvpView{

    }
}
