package com.example.barbershop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

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
import com.example.barbershop.adapter.ProductMainAdapter;
import com.example.barbershop.dao.ProductCartDAO;
import com.example.barbershop.model.Product;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends BaseFragment {
    private ProductMainAdapter productMainAdapter;
    private GridLayoutManager gridLayoutManager;
    String url = "https://barber-shopp.herokuapp.com/result";
    private ViewFlipper viewFlipper;
    private RecyclerView rvWaxMain;
    private RecyclerView rvShampooMain;
    private List<Product> waxList;
    private List<Product> shampooList;
    private ImageButton btnCart;
    private TextView tvProductCart;
    private ProductCartDAO productCartDAO;
    private LinearLayout llWax;
    private LinearLayout llCleanser;
    private LinearLayout llUnderwear;
    private LinearLayout llBeard;
    private Button btnMallSearch;
    private TextView tvSeeMoreWax;
    private TextView tvSeeMoreCleanser;
    private ShimmerFrameLayout shimmerFrameLayoutWaxMain;

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
        shimmerFrameLayoutWaxMain = view.findViewById(R.id.shimmerFrameLayoutWaxMain);
        tvSeeMoreWax = view.findViewById(R.id.tvSeeMoreWax);
        tvSeeMoreCleanser = view.findViewById(R.id.tvSeeMoreCleanser);
        btnMallSearch = view.findViewById(R.id.btnMallSearch);
        llWax = view.findViewById(R.id.llWax);
        llCleanser = view.findViewById(R.id.llCleanser);
        llUnderwear = view.findViewById(R.id.llUnderwear);
        llBeard = view.findViewById(R.id.llBeard);
        productCartDAO = new ProductCartDAO(getContext());
        waxList = new ArrayList<>();
        shampooList = new ArrayList<>();
        tvProductCart = view.findViewById(R.id.tvProductCart);
        btnCart = view.findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartFragment nextFrag = new CartFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        viewFlipper = view.findViewById(R.id.vpShopSlider);
        rvWaxMain = view.findViewById(R.id.rvWaxMain);
        rvShampooMain = view.findViewById(R.id.rvShampooMain);

        //Code start các màn hình chuyên mục sản phẩm
        startNewFragment();
        //Load sản phẩm
        loadWaxProduct();
        loadShampooProduct();
        tvProductCart.setText(Integer.toString(productCartDAO.getNumberInCart()));
    }

    private void loadWaxProduct() {
        AndroidNetworking.get("http://barber123.herokuapp.com/result?id={typeProduct}")
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
                            productMainAdapter = new ProductMainAdapter(getContext(), waxList);
                            gridLayoutManager = new GridLayoutManager(getContext(),1,GridLayoutManager.HORIZONTAL,false);
                            rvWaxMain.setAdapter(productMainAdapter);
                            rvWaxMain.setLayoutManager(gridLayoutManager);
                            rvWaxMain.setHasFixedSize(true);
                            rvWaxMain.setNestedScrollingEnabled(false);
                            rvWaxMain.scheduleLayoutAnimation();
                            productMainAdapter.notifyDataSetChanged();
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
    private void loadShampooProduct() {
        AndroidNetworking.get("http://barber123.herokuapp.com/result?id={typeProduct}")
                .addPathParameter("typeProduct", "Shampoo")
                .addQueryParameter("limit", "3")
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
                                        shimmerFrameLayoutWaxMain.stopShimmer();
                                        shimmerFrameLayoutWaxMain.setVisibility(View.GONE);
                                        rvWaxMain.setVisibility(View.VISIBLE);
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        String idProduct = jsonObject.getString("_id");
                                        String imageProduct = jsonObject.getString("imageProduct");
                                        String nameProduct = jsonObject.getString("nameProduct");
                                        String priceProduct = jsonObject.getString("priceProduct");
                                        String typeProduct = jsonObject.getString("typeProduct");
                                        String descriptionProduct = jsonObject.getString("descriptionProduct");
                                        String ratingProduct = jsonObject.getString("ratingProduct");
                                        shampooList.add(new Product(idProduct, imageProduct, nameProduct, priceProduct, typeProduct, descriptionProduct,ratingProduct));
                                    }
                                    productMainAdapter = new ProductMainAdapter(getContext(), shampooList);
                                    gridLayoutManager = new GridLayoutManager(getContext(),1,GridLayoutManager.HORIZONTAL,false);
                                    rvShampooMain.setAdapter(productMainAdapter);
                                    rvShampooMain.setLayoutManager(gridLayoutManager);
                                    rvShampooMain.setHasFixedSize(true);
                                    rvShampooMain.setNestedScrollingEnabled(false);
                                    rvShampooMain.scheduleLayoutAnimation();
                                    productMainAdapter.notifyDataSetChanged();
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
    private void flipperImages(int image) {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2500);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getContext(), android.R.anim.fade_in);
    }
    private void startNewFragment(){
        llWax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaxFragment nextFrag= new WaxFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        llBeard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeardFragment nextFrag= new BeardFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        llCleanser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CleanserFragment nextFrag= new CleanserFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        llUnderwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShampooFragment nextFrag= new ShampooFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnMallSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFragment nextFrag= new SearchFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        tvSeeMoreWax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaxFragment nextFrag= new WaxFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        tvSeeMoreCleanser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CleanserFragment nextFrag= new CleanserFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayoutWaxMain.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayoutWaxMain.stopShimmer();

    }

}