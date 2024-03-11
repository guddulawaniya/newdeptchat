package com.deptchat.livevideocallapp.Ads;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.deptchat.livevideocallapp.login_files.splash_screen;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;

public class Interfb implements MaxAdListener {
    public static com.facebook.ads.InterstitialAd facebookAd;
    private InterstitialAd mInterstitialAd;

    private static String placementId = "video" ;

    private MaxInterstitialAd interstitialAd;
    String interid;
    Activity activity;
    ProgressDialog dialog;

    static SharedPreferences sharedPreferences ;

    public Interfb(Activity activity) {
        this.activity = activity;
        sharedPreferences= activity.getSharedPreferences("login", Context.MODE_PRIVATE);
    }

    public boolean is_internet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void Show_Ads() {


        if (!sharedPreferences.getString("Intersitialadmob","iuhikuhiukh").equals("STOP")) {
            Log.d("onError1", "onError1: ads");

            Facebook_Interstitial();

        }
    }

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dialog.dismiss();
//                activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//            }
//        }, 2000);






    public void Facebook_Interstitial() {

        facebookAd = new com.facebook.ads.InterstitialAd(activity, sharedPreferences.getString("Intersitialfb","iuhikuhiukh"));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                Log.e(TAG, "onError1: facebook");
                Log.e(TAG, "Show_Ads: admob");
                Log.e(TAG, "onError1: facebook: " + adError.getErrorMessage());
                if ( sharedPreferences.getString("Intersitialadmob","iuhikuhiukh").startsWith("applovin#")){
                    String[] parts1 =  sharedPreferences.getString("Intersitialadmob","iuhikuhiukh").split("#");

                    if (parts1.length > 1) {
                        interid = parts1[1];
                        createInterstitialAd(parts1[1]);
                        //    System.out.println("String after #: " + interid);
                    }

                }else if(sharedPreferences.getString("Intersitialadmob","iuhikuhiukh").startsWith("unity#")){
                    String[] parts1 =  sharedPreferences.getString("Intersitialadmob","iuhikuhiukh").split("#");

                    if (parts1.length > 1) {
                        placementId = parts1[1];
                        createUnityInterstitial();
                        //  System.out.println("String after #: " + interid);
                    }

                }
                else {admob_Interstitial();}
                //dialog.cancel();

            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e(TAG, "onAdLoaded: facebook");
                Log.e("tt", ad.toString());
                if (facebookAd.isAdLoaded()) {
                    facebookAd.show();
                }

            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e(TAG, "onAdClicked: facebook");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e(TAG, "onLoggingImpression: facebook");
            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Log.e("tt", ad.toString());
                Log.e(TAG, "onInterstitialDisplayed: facebook");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.e(TAG, "onInterstitialDismissed: facebook");
                if (!splash_screen.adloading.equals("STOP")) {

                }
                //activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        };

        facebookAd.loadAd(
                facebookAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    private void admob_Interstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(bannerad.context, sharedPreferences.getString("Intersitialadmob","iuhikuhiukh"), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        if (!splash_screen.adloading.equals("STOP")) {

                        }
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        showAdMobInterstitialAd();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d(TAG, loadAdError.toString());
                        Log.e("onError1: admobinter",loadAdError.getMessage()+"---"+sharedPreferences.getString("Intersitialadmob","iuhikuhiukh"));
                        mInterstitialAd = null;

                        //   activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                });
    }

    private void showAdMobInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    Log.d(TAG, "onAdDismissedFullScreenContent: AdMob");
                    // Start the next activity after the AdMob interstitial ad is dismissed

                }
            });
            mInterstitialAd.show(activity);
        } else {
            // If AdMob interstitial is not loaded, proceed to the next activity
            // activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void FB_BackActivity() {
        facebookAd = new com.facebook.ads.InterstitialAd(activity, sharedPreferences.getString("Intersitialfb","iuhikuhiukh"));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {

            @SuppressLint("LongLogTag")
            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                if ( sharedPreferences.getString("Intersitialadmob","iuhikuhiukh").startsWith("applovin#")){
                    String[] parts1 =  sharedPreferences.getString("Intersitialadmob","iuhikuhiukh").split("#");

                    if (parts1.length > 1) {
                        interid = parts1[1];
                        createInterstitialAd(parts1[1]);
                        System.out.println("String after #: " + interid);
                    }

                }else {
                    admob_BackActivity();


                }

                Log.e(TAG, "onError1: facebook back ad");
                Log.d( "onError1: facebook back ad",adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e(TAG, "onError1: facebook back ad");
                dialog.dismiss();

                facebookAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // activity.finish();
            }
        };

        facebookAd.loadAd(
                facebookAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }
    private void admob_BackActivity(){

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(bannerad.context, splash_screen.ADINTERSTITIAL_AD, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        if (!splash_screen.adloading.equals("STOP")) {

                        }
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        showAdMob();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        if (!splash_screen.adloading.equals("STOP")) {

                        }                    }

                });

    }

    private void showAdMob() {
        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    Log.d(TAG, "onAdDismissedFullScreenContent: AdMob");
                    // activity.finish();
                }
            });
            mInterstitialAd.show(activity);
        } else {
            //  activity.finish();
        }
    }


    private     void createInterstitialAd(String interids)
    {
        interstitialAd = new MaxInterstitialAd( interids,this.activity  );
        interstitialAd.setListener( this );


        interstitialAd.loadAd();


    }


    @Override
    public void onAdLoaded(@NonNull MaxAd maxAd) {

        showintriad();

    }

    @Override
    public void onAdDisplayed(@NonNull MaxAd maxAd) {

    }

    @Override
    public void onAdHidden(@NonNull MaxAd maxAd) {
        //    interstitialAd.loadAd();


    }

    @Override
    public void onAdClicked(@NonNull MaxAd maxAd) {

    }

    @Override
    public void onAdLoadFailed(@NonNull String s, @NonNull MaxError maxError) {

    }

    @Override
    public void onAdDisplayFailed(@NonNull MaxAd maxAd, @NonNull MaxError maxError) {

        interstitialAd.loadAd();
    }
    public void showintriad() {
        if ( interstitialAd.isReady()) {
            interstitialAd.showAd();
        }
    }


    private void createUnityInterstitial(){

        UnityAds.load(placementId, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                Log.d("unityrishi", "Unity Interstitial Ad loaded: " + placementId);
                showUnityInterstitial();

            }

            @Override


            public

            void

            onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message)

            {
                Log.e("unityrishi", "Unity Interstitial Ad failed to load: " + message);
                // Handle ad load failure (e.g., try loading a different ad network)
            }
        });

    }
    private void showUnityInterstitial(){
        UnityAds.show(activity, placementId, new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {

            }

            @Override
            public void onUnityAdsShowStart(String placementId) {

            }

            @Override
            public void onUnityAdsShowClick(String placementId) {

            }

            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {

            }
        });
    }
}