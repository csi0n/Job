package com.csi0n.searchjob.ui;
import android.os.Bundle;
import android.widget.TextView;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.core.system.SystemUtils;
import butterknife.Bind;
import butterknife.BindString;
/**
 * Created by chqss on 2016/4/29 0029.
 */
public class AppStartActivity extends MvpActivity<AppStartPresenter, AppStartPresenter.IAppStartView> implements AppStartPresenter.IAppStartView {
    @Bind(value = R.id.tv_ver) TextView tv_ver;
    @Override
    protected int getRootView() {
        return R.layout.aty_start;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        gotoNext();
    }
    @BindString(value = R.string.version) String VERSION;
    private void init() {
        tv_ver.setText(VERSION+SystemUtils.getVersion());
    }

    private void gotoNext() {
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putBoolean(MainActivity.MAIN_ACTIVITY_HAS_TOKEN, true);
                skipActivityWithBundleWithOutExit(getContext(), MainActivity.class, bundle);
            }
        }, 1000);
    }
}
