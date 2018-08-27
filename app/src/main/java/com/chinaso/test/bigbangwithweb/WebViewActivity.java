package com.chinaso.test.bigbangwithweb;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewActivity extends Activity {

    private boolean isLoading;
    private WebView webView;
    public String JS_INSERT_STR = "javascript: window.__dominoPlugin__= (function(){ var obj = { timeThrehold: 1000, distThrehold: 2, startX: 0, startY :0, x:0, y:0, animateId: null, startTime: (new Date()).getTime(), target: null }; var getClosestTextTag = function(elm) { var tag = 'p'; var matchesFn; ['matches','webkitMatchesSelector','mozMatchesSelector','msMatchesSelector','oMatchesSelector'].some(function(fn) { if (typeof document.body[fn] == 'function') { matchesFn = fn; return true; } return false; }); if (elm && elm[matchesFn] && elm[matchesFn](tag)) {return elm} var parent; while(elm) { parent = elm.parentElement; if (parent && parent[matchesFn] && parent[matchesFn](tag)) { return parent; } elm = parent; } return null; }; var outputToJava = function(){ var elm = getClosestTextTag(obj.target); var txt = elm && elm.innerText.trim(); if(txt) {window.__domino__.getContent(txt)} }; var onTouchMove = function(e){ obj.x = e.touches[0].clientX; obj.y = e.touches[0].clientY; }; var monitor = function(){ var dx = obj.x - obj.startX; var dy = obj.y - obj.startY; var dist = Math.sqrt(dx*dx + dy*dy); var elapse = (new Date()).getTime() - obj.startTime; if (dist < obj.distThrehold) { if(elapse < obj.timeThrehold) { obj.animateId = setTimeout(monitor, 0); } else { outputToJava(); clearTimeout(obj.animateId); } } else { clearTimeout(obj.animateId); } }; document.addEventListener('touchstart', function(e){ var evt = e.touches[0]; obj.startX = obj.x = evt.clientX; obj.startY = obj.y = evt.clientY; obj.startTime = (new Date()).getTime(); obj.target = evt.target; monitor(); document.addEventListener('touchmove', onTouchMove , false) }, false); document.addEventListener('touchend', function(e){ document.removeEventListener('touchmove', onTouchMove); clearTimeout(obj.animateId); }, false); })();";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.webview);
        initWebViewSetting();
    }


    private void initWebViewSetting() {
        WebSettings webSettings = webView.getSettings();

        webView.addJavascriptInterface(new JSEngine(this, webView), "__domino__");
        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 支持保存数据
        webSettings.setSaveFormData(true);
        // 设置默认缩放方式尺寸是far
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(false);
        //设置 缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        // 清除缓存
        webView.clearCache(true);
        // 清除历史记录
        webView.clearHistory();
        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.loadUrl("https://www.jianshu.com");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isLoading = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isLoading) {
                    isLoading = false;
                    //做些处理
                    webView.loadUrl(JS_INSERT_STR);
                    return;
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                webView.goBack();
                return true;
            } else {
                finish();
                return true;
            }
        }
        return false;
    }

}
