package com.example.asus.app04_webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mWebView = findViewById(R.id.webView);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initBrowser(mWebView);
    }

    void initBrowser(WebView mWebView) {
        Intent intent = getIntent();
//        String url = intent.getStringExtra("url");
        Uri url = intent.getData();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(String.valueOf(url));
        mWebView.setWebViewClient(new myClient());
    }

    class myClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(String.valueOf(request.getUrl()));//跳转页面，在内部实现
            return super.shouldOverrideUrlLoading(view, request);
        }

//        @Override
//        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
//            return super.shouldOverrideKeyEvent(view, event);
//        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
