package com.csi0n.searchjob.ui.fragment;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.utils.StringUtils;
import com.csi0n.searchjob.model.event.WangZhiDaoHangEvent;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by csi0n on 2/21/16.
 */
@ContentView(R.layout.frag_webview)
public class WebViewFragment extends BaseFragment {
    @ViewInject(value = R.id.webview)
    private WebView mWebView;
    private String url;

    @Override
    protected void initWidget() {
        url = StringUtils.switchNewsURL(FragmentPagerItem.getPosition(getArguments()));
        WebSettings webSettings = mWebView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        mWebView.loadUrl(url);
        //设置Web视图
        mWebView.setWebViewClient(new mWebViewClient());
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(WangZhiDaoHangEvent wangZhiDaoHang) {
        if (wangZhiDaoHang.isBack())
            mWebView.goBack();
        else if (wangZhiDaoHang.isHome())
            mWebView.loadUrl(url);
    }

    private class mWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
