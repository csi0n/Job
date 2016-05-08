package com.csi0n.searchjob.ui;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.pojo.event.ext.MainFragmentSkipEvent;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.core.string.Constants.MainSkipTYPE;
import com.csi0n.searchjob.ui.base.BaseFragment;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.csi0n.searchjob.ui.home.MeFragment;
import com.csi0n.searchjob.ui.home.SearchJobFragment;
import com.csi0n.searchjob.ui.home.WangzhiDaoHangActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import roboguice.inject.ContentView;

import static com.csi0n.searchjob.core.string.Constants.MainSkipTYPE.*;

@ContentView(R.layout.aty_main)
public class MainActivity extends MvpActivity<MainPresenter, MainPresenter.IMainView> implements MainPresenter.IMainView {
    MeFragment mMeFragment;
    SearchJobFragment mSearchJobFragment;
    @Bind(value = R.id.rg_bottom)
    RadioGroup mBottom;
    public static final String MAIN_ACTIVITY_HAS_TOKEN = "main_activity_has_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setListeners();
        goNext();
    }

    private void init() {
        mMeFragment = new MeFragment();
        mSearchJobFragment = new SearchJobFragment();
    }

    private void setListeners() {
        mBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rd_news:
                        startWangZhiDaoHang();
                        break;
                    case R.id.rd_search_job:
                        changeSearchJob();
                        break;
                    case R.id.rd_me:
                        if (Constants.LOGIN_USER != null)
                            changeMe();
                        else{
                            EventBus.getDefault().post(new MainFragmentSkipEvent(SEARCHJOB));
                            startLogin();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void goNext() {
        changeSearchJob();
    }

    private void changeMe() {
        changeFragment(mMeFragment);
    }

    private void changeSearchJob() {
        changeFragment(mSearchJobFragment);
    }

    private void changeFragment(BaseFragment targetFragment) {
        super.changeFragment(R.id.fl_content, targetFragment);
    }

    private void startWangZhiDaoHang() {
        startActivity(getContext(), WangzhiDaoHangActivity.class);
    }

    private void startLogin() {
        startActivity(getContext(), LoginActivity.class);
    }

    public void onEvent(MainFragmentSkipEvent object) {
        switch (object.MAIN_SKIP_TYPE) {
            case ME:
                changeMe();
                break;
            case SEARCHJOB:
                changeSearchJob();
                break;
            case WANGZHIDAOHANG:
                startWangZhiDaoHang();
                break;
            default:
                break;
        }
    }
}
