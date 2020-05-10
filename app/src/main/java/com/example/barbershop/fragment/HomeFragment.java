package com.example.barbershop.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.activity.BaseFragment;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends BaseFragment {
    private ViewFlipper viewFlipper;
    private LocalBroadcastManager localBroadcastManager;
    Button btnBookNow1, btnBookNow2;
    private Button btnBookNowHome;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        int images[] = {R.drawable.main1, R.drawable.main2, R.drawable.main3, R.drawable.main4};
        for (int image : images) {
            flipperImages(image);
        }

        return view;
    }

    private void flipperImages(int image) {

        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2500);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getContext(), android.R.anim.fade_in);
    }

    private void initView(View view) {
        btnBookNowHome = view.findViewById(R.id.btnBookNowHome);

        btnBookNow1 = view.findViewById(R.id.btnBookNow1);
        btnBookNow2 = view.findViewById(R.id.btnBookNow2);

        viewFlipper = view.findViewById(R.id.vpSlider);
        btnBookNow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookingFragment nextFrag = new BookingFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnBookNow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookingFragment nextFrag = new BookingFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnBookNowHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookingFragment nextFrag = new BookingFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    private String getRootPhone() {
        String name;
        name = getContext().getSharedPreferences("USER", MODE_PRIVATE).getString("PHONE", "");
        return name;
    }


}
