package com.deptchat.livevideocallapp.Ads;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.applovin.sdk.AppLovinSdk;
import com.deptchat.livevideocallapp.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.onesignal.OneSignal;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.UnityAds;


public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks, DefaultLifecycleObserver {
    private AppOpenAdManager appOpenAdManager;
    private Activity currentActivity;
    SharedPreferences sharedPreferences;
    static String opends;
   // public static final String TEST_APP_OPEN_AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294";

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);
        MobileAds.initialize(this);
        AppLovinSdk.initializeSdk(this);

        OneSignal.initWithContext(this, getString(R.string.ONESIGNAL_APP_ID));

        UnityAds.initialize(this, "5569390",false, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                Log.d("unityrishi", "Unity Ads initialization complete");
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError unityAdsInitializationError, String errorMessage) {
                Log.e("unityrishi", "Unity Ads initialization failed: " + errorMessage);
            }
        });


        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
       opends= sharedPreferences.getString("openad","yuguygygygyiu");
        Log.d("openazd",opends);

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        appOpenAdManager = new AppOpenAdManager();
        // Verbose Logging set to help debug issues, remove before releasing your app.

    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
        // Show the App Open Ad when the app is in the foreground
        //appOpenAdManager.showAdIfAvailable(currentActivity);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        // Load the App Open Ad when any activity is resumed
        //appOpenAdManager.loadAd(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        // Pause or stop showing the ad when an activity is paused
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        // Pause or stop showing the ad when an activity is stopped
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        // Handle save instance state
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        // Handle activity destruction
    }

    public void loadAd(@NonNull Activity activity) {
        //appOpenAdManager.loadAd(activity);
    }

    public interface OnShowAdCompleteListener {
        void onAdShown();
    }

    public void showAdIfAvailable(Activity activity, OnShowAdCompleteListener onShowAdCompleteListener) {
        appOpenAdManager.showAdIfAvailable(activity, onShowAdCompleteListener);
    }

    private static class AppOpenAdManager {
        private AppOpenAd appOpenAd = null;
        private boolean isLoadingAd = false;
        private boolean isShowingAd = false;

        public AppOpenAdManager() {
        }

        private void loadAd(Context context) {
            if (isLoadingAd || isAdAvailable()) {
                return;
            }

            isLoadingAd = true;
            AdRequest request = new AdRequest.Builder().build();
            AppOpenAd.load(context,opends , request, new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    isLoadingAd = false;
                    Log.e(TAG, "onError: admobopen: " + loadAdError.getMessage());
                 //   Toast.makeText(context.getApplicationContext(), "THIS IS ERORR-"+loadAdError.getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("onError: admobopen: --",opends);

                    //  Toast.makeText(context, "There was an error while loading ad", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdLoaded(@NonNull AppOpenAd openAd) {
                    super.onAdLoaded(openAd);
                    appOpenAd = openAd;
                    isLoadingAd = false;

                }
            });
        }

        private boolean isAdAvailable() {
            return appOpenAd != null;
        }

        private void showAdIfAvailable(Activity activity) {
            showAdIfAvailable(activity, new OnShowAdCompleteListener() {
                @Override
                public void onAdShown() {
                }
            });
        }

        private void showAdIfAvailable(Activity activity, OnShowAdCompleteListener onShowAdCompleteListener) {
            if (isShowingAd) {
                return;
            }

            if (!isAdAvailable()) {
                onShowAdCompleteListener.onAdShown();
                return;
            }

            appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    isShowingAd = false;
                    onShowAdCompleteListener.onAdShown();
                    appOpenAd = null;
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    isShowingAd = false;
                    onShowAdCompleteListener.onAdShown();
                    appOpenAd = null;
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                }
            });

            isShowingAd = true;
            appOpenAd.show(activity);
        }
    }
}
