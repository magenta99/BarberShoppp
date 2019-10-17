package com.example.barbershop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.authenticationsms.R;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {
    List<String> locationList;
    private Spinner spListLocation;
    private ArrayAdapter<String> spAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_book,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        locationList = new ArrayList<>();
        locationList.add("TP Hà Nội");
        spListLocation = view.findViewById(R.id.spnLocation);
        spAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, locationList);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spListLocation.setAdapter(spAdapter);

    }
}
