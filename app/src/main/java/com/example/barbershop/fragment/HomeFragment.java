package com.example.barbershop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.authenticationsms.R;

public class HomeFragment extends Fragment {
    private ViewFlipper viewFlipper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home,container,false);
        initView(view);
        int images[] = {R.drawable.main1, R.drawable.main2, R.drawable.main3,R.drawable.main4};
        for (int image : images) {
            flipperImages(image);
        }
        return view;
    }

    private void flipperImages(int image) {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2500);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getContext(), android.R.anim.fade_in);
    }

    private void initView(View view){
        viewFlipper = view.findViewById(R.id.vpSlider);
    }
}
