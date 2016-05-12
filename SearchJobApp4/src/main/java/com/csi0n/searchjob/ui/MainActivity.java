package com.csi0n.searchjob.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.callback.AdvancedSubscriber;
import com.csi0n.searchjob.business.pojo.event.ext.MainFragmentSkipEvent;
import com.csi0n.searchjob.business.pojo.event.ext.UserLoginEvent;
import com.csi0n.searchjob.business.pojo.response.ext.GetCheckTimeOutResponse;
import com.csi0n.searchjob.core.io.SharePreferenceManager;
import com.csi0n.searchjob.core.log.CLog;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.core.string.Constants.MainSkipTYPE;
import com.csi0n.searchjob.ui.base.BaseFragment;
import com.csi0n.searchjob.ui.base.mvp.MvpActivity;
import com.csi0n.searchjob.ui.home.MeFragment;
import com.csi0n.searchjob.ui.home.SearchJobFragment;
import com.csi0n.searchjob.ui.home.WangzhiDaoHangActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import roboguice.inject.ContentView;

import static com.csi0n.searchjob.core.string.Constants.MainSkipTYPE.*;

@ContentView(R.layout.aty_main)
public class MainActivity extends MvpActivity<MainPresenter, MainPresenter.IMainView> implements MainPresenter.IMainView {
    MeFragment mMeFragment;
    SearchJobFragment mSearchJobFragment;
    @Bind(value = R.id.rg_bottom)
    RadioGroup mBottom;
    @Bind(value = R.id.rd_news)
    RadioButton mRdNews;
    @Bind(value = R.id.rd_search_job)
    RadioButton mRdSearchJob;
    @Bind(value = R.id.rd_me)
    RadioButton mRdMe;
    public static final String MAIN_ACTIVITY_HAS_TOKEN = "main_activity_has_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autoLogin();
        init();
        setListeners();
        goNext();
    }

    void autoLogin(){
        String token= SharePreferenceManager.getKeyCachedToken();
        if (!TextUtils.isEmpty(token)){
            presenter.doCheckTimeOut(token).subscribe(new AdvancedSubscriber<GetCheckTimeOutResponse>(){
                @Override
                public void onHandleSuccess(GetCheckTimeOutResponse response) {
                    super.onHandleSuccess(response);
                    Constants.LOGIN_USER=response.user;
                    EventBus.getDefault().post(new UserLoginEvent(response.user));
                    CLog.e("AUTO LOGIN SUCCESS!");
                }

                @Override
                public void onHandleFail(String message, Throwable throwable) {
                    super.onHandleFail(message, throwable);
                    CLog.e("AUTO LOGIN FAILED "+message);
                    SharePreferenceManager.setKeyCachedToken("");
                }
            });
        }
    }
    void init() {
        mMeFragment = new MeFragment();
        mSearchJobFragment = new SearchJobFragment();
    }

    void setListeners() {
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

    void goNext() {
        changeSearchJob();
    }

    void changeMe() {
        changeFragment(mMeFragment);
    }

    void changeSearchJob() {
        changeFragment(mSearchJobFragment);
    }

    void changeFragment(BaseFragment targetFragment) {
        super.changeFragment(R.id.fl_content, targetFragment);
    }

    void startWangZhiDaoHang() {
        startActivity(getContext(), WangzhiDaoHangActivity.class);
    }

    void startLogin() {
        startActivity(getContext(), LoginActivity.class);
    }

    @Override
    public void onEvent(Object object) {
        super.onEvent(object);
        if (object.getClass()==MainFragmentSkipEvent.class){
            switch (((MainFragmentSkipEvent)object).MAIN_SKIP_TYPE) {
                case ME:
                    mRdNews.setChecked(true);
                    break;
                case SEARCHJOB:
                    mRdSearchJob.setChecked(true);
                    break;
                case WANGZHIDAOHANG:
                    mRdNews.setChecked(true);
                    break;
                default:
                    break;
            }
        }
    }
}
