package com.deptchat.livevideocallapp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.deptchat.livevideocallapp.Ads.bannerad;
import com.google.android.material.card.MaterialCardView;

public class Exit_dialogBox extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exit_dialog_box, container, false);

        MaterialCardView nobutton = view.findViewById(R.id.nobutton);
        MaterialCardView yesbutton = view.findViewById(R.id.exitbutton);
        MaterialCardView rateusbutton = view.findViewById(R.id.rateus);
        yesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        nobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        rateusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });


        try {
            new bannerad(getContext(), getActivity()).Native_Ad(view.findViewById(R.id.nativead), view.findViewById(R.id.my_template));
//            new bannerad(this, this).Banner_Ad(view.findViewById(R.id.bannerad));
        } catch (Exception e) {

        }

        return  view;
    }
    private void showRatingDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_rating, null);

        TextView textViewDialogTitle = view.findViewById(R.id.textViewRatingDialogTitle);
        RatingBar ratingBarDialog = view.findViewById(R.id.ratingBarDialog);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        float rating = ratingBarDialog.getRating();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        // Show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}