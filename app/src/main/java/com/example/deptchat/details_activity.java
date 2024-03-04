package com.example.deptchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.deptchat.Adapters.favoratemodule;
import com.example.deptchat.Ads.BannerAds;
import com.example.deptchat.sqllite.ConnectCallTB;
import com.example.deptchat.sqllite.MessageHelper;
import com.example.deptchat.sqllite.favorateHalper;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Random;

public class details_activity extends AppCompatActivity {


    String video;

    favorateHalper Helper;
    MessageHelper messageHelper;
    ConnectCallTB callHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BannerAds bannerAds = new BannerAds(this);

//        bannerAds.interstitialads(details_activity.this);

        setContentView(R.layout.activity_details);
        TextView connectnow = findViewById(R.id.connectnow);
        TextView id = findViewById(R.id.userid);
        ImageView backarrow = findViewById(R.id.backarrow);
        ToggleButton toggleFavorite = findViewById(R.id.toggleFavorite);
        CardView messagebutton = findViewById(R.id.messagebutton);
        TextView permincharge = findViewById(R.id.permincharge);
        toggleFavorite.setTextOff("");
        toggleFavorite.setTextOn("");
        Helper = new favorateHalper(details_activity.this);
        messageHelper = new MessageHelper(details_activity.this);
        callHelper = new ConnectCallTB(details_activity.this);

        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        int perminchargetext = preferences.getInt("perminchage", 0);
        permincharge.setText(perminchargetext + "/min");


        TextView name = findViewById(R.id.name);
        TextView age = findViewById(R.id.age);
        ImageView imagefull = findViewById(R.id.fullimageview);
        ImageView menu = findViewById(R.id.menu);
        ImageView shortimage = findViewById(R.id.shortimageview);


        String nametext = preferences.getString("name", null);
        name.setText(nametext);
        age.setText(preferences.getString("age", null));
        String imageurl = preferences.getString("image", null);


        Picasso.get().load(imageurl).into(imagefull);
        Picasso.get().load(imageurl).into(shortimage);


        video = preferences.getString("video", null);


        id.setText(new DecimalFormat("000000000").format(new Random().nextInt(999999999)));


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diamond_bottomsheet bottomsheet = new diamond_bottomsheet();

                bottomsheet.show(getSupportFragmentManager(), bottomsheet.getTag());
            }
        });


        toggleFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                    editor.putBoolean("favocheck", true);
                    editor.commit();
                    favoratemodule model = new favoratemodule(nametext, imageurl, video);
                    Helper.insertdata(model);
                    toggleFavorite.setBackgroundResource(R.drawable.favoritered);

                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                    editor.putBoolean("favocheck", false);
                    editor.commit();
                    Helper.deleteRowWithCondition(nametext);
                    toggleFavorite.setBackgroundResource(R.drawable.favorite);
                }
            }
        });


        messagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putBoolean("checksms", true);
                editor.commit();

//                favoratemodule model = new favoratemodule(nametext, imageurl, video);
//                messageHelper.insertData(model);

                Intent intent = new Intent(details_activity.this, chat_activity.class);
                startActivity(intent);
            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        connectnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                int permincharge = preferences.getInt("perminchage", 0);
                int availablecoin = preferences.getInt("coins", 0);

                if (availablecoin >= permincharge && availablecoin != 0) {

                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                    editor.putBoolean("callcheck", true);
                    editor.commit();

//                    favoratemodule model = new favoratemodule(nametext, imageurl, video);
//                    callHelper.insertData(model);

                    Intent intent = new Intent(details_activity.this, ConnectionVideoActivity.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(details_activity.this, plan_activity.class);
                    startActivity(intent);
                }

            }
        });
    }

}