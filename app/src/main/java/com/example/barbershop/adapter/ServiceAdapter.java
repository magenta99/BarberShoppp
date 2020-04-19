package com.example.barbershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authenticationsms.R;
import com.example.barbershop.model.Service;

import java.text.DecimalFormat;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {
    int selectedPosition = -1;

    List<Service> serviceList;
    Context context;

    public ServiceAdapter(List<Service> serviceList, Context context) {
        this.serviceList = serviceList;
        this.context = context;
    }

    onItemClickListner onItemClickListner;

    public void setOnItemClickListner() {
        setOnItemClickListner();
    }

    public void setOnItemClickListner(ServiceAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface onItemClickListner {
        void onClick(String str,String str2);//pass your object types.
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_item, parent, false);
        return new ServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceHolder serviceHolder, final int position) {
        if (selectedPosition == position) {
            serviceHolder.itemView.setBackgroundResource(R.color.colorGold);
        } else {
            serviceHolder.itemView.setBackgroundResource(R.color.colorWhite);
        }
        serviceHolder.service = serviceList.get(position);
        serviceHolder.nameService.setText(serviceHolder.service.getNameService());
        serviceHolder.detailService.setText(serviceHolder.service.getDetailService());
        serviceHolder.priceService.setText(decimalFormat.format(Integer.parseInt(serviceHolder.service.getPriceService()))+" VND");

        serviceHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
                onItemClickListner.onClick(serviceHolder.service.getNameService(),serviceHolder.service.getPriceService());
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ServiceHolder extends RecyclerView.ViewHolder {
        private TextView nameService;
        private TextView priceService;
        private TextView detailService;
        private Service service;


        public ServiceHolder(@NonNull View itemView) {
            super(itemView);
            nameService = itemView.findViewById(R.id.nameService);
            priceService = itemView.findViewById(R.id.priceService);
            detailService = itemView.findViewById(R.id.detailService);
//            tvDistance = itemView.findViewById(R.id.tvDistance);

        }
    }

    public static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");


}
