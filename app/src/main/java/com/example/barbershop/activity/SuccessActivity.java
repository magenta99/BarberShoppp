package com.example.barbershop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.authenticationsms.R;
import com.example.barbershop.activity.BaseActivity;
import com.example.barbershop.activity.HomeActivity;

public class SuccessActivity extends BaseActivity {
    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        btnHome =  findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity(HomeActivity.class);
            }
        });
    }
}
