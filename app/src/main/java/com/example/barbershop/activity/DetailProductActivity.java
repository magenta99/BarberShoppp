package com.example.barbershop.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authenticationsms.R;
import com.example.barbershop.model.ProductCart;
import com.example.barbershop.dao.ProductCartDAO;
import com.squareup.picasso.Picasso;

import static com.example.barbershop.database.Constants.decimalFormat;

public class DetailProductActivity extends BaseActivity {
    private ImageView imgProductDetail;
    private TextView tvNameProductDetail;
    private TextView tvPriceProductDetail;
    private TextView tvDescriptionDetail;
    private Button btnAddProduct;
    private ProductCartDAO productCartDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initView();
    }

    public void initView() {
        imgProductDetail = findViewById(R.id.imgProductDetail);
        tvNameProductDetail = findViewById(R.id.tvNameProductDetail);
        tvPriceProductDetail = findViewById(R.id.tvPriceProductDetail);
        tvDescriptionDetail = findViewById(R.id.tvDescriptionDetail);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        productCartDAO = new ProductCartDAO(this);

        Intent intent = getIntent();
        final String urlImage = intent.getStringExtra("urlImage");
        final String nameProduct = intent.getStringExtra("nameProduct");
        final String priceProduct = intent.getStringExtra("priceProduct");
        String descriptionProduct = intent.getStringExtra("descriptionProduct");
        Picasso.get().load(urlImage).into(imgProductDetail);
        if (tvNameProductDetail != null)
            tvNameProductDetail.setText(nameProduct);
        if (tvPriceProductDetail != null)
            tvPriceProductDetail.setText(decimalFormat.format(Integer.parseInt(priceProduct)));
        if (tvDescriptionDetail != null)
            tvDescriptionDetail.setText(descriptionProduct);
        getSupportActionBar().setTitle(nameProduct);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (productCartDAO.checkProduct(nameProduct) == true) {
                        showMessegeWarning("Sản phẩm đã tồn tại");
                    } else if (productCartDAO.insertProductCart
                            (new ProductCart(nameProduct, 1, Integer.parseInt(priceProduct), urlImage)) >= 0) {
                        showMessegeSuccess("Thêm vào giỏ hàng thành công");
                    }
                } catch (Exception e) {
                    Toast.makeText(DetailProductActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        if ( getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }
}
