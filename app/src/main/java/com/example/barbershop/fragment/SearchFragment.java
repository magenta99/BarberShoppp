package com.example.barbershop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

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

public class SearchFragment extends BaseFragment {
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private RecyclerView rvSearchProduct;
    private GridLayoutManager gridLayoutManager;
    private AutoCompleteTextView actvNameProduct;
    private Button btnSearchProduct;
    private ImageButton btnBack;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        productList = new ArrayList<>();
        btnBack = view.findViewById(R.id.btnBack);
        btnSearchProduct = view.findViewById(R.id.btnSearchProduct);
        rvSearchProduct = view.findViewById(R.id.rvSearchProduct);
        actvNameProduct = view.findViewById(R.id.actvNameProduct);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, CATEGORY);
        actvNameProduct.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopFragment nextFrag= new ShopFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productList.clear();
                String nameProduct = actvNameProduct.getEditableText().toString();
                loadShampooProduct(nameProduct);
                showMessage(nameProduct);
            }
        });
    }

    private void loadShampooProduct(String nameProduct) {
        AndroidNetworking.get("https://barber123.herokuapp.com/searchProduct?name={nameProduct}")
                .addPathParameter("nameProduct", nameProduct)
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
                                productList.add(new Product(idProduct, imageProduct, nameProduct, priceProduct, typeProduct, descriptionProduct,ratingProduct));
                            }
                            productAdapter = new ProductAdapter(getContext(), productList);
                            gridLayoutManager = new GridLayoutManager(getContext(),2);
                            rvSearchProduct.setAdapter(productAdapter);
                            rvSearchProduct.setLayoutManager(gridLayoutManager);
                            rvSearchProduct.setHasFixedSize(true);
                            rvSearchProduct.setNestedScrollingEnabled(false);
                            rvSearchProduct.scheduleLayoutAnimation();
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

    private static final String[] CATEGORY = new String[]{"Sáp Bed Head for Men-Matt Separation", "Sáp Glanzen Clay Chính Hãng 100g", "Sáp By Vilain Gold Digger Chính Hãng", "Sáp Kevin Murphy - Rough Rider", "Sáp Glanzen Prime - Floral Hương Hoa"};


}
