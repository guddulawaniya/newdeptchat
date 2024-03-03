package com.example.deptchat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class BaseActi extends AppCompatActivity {
    public static boolean enableVideo = false;
    public ArrayList<String> firebaseArrayList = new ArrayList<>();
    public SharedPreferences mPrefs;
    public VideoView mVideoView;
    public int nameURL;
    public Uri pVideo;
    public boolean showAdss = false;
    public int totalUrl;
    public String[] url = {"vid_1", "vid_2", "vid_3", "vid_4", "vid_5", "vid_6", "vid_7", "vid_8", "vid_9", "vid_10", "vid_11", "vid_1", "vid_2", "vid_3", "vid_4", "vid_5", "vid_6", "vid_7", "vid_8", "vid_9", "vid_10", "vid_11"};

    @Override
    // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.e("sdf", "");
    }

    private void RealDataBase(int i) throws IOException {
        this.firebaseArrayList.clear();
        this.mPrefs = getPreferences(0);
        this.firebaseArrayList.addAll(Arrays.asList(this.url));
        setVideo(i);
    }

    private void reteriveData() throws IOException {

        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.mPrefs = defaultSharedPreferences;
        if (defaultSharedPreferences != null) {
            this.nameURL = defaultSharedPreferences.getInt("URL", 0);
            Log.d("TAG", " oncreate reteriveData : " + this.nameURL);
            int i = this.nameURL;
            if (i > 0) {
                Log.e("TAG", " oncreate reteriveData: greater 0");
                RealDataBase(this.nameURL);
                return;
            }
            RealDataBase(i);
            Log.e("TAG", " oncreate reteriveData: lesss ");
            return;
        }
        Log.d("TAG", "oncreate no sharedpref: ");
    }



    private void setVideo(int i) throws IOException {
        int identifier = getResources().getIdentifier(this.firebaseArrayList.get(i), "raw", getPackageName());
        pVideo = Uri.parse("android.resource://" + getPackageName() + "/" + identifier);
        totalUrl = i + 1;
        PrintStream printStream = System.out;
        printStream.println("---------------------this.totalUrl" + this.totalUrl);
        saveArrayList(this.totalUrl);
    }

    private void saveArrayList(int i) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
        if (this.firebaseArrayList.size() <= i) {
            edit.putInt("URL", 0);
        } else {
            edit.putInt("URL", i);
        }
        Log.d("TAG", "oncreate  array SAVE: " + i);
        edit.apply();
    }




    public Boolean netConnection() throws IOException {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Internet Connection Failed");
            builder.setMessage("Please Check Your Internet connection ");
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                /* class com.developerrajnagor.okvpn.Videocallandchat.BaseActi.AnonymousClass1 */

                public final void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        BaseActi.this.netConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                /* class com.developerrajnagor.okvpn.Videocallandchat.BaseActi.AnonymousClass2 */

                public final void onClick(DialogInterface dialogInterface, int i) {
                    BaseActi.this.finishAffinity();
                }
            });
            builder.show();
        } else {
            reteriveData();
        }
        return false;
    }
}
