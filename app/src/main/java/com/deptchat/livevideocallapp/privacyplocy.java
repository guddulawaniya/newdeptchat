package com.deptchat.livevideocallapp;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.deptchat.livevideocallapp.Ads.BannerAds;
import com.deptchat.livevideocallapp.Ads.bannerad;
import com.deptchat.livevideocallapp.Ads.intersital;

public class privacyplocy extends AppCompatActivity {
    FrameLayout bannerframlayout, nativeframlaout2;
    Boolean adSwitch = false;
    private BroadcastReceiver broadcastReceiver;
    BannerAds bannerAds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacyplocy);
//        bannerAds = new BannerAds(this);
        new intersital(this).Show_Ads();
        Intent intent = getIntent();

        String url = intent.getStringExtra("url");

        WebView webView = findViewById(R.id.webview);
        ProgressBar progressBar = findViewById(R.id.progressbar);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load a URL into the WebView


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Show the ProgressBar when the page starts loading
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Hide the ProgressBar when the page finishes loading
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // Update the ProgressBar's progress based on the loading progress
                progressBar.setProgress(newProgress);
            }
        });

        webView.loadUrl(url);


        try {
            new bannerad(this,this).Native_Ad(findViewById(R.id.nativead),findViewById(R.id.my_template));
            new bannerad(this,this).Banner_Ad(findViewById(R.id.bannerad));
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        bannerAds.interstitialads(privacyplocy.this);
    }

}