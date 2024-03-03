package com.example.deptchat.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deptchat.Adapters.favoratemodule;
import com.example.deptchat.Adapters.historyshowAdapter;
import com.example.deptchat.Ads.bannerad;
import com.example.deptchat.R;
import com.example.deptchat.show_history_record;
import com.example.deptchat.sqllite.MessageHelper;

import java.util.ArrayList;

public class message_fragment extends Fragment {

    ArrayList<favoratemodule> chatroom;
    LinearLayout nodatafound;
    private RecyclerView recView;
    String img, video, name, city;
    Cursor partydb, favoratehelper;
    TextView favnumber;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message_fragment, container, false);
        CardView call_history = view.findViewById(R.id.call_history);
        CardView likecard = view.findViewById(R.id.likecard);
        nodatafound = view.findViewById(R.id.nodatafound);
        favnumber = view.findViewById(R.id.favnumber);
        recView = view.findViewById(R.id.chatrecyclerview);
        chatroom = new ArrayList<>();


        try {
//            new bannerad(getContext(),getActivity()).Native_Ad(view.findViewById(R.id.nativead),findViewById(R.id.my_template));
            new bannerad(getContext(),getActivity()).Banner_Ad(view.findViewById(R.id.bannerad));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        call_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), show_history_record.class);
                intent.putExtra("title", "Call history");
                intent.putExtra("id", 2);
                startActivity(intent);
            }
        });

        SharedPreferences preferences = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE);

        boolean checksms = preferences.getBoolean("checksms", false);

        if (checksms) {
//            favoratelist();
            nodatafound.setVisibility(View.GONE);
        } else {
            nodatafound.setVisibility(View.VISIBLE);

        }


        likecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), show_history_record.class);
                intent.putExtra("title", "I like");
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });


        historyshowAdapter history = new historyshowAdapter(chatroom, getContext());
        recView.setAdapter(history);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }


    public void favoratelist() {

        partydb = new MessageHelper(getContext()).getData();

        if (partydb != null && partydb.moveToNext()) {
            do {
                String name = partydb.getString(1);
                String image = partydb.getString(2);
                String video = partydb.getString(3);
                chatroom.add(new favoratemodule(name, image, video));

            } while (partydb.moveToNext());

        }
    }

}

