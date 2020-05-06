package com.example.barbershop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.authenticationsms.R;

public class LoginActivity extends BaseActivity {
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity(MainActivity.class);
            }
        });
    }
}
