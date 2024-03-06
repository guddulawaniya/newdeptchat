package com.deptchat.livevideocallapp;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.deptchat.livevideocallapp.Ads.BannerAds;
import com.deptchat.livevideocallapp.Ads.bannerad;

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

        try {
            new bannerad(this,this).Native_Ad(findViewById(R.id.nativead),findViewById(R.id.my_template));
            new bannerad(this,this).Banner_Ad(findViewById(R.id.bannerad));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        bannerAds.interstitialads(privacyplocy.this);
    }

}