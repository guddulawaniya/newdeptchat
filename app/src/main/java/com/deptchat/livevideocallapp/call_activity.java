package com.deptchat.livevideocallapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.view.PreviewView;


public class call_activity extends AppCompatActivity {

    PreviewView previewView;
    private VideoView videoView;
    private CameraViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        videoView = findViewById(R.id.fullscreenVideoView);
        previewView = findViewById(R.id.cameraView);
        viewModel = new CameraViewModel();

        Uri videoUri = getIntent().getData();

//        viewModel = new ViewModelProvider(this).get(CameraViewModel.class);
//        viewModel.bindCameraUseCases(cameraView);
//        if (videoUri != null) {
//            shortvideoview.setVideoURI(videoUri);
//            shortvideoview.start();
//        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}

