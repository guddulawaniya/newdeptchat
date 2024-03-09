package com.deptchat.livevideocallapp.Ads;


import com.deptchat.livevideocallapp.Adapters.Chatdatamodule;
import com.deptchat.livevideocallapp.Adapters.Slidermodule;
import com.deptchat.livevideocallapp.Adapters.YourDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("samman_nidhi_ads_fetch.php")
    Call<List<SammanNidhiAdsModel>> sammanNidhifetchAds(@Field("id") String id);

    @GET("getData.php")
    Call<List<YourDataModel>> getData();


    @POST("fetch_others_api.php")
    Call<Slidermodule> getAllOthers();

    @POST("get_chat_data.php")
    Call<List<Chatdatamodule>> getchatMessage();
    @POST("fetch_intro_sentences.php")
    Call<Slidermodule> getIntroSentences();

}
