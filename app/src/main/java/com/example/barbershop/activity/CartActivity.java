package com.example.barbershop.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.authenticationsms.R;
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
    private EditText edtFullNameFinal;
    private EditText edtPhoneFinal;
    private EditText edtAddressFinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Giỏ hàng");
        initView();
    }

    private void initView() {
        edtFullNameFinal = findViewById(R.id.edtFullNameFinal);
        edtPhoneFinal = findViewById(R.id.edtPhoneFinal);
        edtAddressFinal = findViewById(R.id.edtAddressFinal);
        btnOrderInCart = findViewById(R.id.btnOrderInCart);
        tvSumPrice = findViewById(R.id.tvSumPrice);
        linearLayoutManager = new LinearLayoutManager(this);
        productCartDAO = new ProductCartDAO(this);
        productCartList = new ArrayList<>();
        productCartList = productCartDAO.getAllProductCart();
        recyclerView = findViewById(R.id.rvCartProduct);
        productCartAdapter = new ProductCartAdapter(this, productCartList);
        recyclerView.setAdapter(productCartAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        double tongtien = productCartDAO.getTongTien();
        if (tvSumPrice != null)
            tvSumPrice.setText(decimalFormat.format(tongtien));

        btnOrderInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String fullname = edtFullNameFinal.getText().toString();
                    String phoneNumber = edtPhoneFinal.getText().toString();
                    String address = edtAddressFinal.getText().toString();
                    if (productCartList.size() == 0) {
                        showMessegeWarning("Bạn không có sản phẩm nào");
                    } else if (fullname.isEmpty()) {
                        showMessegeWarning("Vui lòng nhập họ tên");
                    } else if (phoneNumber.isEmpty()) {
                        showMessegeWarning("Vui lòng nhập số điện thoại");
                    } else if (address.isEmpty()) {
                        showMessegeWarning("Vui lòng nhập địa chỉ");
                    } else {
                        for (int i = 0; i <= productCartList.size(); i++) {
                            ProductCart productCart = productCartList.get(i);
                            int sumPrice = productCart.PRODUCT_CART_PRICE ;
                            AndroidNetworking.post("http://barber123.herokuapp.com/oder")
                                    .addBodyParameter("imageProduct", productCart.PRODUCT_CART_IMAGE)
                                    .addBodyParameter("nameProduct", productCart.PRODUCT_CART_NAME)
                                    .addBodyParameter("amountProduct", Integer.toString(productCart.PRODUCT_CART_NUMBER))
                                    .addBodyParameter("priceProduct", Integer.toString(sumPrice))
                                    .addBodyParameter("fullName", fullname)
                                    .addBodyParameter("phoneNumber", phoneNumber)
                                    .addBodyParameter("address", address)
                                    .setTag("test")
                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            showMessegeSuccess("Đặt hàng thành công");
                                            edtFullNameFinal.setText("");
                                            edtAddressFinal.setText("");
                                            edtPhoneFinal.setText("");
                                            productCartDAO.deleteCart();
                                            productCartAdapter.notifyDataSetChanged();
                                            (CartActivity.this).recreate();
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



            }
        });
    }

}

