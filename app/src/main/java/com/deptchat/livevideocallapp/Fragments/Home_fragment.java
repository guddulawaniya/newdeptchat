package com.deptchat.livevideocallapp.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deptchat.livevideocallapp.Adapters.Datum;
import com.deptchat.livevideocallapp.Adapters.SliderAdapter;
import com.deptchat.livevideocallapp.Adapters.Slidermodule;
import com.deptchat.livevideocallapp.Adapters.YourDataModel;
import com.deptchat.livevideocallapp.Adapters.maineAdapter;
import com.deptchat.livevideocallapp.Ads.ApiInterface;
import com.deptchat.livevideocallapp.Ads.ApiWebServices;
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


public class Home_fragment extends Fragment {
    String img, video, name, city;
    RecyclerView recyclerView;
    private ProgressBar progressDialog;
    String permincoin;
    List<Datum> sliderlist;
    SliderView sliderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        sliderlist = new ArrayList<>();
        sliderView = view.findViewById(R.id.slider);
        sliderfetchData();


        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        sliderView.setScrollTimeInSec(3);


        sliderView.setAutoCycle(true);

        sliderView.startAutoCycle();


        try {
            new bannerad(getContext(), getActivity()).Banner_Ad(view.findViewById(R.id.bannerad));
        } catch (Exception e) {

        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);

        progressDialog = view.findViewById(R.id.progressBar);
        permincoin = sharedPreferences.getString("prices", "10").split("#")[18];

        int coin = Integer.parseInt(permincoin);



        fetchData();
        return view;
    }

    private void sliderfetchData() {

        Call<Slidermodule> call = ApiWebServices.getApiInterface().getAllOthers();
        call.enqueue(new Callback<Slidermodule>() {
            @Override
            public void onResponse(Call<Slidermodule> call, Response<Slidermodule> response) {
                if (response.isSuccessful()) {
                    Slidermodule sliderModule = response.body();

                    if (sliderModule != null && sliderModule.getData() != null && !sliderModule.getData().isEmpty()) {
                        sliderlist = sliderModule.getData();

//                        // Access the image from the first item in the dataList
//                        String imageUrl = dataList.get(0).getImage();
//                        sliderlist.add(imageUrl);
                        SliderAdapter adapter = new SliderAdapter(getActivity(), sliderlist);
                        sliderView.setSliderAdapter(adapter);

                        // Shuffle the list to display items in random order
                        Collections.shuffle(sliderlist);

                        // Update your UI or perform any other actions here

                        progressDialog.setVisibility(View.GONE);
                    } else {
                        // Handle empty response or null list
                    }
                } else {
                    // Handle error
                    Log.e("API Error", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Slidermodule> call, Throwable t) {
                // Handle failure
                progressDialog.setVisibility(View.GONE);
                Log.e("fetchdata", t.getMessage());
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void fetchData() {

        // Make the API call
        Call<List<YourDataModel>> call = ApiWebServices.getApiInterface().getData();
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