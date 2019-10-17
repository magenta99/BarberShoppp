package com.example.barbershop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.authenticationsms.R;
import com.example.barbershop.adapter.ProductCartAdapter;
import com.example.barbershop.dao.ProductCartDAO;
import com.example.barbershop.model.ProductCart;

import java.util.ArrayList;
import java.util.List;

import static com.example.barbershop.adapter.ProductAdapter.decimalFormat;

public class CartActivity extends AppCompatActivity {
    ProductCartDAO productCartDAO;
    ProductCartAdapter productCartAdapter;
    LinearLayoutManager linearLayoutManager;
    List<ProductCart> productCartList;
    RecyclerView recyclerView;
    TextView tvSumPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Giỏ hàng");
        initView();
    }

    private void initView(){
        tvSumPrice = findViewById(R.id.tvSumPrice);
        linearLayoutManager = new LinearLayoutManager(this);
        productCartDAO = new ProductCartDAO(this);
        productCartList = new ArrayList<>();
        productCartList = productCartDAO.getAllProductCart();
        recyclerView = findViewById(R.id.rvCartProduct);
        productCartAdapter = new ProductCartAdapter(this,productCartList);
        recyclerView.setAdapter(productCartAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        productCartAdapter.notifyDataSetChanged();

        double tongtien = productCartDAO.getTongTien();
        if (tvSumPrice != null)
            tvSumPrice.setText(decimalFormat.format(tongtien));
    }
}
