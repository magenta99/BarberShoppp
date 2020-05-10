package com.example.barbershop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.activity.BaseFragment;
import com.example.barbershop.adapter.LocationAdapter;
import com.example.barbershop.adapter.MyViewPagerAdapter;
import com.example.barbershop.model.Location;
import com.shuhart.stepview.StepView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends BaseFragment {
    private List<Location> locationList;
    private RecyclerView rvLocation;
    private LocationAdapter locationAdapter;
    private GridLayoutManager gridLayoutManagerLocation;
    private LocalBroadcastManager localBroadcastManager;
    public String location;

    static LocationFragment instance;
    public static LocationFragment getInstance(){
        if(instance == null){
            instance = new LocationFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        initView(view);
        loadLocation();
        return view;
    }
    private void initView(View view) {
        locationList = new ArrayList<>();
        locationList.clear();
        rvLocation = view.findViewById(R.id.rvLocation);
    }
    private void loadLocation() {
        AndroidNetworking.get("https://barber123.herokuapp.com/location")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String idLocation = jsonObject.getString("_id");
                                String addressLocation = jsonObject.getString("addressLocation");
                                String districtLocation = jsonObject.getString("districtLocation");
                                String districtDetailLocation = jsonObject.getString("districtDetailLocation");
                                String cityLocation = jsonObject.getString("cityLocation");
                                locationList.add(new Location (idLocation, addressLocation, districtLocation, districtDetailLocation, cityLocation));
                            }

                            locationAdapter = new LocationAdapter(locationList, getContext());
                            gridLayoutManagerLocation = new GridLayoutManager(getContext(), 2);
                            rvLocation.setLayoutManager(gridLayoutManagerLocation);
                            rvLocation.setAdapter(locationAdapter);
                            locationAdapter.notifyDataSetChanged();
                            locationAdapter.setOnItemClickListner(new LocationAdapter.onItemClickListner() {
                                @Override
                                public void onClick(final String strLocation) {
                                    location = strLocation;
                                    Intent intent = new Intent();
                                    intent.setAction("My BroadCast");
                                    intent.putExtra("location",location);
                                    localBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                                }
                            });


                        } catch (Error error) {
                            Log.e("Lỗi",""+error);
                        } catch (JSONException e) {
                            Log.e("Lỗi",""+e);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("Error",""+error);
                    }
                });


    }



}
