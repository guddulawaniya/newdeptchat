package com.deptchat.livevideocallapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class payments_activity extends AppCompatActivity {
    int selectedRadioButtonId;
    int cardcoin;
    String UPIid;
    String phonepay_package = "com.phonepe.app";
    String paytm_package = "net.one97.paytm";
    String google_package = "com.google.android.apps.nbu.paisa.user";
    String bhimupi_package = "in.org.npci.upiapp";

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        ImageView backarrow = findViewById(R.id.backarrow);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        TextView amountext1 = findViewById(R.id.amount1);
        TextView amountext2 = findViewById(R.id.amount2);
        Button paybutton = findViewById(R.id.paybutton);
        RadioGroup radiogroup = findViewById(R.id.radiogroup);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        String amount =  sharedPreferences.getString("prices","").split("#")[id];
        String coinstring =  sharedPreferences.getString("prices","").split("#")[id-6];
        cardcoin = Integer.parseInt(coinstring);

        amountext1.setText("₹" + amount+".00");
        amountext2.setText("₹" + amount+".00");
        UPIid = intent.getStringExtra("upiid");
        paybutton.setText("Pay ₹" + amount+".00");
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                // checkedId will contain the id of the selected RadioButton

                View radioButton = group.findViewById(i);
                int index = group.indexOfChild(radioButton);
                selectedRadioButtonId = index+1;

            }
        });

        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRadioButtonId != 0) {
                    switch (selectedRadioButtonId) {
                        case 1:
                            if (isPhonePeInstalled(phonepay_package)) {

                                payment(amount, phonepay_package);
                            } else {
                                Toast.makeText(payments_activity.this, "Phone Pay not Installed", Toast.LENGTH_SHORT).show();
//                                redirectToPlayStore(phonepay_package);
                            }
                            break;
                        case 2:
                            if (isPhonePeInstalled(google_package)) {
                                payment(amount, google_package);
                            }
                            else {
                                Toast.makeText(payments_activity.this, "Google pay Pay not Installed", Toast.LENGTH_SHORT).show();
//                                redirectToPlayStore(google_package);
                            }
                            break;
                        case 3:
                            if (isPhonePeInstalled(paytm_package)) {

                                payment(amount, paytm_package);
                            } else {
                                Toast.makeText(payments_activity.this, "Paytm not Installed", Toast.LENGTH_SHORT).show();
//                                redirectToPlayStore(paytm_package);
                            }
                            break;
                        case 4:
                            if (isPhonePeInstalled(bhimupi_package)) {
                                payment(amount, bhimupi_package);
                            } else {
                                Toast.makeText(payments_activity.this, "BHIM UPI not Installed", Toast.LENGTH_SHORT).show();

//                                redirectToPlayStore(bhimupi_package);
                            }
                            break;
                        case 5:
                            otherpayment(amount);
                            break;
                    }
                } else {
                    Toast.makeText(payments_activity.this, "Select Pay Method", Toast.LENGTH_SHORT).show();
                }

            }
        });


        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    void payment(String amount, String phonePePackageName) {
        Intent upiIntent = new Intent(Intent.ACTION_VIEW);
        String uriString = "upi://pay?pa=" + UPIid + "&pn=PhonePeMerchant&am=" + amount + "&mc=0000&mode=02&purpose=00";

        // Intent upiIntent = new Intent(Intent.ACTION_VIEW);String uriString = "upi://pay?pa=Vyapar.170266868298@hdfcbank&pn=Default&tr=STQU170266868298&mc=8999&am=1&tn=payment&cu=INR";

        upiIntent.setData(Uri.parse(uriString));
//        Intent chooser = Intent.createChooser(upiIntent, "Pay with...");
        upiIntent.setPackage(phonePePackageName);

//        startActivityForResult(chooser, 100, null);
        startActivity(upiIntent);
    }

    void otherpayment(String amount) {
        Intent upiIntent = new Intent(Intent.ACTION_VIEW);
        String uriString = "upi://pay?pa=" + UPIid + "&pn=PhonePeMerchant&am=" + amount + "&mc=0000&mode=02&purpose=00";

        // Intent upiIntent = new Intent(Intent.ACTION_VIEW);String uriString = "upi://pay?pa=Vyapar.170266868298@hdfcbank&pn=Default&tr=STQU170266868298&mc=8999&am=1&tn=payment&cu=INR";

        upiIntent.setData(Uri.parse(uriString));
        Intent chooser = Intent.createChooser(upiIntent, "Pay with...");

        startActivityForResult(chooser, 100, null);
    }


    private void redirectToPlayStore(String appPackageName) {
        try {

            // Create the Google Play Store URL
            Uri playStoreUri = Uri.parse("market://details?id=" + appPackageName);

            // Create an Intent to open the Play Store
            Intent intent = new Intent(Intent.ACTION_VIEW, playStoreUri);

            // Set flags to ensure the Play Store is opened outside the app
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // Start the intent
            startActivity(intent);
        } catch (Exception e) {
            // Handle exceptions, for example, if the Play Store app is not available on the device
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == RESULT_OK || resultCode == 11) {  // 11 is often used for success


                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                int available = preferences.getInt("coins", 0);
                available = available + cardcoin;

                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putInt("coins", available);
                editor.commit();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(payments_activity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }
                },1000);

//

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Payment canceled by user", Toast.LENGTH_SHORT).show();
                // Payment canceled by user
            } else {
                Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
                // Payment failed
            }
        }
    }


    private boolean isPhonePeInstalled(String packagename) {
        PackageManager packageManager = getPackageManager();
        try {
            packageManager.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


}