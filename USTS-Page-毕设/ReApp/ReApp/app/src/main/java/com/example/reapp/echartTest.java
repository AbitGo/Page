package com.example.reapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.reapp.utils.MyWebviewClient;
import com.lock.myapplication.R;

public class echartTest extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echart_test);
        webView = this.findViewById(R.id.chart_e);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/example.html");
        webView.setWebViewClient(new MyWebviewClient());
    }
}
