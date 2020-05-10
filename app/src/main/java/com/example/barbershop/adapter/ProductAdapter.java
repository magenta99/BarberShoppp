package com.example.barbershop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.activity.DetailProductActivity;
import com.example.barbershop.fragment.ShopFragment;
import com.example.barbershop.model.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    Context context;
    List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.ProductHolder holder, int position) {
        holder.product = productList.get(position);
        final String urlImage = holder.product.getImageProduct();
        Picasso.get().load(urlImage).into(holder.imgProduct);
        holder.tvNameProduct.setText(holder.product.getNameProduct());
        holder.tvPriceProduct.setText(decimalFormat.format(Integer.parseInt(holder.product.getPriceProduct())));
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), DetailProductActivity.class);
                intent.putExtra("urlImage", holder.product.getImageProduct());
                intent.putExtra("nameProduct", holder.product.getNameProduct());
                intent.putExtra("priceProduct", holder.product.getPriceProduct());
                intent.putExtra("descriptionProduct", holder.product.getDescriptionProduct());
                context.startActivity(intent);
            }
        });

        holder.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                View mView = View.inflate(context, R.layout.order_item, null);
                mBuilder.setView(mView);
                mBuilder.setCancelable(true);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                final EditText edtEditFullName;
                final EditText edtEditPhoneNumber;
                final EditText edtEditAddress;
                final TextView tvSumPriceOrder;
                final TextView tvNumberOrder;
                final Button btnDecreaseNumberOrder;
                final Button btnIncreaseNumberOrder;
                Button btnOrder;
                Button btnCancel;

                edtEditFullName = mView.findViewById(R.id.edtEditFullName);
                edtEditPhoneNumber = mView.findViewById(R.id.edtEditPhoneNumber);
                edtEditAddress = mView.findViewById(R.id.edtEditAddress);
                tvSumPriceOrder = mView.findViewById(R.id.tvSumPriceOrder);
                tvNumberOrder = mView.findViewById(R.id.tvNumberOrder);
                btnOrder = mView.findViewById(R.id.btnOrder);
                btnCancel = mView.findViewById(R.id.btnCancel);
                btnDecreaseNumberOrder = mView.findViewById(R.id.btnDecreaseNumberOrder);
                btnIncreaseNumberOrder = mView.findViewById(R.id.btnIncreaseNumberOrder);


                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                int i = Integer.parseInt(tvNumberOrder.getText().toString());
                int sumPrice = i * Integer.parseInt(holder.product.getPriceProduct());
                tvSumPriceOrder.setText(decimalFormat.format(sumPrice));

                btnIncreaseNumberOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = Integer.parseInt(tvNumberOrder.getText().toString());
                        i++;
                        tvNumberOrder.setText(Integer.toString(i));
                        int sumPrice = i * Integer.parseInt(holder.product.getPriceProduct());
                        tvSumPriceOrder.setText(decimalFormat.format(sumPrice));
                    }
                });

                btnDecreaseNumberOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = Integer.parseInt(tvNumberOrder.getText().toString());
                        if (i < 1) {
                            Toasty.warning(context, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                        } else {
                            i--;
                            tvNumberOrder.setText(Integer.toString(i));
                            int sumPrice = i * Integer.parseInt(holder.product.getPriceProduct());
                            tvSumPriceOrder.setText(decimalFormat.format(sumPrice));
                        }
                    }
                });


                btnOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String fullName = edtEditFullName.getText().toString();
                        String phoneNumber = edtEditPhoneNumber.getText().toString();
                        String address = edtEditAddress.getText().toString();
                        String amount = tvNumberOrder.getText().toString();
                        final int sumPrice = Integer.parseInt(amount) * Integer.parseInt(holder.product.getPriceProduct());

                        if (fullName.isEmpty()) {
                            Toasty.warning(context, "Không để trống họ tên", Toast.LENGTH_SHORT).show();
                        } else if (amount.isEmpty() || Integer.parseInt(amount) <= 0) {
                            Toasty.warning(context, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                        } else if (phoneNumber.length() > 10 || phoneNumber.length() < 10 || phoneNumber.isEmpty() | phoneNumber.length() < 0) {
                            Toasty.warning(context, "Số điện thoại phải có 10 số", Toast.LENGTH_SHORT).show();
                        } else if (address.isEmpty()) {
                            Toasty.warning(context, "Không để trống địa chỉ", Toast.LENGTH_SHORT).show();
                        } else {
                            AndroidNetworking.post("http://barber123.herokuapp.com/oder")
                                    .addBodyParameter("imageProduct", urlImage)
                                    .addBodyParameter("nameProduct", holder.product.getNameProduct())
                                    .addBodyParameter("amountProduct", amount)
                                    .addBodyParameter("priceProduct", Integer.toString(sumPrice))
                                    .addBodyParameter("fullName", fullName)
                                    .addBodyParameter("phoneNumber", phoneNumber)
                                    .addBodyParameter("address", address)
                                    .setTag("test")
                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                        }

                                        @Override
                                        public void onError(ANError error) {
                                        }
                                    });
                            Toasty.success(context, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView tvNameProduct;
        private TextView tvPriceProduct;
        private Product product;
        private Button btnBuyNow;


        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvPriceProduct = itemView.findViewById(R.id.tvPriceProduct);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);


        }
    }

    public static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

}
