package com.example.barbershop.activity;


import android.content.Intent;
import android.content.SharedPreferences;
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

    String requestId;
    private int SUCCESS = 0;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        edtPhone = findViewById(R.id.edtPhoneNumber);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPhone.getText().toString().isEmpty()) {
                    showMessage("Không để trống số điện thoại");
                } else {
                    final String phoneNumber = edtPhone.getText().toString();
                    String subNumber = phoneNumber.substring(1, 10);
                    if (phoneNumber.isEmpty()) {
                        showMessegeWarning("Vui lòng nhập số điện thoại");
                    } else {
                        AndroidNetworking.post("http://barber123.herokuapp.com/sendOTP")
                                .addBodyParameter("phone", subNumber)
                                .setTag("test")
                                .setPriority(Priority.MEDIUM)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String status = response.getString("status");
                                            if (Integer.parseInt(status) == SUCCESS) {
                                                Log.d("sendOtp", response.toString());
                                                String id = response.getString("request_id");
                                                Intent intent = new Intent(MainActivity.this, ConfirmCodeActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("phoneNumber", phoneNumber);
                                                bundle.putString("request_id", id);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                showMessegeSuccess("Vui lòng kiểm tra mã trong tin nhắn");
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
                }
            }
        });
    }

}



