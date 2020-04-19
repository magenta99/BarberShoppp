package com.example.barbershop.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.authenticationsms.R;

public class ConfirmFragment extends Fragment {
    static ConfirmFragment instance;
    private BroadcastReceiver broadcastReceiver;
    private String locationConfirm;
    private String timeConfirm;
    private String dateConfirm;
    private String stylistConfirm;
    private String serviceConfirm;
    private TextView locationBooking;
    private TextView timeBooking;
    private TextView dateBooking;
    private TextView stylistBooking;
    private TextView serviceBooking;


    public static ConfirmFragment getInstance() {
        if (instance == null) {
            instance = new ConfirmFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        locationBooking = view.findViewById(R.id.locationBooking);
        timeBooking = view.findViewById(R.id.timeBooking);
        dateBooking = view.findViewById(R.id.dateBooking);
        stylistBooking = view.findViewById(R.id.stylistBooking);
        serviceBooking = view.findViewById(R.id.serviceBooking);

        IntentFilter filter = new IntentFilter();
        filter.addAction("Confirmation");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("Confirmation")) {
                    String locationSchedule = intent.getStringExtra("locationSchedule");
                    String timeSchedule = intent.getStringExtra("timeSchedule");
                    String dateSchedule = intent.getStringExtra("dateSchedule");
                    String stylistSchedule = intent.getStringExtra("stylistSchedule");
                    String serviceSchedule = intent.getStringExtra("serviceSchedule");
                    locationBooking.setText(locationSchedule);
                    timeBooking.setText(timeSchedule);
                    dateBooking.setText(dateSchedule+"/04");
                    stylistBooking.setText(stylistSchedule);
                    serviceBooking.setText(serviceSchedule);
                }
            }
        };
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, filter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    private void sendDataToServer(){

    }

}
