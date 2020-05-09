package com.example.barbershop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.example.authenticationsms.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends BaseActivity {
    String phoneNumber;

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
                    String phone = getSharedPreferences("USER", MODE_PRIVATE).getString("PHONE", "");
                    if (phone.isEmpty()) {
                        Intent obj = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(obj);
                    } else {
                        Intent obj = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(obj);
                    }
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

    private String getRootUsername() {
        String name;
        name = this.getSharedPreferences("USER", MODE_PRIVATE).getString("NAME", "");
        return name;
    }
}
