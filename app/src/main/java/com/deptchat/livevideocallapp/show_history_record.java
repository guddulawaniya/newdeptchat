package com.deptchat.livevideocallapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deptchat.livevideocallapp.Adapters.favoratemodule;
import com.deptchat.livevideocallapp.Adapters.historyshowAdapter;
import com.deptchat.livevideocallapp.Ads.bannerad;
import com.deptchat.livevideocallapp.Ads.intersital;
import com.deptchat.livevideocallapp.sqllite.ConnectCallTB;
import com.deptchat.livevideocallapp.sqllite.favorateHalper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class show_history_record extends AppCompatActivity {

    List<favoratemodule> list;
    Cursor partydb, connectcall;
    RecyclerView recyclerView;
    LinearLayout nodatafound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history_record);

        Intent intent = getIntent();
        recyclerView = findViewById(R.id.recycler_view);
        nodatafound = findViewById(R.id.nodatafound);
        list = new ArrayList<>();
        new intersital(this).Show_Ads();
        try {
//            new bannerad(this,this).Native_Ad(findViewById(R.id.nativead),findViewById(R.id.my_template));
            new bannerad(this,this).Banner_Ad(findViewById(R.id.bannerad));
            new bannerad(this,this).Banner_Ad(findViewById(R.id.bannerada));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        TextView title = findViewById(R.id.titletext);
        title.setText(intent.getStringExtra("title"));
        int id = intent.getIntExtra("id", 0);
        ImageView backarrow = findViewById(R.id.backarrow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        boolean checkfav = preferences.getBoolean("favocheck", false);
        boolean callcheck = preferences.getBoolean("callcheck", false);
        nodatafound.setVisibility(View.GONE);

        if (id == 1 && checkfav) {
            favoratelist();

        } else if (id == 2) {
            callconnect();
        } else {
            nodatafound.setVisibility(View.VISIBLE);
        }


        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void favoratelist() {
        partydb = new favorateHalper(show_history_record.this).getdata();

        if (partydb != null && partydb.moveToNext()) {
            do {
                int id = partydb.getInt(0);
                String name = partydb.getString(1);
                String image = partydb.getString(2);
                String video = partydb.getString(3);
                list.add(new favoratemodule(id,name, image, video));


            } while (partydb.moveToNext());


            historyshowAdapter history = new historyshowAdapter(list, this);
            recyclerView.setAdapter(history);
        }


    }

    public void callconnect() {
        connectcall = new ConnectCallTB(show_history_record.this).getData();

        if (connectcall != null && connectcall.moveToNext()) {
            do {
                int id = connectcall.getInt(0);
                String name = connectcall.getString(1);
                String image = connectcall.getString(2);
                String video = connectcall.getString(3);
                list.add(new favoratemodule(id,name, image, video));


            } while (connectcall.moveToNext());


            historyshowAdapter history = new historyshowAdapter(list, this);
            recyclerView.setAdapter(history);
        }
    }
}