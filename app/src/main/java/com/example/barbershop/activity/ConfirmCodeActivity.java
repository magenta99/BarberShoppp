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
    private int SUCCESS = 0;
    String phoneNumber;
    String requestId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        pvCode = findViewById(R.id.pvCode);
        btnContinue = findViewById(R.id.btnContinue);
        tvResendCode = findViewById(R.id.tvResendCode);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String phone = bundle.getString("phoneNumber", "");
        String id = bundle.getString("request_id", "");
        phoneNumber = phone;
        requestId = id;
        tvPhoneNumber.setText(phoneNumber);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmOTP(requestId, phoneNumber);
            }
        });

        tvResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSendOtp(requestId, phoneNumber);
            }
        });
    }

    public void confirmOTP(final String request_id, final String phone) {
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
                            if (Integer.parseInt(status) == SUCCESS) {
                                Intent intent = new Intent(ConfirmCodeActivity.this, HomeActivity.class);
                                startActivity(intent);
                                saveUsername(phone);
                            } else {
                                showMessegeWarning("Vui lòng kiểm tra lại OTP");
                            }
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

    private void reSendOtp(final String request_id, final String phone) {
        final String phoneNumber = phone;
        String subNumber = phoneNumber.substring(1, 10);
        AndroidNetworking.post("http://barber123.herokuapp.com/confirmOTP")
                .addBodyParameter("id", request_id)
                .addBodyParameter("phone", subNumber)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("confirmOtp", response.toString());
                            String status = response.getString("status");
                            if (Integer.parseInt(status) == SUCCESS) {
                                showMessegeSuccess("Mã đã gửi lại thành công");
                                String id = response.getString("request_id");
                                requestId = id;
                            } else {
                                showMessegeWarning("Vui lòng kiểm tra lại");
                            }
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

    private void saveUsername(String phoneNumber) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("PHONE", phoneNumber);
        edit.apply();
    }
}
