package com.example.barbershop.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.authenticationsms.R;
import com.example.barbershop.adapter.MyViewPagerAdapter;
import com.shuhart.stepview.StepView;
import java.util.ArrayList;
import java.util.List;


public class BookingFragment extends Fragment {
    StepView stepView;
    ViewPager viewPagerStep;
    Button btn_back;
    Button btn_next;
    int number = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        stepView = view.findViewById(R.id.step_view);
        viewPagerStep = view.findViewById(R.id.viewPagerStep);
        btn_back = view.findViewById(R.id.btn_back);
        btn_next = view.findViewById(R.id.btn_next);

        setupStepView();

        viewPagerStep.setAdapter(new MyViewPagerAdapter(getFragmentManager()));
        viewPagerStep.setCurrentItem(0);
        viewPagerStep.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
                stepView.go(i,true);
                number = i;
                if (number == 0) {
                    btn_back.setEnabled(false);
                    Toast.makeText(getContext(), "dmm", Toast.LENGTH_SHORT).show();
                    btn_back.setBackgroundResource(R.color.colorUnselected);
                    btn_back.setTextColor(getResources().getColor(R.color.colorBlack));
                } else {
                    btn_back.setEnabled(true);
                    btn_back.setBackgroundResource(R.color.colorBlack);
                    btn_back.setTextColor(getResources().getColor(R.color.colorGold));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "aaaa", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void setupStepView() {
        List<String> stepList = new ArrayList<>();
        stepList.add("Salon");
        stepList.add("Ngày giờ");
        stepList.add("Dịch vụ và thợ cắt");
        stepList.add("Xác nhận");
        stepView.setSteps(stepList);
    }
}
