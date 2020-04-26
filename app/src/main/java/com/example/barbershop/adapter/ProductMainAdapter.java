package com.example.barbershop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.activity.DetailProductActivity;
import com.example.barbershop.model.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ProductMainAdapter extends RecyclerView.Adapter<ProductMainAdapter.ProductHolder> {
    Context context;
    List<Product> productList;

    public ProductMainAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductMainAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_main_item, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductMainAdapter.ProductHolder holder, int position) {
        holder.product = productList.get(position);
        final String urlImage = holder.product.getImageProduct();
        Picasso.get().load(urlImage).into(holder.imgProduct);
        holder.tvNameProduct.setText(holder.product.getNameProduct());
        holder.tvPriceProduct.setText(decimalFormat.format(Integer.parseInt(holder.product.getPriceProduct())));
        holder.ratingProduct.setRating(Float.valueOf(holder.product.getRatingProduct()));
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), DetailProductActivity.class);
                intent.putExtra("urlImage", holder.product.getImageProduct());
                intent.putExtra("nameProduct", holder.product.getNameProduct());
                intent.putExtra("priceProduct", holder.product.getPriceProduct());
                intent.putExtra("descriptionProduct", holder.product.getDescriptionProduct());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView tvNameProduct;
        private TextView tvPriceProduct;
        private Product product;
        private RatingBar ratingProduct;


        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProductMain);
            tvNameProduct = itemView.findViewById(R.id.tvNameProductMain);
            tvPriceProduct = itemView.findViewById(R.id.tvPriceProductMain);
            ratingProduct = itemView.findViewById(R.id.ratingProductMain);
        }
    }

    public static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

}
