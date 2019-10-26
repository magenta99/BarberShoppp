package com.example.barbershop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.Location;
import com.example.barbershop.Schedule;
import com.example.barbershop.SuccessActivity;
import com.example.barbershop.adapter.LocationAdapter;
import com.example.barbershop.adapter.ScheduleAdapter;
import com.example.barbershop.dao.LocationDAO;
import com.example.barbershop.dao.ScheduleDAO;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;

public class BookFragment extends Fragment {
    List<String> locationList;
    List<String> dayList;
    private Spinner spListLocation;
    private Spinner spnDay;
    private ArrayAdapter<String> spAdapter;
    private ArrayAdapter<String> spAdapterDay;
    private RecyclerView rvLocation, rvSchedule;
    private LocationAdapter locationAdapter;
    private ScheduleAdapter scheduleAdapter;
    private GridLayoutManager gridLayoutManagerLocation, gridLayoutManagerSchedule;
    private List<Location> lctList;
    private List<Schedule> scheduleList;
    private LocationDAO locationDAO;
    private ScheduleDAO scheduleDAO;
    Button btnSchedule;
    String schedule;
    String location;
    String dateSchedule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnSchedule = view.findViewById(R.id.btnSchedule);
        locationList = new ArrayList<>();
        locationList.add("TP Hà Nội");
        rvLocation = view.findViewById(R.id.rvLocation);
        rvSchedule = view.findViewById(R.id.rvSchedule);
        spListLocation = view.findViewById(R.id.spnLocation);
        spnDay = view.findViewById(R.id.spnDay);
        spAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, locationList);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spListLocation.setAdapter(spAdapter);

        dayList = new ArrayList<>();
        dayList.add("Ngày 31/10");
        dayList.add("Ngày 01/10");
        dayList.add("Ngày 02/10");
        spAdapterDay = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dayList);
        spAdapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDay.setAdapter(spAdapterDay);

        spnDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String day = dayList.get(i);
                dateSchedule = day;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        locationDAO = new LocationDAO(getContext());
//        locationDAO.insertLocation(new Location("702 Đường Láng","P. Láng Thương, Q.Đống Đa","1.33 Km"));
//        locationDAO.insertLocation(new Location("407 Trường Chinh","P. Khương Trung, Q.Thanh Xuân","5.33 Km"));
//        locationDAO.insertLocation(new Location("77 Kim Mã","P. Kim Mã, Q.Ba Đình","2.47 Km"));
//        locationDAO.insertLocation(new Location("151 Cầu Giấy","P. Quan Hoa, Q.Cầu Giấy","3.55 Km"));
        scheduleDAO = new ScheduleDAO(getContext());
//        scheduleDAO.insertSchedule(new Schedule("9h00","Còn chỗ"));
//        scheduleDAO.insertSchedule(new Schedule("10h00","Còn chỗ"));
//        scheduleDAO.insertSchedule(new Schedule("11h00","Còn chỗ"));
//        scheduleDAO.insertSchedule(new Schedule("12h00","Còn chỗ"));
//        scheduleDAO.insertSchedule(new Schedule("13h00","Còn chỗ"));
//        scheduleDAO.insertSchedule(new Schedule("14h00","Còn chỗ"));
//        scheduleDAO.insertSchedule(new Schedule("15h00","Còn chỗ"));
//        scheduleDAO.insertSchedule(new Schedule("16h00","Còn chỗ"));
//        scheduleDAO.insertSchedule(new Schedule("17h00","Còn chỗ"));
//        scheduleDAO.insertSchedule(new Schedule("18h00","Còn chỗ"));
//        scheduleDAO.insertSchedule(new Schedule("19h00","Còn chỗ"));
//        scheduleDAO.insertSchedule(new Schedule("20h00","Còn chỗ"));

        scheduleList = new ArrayList<>();
        scheduleList = scheduleDAO.getAllSchedule();
        scheduleAdapter = new ScheduleAdapter(scheduleList, getContext());
        gridLayoutManagerSchedule = new GridLayoutManager(getContext(), 4);
        rvSchedule.setLayoutManager(gridLayoutManagerSchedule);
        rvSchedule.setAdapter(scheduleAdapter);
        scheduleAdapter.notifyDataSetChanged();
        scheduleAdapter.setOnItemClickListner(new ScheduleAdapter.onItemClickListner() {
            @Override
            public void onClick(String strSchedule) {
                schedule = strSchedule;
            }
        });

        lctList = new ArrayList<>();
        lctList = locationDAO.getAllLocation();
        locationAdapter = new LocationAdapter(lctList, getContext());
        gridLayoutManagerLocation = new GridLayoutManager(getContext(), 2);
        rvLocation.setLayoutManager(gridLayoutManagerLocation);
        rvLocation.setAdapter(locationAdapter);
        locationAdapter.notifyDataSetChanged();
        locationAdapter.setOnItemClickListner(new LocationAdapter.onItemClickListner() {
            @Override
            public void onClick(final String strLocation) {
                location = strLocation;
            }
        });

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (location == null) {
                        Toasty.warning(getContext(), "Vui lòng chọn salon", Toast.LENGTH_SHORT).show();
                    } else if (schedule == null) {
                        Toasty.warning(getContext(), "Vui lòng chọn giờ cắt", Toast.LENGTH_SHORT).show();
                    } else {
                        AndroidNetworking.post("http://barber-shopp.herokuapp.com/schedule")
                                .addBodyParameter("fullName", "Huy Anh")
                                .addBodyParameter("phoneNumber", getRootUsername())
                                .addBodyParameter("day", dateSchedule)
                                .addBodyParameter("time", schedule)
                                .addBodyParameter("place", location)
                                .setTag("test")
                                .setPriority(Priority.MEDIUM)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                    }

                                    @Override
                                    public void onError(ANError error) {
                                        // handle error
                                    }
                                });
                        Intent intent = new Intent(getActivity(), SuccessActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {

                }
            }
        });

    }
    private String getRootUsername() {
        String name;
        name = getContext().getSharedPreferences("USER", MODE_PRIVATE).getString("NAME", null);
        return name;
    }

}
