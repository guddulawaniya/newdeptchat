package com.example.deptchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deptchat.Adapters.favoratemodule;
import com.example.deptchat.Adapters.historyshowAdapter;
import com.example.deptchat.sqllite.ConnectCallTB;
import com.example.deptchat.sqllite.favorateHalper;

import java.util.ArrayList;
import java.util.List;

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
                String name = partydb.getString(1);
                String image = partydb.getString(2);
                String video = partydb.getString(3);
                list.add(new favoratemodule(name, image, video));


            } while (partydb.moveToNext());


            historyshowAdapter history = new historyshowAdapter(list, this);
            recyclerView.setAdapter(history);
        }


    }

    public void callconnect() {
        connectcall = new ConnectCallTB(show_history_record.this).getData();

        if (partydb != null && partydb.moveToNext()) {
            do {
                String name = partydb.getString(1);
                String image = partydb.getString(2);
                String video = partydb.getString(3);
                list.add(new favoratemodule(name, image, video));


            } while (partydb.moveToNext());


            historyshowAdapter history = new historyshowAdapter(list, this);
            recyclerView.setAdapter(history);
        }


    }
}