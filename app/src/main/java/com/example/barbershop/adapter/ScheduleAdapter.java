package com.example.barbershop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.authenticationsms.R;
import com.example.barbershop.model.Booking;
import com.example.barbershop.model.Schedule;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder> {
    int selectedPosition = -1;

    List<Booking> bookingList;
    Context context;

    public ScheduleAdapter( Context context,List<Booking> bookingList) {
        this.bookingList = bookingList;
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
        void onClick(String str1,String str2,String str3);
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
        scheduleHolder.booking = bookingList.get(position);
        scheduleHolder.tvTime.setText(scheduleHolder.booking.getTime());
        if(scheduleHolder.booking.isExist() == true){
            scheduleHolder.tvInfo.setText("Còn chỗ");
            scheduleHolder.colorStatus.setBackgroundColor(Color.GREEN);

        }else {
            scheduleHolder.tvInfo.setText("Hết chỗ");
            scheduleHolder.colorStatus.setBackgroundColor(Color.RED);
            scheduleHolder.itemView.setClickable(false);
            scheduleHolder.itemView.setBackgroundColor(Color.parseColor("#f2f2f2"));

        }

        scheduleHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
                onItemClickListner.onClick(scheduleHolder.booking.getId(),scheduleHolder.booking.getTime(),scheduleHolder.booking.getDate());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvInfo;
        private Booking booking;
        private TextView colorStatus;


        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvInfo = itemView.findViewById(R.id.tvInfo);
            colorStatus =itemView.findViewById(R.id.colorStatus);

        }
    }

}
