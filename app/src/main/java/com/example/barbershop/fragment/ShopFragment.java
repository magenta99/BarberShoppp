package com.example.barbershop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.activity.CartActivity;
import com.example.barbershop.adapter.ProductAdapter;
import com.example.barbershop.dao.ProductCartDAO;
import com.example.barbershop.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {
    private ProductAdapter productAdapter;
    private GridLayoutManager gridLayoutManager;
    String url = "https://barber-shopp.herokuapp.com/result";
    private ViewFlipper viewFlipper;
    private RecyclerView rvProduct;
    private List<Product> productList;
    private ImageButton btnCart;
    private TextView tvProductCart;
    private ProductCartDAO productCartDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        init(view);
        int images[] = {R.drawable.shop1, R.drawable.shop2, R.drawable.shop33, R.drawable.shop44};
        for (int image : images) {
            flipperImages(image);
        }
        return view;
    }

    private void init(View view) {
        productCartDAO = new ProductCartDAO(getContext());
        tvProductCart = view.findViewById(R.id.tvProductCart);
        btnCart = view.findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });
        viewFlipper = view.findViewById(R.id.vpShopSlider);
        rvProduct = view.findViewById(R.id.rvProduct);
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(), productList);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        rvProduct.setAdapter(productAdapter);
        rvProduct.setLayoutManager(gridLayoutManager);
        rvProduct.hasFixedSize();
        rvProduct.setNestedScrollingEnabled(false);
        productAdapter.notifyDataSetChanged();
        loadProducts();
        tvProductCart.setText(Integer.toString(productCartDAO.getNumberInCart()));

    }


    private void loadProducts() {
        AndroidNetworking.get("https://barber-shopp.herokuapp.com/result/{typeProduct}")
                .addPathParameter("typeProduct", "Sáp")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String idProduct = jsonObject.getString("_id");
                                String imageProduct = jsonObject.getString("imageProduct");
                                String nameProduct = jsonObject.getString("nameProduct");
                                String priceProduct = jsonObject.getString("priceProduct");
                                String typeProduct = jsonObject.getString("typeProduct");
                                String descriptionProduct = jsonObject.getString("descriptionProduct");
                                productList.add(new Product(idProduct, imageProduct, nameProduct, priceProduct, typeProduct, descriptionProduct));
                            }
                            Toast.makeText(getContext(), ""+productList.size(), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

    }

    private void flipperImages(int image) {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2500);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getContext(), android.R.anim.fade_in);
    }

}