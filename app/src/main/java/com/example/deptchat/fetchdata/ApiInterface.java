package com.example.deptchat.fetchdata;


import com.example.deptchat.Ads.SammanNidhiAdsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("samman_nidhi_ads_fetch.php")
    Call<List<SammanNidhiAdsModel>> sammanNidhifetchAds(@Field("id") String id);

}
