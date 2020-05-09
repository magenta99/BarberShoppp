package com.example.barbershop.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.authenticationsms.R;
import com.example.barbershop.fragment.BookingFragment;
import com.example.barbershop.fragment.HomeFragment;
import com.example.barbershop.fragment.SettingsFragment;
import com.example.barbershop.fragment.ShopFragment;
import com.example.barbershop.fragment.StyleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {
    private static final int NOTIFICATION_PERMISSION_CODE = 123;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectFragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,selectFragment).commit();
                    break;
                case R.id.navigation_style:
                    selectFragment = new StyleFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,selectFragment).commit();
                    break;
                case R.id.navigation_book:

                    selectFragment = new BookingFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,selectFragment).commit();
                    break;
                case R.id.navigation_shop:
                    selectFragment = new ShopFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,selectFragment).commit();
                    break;
                case R.id.navigation_settings:
                    selectFragment = new SettingsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,selectFragment).commit();
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new HomeFragment()).commit();
        requestNotificationPermission();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
            } else {
                //Displaying another toast if permission is not granted
            }
        }
    }
    @Override
    public void onBackPressed() {
        return;
    }

    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {

        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, NOTIFICATION_PERMISSION_CODE);
    }
}
