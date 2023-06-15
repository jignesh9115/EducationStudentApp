package com.ai.educationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ai.educationapp.databinding.ActivitySplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private static final int DIALOG_ERROR_CONNECTION = 1;

    ActivitySplashScreenBinding binding;

    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        animation.setDuration(2000);

        binding.imgLogo.startAnimation(animation);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //=======================SET TITLE BAR CODE START======================

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        //=======================SET TITLE BAR CODE END========================

        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        if (!isOnline(SplashScreen.this)) {
            showDialog(DIALOG_ERROR_CONNECTION); // displaying the created
        } else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                  /*  Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();*/

                    if (currentUser != null) {

                        Intent intent = new Intent(SplashScreen.this, ChildListActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

                        Intent i = new Intent(SplashScreen.this, SentOTPActivity.class);
                        startActivity(i);
                        finish();

                    }

                }
            }, 5000);
        }
    }


    public boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        // check ni otherwise if connection not there it will thrown an exception..

        if (ni != null && ni.isConnected())
            return true;
        else
            return false;
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DIALOG_ERROR_CONNECTION:
                Log.d("inside net error", " " + DIALOG_ERROR_CONNECTION);
                return new AlertDialog.Builder(this, R.style.AlertDialogCustom)
                        .setIcon(R.drawable.warning)
                        .setCancelable(false)
                        .setTitle("Error")
                        .setMessage("No internet connection.")
                        .setNegativeButton("ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                })

                        .setOnKeyListener(new Dialog.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface arg0, int keyCode,
                                                 KeyEvent event) {
                                // TODO Auto-generated method stub
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    finish();
                                }
                                return true;
                            }
                        }).create();

            default:
                return null;
        }


    }
}