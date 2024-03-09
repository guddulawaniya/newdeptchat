package com.deptchat.livevideocallapp.login_files;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.deptchat.livevideocallapp.Ads.bannerad;
import com.deptchat.livevideocallapp.MainActivity;
import com.deptchat.livevideocallapp.R;
import com.deptchat.livevideocallapp.privacyplocy;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class start_activity extends AppCompatActivity implements PurchasesUpdatedListener {


    private BillingClient billingClient;
    private SkuDetails skuDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        String permincoin = sharedPreferences.getString("prices", "10").split("#")[18];

        int coin = Integer.parseInt(permincoin);
        SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
        editor.putInt("perminchage", coin);
        editor.putInt("coins", 1000);
        editor.commit();
        billingClient = BillingClient.newBuilder(this)
                .setListener(this)
                .enablePendingPurchases()
                .build();

        // Connect to Google Play
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {

            }

            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                    querySkuDetails();
                    // Billing client is ready
                }
            }
        });


        try {
            new bannerad(this, this).Native_Ad(findViewById(R.id.nativead), findViewById(R.id.my_template));
            new bannerad(this, this).Banner_Ad(findViewById(R.id.bannerad));
        } catch (Exception e) {

        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        67798);
            }
        }


        TextView nextbutton = findViewById(R.id.nextbutton);
        TextView termcondition = findViewById(R.id.termcondition);
        TextView privacytextview = findViewById(R.id.privacy);

        termcondition.setPaintFlags(termcondition.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        privacytextview.setPaintFlags(privacytextview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        termcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start_activity.this, privacyplocy.class);
                intent.putExtra("url", "https://appkiprivacypolicy.blogspot.com/2024/03/deptchat-terms-and-condtions.html");
                startActivity(intent);
            }
        });
        privacytextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start_activity.this, privacyplocy.class);
                intent.putExtra("url", "https://appkiprivacypolicy.blogspot.com/2024/03/privacy-policy.html");
                startActivity(intent);
            }
        });


        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid = new DecimalFormat("000000").format(new Random().nextInt(999999));

                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putInt("clientid", Integer.parseInt(userid));
                editor.commit();

                startActivity(new Intent(start_activity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void querySkuDetails() {
        SkuDetailsParams params = SkuDetailsParams.newBuilder()
                .setSkusList(Arrays.asList(String.valueOf(R.string.license)))
                .setType(BillingClient.SkuType.INAPP)  // or BillingClient.SkuType.SUBS for subscriptions
                .build();

        billingClient.querySkuDetailsAsync(params, (billingResult, skuDetailsList) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                // Handle sku details list, for simplicity, we assume only one product is queried
                skuDetails = skuDetailsList.get(0);
            }
        });
    }
    private void initiatePurchase() {
        if (skuDetails != null) {
            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                    .setSkuDetails(skuDetails)
                    .build();

            int responseCode = billingClient.launchBillingFlow(this, billingFlowParams).getResponseCode();
        } else {
            // Handle the case where skuDetails is not available
        }
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            // Handle successful purchase
            for (Purchase purchase : purchases) {
                // Process the purchase, e.g., acknowledge the purchase
                acknowledgePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle user cancelation
            Log.d("Billing", "User canceled the purchase");
        } else {
            // Handle other errors
            Log.e("Billing", "Error code: " + billingResult.getResponseCode());
        }
    }

    private void acknowledgePurchase(Purchase purchase) {
        if (!purchase.isAcknowledged()) {
            AcknowledgePurchaseParams params = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.getPurchaseToken())
                    .build();

            billingClient.acknowledgePurchase(params, billingResult -> {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Purchase acknowledged
                } else {
                    // Handle acknowledgment error
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Disconnect BillingClient when the activity is destroyed
        if (billingClient != null && billingClient.isReady()) {
            billingClient.endConnection();
        }
    }

}