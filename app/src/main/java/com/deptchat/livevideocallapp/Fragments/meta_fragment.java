package com.deptchat.livevideocallapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.deptchat.livevideocallapp.Adapters.YourDataModel;
import com.deptchat.livevideocallapp.Ads.ApiInterface;
import com.deptchat.livevideocallapp.Ads.bannerad;
import com.deptchat.livevideocallapp.ConnectionVideoActivity;

import com.deptchat.livevideocallapp.R;
import com.deptchat.livevideocallapp.plan_activity;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class meta_fragment extends Fragment {


    Handler handler;
    private int count = 0;
    TextView textviewsetnumber,coins;
    Cursor partydb;
    LottieAnimationView LottieAnimationView,startbutton;


    CircleImageView image1,image2,image3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meta, container, false);

         textviewsetnumber = view.findViewById(R.id.loopnumbers);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        coins = view.findViewById(R.id.coins);
         startbutton = view.findViewById(R.id.startbutton);
         LottieAnimationView = view.findViewById(R.id.scanningview);

        handler = new Handler();


        try {
//            new bannerad(getContext(),getActivity()).Native_Ad(view.findViewById(R.id.nativead),findViewById(R.id.my_template));
            new bannerad(getContext(),getActivity()).Banner_Ad(view.findViewById(R.id.bannerad));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        startCounting(1);

        SharedPreferences preferences = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
        int permincharge = preferences.getInt("perminchage", 0);
        int availablecoin = preferences.getInt("coins", 0);
        coins.setText(""+availablecoin);

            startbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (availablecoin>permincharge) {

                        startbutton.setVisibility(View.GONE);


                        LottieAnimationView.setVisibility(View.VISIBLE);
                        image1.setVisibility(View.VISIBLE);
                        image2.setVisibility(View.VISIBLE);
                        image3.setVisibility(View.VISIBLE);
                        fetchData();

                    }
                    else
                    {
                        Intent intent = new Intent(getContext(), plan_activity.class);
                        getActivity().startActivity(intent);
                    }

                }
            });



        return view;
    }

    void deplayintent()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startbutton.setVisibility(View.VISIBLE);

                LottieAnimationView.setVisibility(View.GONE);
                image1.setVisibility(View.GONE);
                image2.setVisibility(View.GONE);
                image3.setVisibility(View.GONE);

                Intent intent = new Intent(getContext(), ConnectionVideoActivity.class);
                getActivity().startActivity(intent);

            }
        }, 5000);

    }


    private void startCounting(final long delayMillis) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               count= count+200;
               if (count<27670)
               {
                   textviewsetnumber.setText(String.valueOf(count));
                   // Continue counting with the same delay
                   startCounting(delayMillis);
               }

            }
        }, delayMillis);
    }
    @Override
    public void onDestroy() {
        // Remove any pending callbacks to prevent memory leaks
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gedgetsworld.in/PM_Kisan_Yojana/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiService = retrofit.create(ApiInterface.class);

        // Make the API call
        Call<List<YourDataModel>> call = apiService.getData();
        call.enqueue(new Callback<List<YourDataModel>>() {
            String name,city;
            @Override
            public void onResponse(Call<List<YourDataModel>> call, Response<List<YourDataModel>> response) {
                if (response.isSuccessful()) {
                    List<YourDataModel> dataList = response.body();



                    SharedPreferences.Editor preferences = getContext().getSharedPreferences("login",Context.MODE_PRIVATE).edit();


                    // Shuffle the list to display items in random order
                    Collections.shuffle(dataList);
                        YourDataModel randomItem = dataList.get(new Random().nextInt(dataList.size()));
                      String  img = randomItem.getImg();
                       String video = randomItem.getVideo();
                        String[] parts = randomItem.getText().split("-");


                        if (parts.length == 2) {
                            // Extract the name and city
                            name = parts[0];
                            city = parts[1];
                        }
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("login",Context.MODE_PRIVATE).edit();
                        editor.putString("image",img);
                        editor.putString("video",video);
                        editor.putString("name",name);
                        editor.putString("location",city);
                        editor.commit();
                    deplayintent();



                }

                // Stop refreshing animation
            }

            @Override
            public void onFailure(Call<List<YourDataModel>> call, Throwable t) {
                startbutton.setVisibility(View.VISIBLE);

            }
        });
    }
}