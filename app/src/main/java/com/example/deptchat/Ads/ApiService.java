package com.example.deptchat.Ads;


import com.example.deptchat.Adapters.YourDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("getData.php")
    Call<List<YourDataModel>> getData();
}
