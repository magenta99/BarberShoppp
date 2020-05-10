package com.example.barbershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authenticationsms.R;
import com.example.barbershop.dao.ProductCartDAO;
import com.example.barbershop.fragment.BeardFragment;
import com.example.barbershop.fragment.CartFragment;
import com.example.barbershop.model.ProductCart;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.example.barbershop.database.Constants.decimalFormat;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ProductCartHolder> {
    Context context;
    List<ProductCart> productCartList;

    public ProductCartAdapter(Context context, List<ProductCart> productCartList) {
        this.context = context;
        this.productCartList = productCartList;
    }

    @NonNull
    @Override
    public ProductCartAdapter.ProductCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ProductCartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductCartAdapter.ProductCartHolder productCartHolder, final int position) {
        productCartHolder.productCart = productCartList.get(position);
        productCartHolder.tvNameCartProduct.setText(productCartHolder.productCart.PRODUCT_CART_NAME);
        productCartHolder.tvPriceCartProduct.setText(decimalFormat.format(productCartHolder.productCart.PRODUCT_CART_PRICE));
        Picasso.get().load(productCartHolder.productCart.PRODUCT_CART_IMAGE).into(productCartHolder.imgImageCartProduct);
        productCartHolder.tvNumber.setText(String.valueOf(productCartHolder.productCart.PRODUCT_CART_NUMBER));
        productCartHolder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductCartDAO productCartDAO = new ProductCartDAO(context);
                productCartHolder.productCart.PRODUCT_CART_NUMBER++;
                productCartDAO.updateProductCartAmount(new ProductCart("", productCartHolder.productCart.PRODUCT_CART_NUMBER, 0, ""), productCartHolder.productCart.PRODUCT_CART_NAME);
                productCartHolder.tvNumber.setText(Integer.toString(productCartHolder.productCart.PRODUCT_CART_NUMBER));
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                CartFragment nextFrag = new CartFragment();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        productCartHolder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductCartDAO productCartDAO = new ProductCartDAO(context);
                if (productCartHolder.productCart.PRODUCT_CART_NUMBER > 1) {
                    productCartHolder.productCart.PRODUCT_CART_NUMBER--;
                    productCartDAO.updateProductCartAmount(new ProductCart("", productCartHolder.productCart.PRODUCT_CART_NUMBER, 0, ""), productCartHolder.productCart.PRODUCT_CART_NAME);
                    productCartHolder.tvNumber.setText(Integer.toString(productCartHolder.productCart.PRODUCT_CART_NUMBER));
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    CartFragment nextFrag = new CartFragment();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toasty.warning(context, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        productCartHolder.btnDelProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductCartDAO productCartDAO = new ProductCartDAO(context);
                removeProductCart(position);
                productCartDAO.deleteProductCart(productCartHolder.productCart.PRODUCT_CART_NAME);
                Toasty.success(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                context.sendBroadcast(new Intent("update"));
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                CartFragment nextFrag = new CartFragment();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });


    }

    private void removeProductCart(int position) {
        productCartList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, productCartList.size());
    }

    @Override
    public int getItemCount() {
        return productCartList.size();
    }

    public class ProductCartHolder extends RecyclerView.ViewHolder {
        private ImageView imgImageCartProduct;
        private TextView tvNameCartProduct;
        private TextView tvPriceCartProduct;
        private Button btnDecrease;
        private TextView tvNumber;
        private Button btnIncrease;
        private Button btnDelProductCart;
        private ProductCart productCart;

        public ProductCartHolder(@NonNull View itemView) {
            super(itemView);
            imgImageCartProduct = itemView.findViewById(R.id.imgImageCartProduct);
            tvNameCartProduct = itemView.findViewById(R.id.tvNameCartProduct);
            tvPriceCartProduct = itemView.findViewById(R.id.tvPriceCartProduct);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDelProductCart = itemView.findViewById(R.id.btnDelProductCart);
        }
    }
}
