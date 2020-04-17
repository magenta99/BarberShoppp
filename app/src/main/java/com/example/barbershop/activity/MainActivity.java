package com.example.barbershop.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.authenticationsms.R;

public class MainActivity extends BaseActivity{
    private EditText edtPhone;
    private Button btnGetCode;
    String phoneNumber = "+1 650-555-3434";

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        edtPhone = findViewById(R.id.edtPhone);
        btnGetCode = findViewById(R.id.btnGetCode);
        edtPhone.setText(phoneNumber);

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = edtPhone.getText().toString();
                if(phoneNumber.isEmpty()){
                    showMessegeWarning("Vui lòng nhập số điện thoại");
                }else {
                    Intent intent = new Intent(MainActivity.this,ConfirmCodeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("phoneNumber",phoneNumber);
                    intent.putExtra("Key",bundle);
                    startActivity(intent);
                }
            }
        });
    }
}

