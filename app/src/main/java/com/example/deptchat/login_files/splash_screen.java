package com.example.deptchat.login_files;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deptchat.Ads.ApiInterface;
import com.example.deptchat.Ads.ApiWebServices;
import com.example.deptchat.Ads.SammanNidhiAdsModel;
import com.example.deptchat.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class splash_screen extends AppCompatActivity {

    ProgressBar progressBar;
    public static String fb_native_banner_ad_id="1105842507052721_1130782921225346";



    WifiManager wifi;

    Boolean adSwitch = false;


    private BroadcastReceiver broadcastReceiver;

    private CountDownTimer countDownTimer;
    ApiInterface apiInterface;

    public static final String LOG_TAG = "mmmm";
    public static String BANNER_AD_ID = "1786861181760592_1786862218427155";
    public static String openid = "kuhuhk";
    public static String ADBANNER_AD_ID = "ca-app-pub-3940256099942544/6300978111";

    public static String NATIVE_AD_ID = "1786861181760592_1786862308427146";
    public static String MERCNATIVE_AD_ID = "1786861181760592_1786862358427141";

    public static String ADNATIVE_AD_ID = "ca-app-pub-3940256099942544/2247696110";
    public static String INTERSTITIAL_AD = "1786861181760592_1786862281760482";
    public static String ADINTERSTITIAL_AD = "ca-app-pub-3940256099942544/1033173712";

    public static String APP_OPEN_AD = "/21753324030,22996578486";
    public static String URL_FROM_FIREBASE = "https://www.google.com/";
    public static String URL_FROM_FIREBASE_KHULA_KHAJANA = "https://www.google.com/";
    public static String adloading="load";
    String url,gameurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        fetchAds();

        progressBar = findViewById(R.id.progressBar2);
        SharedPreferences editor = getSharedPreferences("login", MODE_PRIVATE);
        int clientid = editor.getInt("clientid", 0);

//
//        if (clientid != 0) {
//            startActivity(new Intent(splash_screen.this, MainActivity.class));
//            finish();
//
//        } else {
//

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    startActivity(new Intent(splash_screen.this, start_activity.class));
//                    finish();
//                }
//            }, 2000);

//        }

    }

    private void fetchAds() {
        apiInterface = ApiWebServices.getApiInterface();
        Call<List<SammanNidhiAdsModel>> call = apiInterface.sammanNidhifetchAds("PM Kisan Yojana");
        call.enqueue(new Callback<List<SammanNidhiAdsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<SammanNidhiAdsModel>> call, @NonNull Response<List<SammanNidhiAdsModel>> response) {
                if (response.isSuccessful()) {
                    String jsonResponse = new Gson().toJson(response.body());
                    Log.d("checkIds1", jsonResponse);
                    if (response.body() != null) {
                        for (SammanNidhiAdsModel ads : response.body()) {
                            Log.d("checkIds",
                                    ads.getId()
                                            + "\n" + ads.getAppId()
                                            + "\n" + ads.getAppLovinAppKey()
                                            + "\n" + ads.getBannerTop()
                                            + "\n" + ads.getBannerTopAdNetwork()
                                            + "\n" + ads.getBannerBottom()
                                            + "unit key  \n" + ads.getBannerBottomAdNetwork()
                                            + "\n" + ads.getInterstitial()
                                            + "\n" + ads.getInterstitalAdNetwork()
                                            + "\n" + ads.getNativeAd()
                                            + "\n" + ads.getNativeAdNetwork()
                                            + "\n" + ads.getNativeType()
                                            + "\n" + ads.getRewardAd()
                                            + "\n" + ads.getRewardAdNetwork()
                            );

                            BANNER_AD_ID=ads.getBannerTop();
                            fb_native_banner_ad_id=ads.getBannerBottom();
                            ADBANNER_AD_ID=ads.getBannerBottomAdNetwork();

                            NATIVE_AD_ID= ads.getNativeAd();
                            MERCNATIVE_AD_ID=ads.getBannerTopAdNetwork();
                            ADNATIVE_AD_ID=ads.getNativeAdNetwork();


                            INTERSTITIAL_AD=ads.getInterstitial();
                            ADINTERSTITIAL_AD=ads.getInterstitalAdNetwork();

                            openid = ads.getAppId();
                            String[] parts = ads.getRewardAdNetwork().split("#");

                            if (parts.length == 2) {
                                // Extract the name and city
                                url = parts[0];
                                gameurl = parts[1];


                            } else {

                            }


                            Log.d("fatal12",adloading);
                            URL_FROM_FIREBASE_KHULA_KHAJANA=ads.getAppLovinAppKey();
                            SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

                            // Creating an Editor object to edit(write to the file)
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("prices", ads.getAppLovinAppKey());
                            myEdit.putString("adtype", ads.getAppLovinAppKey().split("#")[3]);
                            myEdit.putString("fbbanner", ads.getBannerTop());
                            myEdit.putString("fbbannernative", ads.getBannerBottom());
                            myEdit.putString("admobbanner", ads.getBannerBottomAdNetwork());
                            myEdit.putString("fbnative", ads.getNativeAd());
                            myEdit.putString("fbmercnative", ads.getBannerTopAdNetwork());
                            myEdit.putString("admobnative", ads.getNativeAdNetwork());
                            myEdit.putString("openad", ads.getAppId());
                            myEdit.putString("Intersitialfb", ads.getInterstitial());
                            myEdit.putString("Intersitialadmob", ads.getInterstitalAdNetwork());

                            //Toast.makeText(SplashActivity.this, ads.getBannerBottomAdNetwork()+" --- "+ads.getNativeAdNetwork(), Toast.LENGTH_SHORT).show();

                            Log.d("vurl",url+"-"+gameurl);
                            myEdit.putString("url", url);
                            myEdit.putString("gameurl", gameurl);

                            myEdit.putString("payvideo", ads.getNativeType());
                            myEdit.putString("upi", ads.getRewardAd());

                            myEdit.commit();
                            if (ads.getAppId().equals("STOP")){
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(splash_screen.this, start_activity.class);
                                intent.putExtra("fbads","1");

                                startActivity(intent);
                                finish();

                            }else {
                                createTimer();
                            }

                        }

                    }
                } else {
                    Log.e("adsError", response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<SammanNidhiAdsModel>> call, @NonNull Throwable t) {
                Log.d("adsError", t.getMessage());
                progressBar.setVisibility(View.GONE);

            }
        });


    }

    private void createTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {progressBar.setVisibility(View.GONE);

                Intent intent = new Intent(splash_screen.this, start_activity.class);
                intent.putExtra("fbads","2");
                startActivity(intent);
                finish();

//                Application application = getApplication();
//                ((MyApplication) application).showAdIfAvailable(SplashActivity.this, new MyApplication.OnShowAdCompleteListener() {
//                    @Override
//                    public void onAdShown() {
//                        progressBar.setVisibility(View.GONE);
//
//                        Intent intent = new Intent(SplashActivity.this, Privacyac.class);
//                        intent.putExtra("fbads","1");
//                        startActivity(intent);
//                        finish();
//                    }
//                });
            }
        };
        countDownTimer.start();
    }
}