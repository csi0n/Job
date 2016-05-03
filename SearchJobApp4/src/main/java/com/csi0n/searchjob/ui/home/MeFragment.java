package com.csi0n.searchjob.ui.home;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;
/**
 * Created by chqss on 2016/5/1 0001.
 */
public class MeFragment extends MvpFragment<MePresenter,MePresenter.IMeView> {
    @Override
    protected int getFragmentLayout() {
        return R.layout.frag_me;
    }
}
