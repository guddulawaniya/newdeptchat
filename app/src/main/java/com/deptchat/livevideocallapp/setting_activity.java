package com.deptchat.livevideocallapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.deptchat.livevideocallapp.Ads.bannerad;
import com.deptchat.livevideocallapp.Ads.intersital;
import com.deptchat.livevideocallapp.login_files.start_activity;

public class setting_activity extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ImageView backarrow = findViewById(R.id.backarrow);

        Switch micswitch = findViewById(R.id.microphone);
        Switch cameraswitch = findViewById(R.id.cameraswitch);

        TextView logout = findViewById(R.id.logout);
        TextView delete = findViewById(R.id.delete);
        TextView termscondtion  = findViewById(R.id.termcondition);

        try {
            new bannerad(this,this).Native_Ad(findViewById(R.id.nativead),findViewById(R.id.my_template));
            new bannerad(this,this).Banner_Ad(findViewById(R.id.bannerad));
        } catch (Exception e) {

        }

        new intersital(this).Show_Ads();

        micswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    micswitch.setChecked(false);
                } else {
                    micswitch.setChecked(true);
                }
                // Handle switch state change
            }
        });

        micswitch.setOnCheckedChangeListener(((buttonView, isChecked) -> {

            if (isChecked)
            {

                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putBoolean("micenable", true);
                editor.commit();
                Toast.makeText(this, "Mic Enable", Toast.LENGTH_SHORT).show();
            }
            else
            {
                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putBoolean("micenable", true);
                editor.commit();
                Toast.makeText(this, "Mic Disable", Toast.LENGTH_SHORT).show();
            }

        }));



        cameraswitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle switch state changes
            if (isChecked) {

                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putBoolean("camerapreview", true);
                editor.commit();
                Toast.makeText(this, "Camera preview Enable", Toast.LENGTH_SHORT).show();
                // Switch is ON
                // Perform actions when the switch is turned on
            } else {
                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putBoolean("camerapreview", false);
                editor.commit();
                Toast.makeText(this, "Camera preview Disable", Toast.LENGTH_SHORT).show();

                // Switch is OFF
                // Perform actions when the switch is turned off
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putInt("clientid", 0);
                editor.commit();

                Intent intent = new Intent(setting_activity.this, start_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        termscondtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting_activity.this, privacyplocy.class);
                intent.putExtra("url","https://appkiprivacypolicy.blogspot.com/2024/03/deptchat-terms-and-condtions.html");
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putInt("clientid", 0);
                editor.commit();

                Intent intent = new Intent(setting_activity.this, start_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });



        cameraswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (allPermissionsGranted()) {

                } else {
                    ActivityCompat.requestPermissions(setting_activity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                }
                // Handle switch state change
            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean allPermissionsGranted() {

        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                Toast.makeText(setting_activity.this, "Camera Enable", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera Disable", Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
//                this.finish();
            }
        }
    }
}