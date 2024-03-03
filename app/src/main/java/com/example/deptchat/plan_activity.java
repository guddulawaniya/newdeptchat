package com.example.deptchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.deptchat.Ads.BannerAds;
import com.example.deptchat.Ads.bannerad;
import com.google.android.gms.ads.AdView;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;

public class plan_activity extends AppCompatActivity {

    boolean paymethod = true;

    int cardcolordynamic = Color.parseColor("#9E00B7");
    int background_dynamicColor = Color.parseColor("#33FFDA");

    String UPIid = "Q378642318@ybl";
    TextView coin1, coin2, coin3, coin4, coin5, coin6;
    int cardcoin;
    int getamount1 = 100;
    int getamount2=200;
    int getamount3=600;
    int getamount4=1500;
    int getamount5=2500;
    int getamount6=5000;
    int getcoin1 = 9600;
    int getcoin2=20000;
    int getcoin3=5000;
    int getcoin4=150000;
    int getcoin5=250000;
    int getcoin6=500000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);


        try {
            new bannerad(this,this).Native_Ad(findViewById(R.id.nativead),findViewById(R.id.my_template));
            new bannerad(this,this).Banner_Ad(findViewById(R.id.bannerad));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        ImageView closebutton = findViewById(R.id.closebutton);
        CardView card1 = findViewById(R.id.card1);
        CardView card2 = findViewById(R.id.card2);
        CardView card3 = findViewById(R.id.card3);
        CardView card4 = findViewById(R.id.card4);
        CardView card5 = findViewById(R.id.card5);
        CardView card6 = findViewById(R.id.card6);

        coin1 = findViewById(R.id.coins1);
        coin2 = findViewById(R.id.coins2);
        coin3 = findViewById(R.id.coins3);
        coin4 = findViewById(R.id.coins4);
        coin5 = findViewById(R.id.coins5);
        coin6 = findViewById(R.id.coins6);

        TextView amount1 = findViewById(R.id.card1amount);
        TextView amount2 = findViewById(R.id.card2amount);
        TextView amount3 = findViewById(R.id.card3amount);
        TextView amount4 = findViewById(R.id.card4amount);
        TextView amount5 = findViewById(R.id.card5amount);
        TextView amount6 = findViewById(R.id.card6amount);

        amount1.setText("₹" + getamount1);
        amount2.setText("₹" + getamount2);
        amount3.setText("₹" + getamount3);
        amount4.setText("₹" + getamount4);
        amount5.setText("₹" + getamount5);
        amount6.setText("₹" + getamount6);

        coin1.setText("₹" + getcoin1);
        coin2.setText("₹" + getcoin2);
        coin3.setText("₹" + getcoin3);
        coin4.setText("₹" + getcoin4);
        coin5.setText("₹" + getcoin5);
        coin6.setText("₹" + getcoin6);
        ConstraintLayout constraintlayout = findViewById(R.id.constraintlayout);

        // Example color (orange)
        card1.setCardBackgroundColor(cardcolordynamic);
        card2.setCardBackgroundColor(cardcolordynamic);
        card3.setCardBackgroundColor(cardcolordynamic);
        card4.setCardBackgroundColor(cardcolordynamic);
        card5.setCardBackgroundColor(cardcolordynamic);
        card6.setCardBackgroundColor(cardcolordynamic);
        constraintlayout.setBackgroundColor(background_dynamicColor);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putInt("coins", 1000);
                editor.commit();
                nextactivity(getamount1, getcoin1);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextactivity(getamount2, getcoin2);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextactivity(getamount3, getcoin3);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextactivity(getamount4, getcoin4);
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextactivity(getamount5, getcoin5);
            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextactivity(getamount6, getcoin6);
            }
        });




        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    void nextactivity(int amount, int coin) {
        if (paymethod) {
            Intent intent = new Intent(plan_activity.this, payments_activity.class);
            intent.putExtra("amount", amount);
            intent.putExtra("cardid", coin);
            intent.putExtra("upiid",UPIid);
            startActivity(intent);

        } else {
            cardcoin = coin;
            payment(amount);
        }


    }

    void payment(int amount) {
        Intent upiIntent = new Intent(Intent.ACTION_VIEW);
        String uriString = "upi://pay?pa=" + UPIid + "&pn=PhonePeMerchant&am=" + amount + "&mc=0000&mode=02&purpose=00";

        // Intent upiIntent = new Intent(Intent.ACTION_VIEW);String uriString = "upi://pay?pa=Vyapar.170266868298@hdfcbank&pn=Default&tr=STQU170266868298&mc=8999&am=1&tn=payment&cu=INR";

        upiIntent.setData(Uri.parse(uriString));
        Intent chooser = Intent.createChooser(upiIntent, "Pay with...");
//        upiIntent.setPackage(phonePePackageName);

        startActivityForResult(chooser, 100, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == RESULT_OK || resultCode == 11) {
                // 11 is often used for success
                // Payment successful

                SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);

                int avaiablecoin = preferences.getInt("coins",0);

                avaiablecoin = avaiablecoin+cardcoin;

                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putInt("coins", avaiablecoin);
                editor.commit();


            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Payment canceled by user", Toast.LENGTH_SHORT).show();
                // Payment canceled by user
            } else {
                Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
                // Payment failed
            }
        }
    }





















    private void startUpiPayment(String amount) {

//    String upi =    "upi://pay?pa=guddulawaniya123-2@okhdfcbank";
//            "&pn=guddu kumar&am=1.00&aid=uGICAgIDDxu2qBA";

        String tr = "TR" + String.valueOf(System.currentTimeMillis());
        String ref = "Ref" + String.valueOf(System.currentTimeMillis());
        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                .setPayeeVpa("guddulawaniya123-2@okhdfcbank")
                .setPayeeName("guddu kumar")
                .setPayeeMerchantCode("")
                .setTransactionId(tr)
                .setTransactionRefId(ref)
                .setDescription("Subscribe for Live Videocall")
                .setAmount("1.00");

        try {
            EasyUpiPayment easyUpiPayment = builder.build();


            easyUpiPayment.startPayment();
            easyUpiPayment.setPaymentStatusListener(new PaymentStatusListener() {
                @Override
                public void onTransactionCompleted(@NonNull TransactionDetails transactionDetails) {
//                    Toast.makeText(getApplicationContext(),"Your Payment has been Successfull",Toast.LENGTH_LONG).show();
                    complatetepayment(transactionDetails);
                }

                @Override
                public void onTransactionCancelled() {
                    Toast.makeText(getApplicationContext(), "Your Payment has been Cancelled", Toast.LENGTH_LONG).show();
                }
            });

        } catch (AppNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    void complatetepayment(TransactionDetails transactionDetails) {
        Log.d("hellovk", String.valueOf(transactionDetails));

        if (transactionDetails.getTransactionStatus().toString().equals("SUCCESS")) {
            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("ispaymentdone", true);
            myEdit.commit();
            Toast.makeText(getApplicationContext(), "Your Payment has been Successfull", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(plan_activity.this, ConnectionVideoActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Your Payment has been Failed", Toast.LENGTH_LONG).show();

        }
    }
}