package com.example.barbershop.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.authenticationsms.R;
import com.example.barbershop.activity.BSTActivity;
import com.example.barbershop.activity.SalonActivity;
import com.example.barbershop.activity.TVActivity;
import com.example.barbershop.activity.LoginActivity;

import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {
    LinearLayout llSignOut, llSalonList, llSalon, llTV, llHair;
    private TextView tvPhoneSettings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initView(view);

        return view;
    }

    public void initView(View view) {
        tvPhoneSettings = view.findViewById(R.id.tvPhoneSettings);
        llSignOut = view.findViewById(R.id.llSignOut);
        llSalon = view.findViewById(R.id.llSalon);
        llHair = view.findViewById(R.id.llHair);
        llTV = view.findViewById(R.id.llTV);
        llSalonList = view.findViewById(R.id.llSalonList);
        llSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                mBuilder.setMessage("Bạn có muốn đăng xuất không?");
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER", MODE_PRIVATE);
                        sharedPreferences.edit().remove("PHONE").commit();
                    }
                });
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        llHair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.warning(getContext(), "Bạn không có lịch sử cắt tóc", Toast.LENGTH_SHORT).show();
            }
        });

        llTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TVActivity.class);
                startActivity(intent);
            }
        });

        llSalonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BSTActivity.class);
                startActivity(intent);
            }
        });

        llSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SalonActivity.class);
                startActivity(intent);
            }
        });

        tvPhoneSettings.setText(getRootUsername());

    }

    private String getRootUsername() {
        String name;
        name = getContext().getSharedPreferences("USER", MODE_PRIVATE).getString("NAME", "");
        return name;
    }

}

