package com.deptchat.livevideocallapp.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deptchat.livevideocallapp.Adapters.SliderAdapter;
import com.deptchat.livevideocallapp.Adapters.SliderData;
import com.deptchat.livevideocallapp.Adapters.YourDataModel;
import com.deptchat.livevideocallapp.Adapters.maineAdapter;
import com.deptchat.livevideocallapp.Ads.ApiService;
import com.deptchat.livevideocallapp.Ads.bannerad;
import com.deptchat.livevideocallapp.R;
import com.google.android.gms.ads.AdView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Home_fragment extends Fragment {
    String img, video, name, city;
    RecyclerView recyclerView;
    private ProgressBar progressDialog;
    int permincoin = 100;
    AdView mAdView;
    String url1 = "https://c4.wallpaperflare.com/wallpaper/426/215/771/girl-landscape-view-wallpaper-preview.jpg";
    String url2 = "https://c4.wallpaperflare.com/wallpaper/337/789/191/girl-landscape-the-city-the-wind-wallpaper-preview.jpg";
    String url3 = "https://w0.peakpx.com/wallpaper/808/709/HD-wallpaper-beatrice-in-pink-swimsuit-posture-sexy-sea-beach-breasts-beatrice-chirita-girl-landscape.jpg";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);

//        BannerAds bannerAds = new BannerAds(getContext());


        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
//        UnityAds.initialize(getContext(), "your_game_id", (IUnityAdsInitializationListener) new UnityAdsListener());

        // initializing the slider view.
        SliderView sliderView = view.findViewById(R.id.slider);


        try {
            new bannerad(getContext(), getActivity()).Banner_Ad(view.findViewById(R.id.bannerad));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(getContext(), sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                onDestroy();
//            }
//        }, 6000);
//
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                bannerAds.interstitialads(getActivity());
//            }
//        }, 40000);


//        bannerAds.interstitialads(getActivity());
        progressDialog = view.findViewById(R.id.progressBar);

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("login", MODE_PRIVATE).edit();
        editor.putInt("perminchage", permincoin);
        editor.putInt("coins", 1000);
        editor.commit();


        fetchData();
        return view;
        // Inflate the layout for this fragment
    }

//    @Override
//    public void onDestroy() {
//        // Destroy the AdView when the activity is destroyed to release resources
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
//        super.onDestroy();
//    }

    private void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gedgetsworld.in/PM_Kisan_Yojana/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Make the API call
        Call<List<YourDataModel>> call = apiService.getData();
        call.enqueue(new Callback<List<YourDataModel>>() {
            @Override
            public void onResponse(Call<List<YourDataModel>> call, Response<List<YourDataModel>> response) {
                if (response.isSuccessful()) {
                    List<YourDataModel> dataList = response.body();

                    // Shuffle the list to display items in random order
                    Collections.shuffle(dataList);
                    if (!dataList.isEmpty()) {
                        YourDataModel randomItem = dataList.get(new Random().nextInt(dataList.size()));
                        img = randomItem.getImg();
                        video = randomItem.getVideo();
                        String[] parts = randomItem.getText().split("-");

                        if (parts.length == 2) {
                            // Extract the name and city
                            name = parts[0];
                            city = parts[1];
                        }
                        Log.d("RandomValues", "img: " + img + ", video: " + video);
                    }
                    progressDialog.setVisibility(View.GONE);

                    maineAdapter adapter = new maineAdapter(dataList, getContext());
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    recyclerView.setAdapter(adapter);


                } else {
                    // Handle error
                }

                // Stop refreshing animation
            }

            @Override
            public void onFailure(Call<List<YourDataModel>> call, Throwable t) {
                // Handle failure
                progressDialog.setVisibility(View.GONE);

                // Stop refreshing animati
            }
        });
    }
}