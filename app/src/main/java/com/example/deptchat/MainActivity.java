package com.example.deptchat;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.deptchat.Ads.BannerAds;
import com.example.deptchat.Fragments.Home_fragment;
import com.example.deptchat.Fragments.Profile_Fragment;
import com.example.deptchat.Fragments.message_fragment;
import com.example.deptchat.Fragments.meta_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        BannerAds bannerAds =new BannerAds(this);
////        bannerAds.BnnersAdview(findViewById(R.id.adView));
//
//        bannerAds.interstitialads(MainActivity.this);
        replaceFragment(new Home_fragment());

        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (R.id.home_fragment == item.getItemId()) {
            replaceFragment(new Home_fragment());
            return true;
        } else if (R.id.meta_fragment == item.getItemId()) {
            replaceFragment(new meta_fragment());
            return true;
        } else if (R.id.message_fragment == item.getItemId()) {
            replaceFragment(new message_fragment());
            return true;
        } else if (R.id.profile_fragment == item.getItemId()) {

            replaceFragment(new Profile_Fragment());
            return true;

        }

        return true;
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}