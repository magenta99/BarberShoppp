package com.example.barbershop.adapter;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authenticationsms.R;
import com.example.barbershop.model.Stylist;

import java.util.List;

public class StylistAdapter extends RecyclerView.Adapter<StylistAdapter.StylistHolder> {
    int selectedPosition = -1;

    List<Stylist> stylistList;
    Context context;

    public StylistAdapter(List<Stylist> stylistList, Context context) {
        this.stylistList = stylistList;
        this.context = context;
    }

    onItemClickListner onItemClickListner;

    public void setOnItemClickListner() {
        setOnItemClickListner();
    }

    public void setOnItemClickListner(StylistAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface onItemClickListner{
        void onClick(String str);//pass your object types.
    }

    @NonNull
    @Override
    public StylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stylist_item, parent, false);
        return new StylistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StylistHolder stylistHolder, final int position) {
        if (selectedPosition == position) {
            stylistHolder.itemView.setBackgroundResource(R.color.colorGold);
        } else {
            stylistHolder.itemView.setBackgroundResource(R.color.colorWhite);
        }
        stylistHolder.stylist = stylistList.get(position);
        stylistHolder.nameStylist.setText(stylistHolder.stylist.getNameStylist());
        stylistHolder.ratingBar.setRating(Float.valueOf(stylistHolder.stylist.ratingStylist));

        stylistHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
                onItemClickListner.onClick(stylistHolder.stylist.getNameStylist());
            }
        });
    }

    @Override
    public int getItemCount() {
        return stylistList.size();
    }

    public class StylistHolder extends RecyclerView.ViewHolder {
        private TextView nameStylist;
        private RatingBar ratingBar;
        private Stylist stylist;

        public StylistHolder(@NonNull View itemView) {
            super(itemView);
            nameStylist = itemView.findViewById(R.id.nameStylist);
            ratingBar = itemView.findViewById(R.id.ratingBar);
//            tvDistance = itemView.findViewById(R.id.tvDistance);

        }
    }


}
