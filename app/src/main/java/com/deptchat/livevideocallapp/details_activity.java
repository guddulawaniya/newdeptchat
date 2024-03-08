package com.deptchat.livevideocallapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.deptchat.livevideocallapp.Adapters.Datum;
import com.deptchat.livevideocallapp.Adapters.Slidermodule;
import com.deptchat.livevideocallapp.Adapters.favoratemodule;
import com.deptchat.livevideocallapp.Ads.ApiInterface;
import com.deptchat.livevideocallapp.Ads.ApiWebServices;
import com.deptchat.livevideocallapp.Ads.bannerad;
import com.deptchat.livevideocallapp.Ads.intersital;
import com.deptchat.livevideocallapp.sqllite.ConnectCallTB;
import com.deptchat.livevideocallapp.sqllite.chatHalper;
import com.deptchat.livevideocallapp.sqllite.favorateHalper;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class details_activity extends AppCompatActivity {


    String video;

    favorateHalper Helper;
    ConnectCallTB callHelper;
    static int percount;
    private ApiInterface apiInterface;
    TextView sentence;
    TextView district;
    chatHalper messageHelper;
    private SharedPreferences sharedPreferences;
    int imageid;
    Cursor partydb;

    ToggleButton toggleFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        try {
            new bannerad(this, this).Banner_Ad(findViewById(R.id.bannerad));
        } catch (Exception e) {

        }
        percount++;

        district = findViewById(R.id.district);
        TextView connectnow = findViewById(R.id.connectnow);
        TextView id = findViewById(R.id.userid);
        sentence = findViewById(R.id.sentence);
        ImageView backarrow = findViewById(R.id.backarrow);
        toggleFavorite = findViewById(R.id.toggleFavorite);
        CardView messagebutton = findViewById(R.id.messagebutton);
        TextView permincharge = findViewById(R.id.permincharge);
        toggleFavorite.setTextOff("");
        toggleFavorite.setTextOn("");


        apiInterface = ApiWebServices.getApiInterface();

        String[] numbers = {"Chennai", "Jharkhand", "Kerala", "Maharashtra", "Rajasthan", "Uttarakhand", "Ladakh"};
        Random random = new Random();
        int randomIndex = random.nextInt(numbers.length);
        String randomAge = numbers[randomIndex];
        district.setText(randomAge);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        imageid = sharedPreferences.getInt("id", 0);


        favoratelist();


        Helper = new favorateHalper(details_activity.this);
//        messageHelper = new MessageHelper(details_activity.this);
        callHelper = new ConnectCallTB(details_activity.this);

        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        int perminchargetext = preferences.getInt("perminchage", 0);
        permincharge.setText(perminchargetext + "/min");
        String count = preferences.getString("prices", "").split("#")[20];
        int countintoINteger = Integer.parseInt(count);
        if ((percount % countintoINteger) == 0) {
            new intersital(this).Show_Ads();
        }

        fetchsentence();

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
                    favoratemodule model = new favoratemodule(imageid, nametext, imageurl, video);
                    Helper.insertdata(model);
                    toggleFavorite.setBackgroundResource(R.drawable.favoritered);

                } else {

                    Helper.deleteDataById(imageid);
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
                messageHelper = new chatHalper(details_activity.this);

                favoratemodule model = new favoratemodule(imageid, nametext, imageurl, video);
                messageHelper.insertdata(model);

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


                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putBoolean("callcheck", true);
                editor.commit();

                new intersital(details_activity.this).Show_Ads();

                favoratemodule model = new favoratemodule(imageid, nametext, imageurl, video);
                callHelper.insertData(model);

                Intent intent1 = new Intent(details_activity.this, ConnectionVideoActivity.class);
                startActivity(intent1);


            }
        });
    }

    public void favoratelist() {
        partydb = new favorateHalper(details_activity.this).getdata();

        if (partydb != null && partydb.moveToNext()) {
            do {
                int id = partydb.getInt(0);
                if (id == imageid) {
                    toggleFavorite.setBackgroundResource(R.drawable.favoritered);
                }


            } while (partydb.moveToNext());

        }


    }

    void fetchsentence() {
        apiInterface.getIntroSentences().enqueue(new Callback<Slidermodule>() {
            @Override
            public void onResponse(Call<Slidermodule> call, Response<Slidermodule> response) {
                ArrayList<String> texts = new ArrayList<>();
                for (Datum model : response.body().getData()) {
                    texts.add(model.getSentence());
                }
                Random random = new Random();
                int randomIndex = random.nextInt(texts.size());
                String randomText = texts.get(randomIndex);
                sentence.setText(randomText);
            }

            @Override
            public void onFailure(Call<Slidermodule> call, Throwable t) {

            }
        });
    }


}