package com.example.barbershop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authenticationsms.R;
import com.example.barbershop.Schedule;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder> {
    int selectedPosition = -1;

    List<Schedule> scheduleList;
    Context context;

    public ScheduleAdapter(List<Schedule> locationList, Context context) {
        this.scheduleList = locationList;
        this.context = context;
    }

    onItemClickListner onItemClickListner;

    public void setOnItemClickListner() {
        setOnItemClickListner();
    }

    public void setOnItemClickListner(ScheduleAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface onItemClickListner{
        void onClick(String str);//pass your object types.
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_item, parent, false);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ScheduleHolder scheduleHolder, final int position) {
        if (selectedPosition == position) {
            scheduleHolder.itemView.setBackgroundColor(Color.parseColor("#FFDD00"));
        } else {
            scheduleHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        scheduleHolder.schedule = scheduleList.get(position);
        scheduleHolder.tvTime.setText(scheduleHolder.schedule.SCHEDULE_TIME);
        scheduleHolder.tvInfo.setText(scheduleHolder.schedule.SCHEDULE_INFO);

        scheduleHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
                onItemClickListner.onClick(scheduleHolder.schedule.SCHEDULE_TIME);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvInfo;
        private Schedule schedule;

        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvInfo = itemView.findViewById(R.id.tvInfo);
        }
    }

}
