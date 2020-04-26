package com.example.barbershop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.activity.BaseFragment;
import com.example.barbershop.adapter.ProductAdapter;
import com.example.barbershop.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BeardFragment111 extends BaseFragment {
    private List<Product> beardList;
    private ProductAdapter productAdapter;
    private RecyclerView rvBread;
    private GridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.beard_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        beardList = new ArrayList<>();
        rvBread = view.findViewById(R.id.rvBread);
        loadBeardProduct();
    }

    private void loadBeardProduct() {
        AndroidNetworking.get("https://barber-shopp.herokuapp.com/result?id={typeProduct}")
                .addPathParameter("typeProduct", "Beard")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.HIGH)
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
                                String ratingProduct = jsonObject.getString("ratingProduct");
                                beardList.add(new Product(idProduct, imageProduct, nameProduct, priceProduct, typeProduct, descriptionProduct,ratingProduct));
                            }
                            productAdapter = new ProductAdapter(getContext(), beardList);
                            gridLayoutManager = new GridLayoutManager(getContext(),2);
                            rvBread.setAdapter(productAdapter);
                            rvBread.setLayoutManager(gridLayoutManager);
                            rvBread.setHasFixedSize(true);
                            rvBread.setNestedScrollingEnabled(false);
                            rvBread.scheduleLayoutAnimation();
                            productAdapter.notifyDataSetChanged();
                            gridLayoutManager.setAutoMeasureEnabled(true);
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
}
