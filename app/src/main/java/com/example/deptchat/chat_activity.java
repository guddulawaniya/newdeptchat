package com.example.deptchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.deptchat.Adapters.ChatAdapter;
import com.example.deptchat.Adapters.MessagesModule;
import com.example.deptchat.sqllite.chatHalpder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class chat_activity extends AppCompatActivity {

    ImageView back_arrow;
    CircleImageView videocall;
    TextView userName,textmessage,buyfreevideobutton;
    ImageView chatmenu;
    LinearLayout locklinearbg;
    CardView sendsms;
    RecyclerView recyclerView;
    ArrayList<MessagesModule> messagelist;
    LinearLayout chatlocklinearlayout,chatlinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messagelist = new ArrayList<>();

       String age = new DecimalFormat("18").format(new Random().nextInt(36));
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String imagetext = preferences.getString("image",null);
        String name = preferences.getString("name",null);
        String video = preferences.getString("video",null);
        int avaiblecoin = preferences.getInt("coins",0);


        recyclerView = findViewById(R.id.ChatRecyclerView);
        back_arrow = findViewById(R.id.backarrow);
        userName = findViewById(R.id.userName);
        chatmenu = findViewById(R.id.chatmenu);
        videocall = findViewById(R.id.videocallbutton);
        sendsms = findViewById(R.id.sendsms);
        textmessage = findViewById(R.id.textmessage);
        buyfreevideobutton = findViewById(R.id.buyfreevideobutton);
        chatlocklinearlayout = findViewById(R.id.chatlocklinearlayout);
        chatlinear = findViewById(R.id.locklinear);
        locklinearbg = findViewById(R.id.locklinearbg);
        sendsms.setEnabled(false);

        if (avaiblecoin>0)
        {
            chatlocklinearlayout.setVisibility(View.INVISIBLE);
            chatlinear.setVisibility(View.VISIBLE);
            locklinearbg.setVisibility(View.INVISIBLE);

        }
        else {

            chatlinear.setVisibility(View.INVISIBLE);
            locklinearbg.setVisibility(View.VISIBLE);
            chatlocklinearlayout.setVisibility(View.VISIBLE);
        }


        buyfreevideobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chat_activity.this, plan_activity.class);
                startActivity(intent);
            }
        });


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                messagelist.add(new MessagesModule(imagetext));
//                recyclerView.setLayoutManager(new LinearLayoutManager(chat_activity.this));
//                ChatAdapter chatAdapter = new ChatAdapter(messagelist,chat_activity.this,3);
//                recyclerView.setAdapter(chatAdapter);
//            }
//        },5000);



        chatHalpder chathelper=  new chatHalpder(chat_activity.this);

        chatmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                diamond_bottomsheet bottomsheet = new diamond_bottomsheet();

                bottomsheet.show(getSupportFragmentManager(), bottomsheet.getTag());
            }
        });


        textmessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>1)
                {
                    sendsms.setEnabled(true);
                }
                else
                {
                    sendsms.setEnabled(false);
                }


            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter();
        sendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textmessage.getText().toString().isEmpty())
                {
                    MessagesModule module = new MessagesModule(textmessage.getText().toString(),1);


//                    chathelper.insertdata(module);

//                    messagelist.add();
//                    textmessage.setText("");

                    recyclerView.setLayoutManager(new LinearLayoutManager(chat_activity.this));
                    ChatAdapter chatAdapter = new ChatAdapter(messagelist,chat_activity.this,1);
                    recyclerView.setAdapter(chatAdapter);
                }
            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                int permincharge = preferences.getInt("perminchage", 0);
                int availablecoin = preferences.getInt("coins", 0);

                if (availablecoin >= permincharge && availablecoin != 0) {

                    SharedPreferences.Editor editor = getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                    editor.putString("image", imagetext);
                    editor.putString("name", name);
                    editor.putString("video", video);
                    editor.putString("video", video);
                    editor.commit();

                    Intent intent = new Intent(chat_activity.this, ConnectionVideoActivity.class);
                    intent.putExtra("video", video);
                    startActivity(intent);

                }
                else
                {
                    Intent intent = new Intent(chat_activity.this, plan_activity.class);
                    startActivity(intent);
                }
            }
        });

        userName.setText(name);

    }
}