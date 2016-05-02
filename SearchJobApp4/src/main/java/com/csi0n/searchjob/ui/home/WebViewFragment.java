package com.csi0n.searchjob.ui.home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.csi0n.searchjobapp.R;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.ui.base.mvp.MvpFragment;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import butterknife.Bind;

/**
 * Created by chqss on 2016/5/1 0001.
 */
public class WebViewFragment extends MvpFragment<WebViewPresenter,WebViewPresenter.IWebView>{
    @Bind(value = R.id.webview)WebView mWebView;
    private String url;
    @Override
    protected int getFragmentLayout() {
        return R.layout.frag_webview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        url = Constants.switchNewsURL(FragmentPagerItem.getPosition(getArguments()));
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
    }
    private class mWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
