package com.example.barbershop.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.authenticationsms.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{
    private EditText edtPhone;
    private Button btnGetCode;
    private Spinner spinner;
    private List<String>  countryCode;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle saveInstanceState){
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
                String phoneNumber = edtPhone.getText().toString();
                if(phoneNumber.isEmpty()){
                    showMessegeWarning("Vui lòng nhập số điện thoại");
                }else {
                    Intent intent = new Intent(MainActivity.this,ConfirmCodeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("phoneNumber","+84"+phoneNumber);
                    intent.putExtra("Key",bundle);
                    startActivity(intent);
                }
            }
        });
    }
}

