package com.example.reapp.utils;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebviewClient extends WebViewClient {
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        //w1.loadUrl("javascript:doCreatChart('line',[89,78,77,44,66,83]);");
    }
}
