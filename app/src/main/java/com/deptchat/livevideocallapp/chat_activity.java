package com.deptchat.livevideocallapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deptchat.livevideocallapp.Adapters.ChatAdapter;
import com.deptchat.livevideocallapp.Adapters.Chatdatamodule;
import com.deptchat.livevideocallapp.Adapters.MessagesModule;
import com.deptchat.livevideocallapp.Ads.ApiWebServices;
import com.deptchat.livevideocallapp.Ads.Interfb;
import com.deptchat.livevideocallapp.Ads.intersital;
import com.deptchat.livevideocallapp.sqllite.chatHalper;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class chat_activity extends AppCompatActivity {

    ImageView back_arrow;
    CircleImageView videocall;
    TextView userName, textmessage, cointext,buyfreevideobutton;
    ImageView chatmenu;
    LinearLayout locklinearbg;
    CardView sendsms;
    RecyclerView recyclerView;
    chatHalper messageHelper;
    ArrayList<MessagesModule> messagelist;
    LinearLayout chatlocklinearlayout, chatlinear;
    SharedPreferences sharedPreferences;
    private int activityOpenCount = 0;
    ChatAdapter adapter;
    String timer;
    boolean checksms = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messagelist = new ArrayList<>();

//       String age = new DecimalFormat("18").format(new Random().nextInt(36));
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String imagetext = preferences.getString("image", null);
        int permincharge = preferences.getInt("perminchage",0);
        String name = preferences.getString("name", null);
        String video = preferences.getString("video", null);
        int avaiblecoin = preferences.getInt("coins", 0);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);



//        messageHelper = new chatHalper(this);
//
//        favoratemodule model = new favoratemodule(name, imagetext, video);
//        messageHelper.insertdata(model);


        if (getIntent().hasExtra("isfromstart")) {
            new intersital(this).Show_Ads();
        } else if (sharedPreferences.getString("adtype", "1").equals("1")) {
            new Interfb(this).Show_Ads();
        } else if (sharedPreferences.getString("adtype", "1").equals("2")) {
            if (activityOpenCount % 2 != 0) {
                new Interfb(this).Show_Ads();
            }
        }

        recyclerView = findViewById(R.id.ChatRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(messagelist, chat_activity.this);
        recyclerView.setAdapter(adapter);


        cointext = findViewById(R.id.cointext);
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
        String  stoppayment = preferences.getString("upi","123@PAYTM").split("#")[0];

        if (stoppayment.equals("STOP"))
        {
            cointext.setVisibility(View.GONE);
        }
        else
        {
            cointext.setVisibility(View.VISIBLE);

        }


        cointext.setText(permincharge+"/min");

        if (avaiblecoin > 0 || stoppayment.equals("STOP") ) {
            chatlocklinearlayout.setVisibility(View.INVISIBLE);
            chatlinear.setVisibility(View.VISIBLE);
            locklinearbg.setVisibility(View.INVISIBLE);

        } else {

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
                if (s.length() > 1) {
                    sendsms.setEnabled(true);
                } else {
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

    private void fetchChatData() {
        Call<List<Chatdatamodule>> call = ApiWebServices.getApiInterface().getchatMessage();
        call.enqueue(new Callback<List<Chatdatamodule>>() {
            @Override
            public void onResponse(Call<List<Chatdatamodule>> call, Response<List<Chatdatamodule>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Chatdatamodule> chatDataList = response.body();


                    Collections.shuffle(chatDataList);
                    if (!chatDataList.isEmpty()) {

                        Random random = new Random();

                        // Generate a random index within the range of the messagelist
                        int randomIndex = random.nextInt(chatDataList.size());

                        // Retrieve the randomly selected item from the messagelist
//                        Chatdatamodule randomMessage = chatDataList.get(randomIndex);

                        Chatdatamodule chatdatamodule = chatDataList.get(randomIndex);
                        messagelist.add(new MessagesModule(chatdatamodule.getText(), 1));
                        adapter.notifyDataSetChanged();
                        checksms = false;

                        if (chatdatamodule.getImage() != null) {
                            String imageUrl = chatdatamodule.getImage();

                            messagelist.add(new MessagesModule(imageUrl, 2));
                            adapter.notifyDataSetChanged();
                        }
                    }

                } else {
                    // Handle unsuccessful response
                    int statusCode = response.code();
                    Toast.makeText(chat_activity.this, "Failed with status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Chatdatamodule>> call, Throwable t) {
                Log.e("error", t.getMessage());
                Toast.makeText(chat_activity.this, "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void sendMessage() {

        String message = textmessage.getText().toString().trim();
        if (!message.isEmpty()) {

            messagelist.add(new MessagesModule(message, 3));
            adapter.notifyDataSetChanged();
            textmessage.setText("");

            if (checksms)
            {
                fetchChatData();
            }
            else
            {
                insertdatamessage();
            }
           
        }
    }

    public void insertdatamessage() {

        String url = "https://gedgetsworld.in/PM_Kisan_Yojana/get_messsage.php";


        class registration extends AsyncTask<String, String, String> {

            @Override
            protected void onPostExecute(String s) {

                try {
                    JSONArray array = new JSONArray(s);

                    int randomIndex = new Random().nextInt(array.length());

                    JSONObject obj = array.getJSONObject(randomIndex);
                    String message = obj.getString("messages");
                    messagelist.add(new MessagesModule(message, 1));
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... param) {


                try {
                    URL url = new URL(param[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine();
                } catch (Exception ex) {
                    return ex.getMessage();
                }

            }
        }
        registration obj = new registration();
        obj.execute(url);

    }



    void recievermsg() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                messagelist.add(new MessagesModule("hello kaise ho", 1));
                adapter.notifyDataSetChanged();


            }
        }, 2000);
    }

    void imagereciever() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String imageurl = "https://images.pexels.com/photos/1133957/pexels-photo-1133957.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1";
                messagelist.add(new MessagesModule(imageurl, 2));
                adapter.notifyDataSetChanged();

            }

        }, 3000);
    }
}