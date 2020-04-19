package com.example.barbershop.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.example.authenticationsms.R;
import com.example.barbershop.activity.BaseActivityFrg;
import com.example.barbershop.activity.HomeActivity;
import com.example.barbershop.model.NonSwipeViewPager;
import com.example.barbershop.adapter.MyViewPagerAdapter;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class BookingFragment extends BaseActivityFrg {
    StepView stepView;
    NonSwipeViewPager viewPagerStep;
    Button btn_back;
    Button btn_next;
    int number = 0;
    int step = 0;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager localBroadcastManager;
    String locationSchedule = "";
    String dateSchedule = "";
    String timeSchedule = "";
    String nameStylistSchedule = "";
    String nameServiceSchedule = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        stepView = view.findViewById(R.id.step_view);
        viewPagerStep = view.findViewById(R.id.viewPagerStep);
        btn_back = view.findViewById(R.id.btn_back);
        btn_next = view.findViewById(R.id.btn_next);

        IntentFilter filter = new IntentFilter();
        filter.addAction("My BroadCast");
        filter.addAction("Time Broadcast");
        filter.addAction("Stylist Broadcast");
        filter.addAction("Service Broadcast");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("My BroadCast")) {
                    String location = intent.getStringExtra("location");
                    locationSchedule = location;
                } else if (intent.getAction().equals("Time Broadcast")) {
                    String timeBook = intent.getStringExtra("timeBook");
                    String dateBook = intent.getStringExtra("dateBook");
                    timeSchedule = timeBook;
                    dateSchedule = dateBook;
                } else if (intent.getAction().equals("Stylist Broadcast")) {
                    String nameStylist = intent.getStringExtra("nameStylist");
                    nameStylistSchedule = nameStylist;
                } else if (intent.getAction().equals("Service Broadcast")) {
                    String nameService = intent.getStringExtra("nameService");
                    nameServiceSchedule = nameService;
                }
            }
        };
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, filter);


        setupStepView();

        viewPagerStep.setAdapter(new MyViewPagerAdapter(getFragmentManager()));
        viewPagerStep.setOffscreenPageLimit(4);
        viewPagerStep.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int i) {
                stepView.go(i, true);
                number = i;
                if (number == 0) {
                    btn_back.setEnabled(false);
                    btn_back.setBackgroundResource(R.color.colorUnselected);
                    btn_back.setTextColor(getResources().getColor(R.color.colorBlack));
                } else if (number == 3) {
                    btn_back.setEnabled(false);
                    btn_next.setText("Xác nhận");
                    btn_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startNewActivity(HomeActivity.class);
                        }
                    });
                } else {
                    btn_back.setEnabled(true);
                    btn_back.setBackgroundResource(R.color.colorBlack);
                    btn_back.setTextColor(getResources().getColor(R.color.colorGold));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (step == 3 || step > 0) {
                    step--;
                    viewPagerStep.setCurrentItem(step);
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (step == 0 && locationSchedule == "") {
                    showMessegeWarning("Vui lòng chọn Salon");
                } else if (step == 0 && locationSchedule != "") {
                    step++;
                    viewPagerStep.setCurrentItem(step);
                } else if (step == 1 && timeSchedule == "") {
                    showMessegeWarning("Vui lòng chọn giờ");
                } else if (step == 1 && timeSchedule != "") {
                    step++;
                    viewPagerStep.setCurrentItem(step);
                } else if (step == 2 && nameStylistSchedule == "") {
                    showMessegeWarning("Vui lòng chọn Stylist");
                } else if (step == 2 && nameServiceSchedule == "") {
                    showMessegeWarning("Vui lòng chọn Dịch vụ");
                } else if (step == 2 && timeSchedule != "" && nameServiceSchedule != "") {
                    step++;
                    viewPagerStep.setCurrentItem(step);
                    sendData();
                }
            }
        });
    }


    private void setupStepView() {
        List<String> stepList = new ArrayList<>();
        stepList.add("Salon");
        stepList.add("Ngày giờ");
        stepList.add("Stylist/Dịch vụ");
        stepList.add("Xác nhận");
        stepView.setSteps(stepList);
    }

    private void sendData(){
        Intent intent = new Intent();
        intent.setAction("Confirmation");
        intent.putExtra("locationSchedule",locationSchedule);
        intent.putExtra("timeSchedule",timeSchedule);
        intent.putExtra("dateSchedule",dateSchedule);
        intent.putExtra("stylistSchedule",nameStylistSchedule);
        intent.putExtra("serviceSchedule",nameServiceSchedule);
        localBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }
}