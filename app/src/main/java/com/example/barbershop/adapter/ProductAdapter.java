package com.example.barbershop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authenticationsms.R;
import com.example.barbershop.activity.DetailProductActivity;
import com.example.barbershop.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    Context context;
    List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.ProductHolder holder, int position) {
            holder.product = productList.get(position);
            String urlImage = holder.product.getImageProduct();
            Picasso.get().load(urlImage).into(holder.imgProduct);
            holder.tvNameProduct.setText(holder.product.getNameProduct());
            holder.tvPriceProduct.setText(decimalFormat.format(Integer.parseInt(holder.product.getPriceProduct())));
            holder.imgProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context.getApplicationContext(), DetailProductActivity.class);
                    intent.putExtra("urlImage",holder.product.getImageProduct());
                    intent.putExtra("nameProduct",holder.product.getNameProduct());
                    intent.putExtra("priceProduct",holder.product.getPriceProduct());
                    intent.putExtra("descriptionProduct",holder.product.getDescriptionProduct());
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

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct =  itemView.findViewById(R.id.imgProduct);
            tvNameProduct =  itemView.findViewById(R.id.tvNameProduct);
            tvPriceProduct =  itemView.findViewById(R.id.tvPriceProduct);

        }
    }

    public static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

}
