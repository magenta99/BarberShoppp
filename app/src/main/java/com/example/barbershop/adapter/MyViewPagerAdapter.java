package com.example.barbershop.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.barbershop.fragment.ConfirmFragment;
import com.example.barbershop.fragment.LocationFragment;
import com.example.barbershop.fragment.StylistServiceFragment;
import com.example.barbershop.fragment.TimeFragment;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    public MyViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        Log.d("CountF", String.valueOf(i));
        switch (i){
            case 0:
                return LocationFragment.getInstance();
            case 1:
                return TimeFragment.getInstance();
            case 2:
                return StylistServiceFragment.getInstance();
            case 3:
                return ConfirmFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
