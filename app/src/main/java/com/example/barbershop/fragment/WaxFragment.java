package com.example.barbershop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import com.example.barbershop.adapter.ProductMainAdapter;
import com.example.barbershop.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WaxFragment extends BaseFragment {
    private List<Product> waxList;
    private ProductAdapter productAdapter;
    private RecyclerView rvWax;
    private GridLayoutManager gridLayoutManager;
    private Spinner spnSortWax;
    private List<String> spList;
    private ArrayAdapter<String> spAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wax_fragment, container, false);

        initView(view);
        return view;
    }

    private void initView(View view){
        waxList = new ArrayList<>();

        rvWax = view.findViewById(R.id.rvWax);
        spnSortWax = view.findViewById(R.id.spnSortWax);
        spList = new ArrayList<>();
        spList.add("Sắp xếp sản phẩm");
        spList.add("Giá tăng dần");
        spList.add("Giá giảm dần");
        spAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spList);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSortWax.setAdapter(spAdapter);

        spnSortWax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ten = spList.get(position);
                if (ten.equals("Sắp xếp sản phẩm")) {
                    waxList.clear();
                    loadWaxProduct();
                } else if (ten.equals("Giá tăng dần")) {
                    waxList.clear();
                    loadWaxProductASC();
                } else {
                    waxList.clear();
                    loadWaxProductDSC();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadWaxProduct() {
        AndroidNetworking.get("https://barber-shopp.herokuapp.com/result?id={typeProduct}")
                .addPathParameter("typeProduct", "Sáp")
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
                                waxList.add(new Product(idProduct, imageProduct, nameProduct, priceProduct, typeProduct, descriptionProduct,ratingProduct));
                            }
                            productAdapter = new ProductAdapter(getContext(), waxList);
                            gridLayoutManager = new GridLayoutManager(getContext(),2);
                            rvWax.setAdapter(productAdapter);
                            rvWax.setLayoutManager(gridLayoutManager);
                            rvWax.setHasFixedSize(true);
                            rvWax.setNestedScrollingEnabled(false);
                            rvWax.scheduleLayoutAnimation();
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
    private void loadWaxProductASC() {
        AndroidNetworking.get("https://barber-shopp.herokuapp.com/result/asc?id={typeProduct}")
                .addPathParameter("typeProduct", "Sáp")
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
                                waxList.add(new Product(idProduct, imageProduct, nameProduct, priceProduct, typeProduct, descriptionProduct,ratingProduct));
                            }
                            productAdapter = new ProductAdapter(getContext(), waxList);
                            gridLayoutManager = new GridLayoutManager(getContext(),2);
                            rvWax.setAdapter(productAdapter);
                            rvWax.setLayoutManager(gridLayoutManager);
                            rvWax.setHasFixedSize(true);
                            rvWax.setNestedScrollingEnabled(false);
                            rvWax.scheduleLayoutAnimation();
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
    private void loadWaxProductDSC() {
        AndroidNetworking.get("https://barber-shopp.herokuapp.com/result/dsc?id={typeProduct}")
                .addPathParameter("typeProduct", "Sáp")
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
                                waxList.add(new Product(idProduct, imageProduct, nameProduct, priceProduct, typeProduct, descriptionProduct,ratingProduct));
                            }
                            productAdapter = new ProductAdapter(getContext(), waxList);
                            gridLayoutManager = new GridLayoutManager(getContext(),2);
                            rvWax.setAdapter(productAdapter);
                            rvWax.setLayoutManager(gridLayoutManager);
                            rvWax.setHasFixedSize(true);
                            rvWax.setNestedScrollingEnabled(false);
                            rvWax.scheduleLayoutAnimation();
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
