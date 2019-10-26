package com.example.barbershop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.BaseActivity;
import com.example.barbershop.adapter.ProductCartAdapter;
import com.example.barbershop.dao.ProductCartDAO;
import com.example.barbershop.model.ProductCart;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.example.barbershop.adapter.ProductAdapter.decimalFormat;

public class CartActivity extends BaseActivity {
    ProductCartDAO productCartDAO;
    ProductCartAdapter productCartAdapter;
    LinearLayoutManager linearLayoutManager;
    List<ProductCart> productCartList;
    RecyclerView recyclerView;
    TextView tvSumPrice;
    Button btnOrderInCart;
    EditText edtAdressInCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Giỏ hàng");
        initView();
    }

    private void initView() {
        btnOrderInCart = findViewById(R.id.btnOrderInCart);
        edtAdressInCart = findViewById(R.id.edtAdressInCart);
        tvSumPrice = findViewById(R.id.tvSumPrice);
        linearLayoutManager = new LinearLayoutManager(this);
        productCartDAO = new ProductCartDAO(this);
        productCartList = new ArrayList<>();
        productCartList = productCartDAO.getAllProductCart();
        recyclerView = findViewById(R.id.rvCartProduct);
        productCartAdapter = new ProductCartAdapter(this, productCartList);
        recyclerView.setAdapter(productCartAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        productCartAdapter.notifyDataSetChanged();
        final String adress = edtAdressInCart.getText().toString();

        double tongtien = productCartDAO.getTongTien();
        if (tvSumPrice != null)
            tvSumPrice.setText(decimalFormat.format(tongtien));

        btnOrderInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(productCartList.size() == 0){
                        showMessegeWarning("Bạn không có sản phẩm nào");
                    }else {
                        for (int i = 0; i <= productCartList.size(); i++) {
                            ProductCart productCart = productCartList.get(i);
                            int sumPrice = productCart.PRODUCT_CART_PRICE * productCart.PRODUCT_CART_NUMBER;
                            AndroidNetworking.post("http://barber-shopp.herokuapp.com/order")
                                    .addBodyParameter("imageProduct", productCart.PRODUCT_CART_IMAGE)
                                    .addBodyParameter("nameProduct", productCart.PRODUCT_CART_NAME)
                                    .addBodyParameter("amountProduct", Integer.toString(productCart.PRODUCT_CART_NUMBER))
                                    .addBodyParameter("priceProduct", Integer.toString(sumPrice))
                                    .addBodyParameter("fullName", "Huy Anh")
                                    .addBodyParameter("phoneNumber", getRootUsername())
                                    .addBodyParameter("address", adress)
                                    .setTag("test")
                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                        }

                                        @Override
                                        public void onError(ANError error) {
                                            // handle error
                                        }
                                    });
                        }
                    }
                } catch (Exception e) {

                }
                Toasty.success(CartActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                productCartDAO.deleteCart();
                ((CartActivity.this)).recreate();
            }
        });

    }

    private String getRootUsername() {
        String name;
        name = getSharedPreferences("USER", MODE_PRIVATE).getString("NAME", null);
        return name;
    }
}