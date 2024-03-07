package com.deptchat.livevideocallapp.login_files;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.deptchat.livevideocallapp.Ads.bannerad;
import com.deptchat.livevideocallapp.Ads.intersital;
import com.deptchat.livevideocallapp.MainActivity;

import com.deptchat.livevideocallapp.R;
import com.deptchat.livevideocallapp.privacy_activity;
import com.deptchat.livevideocallapp.privacyplocy;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.text.DecimalFormat;
import java.util.Random;

public class start_activity extends AppCompatActivity {

    AdView bannerview;
    FrameLayout banneradtemplete;

    FrameLayout bannerframlayout, nativead, nativeframlaout, nativeframlaout2;
    AdView mAdView, faceaview;
    InterstitialAd fbInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        try {
            new bannerad(this,this).Native_Ad(findViewById(R.id.nativead),findViewById(R.id.my_template));
            new bannerad(this,this).Banner_Ad(findViewById(R.id.bannerad));
        } catch (Exception e) {

        }

        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        //   sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        //  String channel = (shared.getString(keyValue, ""));
        String url = sharedPreferences.getString("url", "https://www.google.com/");

//        if (getIntent().getStringExtra("backads").equals("1")){
//            Log.d("onError1 ;intent","hello");
//
//        }
        new intersital(this).Show_Ads();



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