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
    private Button btnGetCode;
    private EditText edtName;
    private EditText edtConfirmOTP;
    private Button btnLogin;

    private Spinner spinner;
    private List<String> countryCode;
    private ArrayAdapter<String> spinnerAdapter;
    String requestId;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        edtPhone = findViewById(R.id.edtPhone);
        btnGetCode = findViewById(R.id.btnGetCode);
        edtName = findViewById(R.id.edtFullName);
        edtConfirmOTP = findViewById(R.id.edtConfirmOTP);
        btnLogin = findViewById(R.id.btnLogin);

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneNumber = edtPhone.getText().toString();
                String subNumber = phoneNumber.substring(1, 10);
                showMessage(subNumber);
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
                                        if (Integer.parseInt(status) == 0) {
                                            Log.d("sendOtp", response.toString());
                                            String id = response.getString("request_id");
//                                            Intent intent = new Intent(MainActivity.this, ConfirmCodeActivity.class);
//                                            Bundle bundle = new Bundle();
//                                            bundle.putString("phoneNumber",  phoneNumber);
//                                            bundle.putString("request_id", id);
//                                            intent.putExtras(bundle);
//                                            startActivity(intent);
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
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtConfirmOTP.getText().toString().isEmpty()) {
                    showMessage("Không để trống OTP");
                } else {
                    confirmOTP();
                }
            }
        });
    }

    public void confirmOTP() {
        AndroidNetworking.post("http://barber123.herokuapp.com/checkOTP")
                .addBodyParameter("id", requestId)
                .addBodyParameter("code", edtConfirmOTP.getText().toString().trim())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("checkOtp", response.toString());
                            String status = response.getString("status");
                            if (Integer.parseInt(status) == 0) {
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                                saveUsername(edtPhone.getText().toString().trim());
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

    private void saveUsername(String phoneNumber) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("PHONE",  phoneNumber);
        edit.putString("NAME", edtName.getText().toString().trim());
        edit.apply();
    }

}



