package com.example.deptchat.login_files;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.deptchat.Ads.BannerAds;
import com.example.deptchat.Ads.bannerad;
import com.example.deptchat.MainActivity;
import com.example.deptchat.R;
import com.example.deptchat.privacy_activity;
import com.example.deptchat.privacyplocy;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.text.DecimalFormat;
import java.util.Random;

public class start_activity extends AppCompatActivity {

    AdView bannerview;
    FrameLayout banneradtemplete;

    FrameLayout bannerframlayout, nativead, nativeframlaout, nativeframlaout2;
    AdView mAdView, faceaview;
    InterstitialAd fbInterstitialAd;


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 2) {
                        //  applovinads.createInterstitialAd();

//                        if (SplashActivity.mInterstitialAd != null) {
//                            Log.d(LOG_TAG,"ad on StartACTivity back press");
//                            SplashActivity.mInterstitialAd.show(StartActivity.this);
//                        }else {
//                            Log.d(LOG_TAG,"empty ad on StartACTivity back Press");
//                        }

                        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);

                        String adlink = sharedPreferences.getString("adlink", "");
                        Boolean adurl = sharedPreferences.getBoolean("adurl", false);
                        if (adurl) {
                            CustomTabsIntent intent = new CustomTabsIntent.Builder()
                                    .build();
                            intent.launchUrl(start_activity.this, Uri.parse(adlink));
                        }
                    }
                }
            });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        try {
            new bannerad(this,this).Native_Ad(findViewById(R.id.nativead),findViewById(R.id.my_template));
            new bannerad(this,this).Banner_Ad(findViewById(R.id.bannerad));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        67798);
            }
        }


        TextView nextbutton = findViewById(R.id.nextbutton);
        TextView termcondition = findViewById(R.id.termcondition);
        TextView privacytextview = findViewById(R.id.privacy);

        termcondition.setPaintFlags(termcondition.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        privacytextview.setPaintFlags(privacytextview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);



        termcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start_activity.this, privacy_activity.class);
                startActivity(intent);
            }
        });
        privacytextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start_activity.this, privacyplocy.class);
                startActivity(intent);
            }
        });


        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        boolean adsclose = preferences.getBoolean("closeads", true);

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid = new DecimalFormat("000000").format(new Random().nextInt(999999));

                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putInt("clientid", Integer.parseInt(userid));
                editor.commit();

                startActivity(new Intent(start_activity.this, MainActivity.class));
                finish();
            }
        });
    }

}