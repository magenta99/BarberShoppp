package com.example.barbershop.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.activity.BaseFragment;
import com.example.barbershop.adapter.LocationAdapter;
import com.example.barbershop.adapter.ServiceAdapter;
import com.example.barbershop.adapter.StylistAdapter;
import com.example.barbershop.model.Service;
import com.example.barbershop.model.Stylist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StylistServiceFragment extends BaseFragment {
    public List<Stylist> stylistList;
    public List<Service> serviceList;
    public StylistAdapter stylistAdapter;
    public ServiceAdapter serviceAdapter;
    public LinearLayoutManager stylistLinerLayoutManager;
    public LinearLayoutManager serviceLinerLayoutManager;
    public RecyclerView rvStylist;
    public RecyclerView rvService;
    private BroadcastReceiver broadcastReceiver;
    private String locationSchedule = "";
    public LocalBroadcastManager localBroadcastManager;

    static StylistServiceFragment instance;

    public static StylistServiceFragment getInstance() {
        if (instance == null) {
            instance = new StylistServiceFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        rvService = view.findViewById(R.id.rvService);
        rvStylist = view.findViewById(R.id.rvStylist);
        stylistList = new ArrayList<>();
        serviceList = new ArrayList<>();
        IntentFilter filter = new IntentFilter();
        filter.addAction("My BroadCast");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("My BroadCast")) {
                    String location = intent.getStringExtra("location");
                    locationSchedule = location;
                }
            }
        };
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, filter);
        loadStylist(locationSchedule);
        loadService();
    }

    private void loadStylist(String location) {
        AndroidNetworking.get("http://barber123.herokuapp.com/getStyList")
                .addQueryParameter("location",location)
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String idStylist = jsonObject.getString("_id");
                                String nameStylist = jsonObject.getString("nameStylist");
                                String ratingStylist = jsonObject.getString("ratingStylist");
                                stylistList.add(new Stylist(idStylist, nameStylist, ratingStylist));
                            }
                            stylistAdapter = new StylistAdapter(stylistList, getContext());
                            stylistLinerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            rvStylist.setLayoutManager(stylistLinerLayoutManager);
                            rvStylist.setAdapter(stylistAdapter);
                            stylistAdapter.notifyDataSetChanged();
                            stylistAdapter.setOnItemClickListner(new StylistAdapter.onItemClickListner() {
                                @Override
                                public void onClick(final String strStylist) {
                                    Intent intent = new Intent();
                                    intent.setAction("Stylist Broadcast");
                                    intent.putExtra("nameStylist", strStylist);
                                    localBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });


    }

    private void loadService() {
        AndroidNetworking.get("https://barber123.herokuapp.com/service")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String idService = jsonObject.getString("_id");
                                String nameService = jsonObject.getString("nameService");
                                String priceService = jsonObject.getString("priceService");
                                String detailService = jsonObject.getString("detailService");
                                serviceList.add(new Service(idService, nameService, detailService, priceService));
                            }
                            serviceAdapter = new ServiceAdapter(serviceList, getContext());
                            serviceLinerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            rvService.setLayoutManager(serviceLinerLayoutManager);
                            rvService.setAdapter(serviceAdapter);
                            serviceAdapter.notifyDataSetChanged();
                            serviceAdapter.setOnItemClickListner(new ServiceAdapter.onItemClickListner() {
                                @Override
                                public void onClick(final String strService, String price) {
                                    //Gửi data sang BookingFragment
                                    Intent intent = new Intent();
                                    intent.setAction("Service Broadcast");
                                    intent.putExtra("nameService", strService);
                                    intent.putExtra("priceSevice", price);
                                    localBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

}
