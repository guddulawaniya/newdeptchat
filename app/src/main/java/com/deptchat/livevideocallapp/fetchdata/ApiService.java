package com.deptchat.livevideocallapp.fetchdata;


import com.deptchat.livevideocallapp.Adapters.YourDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("getData.php")
    Call<List<YourDataModel>> getData();
}
