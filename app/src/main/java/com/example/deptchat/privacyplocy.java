package com.example.deptchat;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deptchat.Ads.BannerAds;

public class privacyplocy extends AppCompatActivity {
    FrameLayout bannerframlayout, nativeframlaout2;
    Boolean adSwitch = false;
    private BroadcastReceiver broadcastReceiver;
    BannerAds bannerAds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacyplocy);
        bannerAds = new BannerAds(this);

        bannerAds.BnnersAdview(findViewById(R.id.bannerView));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bannerAds.interstitialads(privacyplocy.this);
    }

}