package com.example.barbershop.activity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.chaos.view.PinView;
import com.example.authenticationsms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class ConfirmCodeActivity extends BaseActivity {
    private TextView tvPhoneNumber;
    private PinView pvCode;
    private Button btnContinue;
    private TextView tvResendCode;
    private FirebaseAuth mAuth;
    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        mAuth = FirebaseAuth.getInstance();
        pvCode = findViewById(R.id.pvCode);
        btnContinue = findViewById(R.id.btnContinue);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String phone = bundle.getString("phoneNumber");
        final String requestId = bundle.getString("request_id");
        tvPhoneNumber.setText(phone);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOTP(requestId, phone);
            }
        });

    }

    private void verifySignInCode(String codeSent) {
        String code = pvCode.getText().toString();
        if (code.isEmpty()) {
            showMessegeWarning("Vui lòng nhập mã code");
        } else {

            //PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
            //signInWithPhoneAuthCredential(credential);
        }
        //codeSent laf cai code da gui
        //dung de so sanh voi ca code nhap vao
    }

    public void checkOTP(String request_id, final String phone) {
        AndroidNetworking.post("http://barber123.herokuapp.com/checkOTP")
                .addBodyParameter("id", request_id)
                .addBodyParameter("code", pvCode.getText().toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("checkOtp", response.toString());
                            String status = response.getString("status");
                            if (status == "0") {
                                saveUsername(phone);
                            } else {
                                showMessegeWarning("Vui lòng kiểm tra lại OTP");
                            }
//                            Intent intent = new Intent(MainActivity.this, ConfirmCodeActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("phoneNumber", "84"+phone);
//                            bundle.putString("request_id",id);
//                            intent.putExtra("loginOTP", bundle);
//                            startActivity(intent);
                        } catch (JSONException e) {
                            Log.d("Error", "" + e);
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            startNewActivity(HomeActivity.class);
//                            saveUsername(phone);
//
//                        } else {
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                showMessegeWarning("Mã không đúng");
//                            } else {
//                                showMessegeSuccess("Đăng nhập thành công");
//
//                            }
//                        }
//                    }
//                });
//    }

//    private void sendVertificationCode(String phoneNumber) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacksPhoneAuthActivity.java
//    }
//
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//
//        }
//
//        @Override
//        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            codeSent = s;
//        }
//    };

    private void saveUsername(String phoneNumber) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("NAME", phoneNumber);
        edit.apply();
    }

}
