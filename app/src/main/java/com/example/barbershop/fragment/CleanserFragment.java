package com.example.barbershop.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.activity.BaseFragment;
import com.example.barbershop.adapter.ProductAdapter;
import com.example.barbershop.model.Product;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CleanserFragment extends BaseFragment {
    private List<Product> cleanserList;
    private ProductAdapter productAdapter;
    private RecyclerView rvCleanser;
    private GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Spinner spnSortCleanser;
    private List<String> spList;
    private ArrayAdapter<String> spAdapter;
    private ImageButton btnBackCleanser;
    private ShimmerFrameLayout shimmerFrameLayoutCleanser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cleanser_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        shimmerFrameLayoutCleanser = view.findViewById(R.id.shimmerFrameLayoutCleanser);
        cleanserList = new ArrayList<>();
        rvCleanser = view.findViewById(R.id.rvCleanser);
        swipeRefreshLayout = view.findViewById(R.id.srlShampoo);
        btnBackCleanser = view.findViewById(R.id.btnBackCleanser);
        spnSortCleanser = view.findViewById(R.id.spnCleanser);
        spList = new ArrayList<>();
        spList.add("Sắp xếp sản phẩm");
        spList.add("Giá tăng dần");
        spList.add("Giá giảm dần");
        spAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spList);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSortCleanser.setAdapter(spAdapter);

        spnSortCleanser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ten = spList.get(position);
                if (ten.equals("Sắp xếp sản phẩm")) {
                    cleanserList.clear();
                    loadCleanserProduct();
                } else if (ten.equals("Giá tăng dần")) {
                    cleanserList.clear();
                    loadCleanserProductASC();
                } else {
                    cleanserList.clear();
                    loadCleanserProductDSC();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    cleanserList.clear();
                    loadCleanserProduct();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } catch (NullPointerException err) {

        } catch (Exception e) {
            Log.e("Lỗi", "" + e);
        }

        btnBackCleanser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopFragment nextFrag = new ShopFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    private void loadCleanserProduct() {
        AndroidNetworking.get("http://barber123.herokuapp.com/result?id={typeProduct}")
                .addPathParameter("typeProduct", "Cleanser")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(final JSONArray response) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    for (int i = 0; i < response.length(); i++) {
                                        shimmerFrameLayoutCleanser.stopShimmer();
                                        shimmerFrameLayoutCleanser.setVisibility(View.GONE);
                                        rvCleanser.setVisibility(View.VISIBLE);
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        String idProduct = jsonObject.getString("_id");
                                        String imageProduct = jsonObject.getString("imageProduct");
                                        String nameProduct = jsonObject.getString("nameProduct");
                                        String priceProduct = jsonObject.getString("priceProduct");
                                        String typeProduct = jsonObject.getString("typeProduct");
                                        String descriptionProduct = jsonObject.getString("descriptionProduct");
                                        String ratingProduct = jsonObject.getString("ratingProduct");
                                        cleanserList.add(new Product(idProduct, imageProduct, nameProduct, priceProduct, typeProduct, descriptionProduct,ratingProduct));
                                    }
                                    productAdapter = new ProductAdapter(getContext(), cleanserList);
                                    gridLayoutManager = new GridLayoutManager(getContext(),2);
                                    rvCleanser.setAdapter(productAdapter);
                                    rvCleanser.setLayoutManager(gridLayoutManager);
                                    rvCleanser.setHasFixedSize(true);
                                    rvCleanser.setNestedScrollingEnabled(false);
                                    rvCleanser.scheduleLayoutAnimation();
                                    productAdapter.notifyDataSetChanged();
                                    gridLayoutManager.setAutoMeasureEnabled(true);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },1500);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

    }
    private void loadCleanserProductASC() {
        AndroidNetworking.get("https://barber123.herokuapp.com/result/asc?id={typeProduct}")
                .addPathParameter("typeProduct", "Cleanser")
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
                                cleanserList.add(new Product(idProduct, imageProduct, nameProduct, priceProduct, typeProduct, descriptionProduct,ratingProduct));
                            }
                            productAdapter = new ProductAdapter(getContext(), cleanserList);
                            gridLayoutManager = new GridLayoutManager(getContext(),2);
                            rvCleanser.setAdapter(productAdapter);
                            rvCleanser.setLayoutManager(gridLayoutManager);
                            rvCleanser.setHasFixedSize(true);
                            rvCleanser.setNestedScrollingEnabled(false);
                            rvCleanser.scheduleLayoutAnimation();
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
    private void loadCleanserProductDSC() {
        AndroidNetworking.get("https://barber123.herokuapp.com/result/dsc?id={typeProduct}")
                .addPathParameter("typeProduct", "Cleanser")
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
                                cleanserList.add(new Product(idProduct, imageProduct, nameProduct, priceProduct, typeProduct, descriptionProduct,ratingProduct));
                            }
                            productAdapter = new ProductAdapter(getContext(), cleanserList);
                            gridLayoutManager = new GridLayoutManager(getContext(),2);
                            rvCleanser.setAdapter(productAdapter);
                            rvCleanser.setLayoutManager(gridLayoutManager);
                            rvCleanser.setHasFixedSize(true);
                            rvCleanser.setNestedScrollingEnabled(false);
                            rvCleanser.scheduleLayoutAnimation();
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

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayoutCleanser.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayoutCleanser.stopShimmer();

    }
}
