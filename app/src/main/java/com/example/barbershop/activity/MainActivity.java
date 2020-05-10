package com.example.barbershop.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.authenticationsms.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends BaseActivity {
    private EditText edtPhone;
    private Button btnLogin;

    private int SUCCESS = 100;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        edtPhone = findViewById(R.id.edtPhoneNumber);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhone.getText().toString();
                String regexStr = "^[0-9]{10}$";
                if (phone.isEmpty()) {
                    showMessegeWarning("Không để trống số điện thoại");
                } else if (!phone.matches(regexStr)) {
                    showMessegeWarning("Vui lòng nhập đúng kiểu số điện thoại");
                } else {
                    android.net.ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    android.net.NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        final String phoneNumber = edtPhone.getText().toString();
                        final int otp = randomWithRange(100000, 999999);
                        Intent intent = new Intent(MainActivity.this, ConfirmCodeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("phoneNumber", phoneNumber);
                        bundle.putString("otp", String.valueOf(otp));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        AndroidNetworking.post("http://barber123.herokuapp.com/sendOtp")
                                .addBodyParameter("phone", phoneNumber)
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
                                        Log.e("Lỗi",""+error.toString());
                                    }
                                });
                    } else {
                        showMessegeWarning("Vui lòng bật mạng");
                    }
                }
            }
        });
    }

    int randomWithRange(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int) (Math.random() * range) + (min <= max ? min : max);
    }
}



