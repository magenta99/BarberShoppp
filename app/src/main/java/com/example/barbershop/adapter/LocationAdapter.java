package com.example.barbershop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authenticationsms.R;
import com.example.barbershop.model.Location;

import java.util.List;

import static android.app.PendingIntent.getActivity;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationHolder> {
    int selectedPosition = -1;

    List<Location> locationList;
    Context context;
    LocalBroadcastManager localBroadcastManager;

    public LocationAdapter(List<Location> locationList, Context context) {
        this.locationList = locationList;
        this.context = context;
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    onItemClickListner onItemClickListner;

    public void setOnItemClickListner() {
        setOnItemClickListner();
    }

    public void setOnItemClickListner(LocationAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface onItemClickListner{
        void onClick(String str);//pass your object types.
    }

    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_item, parent, false);
        return new LocationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationHolder locationHolder, final int position) {
        if (selectedPosition == position) {
            locationHolder.itemView.setBackgroundColor(Color.parseColor("#FFDD00"));
        } else {
            locationHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        locationHolder.location = locationList.get(position);
        locationHolder.tvLocation.setText(locationHolder.location.getAddressLocation());
        locationHolder.tvDetailLocation.setText(locationHolder.location.getDistrictDetailLocation());


        locationHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
                onItemClickListner.onClick(locationHolder.location.getAddressLocation());
            }
        });
    }



    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class LocationHolder extends RecyclerView.ViewHolder {
        private TextView tvLocation;
        private TextView tvDetailLocation;
        private Location location;

        public LocationHolder(@NonNull View itemView) {
            super(itemView);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDetailLocation = itemView.findViewById(R.id.tvDetailLocation);
//            tvDistance = itemView.findViewById(R.id.tvDistance);

        }
    }


}
