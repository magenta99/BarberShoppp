package com.example.barbershop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.activity.BaseFragment;
import com.example.barbershop.adapter.ProductCartAdapter;
import com.example.barbershop.dao.ProductCartDAO;
import com.example.barbershop.model.ProductCart;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.barbershop.adapter.ProductAdapter.decimalFormat;

public class CartFragment extends BaseFragment {
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
    private ImageButton btnBackCart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnBackCart = view.findViewById(R.id.btnBackCart);
        edtFullNameFinal = view.findViewById(R.id.edtFullNameFinal);
        edtPhoneFinal = view.findViewById(R.id.edtPhoneFinal);
        edtAddressFinal = view.findViewById(R.id.edtAddressFinal);
        btnOrderInCart = view.findViewById(R.id.btnOrderInCart);
        tvSumPrice = view.findViewById(R.id.tvSumPrice);
        linearLayoutManager = new LinearLayoutManager(getContext());
        productCartDAO = new ProductCartDAO(getContext());
        productCartList = new ArrayList<>();
        productCartList = productCartDAO.getAllProductCart();
        recyclerView = view.findViewById(R.id.rvCartProduct);
        productCartAdapter = new ProductCartAdapter(getContext(), productCartList);
        recyclerView.setAdapter(productCartAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        btnBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopFragment nextFrag = new ShopFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

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
                            int sumPrice = productCart.PRODUCT_CART_PRICE;
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
                                            CartFragment nextFrag = new CartFragment();
                                            getActivity().getSupportFragmentManager().beginTransaction()
                                                    .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                                                    .addToBackStack(null)
                                                    .commit();
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

