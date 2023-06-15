package com.ai.educationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ai.educationapp.databinding.ActivitySentOTPBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SentOTPActivity extends AppCompatActivity {

    ActivitySentOTPBinding binding;

    String[] permissionsRequired = new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivitySentOTPBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (!AskPermissions(SentOTPActivity.this, permissionsRequired)) {
            ActivityCompat.requestPermissions(SentOTPActivity.this, permissionsRequired, 1);
        }


        binding.btnSentOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (binding.edtMobileNumber.getText().toString().isEmpty()) {

                        Toast.makeText(SentOTPActivity.this, "enter mobile number", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.btnSentOtp.setVisibility(View.INVISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + binding.edtMobileNumber.getText().toString().trim(),
                            60,
                            TimeUnit.SECONDS,
                            SentOTPActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    binding.progressBar.setVisibility(View.GONE);
                                    binding.btnSentOtp.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    binding.progressBar.setVisibility(View.GONE);
                                    binding.btnSentOtp.setVisibility(View.VISIBLE);
                                    Toast.makeText(SentOTPActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    binding.progressBar.setVisibility(View.GONE);
                                    binding.btnSentOtp.setVisibility(View.VISIBLE);

                                    Intent intent = new Intent(SentOTPActivity.this, VerifyOTPActivity.class);
                                    intent.putExtra("mobile_no", binding.edtMobileNumber.getText().toString().trim());
                                    intent.putExtra("verificationId", verificationId);
                                    startActivity(intent);

                                }
                            }
                    );
                }

        });


    }

    public static boolean AskPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}