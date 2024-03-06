package com.deptchat.livevideocallapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.deptchat.livevideocallapp.Ads.BannerAds;
import com.google.common.util.concurrent.ListenableFuture;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class ConnectionVideoActivity extends BaseActi {

    public ImageView Phone;
    public ImageView cameraSwitch;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    private boolean isMuted = false;

    public LinearLayout mFrameLayout;

    RelativeLayout notshow;

    public TextView text;
    public ToggleButton volume;
    SharedPreferences sharedPreferences;
    private int REQUEST_CODE_PERMISSIONS = 1001;

    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};

    //  Activity activity;
    private int currentLensFacing = CameraSelector.LENS_FACING_FRONT; // Initial value is front camera
    private CameraSelector cameraSelector;

    private boolean isCameraBound = false;
    TextView username, location;
    ImageView profile_image;
    LottieAnimationView reject;

    private Handler handler;
    private Runnable updateProgressTask;


    RingtonePlayer ringtonePlayer;
    int watchTimeInSeconds;
    int duration;
    boolean checkmic;
    String videourl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_video);
        BannerAds bannerAds = new BannerAds(this);
//        bannerAds.interstitialads(getActivity());

        ringtonePlayer = new RingtonePlayer();

        ringtonePlayer.playRingtone(this, R.raw.ringing);

        previewView = findViewById(R.id.previewView);
        username = findViewById(R.id.nametxt);
        location = findViewById(R.id.location);
        profile_image = findViewById(R.id.profile_image);
        ImageView backgroundimage = findViewById(R.id.backgroundimage);
        reject = findViewById(R.id.reject);
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringtonePlayer.stopRingtone();
                onBackPressed();
            }
        });
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);



        volumeSpeaker();
        if (allPermissionsGranted()) {
            startCamera(); //start camera if permission has been granted by user
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        notshow = (RelativeLayout) findViewById(R.id.notshow);

        // FrameLayout bannerframlayout=findViewById(R.id.bannerad);

        username.setText(sharedPreferences.getString("name", null));
        String imageurl = sharedPreferences.getString("image", null);
         videourl = sharedPreferences.getString("video", null);
        location.setText(sharedPreferences.getString("location", null));
        Picasso.get().load(imageurl).into(profile_image);
        Picasso.get().load(imageurl).into(backgroundimage);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            public void run() {
                notshow.setVisibility(View.GONE);
                BaseActi.enableVideo = true;
                ringtonePlayer.stopRingtone();
                try {
                    netConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                initView();
            }
        }, 6000);


    }


    public final void initView() {


        mVideoView = (VideoView) findViewById(R.id.video_vw);


        mFrameLayout = (LinearLayout) findViewById(R.id.frameLayout);
        new Handler().postDelayed(new Runnable() {

            public void run() {
                if (mFrameLayout.getVisibility() == View.VISIBLE) {
                    mFrameLayout.setVisibility(View.GONE);
                    mVideoView.setVisibility(View.VISIBLE);
                    playVideo();
                    return;
                }
                mFrameLayout.setVisibility(View.VISIBLE);
                mVideoView.setVisibility(View.GONE);
                if (mVideoView.isPlaying()) {
                    mVideoView.start();
                }
            }
        }, 2000);
        Phone = findViewById(R.id.phone_iv);
        volume = findViewById(R.id.volume_iv);
        cameraSwitch = findViewById(R.id.switch_camera_iv);
        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopwatching();
            }
        });

        volume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                checkmic = isChecked;
                volumeSpeaker();
                if (isChecked) {
                    volume.setBackgroundDrawable(getResources().getDrawable(R.drawable.speaker_off));
                } else {
                    volume.setBackgroundDrawable(getResources().getDrawable(R.drawable.speaker_on));

                }


            }
        });
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        cameraSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCamera();
            }
        });

    }

    public final void playVideo() {

        Uri videoUri = Uri.parse(videourl);

        getWindow().setFormat(-3);
        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        mVideoView.setVideoURI(videoUri);
        mVideoView.requestFocus();
        mVideoView.start();


        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {


//                    if (!billing.equals("STOP") && !ispaymentdone) {
//                        paymentCountDownTimer = new CountDownTimer(Long.parseLong(videotimer), 1000) {
//                            public void onTick(long millisUntilFinished) {
//
//                            }
//
//                            public void onFinish() {
//                                // This method will be called when the countdown is finished
//                                Intent intent = new Intent(ConnectionVideoActivity.this, plan_activity.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                        }.start();
//                    }
                }
                return false;
            }
        });


        handler = new Handler();
        updateProgressTask = new Runnable() {
            @Override
            public void run() {
                updateWatchTime();


                handler.postDelayed(this, 1000); // Update every 1 second
            }
        };

        // Start updating watch time
        handler.post(updateProgressTask);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                duration = (mVideoView.getDuration()) / 1000;
//                Toast.makeText(ConnectionVideoActivity.this, "Duration: " + duration + " milliseconds", Toast.LENGTH_SHORT).show();
            }
        });


        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopwatching();

            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // Handle the error here without displaying the default error dialog
                Toast.makeText(ConnectionVideoActivity.this, "Error to Connecting her please try again", Toast.LENGTH_SHORT).show();
//                if (paymentCountDownTimer != null) {
//                    paymentCountDownTimer.cancel();
//                }

                onBackPressed();

                return true; // Return true to indicate that the error is handled

            }
        });
    }

    void stopwatching() {
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        int permincoint = preferences.getInt("perminchage", 20);
        int availablecoin = preferences.getInt("coins", 0);

        int remainingcoins = availablecoin - watchTimeInSeconds * (permincoint / 60);

//        Toast.makeText(ConnectionVideoActivity.this, "Duration : "+remainingcoins+"\n"+"left coin : "+remainingcoins, Toast.LENGTH_SHORT).show();

        SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
        editor.putInt("coins", remainingcoins);
        editor.commit();
        onBackPressed();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {

            mVideoView.stopPlayback();

        }
    }

    private void updateWatchTime() {
        int currentPosition = mVideoView.getCurrentPosition();
        watchTimeInSeconds = currentPosition / 1000; // Convert to seconds

//        Toast.makeText(this, ""+watchTimeInSeconds, Toast.LENGTH_SHORT).show();
    }


    private void toggleMute() {
        MediaPlayer mediaPlayer = ((MediaPlayer) mVideoView.getTag());

        if (mediaPlayer != null) {
            isMuted = !isMuted;
            mediaPlayer.setVolume(isMuted ? 0f : 1f, isMuted ? 0f : 1f);
        }
    }

    private void volumeSpeaker() {
        Object systemService = getSystemService(Context.AUDIO_SERVICE);
        if (systemService != null) {
            AudioManager audioManager = (AudioManager) systemService;
            if (checkmic) {
                audioManager.setStreamVolume(3, 0, 0);
            } else {
                audioManager.setStreamVolume(3, 100, 0);
            }
            audioManager.setSpeakerphoneOn(checkmic);
        }
    }


    private void toggleCamera() {
        // Unbind the current camera before switching
        if (isCameraBound) {
            unbindCamera();
        }

        // Switch to the other camera
        currentLensFacing = (currentLensFacing == CameraSelector.LENS_FACING_FRONT)
                ? CameraSelector.LENS_FACING_BACK
                : CameraSelector.LENS_FACING_FRONT;

        // Restart the camera preview
        startCamera();
    }

    private void unbindCamera() {
        if (cameraProviderFuture != null) {
            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    runOnUiThread(() -> {
                        cameraProvider.unbindAll();
                        isCameraBound = false;
                    });
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }, ContextCompat.getMainExecutor(this));
        }
    }

    private void startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(currentLensFacing)
                        .build();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();

        Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview);

        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        isCameraBound = true;


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
                startCamera();
            } else {
//                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
//                this.finish();
            }
        }
    }


    @Override
    public void onBackPressed() {
        ringtonePlayer.stopRingtone();
        super.onBackPressed();
    }


}