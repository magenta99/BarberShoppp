package com.example.barbershop.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.activity.BaseFragment;
import com.example.barbershop.activity.HomeActivity;
import com.example.barbershop.model.NonSwipeViewPager;
import com.example.barbershop.adapter.MyViewPagerAdapter;
import com.shuhart.stepview.StepView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class BookingFragment extends BaseFragment {

    private StepView stepView;
    private NonSwipeViewPager viewPagerStep;
    private Button btn_back;
    private Button btn_next;
    private int number = 0;
    private int step = 0;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private String locationSchedule = "";
    private String dateSchedule = "";
    private String timeSchedule = "";
    private String nameStylistSchedule = "";
    private String nameServiceSchedule = "";
    private String idSchedule = "";
    private String nameSchedule ;

    public BookingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        stepView = view.findViewById(R.id.step_view);
        viewPagerStep = view.findViewById(R.id.viewPagerStep);
        btn_back = view.findViewById(R.id.btn_back);
        btn_next = view.findViewById(R.id.btn_next);
        AndroidNetworking.post("https://barber123.herokuapp.com/findNameUser")
                .addQueryParameter("phoneUser", getRootPhone())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        String name = response;
                        nameSchedule = name;
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });



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
                    String idBook = intent.getStringExtra("idBook");
                    String timeBook = intent.getStringExtra("timeBook");
                    String dateBook = intent.getStringExtra("dateBook");
                    timeSchedule = timeBook;
                    dateSchedule = dateBook;
                    idSchedule = idBook;
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

        viewPagerStep.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
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
                viewPagerStep.getAdapter().notifyDataSetChanged();
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
                    Intent intent = new Intent();
                    intent.setAction("Location");
                    intent.putExtra("location", locationSchedule);
                    localBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

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
                    sendDataToFragment();
                    sendDataToBookingID(idSchedule);
                    Calendar calendar = Calendar.getInstance();
                    int month = calendar.get(Calendar.MONTH);
                    int monthChuan = month + 1;
                    String date = dateSchedule + "/" + monthChuan + "/" + "2020";
                    sendDatatoBooking(nameSchedule,getRootPhone(),locationSchedule,timeSchedule,date,nameStylistSchedule,nameServiceSchedule,false,"");
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

    private void sendDataToFragment() {
        Intent intent = new Intent();
        intent.setAction("Confirmation");
        intent.putExtra("locationSchedule", locationSchedule);
        intent.putExtra("timeSchedule", timeSchedule);
        intent.putExtra("dateSchedule", dateSchedule);
        intent.putExtra("stylistSchedule", nameStylistSchedule);
        intent.putExtra("serviceSchedule", nameServiceSchedule);
        intent.putExtra("idSchedule", idSchedule);
        localBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    private void sendDatatoBooking(String nameSchedule, String phoneSchedule, String locationSchedule, String timeSchedule, String dateSchedule, String stylistSchedule, String serviceSchedule, boolean statusSchedule, String imageSchedule) {
        AndroidNetworking.post("https://barber123.herokuapp.com/bookingSchedule")
                .addQueryParameter("nameSchedule", nameSchedule)
                .addQueryParameter("phoneSchedule", phoneSchedule)
                .addQueryParameter("locationSchedule", locationSchedule)
                .addQueryParameter("timeSchedule", timeSchedule)
                .addQueryParameter("dateSchedule", dateSchedule)
                .addQueryParameter("stylistSchedule", stylistSchedule)
                .addQueryParameter("serviceSchedule", serviceSchedule)
                .addQueryParameter("statusSchedule", String.valueOf(statusSchedule))
                .addQueryParameter("imageSchedule", imageSchedule)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("Lỗi", error.getMessage());
                    }
                });

    }

    private void sendDataToBookingID(String idSchedule) {
        AndroidNetworking.post("https://api.tradenowvn.com/v1/other/haircut-order")
                .addQueryParameter("id", idSchedule)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("Lỗi", error.getMessage());
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    private void getUsername(String phoneNumber) {

    }

    private String getRootPhone() {
        String name;
        name = getContext().getSharedPreferences("USER", MODE_PRIVATE).getString("PHONE", "");
        return name;
    }
}

