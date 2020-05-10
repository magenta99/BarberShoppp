package com.example.barbershop.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.EndlessRecyclerViewScrollListener;
import com.example.barbershop.activity.BSTActivity;
import com.example.barbershop.activity.BaseFragment;
import com.example.barbershop.activity.HomeActivity;
import com.example.barbershop.activity.LoginActivity;
import com.example.barbershop.activity.SalonActivity;
import com.example.barbershop.activity.SplashActivity;
import com.example.barbershop.activity.TVActivity;
import com.example.barbershop.adapter.HistoryAdapter;
import com.example.barbershop.adapter.ProductAdapter;
import com.example.barbershop.model.History;
import com.example.barbershop.model.Product;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HistoryFragment extends BaseFragment {
    private SwipeRefreshLayout srlShampoo;
    private NestedScrollView mainLayout;
    private ImageButton btnBackHistory;
    private RecyclerView rvHistory;
    private List<History> historyList;
    private LinearLayoutManager linearLayoutManager;
    private HistoryAdapter historyAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        srlShampoo = view.findViewById(R.id.srlShampoo);
        shimmerFrameLayout = view.findViewById(R.id.shimmerFrameLayout);
        mainLayout = view.findViewById(R.id.mainLayout);
        btnBackHistory = view.findViewById(R.id.btnBackHistory);
        rvHistory = view.findViewById(R.id.rvHistory);
        historyList = new ArrayList<>();

        loadShampooProduct(getRootPhone());

        btnBackHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsFragment nextFrag = new SettingsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
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

    private void loadShampooProduct(String phoneNumber) {
        AndroidNetworking.get("http://barber123.herokuapp.com/getSchedulePhone?phone={phone}")
                .addPathParameter("phone", phoneNumber)
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(final JSONArray response) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    for (int i = 0; i < response.length(); i++) {
                                        shimmerFrameLayout.stopShimmer();
                                        rvHistory.setVisibility(View.VISIBLE);
                                        shimmerFrameLayout.setVisibility(View.GONE);
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        String locationHistory = jsonObject.getString("locationSchedule");
                                        String dateHistory = jsonObject.getString("dateSchedule");
                                        String timeHistory = jsonObject.getString("timeSchedule");
                                        String stylistHistory = jsonObject.getString("stylistSchedule");
                                        String serviceHistory = jsonObject.getString("serviceSchedule");
                                        String imageHistory = jsonObject.getString("imageSchedule");
                                        historyList.add(new History(locationHistory, dateHistory, timeHistory, stylistHistory, serviceHistory, imageHistory));
                                    }
                                    historyAdapter = new HistoryAdapter(getContext(), historyList);
                                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                    rvHistory.setAdapter(historyAdapter);
                                    rvHistory.setLayoutManager(linearLayoutManager);
                                    rvHistory.setHasFixedSize(true);
                                    rvHistory.setNestedScrollingEnabled(false);
                                    rvHistory.scheduleLayoutAnimation();
                                    if (historyList.size() == 0) {
                                        shimmerFrameLayout.stopShimmer();
                                        shimmerFrameLayout.setVisibility(View.GONE);
                                    }

                                } catch (JSONException e) {

                                }
                            }
                        }, 2000);
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("Lỗi", "" + error);
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();

    }
}

