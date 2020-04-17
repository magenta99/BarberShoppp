package com.example.barbershop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.authenticationsms.R;
import com.example.barbershop.adapter.LocationAdapter;
import com.example.barbershop.model.Location;

import java.util.ArrayList;
import java.util.List;

public class SalonActivity extends AppCompatActivity {
    List<Location> lctList;
//    LocationDAO locationDAO;
    LocationAdapter locationAdapter;
    RecyclerView rvSalon;
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách salon");
        rvSalon = findViewById(R.id.rvSalon);
        lctList = new ArrayList<>();
//        locationDAO = new LocationDAO(this);
//        lctList = locationDAO.getAllLocation();
        locationAdapter = new LocationAdapter(lctList, this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        rvSalon.setLayoutManager(gridLayoutManager);
        rvSalon.setAdapter(locationAdapter);
        locationAdapter.notifyDataSetChanged();

    }
}
