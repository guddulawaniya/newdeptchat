package com.example.deptchat.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.deptchat.R;
import com.example.deptchat.plan_activity;
import com.example.deptchat.setting_activity;


public class Profile_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_, container, false);


        LinearLayout rateus = view.findViewById(R.id.rateus);
        LinearLayout share = view.findViewById(R.id.share);
        LinearLayout settingcard = view.findViewById(R.id.settingcard);
        CardView buybutton = view.findViewById(R.id.buybutton);
        TextView userid = view.findViewById(R.id.userid);
        TextView totalcoin = view.findViewById(R.id.totalcoin);


        SharedPreferences preferences = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE);
        int useridtext = preferences.getInt("clientid", 78654568);
        int addnewcoin = preferences.getInt("coins", 0);

        userid.setText("ID : " + useridtext);


        totalcoin.setText(""+addnewcoin);

        SharedPreferences.Editor editor = getContext().getSharedPreferences("login", Context.MODE_PRIVATE).edit();

        editor.putInt("coins", Integer.parseInt(totalcoin.getText().toString()));
        editor.commit();

        buybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), plan_activity.class);
                startActivity(intent);

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Rating")
                .setMessage("Dialog Rating")
                .setPositiveButton("Rate us", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle positive button click
                    }
                });
        AlertDialog dialog = builder.create();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", "Hey check out this Awesome " + getContext().getString(R.string.app_name) + " here\n\nhttp://play.google.com/store/apps/details?id=" + getContext().getPackageName());
                intent.setType("text/plain");
                startActivity(intent);
                Toast.makeText(getContext(), "Thank You for Sharing", Toast.LENGTH_LONG).show();
            }
        });
        settingcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), setting_activity.class);
                startActivity(intent);
            }
        });

        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getContext().getPackageName()));
                intent.addFlags(1208483840);
                try {
                    getContext().startActivity(intent);
                } catch (ActivityNotFoundException unused) {
                    getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getContext().getPackageName())));
                }
                Toast.makeText(getContext(), "Thank You for your Rating", Toast.LENGTH_LONG).show();
            }
        });


        return view;

    }

}