package com.example.barbershop.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.authenticationsms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private EditText edtPhone;
    private Button btnGetCode;
    private Spinner spinner;
    private List<String> countryCode;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        edtPhone = findViewById(R.id.edtPhone);
        btnGetCode = findViewById(R.id.btnGetCode);
        spinner = findViewById(R.id.spCountry);

        countryCode = new ArrayList<>();
        countryCode.add("VN (+84)");
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryCode);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneNumber = edtPhone.getText().toString();
                if (phoneNumber.isEmpty()) {
                    showMessegeWarning("Vui lòng nhập số điện thoại");
                } else {

                    AndroidNetworking.post("http://barber123.herokuapp.com/sendOTP")
                            .addBodyParameter("phone", phoneNumber)
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        Log.d("sendOtp",response.toString());
                                        String id = response.getString("request_id");
                                        Intent intent = new Intent(MainActivity.this, ConfirmCodeActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("phoneNumber", "84"+phoneNumber);
                                        bundle.putString("request_id",id);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
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
    }
}

