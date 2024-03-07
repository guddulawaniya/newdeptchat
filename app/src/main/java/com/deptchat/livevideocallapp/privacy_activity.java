package com.deptchat.livevideocallapp;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.deptchat.livevideocallapp.Ads.intersital;


public class privacy_activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        new intersital(this).Show_Ads();


    }

}