package com.example.barbershop.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.model.Booking;
import com.example.barbershop.model.Location;
import com.example.barbershop.model.Schedule;
import com.example.barbershop.adapter.LocationAdapter;
import com.example.barbershop.adapter.ScheduleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class BookFragment extends Fragment {
    private List<String> dayList;
    private Spinner spListLocation;
    private ArrayAdapter<String> spAdapter;
    private RecyclerView rvLocation, rvSchedule;
    private LocationAdapter locationAdapter;
    private ScheduleAdapter scheduleAdapter;
    private GridLayoutManager gridLayoutManagerLocation, gridLayoutManagerSchedule;
    private List<Location> locationList;
    private List<Schedule> scheduleList;
    private List<Booking> bookingList;
    private TextView dateBook1;
    private TextView dateBook2;
    private CardView cardToday;
    private CardView cardTomorrow;
    Button btnSchedule;
    String schedule;
    String dateSchedule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        dateBook1 = view.findViewById(R.id.dateBook1);
        dateBook2 = view.findViewById(R.id.dateBook2);
        cardToday = view.findViewById(R.id.cardToday);
        cardTomorrow = view.findViewById(R.id.cardTomorrow);


        //Lấy ngày hiện tại
        String currentDate = new SimpleDateFormat("dd/MM", Locale.getDefault()).format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        dateBook1.setText(dayOfTheWeek + ", " + currentDate);
        //Lấy ngày mai
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM");
        String tomorowDate = dateFormat.format(tomorrow);
        dateBook2.setText(tomorowDate);

        rvSchedule = view.findViewById(R.id.rvSchedule);
        bookingList = new ArrayList<>();

        //Tự động chọn ngày hôm nay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cardToday.performClick();
            }
        }, 200);

        cardToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingList.clear();
                cardToday.setCardBackgroundColor(Color.parseColor("#ffcc33"));
                cardTomorrow.setCardBackgroundColor(Color.WHITE);
                loadBooking(1);
            }
        });

        cardTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingList.clear();
                cardTomorrow.setCardBackgroundColor(Color.parseColor("#ffcc33"));
                cardToday.setCardBackgroundColor(Color.WHITE);
                loadBooking(2);
            }
        });

//        btnSchedule = view.findViewById(R.id.btnSchedule);
//        locationList = new ArrayList<>();
//        locationList.add("TP Hà Nội");
//        rvLocation = view.findViewById(R.id.rvLocation);
//        spListLocation = view.findViewById(R.id.spnLocation);
//        spnDay = view.findViewById(R.id.spnDay);
//        spAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, locationList);
//        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spListLocation.setAdapter(spAdapter);


//        btnSchedule.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    if (location == null) {
//                        Toasty.warning(getContext(), "Vui lòng chọn salon", Toast.LENGTH_SHORT).show();
//                    } else if (schedule == null) {
//                        Toasty.wa rning(getContext(), "Vui lòng chọn giờ cắt", Toast.LENGTH_SHORT).show();
//                    } else {
//                        AndroidNetworking.post("http://barber-shopp.herokuapp.com/schedule")
//                                .addBodyParameter("fullName", "Guest")
//                                .addBodyParameter("phoneNumber", getRootUsername())
//                                .addBodyParameter("day", dateSchedule)
//                                .addBodyParameter("time", schedule)
//                                .addBodyParameter("place", location)
//                                .setTag("test")
//                                .setPriority(Priority.MEDIUM)
//                                .build()
//                                .getAsJSONObject(new JSONObjectRequestListener() {
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                    }
//
//                                    @Override
//                                    public void onError(ANError error) {
//                                        // handle error
//                                    }
//                                });
//                        Intent intent = new Intent(getActivity(), SuccessActivity.class);
//                        startActivity(intent);
//                    }
//                } catch (Exception e) {
//                }
//            }
//        });

    }

    private void loadBooking(int day) {
        AndroidNetworking.get("https://api.tradenowvn.com/v1/other/haircut-getall?number={day}")
                .addPathParameter("day", Integer.toString(day))
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String id = jsonObject1.getString("id");
                                String time = jsonObject1.getString("time");
                                boolean exist = jsonObject1.getBoolean("exist");
                                String date = jsonObject1.getString("date");
                                bookingList.add(new Booking(id, time, exist, date));

                            }
                            scheduleAdapter = new ScheduleAdapter(getContext(), bookingList);
                            gridLayoutManagerSchedule = new GridLayoutManager(getContext(), 4, GridLayoutManager.HORIZONTAL, false);
                            rvSchedule.setLayoutManager(gridLayoutManagerSchedule);
                            rvSchedule.setAdapter(scheduleAdapter);
                            scheduleAdapter.notifyDataSetChanged();
                            scheduleAdapter.setOnItemClickListner(new ScheduleAdapter.onItemClickListner() {
                                @Override
                                public void onClick(String strSchedule,String stb) {
                                    schedule = strSchedule;
                                }
                            });

                        } catch (JSONException e) {
                            Log.d("Error", "" + e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }


    private String getRootUsername() {
        String name;
        name = getContext().getSharedPreferences("USER", MODE_PRIVATE).getString("NAME", null);
        return name;
    }


}
