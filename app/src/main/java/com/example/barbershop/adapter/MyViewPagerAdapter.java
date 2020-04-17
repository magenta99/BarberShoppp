package com.example.barbershop.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.barbershop.fragment.ConfirmFragment;
import com.example.barbershop.fragment.LocationFragment;
import com.example.barbershop.fragment.ServiceFragment;
import com.example.barbershop.fragment.TimeFragment;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    public MyViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return LocationFragment.getInstance();
            case 1:
                return TimeFragment.getInstance();
            case 2:
                return ServiceFragment.getInstance();
            case 3:
                return ConfirmFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4
                ;
    }
}
