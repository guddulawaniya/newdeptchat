package com.deptchat.livevideocallapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.deptchat.livevideocallapp.Adapters.ChatAdapter;
import com.deptchat.livevideocallapp.Adapters.MessagesModule;
import com.deptchat.livevideocallapp.Adapters.favoratemodule;
import com.deptchat.livevideocallapp.Ads.Interfb;
import com.deptchat.livevideocallapp.Ads.intersital;
import com.deptchat.livevideocallapp.sqllite.chatHalper;

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
    chatHalper messageHelper;
    ArrayList<MessagesModule> messagelist;
    LinearLayout chatlocklinearlayout,chatlinear;
    SharedPreferences sharedPreferences;
    private int activityOpenCount = 0;
    ChatAdapter adapter;
    String timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messagelist = new ArrayList<>();

//       String age = new DecimalFormat("18").format(new Random().nextInt(36));
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String imagetext = preferences.getString("image",null);
        String name = preferences.getString("name",null);
        String video = preferences.getString("video",null);
        int avaiblecoin = preferences.getInt("coins",0);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);



//        messageHelper = new chatHalper(this);
//
//        favoratemodule model = new favoratemodule(name, imagetext, video);
//        messageHelper.insertdata(model);


        if (getIntent().hasExtra("isfromstart")){
            new intersital(this).Show_Ads();
        }
        else if(sharedPreferences.getString("adtype", "1").equals("1")){
            new Interfb(this).Show_Ads();
        }else if(sharedPreferences.getString("adtype", "1").equals("2")){
            if (activityOpenCount % 2 != 0) {
                new Interfb(this).Show_Ads();
            }
        }

        recyclerView = findViewById(R.id.ChatRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ChatAdapter(messagelist,this,3);
        recyclerView.setAdapter(adapter);



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




        sendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();

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
        });

        userName.setText(name);

    }

    private void sendMessage() {
        String message = textmessage.getText().toString().trim();
        if (!message.isEmpty()) {

            messagelist.add(new MessagesModule(message));
            adapter.notifyDataSetChanged();
            textmessage.setText("");
//            recievermsg();
//            imagereciever();
        }
    }


    void recievermsg()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                messagelist.add(new MessagesModule("hello kaise ho"));
                adapter.notifyDataSetChanged();

            }
        },2000);
    }
    void imagereciever()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                messagelist.add(new MessagesModule("hello kaise ho"));
                adapter.notifyDataSetChanged();

            }

        },3000);
    }
}