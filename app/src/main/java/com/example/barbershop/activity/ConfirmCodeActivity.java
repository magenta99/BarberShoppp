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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class ConfirmCodeActivity extends BaseActivity {
    private TextView tvPhoneNumber;
    private PinView pvCode;
    private Button btnContinue;
    private TextView tvResendCode;
    private int SUCCESS = 100;
    String phoneNumber;
    String OTP;


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
        String otp = bundle.getString("otp", "");
        phoneNumber = phone;
        OTP = otp;
        tvPhoneNumber.setText(phoneNumber);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = pvCode.getText().toString();
                if (code.isEmpty()) {
                    showMessegeWarning("Vui lòng nhập OTP");
                } else if (code.length() < 6) {
                    showMessegeWarning("Vui lòng nhập đủ OTP");
                } else {
                    if (Integer.parseInt(OTP) == Integer.parseInt(code)) {
                        Intent intent = new Intent(ConfirmCodeActivity.this, HomeActivity.class);
                        startActivity(intent);
                        saveUsername(phone);
                    } else {
                        showMessegeWarning("Vui lòng kiểm tra lại OTP");
                    }
                }
            }
        });

        tvResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSendOtp(phoneNumber);
            }
        });
    }

//    public void confirmOTP(final String Otp, final String phone) {
//        if (OTP == pvCode.getText().toString()) {
//            Intent intent = new Intent(ConfirmCodeActivity.this, HomeActivity.class);
//            startActivity(intent);
//            saveUsername(phone);
//        } else {
//            showMessegeWarning("Vui lòng kiểm tra lại OTP");
//        }
//        AndroidNetworking.post("http://barber123.herokuapp.com/checkOTP")
//                .addBodyParameter("id", request_id)
//                .addBodyParameter("code", pvCode.getText().toString())
//                .setTag("test")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            Log.d("checkOtp", response.toString());
//                            String status = response.getString("status");
//                            if (Integer.parseInt(status) == SUCCESS) {
//                                Intent intent = new Intent(ConfirmCodeActivity.this, HomeActivity.class);
//                                startActivity(intent);
//                                saveUsername(phone);
//                            } else {
//                                showMessegeWarning("Vui lòng kiểm tra lại OTP");
//                            }
//                        } catch (JSONException e) {
//                            Log.d("Error", "" + e);
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                    }
//                });
 //   }

    private void reSendOtp(final String phone) {
        final int otp = randomWithRange(100000, 999999);

        AndroidNetworking.post("http://barber123.herokuapp.com/sendOtp")
                .addBodyParameter("phone", phone)
                .addBodyParameter("otp", String.valueOf(otp))
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("CodeResult");
                            if (Integer.parseInt(status) == SUCCESS) {
                                Log.d("sendOtp", response.toString());
                                OTP = String.valueOf(otp);
                                showMessegeSuccess("Vui lòng kiểm tra mã trong tin nhắn");
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

    int randomWithRange(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int) (Math.random() * range) + (min <= max ? min : max);
    }
}
