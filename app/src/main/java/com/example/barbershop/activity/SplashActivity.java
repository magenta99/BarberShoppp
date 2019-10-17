package com.example.barbershop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.authenticationsms.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2500);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent obj = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(obj);
                }
            }
        };
        th.start();
    }
    @Override
    public void onPause() {
        super.onPause();
        finish();

    }
}
