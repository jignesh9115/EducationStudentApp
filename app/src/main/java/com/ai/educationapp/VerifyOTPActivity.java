package com.ai.educationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.ai.educationapp.databinding.ActivityVerifyOTPBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {

    ActivityVerifyOTPBinding binding;

    private String verificationId = "";

    String mobile_number = "";

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVerifyOTPBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.tvMobileNumber.setText(String.format(
                "+91-%s", getIntent().getStringExtra("mobile_no")));

        setupOTPInputs();

        mobile_number = getIntent().getStringExtra("mobile_no");

        verificationId = getIntent().getStringExtra("verificationId");

        binding.btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.edtInputCode1.getText().toString().isEmpty()
                        || binding.edtInputCode2.getText().toString().isEmpty()
                        || binding.edtInputCode3.getText().toString().isEmpty()
                        || binding.edtInputCode4.getText().toString().isEmpty()
                        || binding.edtInputCode5.getText().toString().isEmpty()
                        || binding.edtInputCode6.getText().toString().isEmpty()) {

                    Toast.makeText(VerifyOTPActivity.this, "please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }

                String code = binding.edtInputCode1.getText().toString().trim() +
                        binding.edtInputCode2.getText().toString().trim() +
                        binding.edtInputCode3.getText().toString().trim() +
                        binding.edtInputCode4.getText().toString().trim() +
                        binding.edtInputCode5.getText().toString().trim() +
                        binding.edtInputCode6.getText().toString().trim();

                if (verificationId != null) {

                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.btnVerifyOtp.setVisibility(View.INVISIBLE);

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code);

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    binding.progressBar.setVisibility(View.GONE);
                                    binding.btnVerifyOtp.setVisibility(View.VISIBLE);

                                    if (task.isSuccessful()) {

                                        Intent intent = new Intent(VerifyOTPActivity.this, ChildListActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                        sp = getSharedPreferences("student_Detail", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("mobile_number", mobile_number);
                                        editor.apply();


                                    } else {

                                        Toast.makeText(VerifyOTPActivity.this, "the verification code entered was invalid", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                }
            }
        });


        binding.tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile_no"),
                        60,
                        TimeUnit.SECONDS,
                        VerifyOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                verificationId = newVerificationId;
                                Toast.makeText(VerifyOTPActivity.this, "OTP resend", Toast.LENGTH_SHORT).show();

                            }
                        }
                );

            }
        });

    }

    private void setupOTPInputs() {

        binding.edtInputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    binding.edtInputCode2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edtInputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    binding.edtInputCode3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edtInputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    binding.edtInputCode4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edtInputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    binding.edtInputCode5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edtInputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    binding.edtInputCode6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}