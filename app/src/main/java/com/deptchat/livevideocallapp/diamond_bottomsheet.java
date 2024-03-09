package com.deptchat.livevideocallapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.deptchat.livevideocallapp.Adapters.favoratemodule;
import com.deptchat.livevideocallapp.Adapters.historyshowAdapter;
import com.deptchat.livevideocallapp.sqllite.ConnectCallTB;
import com.deptchat.livevideocallapp.sqllite.chatHalper;
import com.deptchat.livevideocallapp.sqllite.favorateHalper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class diamond_bottomsheet extends BottomSheetDialogFragment {

    Cursor partydb, connectcall;
    int data;
    favorateHalper Helper;
    chatHalper Helperchat;
    ConnectCallTB connectcalld;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


             View view =   inflater.inflate(R.layout.diamond_bottomsheet_layout, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
             data = arguments.getInt("key"); // Replace "key" with the actual key used to pass data
            // Use the data as needed
        }
        Helper = new favorateHalper(getContext());
        Helperchat = new chatHalper(getContext());
        connectcalld = new ConnectCallTB(getContext());

        TextView blockbutton = view.findViewById(R.id.blockbutton);
        TextView reportbutton = view.findViewById(R.id.reportbutton);
        TextView cancelbutton = view.findViewById(R.id.cancelbutton);
        reportbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlet();
            }
        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        blockbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoratelist();
                callconnect();
                setHelperchat();
                dismiss();

            }
        });

        return view;
    }
    public void favoratelist() {
        partydb = new favorateHalper(getContext()).getdata();

        if (partydb != null && partydb.moveToNext()) {
            do {
                int id = partydb.getInt(0);
              if (id==data)
              {
                  Helper.deleteDataById(data);
              }

            } while (partydb.moveToNext());

        }


    }

    private final void showAlet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Report User");
        builder.setView(R.layout.alert_dialog);
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Toast.makeText(getContext(), "Thanks for Reporting.We will take action soon", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    public void callconnect() {
        connectcall = new ConnectCallTB(getContext()).getData();

        if (connectcall != null && connectcall.moveToNext()) {
            do {
                int id = connectcall.getInt(0);
                if (id==data)
                {
                    connectcalld.deleteDataById(data);
                }



            } while (connectcall.moveToNext());

        }
    }
    public void setHelperchat() {
        connectcall = new chatHalper(getContext()).getdata();

        if (connectcall != null && connectcall.moveToNext()) {
            do {
                int id = connectcall.getInt(0);
                if (id==data)
                {
                    connectcalld.deleteDataById(data);
                }

            } while (connectcall.moveToNext());

        }
    }
}
