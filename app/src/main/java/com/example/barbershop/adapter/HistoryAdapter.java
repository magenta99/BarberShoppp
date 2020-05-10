package com.example.barbershop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authenticationsms.R;
import com.example.barbershop.model.Booking;
import com.example.barbershop.model.History;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    int selectedPosition = -1;

    List<History> historyList;
    Context context;

    public HistoryAdapter(Context context, List<History> historyList) {
        this.historyList = historyList;
        this.context = context;
    }

    onItemClickListner onItemClickListner;

    public void setOnItemClickListner() {
        setOnItemClickListner();
    }

    public void setOnItemClickListner(HistoryAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface onItemClickListner {
        void onClick(String str1, String str2, String str3);
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryHolder historyHolder, final int position) {
        historyHolder.history = historyList.get(position);
        final String urlImage = historyHolder.history.getImageHistory();
        try{
            Picasso.get().load(urlImage).into(historyHolder.imgImageHistory);
        }catch (Exception e){

        }
        historyHolder.tvTimeHistory.setText("Thời gian: " + historyHolder.history.getTimeHistory() + " - " + historyHolder.history.getDateHistory());
        historyHolder.tvLocationHistory.setText("Cơ sở: "+ historyHolder.history.getLocationHistory());
        historyHolder.tvServiceHistory.setText("Dịch vụ: "+ historyHolder.history.getServiceHistory());
        historyHolder.tvStylistHistory.setText("Stylist: "+ historyHolder.history.getStylistHistory());

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        private ImageView imgImageHistory;
        private TextView tvLocationHistory;
        private TextView tvTimeHistory;
        private TextView tvStylistHistory;
        private TextView tvServiceHistory;
        private History history;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            imgImageHistory = itemView.findViewById(R.id.imgImageHistory);
            tvLocationHistory = itemView.findViewById(R.id.tvLocationHistory);
            tvTimeHistory = itemView.findViewById(R.id.tvTimeHistory);
            tvStylistHistory = itemView.findViewById(R.id.tvStylistHistory);
            tvServiceHistory = itemView.findViewById(R.id.tvServiceHistory);

        }
    }

}
