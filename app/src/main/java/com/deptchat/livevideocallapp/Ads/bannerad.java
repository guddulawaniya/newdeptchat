package com.deptchat.livevideocallapp.Ads;



import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.deptchat.livevideocallapp.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdView;
import com.facebook.ads.NativeBannerAdView;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class bannerad {
    public static String bannerid,nativeid,unitynative,uniybanner;
    private static String TAG = "Facebook_Native";
    public static Context context;
    public static Activity activity;
    private static AdLoader adLoader;
    private static MaxNativeAdLoader nativeAdLoader;
    private static MaxAd nativeAd;
    private static MaxAdView appadView;


    static SharedPreferences sharedPreferences ;


    public bannerad(Context ctx, Activity atx) {
        context = ctx;
        activity=atx;
        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);

    }

    public static void Native_Ad(final FrameLayout frameLayout ,TemplateView templateView) {
        if (!sharedPreferences.getString("fbnative","iuhikuhiukh").equals("STOP")){
            Facebook_Native(frameLayout,templateView);


        }

    }
    public static void Banner_Ad(final FrameLayout frameLayout) throws Exception {
        if (!sharedPreferences.getString("fbbanner","").equals("STOP")){
            Facebook_Banner(frameLayout);

        }

    }


    public static void Facebook_Native(final FrameLayout frameLayout,TemplateView template) {
        final NativeAd nativeAd = new NativeAd(context, sharedPreferences.getString("fbnative","hjgfhjfjg"));
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {

                Log.e(TAG, "onError: facebooknative: " + adError.getErrorMessage());


                Facebook_Mrec(frameLayout,template);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                frameLayout.setVisibility(View.VISIBLE);
                View adView = NativeAdView.render(context, nativeAd, NativeAdView.Type.HEIGHT_300);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());


    }
    public static void Facebook_Mrec(final FrameLayout frameLayout,TemplateView template) {
        Log.e(TAG, "Adaptive_Banner_mrec: ");
        frameLayout.removeAllViews();
        AdView adView = new AdView(context, sharedPreferences.getString("fbmercnative","uyguyguyg"), AdSize.RECTANGLE_HEIGHT_250);
        com.facebook.ads.AdListener listener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG, "onError: facebookmerc: " + adError.getErrorMessage());
                //       loadadmobNative(frameLayout,template);

                if (sharedPreferences.getString("admobnative","ca-app-pub-3940256099942544/6300978111").startsWith("applovin#")){
                    String[] parts1 = sharedPreferences.getString("admobnative","ca-app-pub-3940256099942544/6300978111").split("#");

                    if (parts1.length > 1) {
                        nativeid = parts1[1];
                        if (parts1[0].equals("applovin")){
                            applovin_native(frameLayout);
                        }

                        // System.out.println("String after #: " + afterHash);
                    }

                }else if (sharedPreferences.getString("admobnative","ca-app-pub-3940256099942544/6300978111").startsWith("unity#")) {
                    String[] parts1 = sharedPreferences.getString("admobnative","ca-app-pub-3940256099942544/6300978111").split("#");

                    if (parts1.length > 1) {
                        unitynative = parts1[1];
                        unity_Nbanner(frameLayout);


                        // System.out.println("String after #: " + afterHash);
                    }

                }
                else {
                    loadadmobNative(frameLayout,template);

                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e(TAG, "onAdLoaded: fb mrec");
                frameLayout.setVisibility(View.VISIBLE);


            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        frameLayout.addView(adView);
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(listener).build());


    }
    public static void Facebook_Native_Banner(final FrameLayout frameLayout) {
        final com.facebook.ads.NativeBannerAd nativeAd = new com.facebook.ads.NativeBannerAd(context, sharedPreferences.getString("fbbannernative","hgfhgfj"));
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG, "onError: facebooknativebaner: " + adError.getErrorMessage());
                if (sharedPreferences.getString("admobbanner","ca-app-pub-3940256099942544/6300978111").startsWith("applovin#")){
                    String[] parts = sharedPreferences.getString("admobbanner","ca-app-pub-3940256099942544/6300978111").split("#");

                    if (parts.length > 1) {
                        bannerid = parts[1];
                        applovin_banner(frameLayout);

                        // System.out.println("String after #: " + afterHash);
                    }

                }else if(sharedPreferences.getString("admobbanner","ca-app-pub-3940256099942544/6300978111").startsWith("unity#")){
                    Log.d("unityrishi", "admob ibanner");

                    String[] parts = sharedPreferences.getString("admobbanner","ca-app-pub-3940256099942544/6300978111").split("#");

                    if (parts.length > 1) {
                        uniybanner = parts[1];
                        unity_banner(frameLayout);

                        // System.out.println("String after #: " + afterHash);
                    }

                }else {
                    //loadAdMobBanner(frameLayout);

                }
                //     loadAdMobBanner(frameLayout);

            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e(TAG, "onAdLoaded: native banner");
                View adView = NativeBannerAdView.render(context, nativeAd, NativeBannerAdView.Type.HEIGHT_120);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }


    public static void loadadmobNative(final FrameLayout frameLayout,TemplateView template) {
        // Create an AdLoader object for AdMob
        AdLoader adLoader = new AdLoader.Builder(context, sharedPreferences.getString("admobnative","ca-app-pub-3940256099942544/2247696110"))
                .forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                        frameLayout.setVisibility(View.GONE);
                        template.setVisibility(View.VISIBLE);
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Log.e("AdMob_Native", "Failed to load native ads: " + loadAdError.getMessage());
                        // Handle the AdMob native ad load failure

                    }
                })
                .build();

        // Load the AdMob native ad
        adLoader.loadAd(new AdRequest.Builder().build());
    }
    public static void Facebook_Banner(final FrameLayout frameLayout) throws Exception {
        AdView adView = new AdView(context, sharedPreferences.getString("fbbanner","3940256099942544/6300978111"), AdSize.BANNER_HEIGHT_50);
        com.facebook.ads.AdListener listener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG, "onError: facebookbanner: " + adError.getErrorMessage());

                Facebook_Native_Banner(frameLayout);
            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        frameLayout.addView(adView);
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(listener).build());

    }

    private static void loadAdMobBanner(FrameLayout frameLayout) {
        // Create an AdView object for AdMob

    }

    public static void applovin_banner(FrameLayout frameLayout){
        appadView = new MaxAdView( bannerid,context);
        appadView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd maxAd) {

            }

            @Override
            public void onAdCollapsed(MaxAd maxAd) {

            }

            @Override
            public void onAdLoaded(MaxAd maxAd) {

            }

            @Override
            public void onAdDisplayed(MaxAd maxAd) {

            }

            @Override
            public void onAdHidden(MaxAd maxAd) {

            }

            @Override
            public void onAdClicked(MaxAd maxAd) {

            }

            @Override
            public void onAdLoadFailed(String s, MaxError maxError) {

            }

            @Override
            public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {

            }
        });

        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;

        // Banner height on phones and tablets is 50 and 90, respectively
        int heightPx = context.getResources().getDimensionPixelSize( R.dimen.banner_height );

        appadView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );

        // Set background or background color for banners to be fully functional
        //  adView.setBackgroundColor( this.context.getResources().getColor(R.color.Appcolor) );
/*
        ViewGroup rootView = this.activity.findViewById( android.R.id.content );
        rootView.addView( adView );


 */
        frameLayout.addView(appadView);
        // Load the ad

        appadView.loadAd();


    }

    public static void  applovin_native(FrameLayout nativeAdContainer )
    {
        // FrameLayout nativeAdContainer = findViewById( R.id.native_ad_layout );

        nativeAdLoader = new MaxNativeAdLoader( nativeid, context );
        nativeAdLoader.setNativeAdListener( new MaxNativeAdListener()
        {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad)
            {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if ( nativeAd != null )
                {
                    nativeAdLoader.destroy( nativeAd );
                }

                // Save ad for cleanup.
                nativeAd = ad;

                // Add ad view to view.
                nativeAdContainer.removeAllViews();
                nativeAdContainer.addView( nativeAdView );
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error)
            {
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad)
            {
                // Optional click callback
            }
        } );


        nativeAdLoader.loadAd();


    }


    public static void unity_Nbanner(FrameLayout frameLayout) {

        BannerView bannerView = new BannerView(activity, unitynative, new UnityBannerSize(FrameLayout.LayoutParams.MATCH_PARENT, 250));

        // 3. Set banner position (optional):
        //   bannerView.setPosition(BannerPosition.BOTTOM_CENTER);

        // 4. Load the banner ad:
        bannerView.load();

        // 5. Add bannerView to the FrameLayout:
        frameLayout.addView(bannerView);
    }

    public static void unity_banner(FrameLayout frameLayout) {

        BannerView bannerView = new BannerView(activity, uniybanner, new UnityBannerSize(FrameLayout.LayoutParams.MATCH_PARENT, 50));

        // 3. Set banner position (optional):
        //   bannerView.setPosition(BannerPosition.BOTTOM_CENTER);

        // 4. Load the banner ad:
        bannerView.load();

        // 5. Add bannerView to the FrameLayout:
        frameLayout.addView(bannerView);
    }


}
