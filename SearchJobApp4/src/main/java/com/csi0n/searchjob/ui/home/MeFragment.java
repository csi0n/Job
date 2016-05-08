package com.csi0n.searchjob.ui.home;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.business.pojo.event.ext.MainFragmentSkipEvent;
import com.csi0n.searchjob.business.pojo.model.ext.MyModel;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by chqss on 2016/5/1 0001.
 */
public class MeFragment extends MvpFragment<MePresenter,MePresenter.IMeView> {
    @Bind(value = R.id.ri_head)
    RoundImageView RiHead;
    @Bind(value = R.id.tv_uname)
    TextView mTvUname;
    @Bind(value = R.id.tv_intro)
    TextView mTvIntro;
    @OnClick(value = {})void onClick(View view){

    }
    @Override
    protected int getFragmentLayout() {
        return R.layout.frag_me;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(Constants.LOGIN_USER);
    }
    private void initView(MyModel my){
        if (my==null)
            EventBus.getDefault().post(new MainFragmentSkipEvent(Constants.MainSkipTYPE.SEARCHJOB));
        else{
            Picasso.with(mvpActivity).load(my.head_ic).into(RiHead, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    RiHead.setImageResource(R.mipmap.ico_default_head_ic);
                }
            });
            mTvUname.setText(my.uname);
            if (!TextUtils.isEmpty(my.intro))
                mTvIntro.setText(my.intro);
        }

    }
}
