package com.example.barbershop.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.authenticationsms.R;
import com.example.barbershop.activity.BSTActivity;
import com.example.barbershop.activity.BaseFragment;
import com.example.barbershop.activity.SalonActivity;
import com.example.barbershop.activity.TVActivity;
import com.example.barbershop.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends BaseFragment {
    LinearLayout llSignOut, llSalonList, llSalon, llTV, llHair;
    private TextView tvPhoneSettings;
    private TextView tvNameUser;
    private String name = "";
    private Button btnEditUserr;
    private BroadcastReceiver broadcastReceiver;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        tvPhoneSettings = view.findViewById(R.id.tvPhoneSettings);
        tvNameUser = view.findViewById(R.id.tvNameUser);
        llSignOut = view.findViewById(R.id.llSignOut);
        btnEditUserr = view.findViewById(R.id.btnEditUserr);
        llSalon = view.findViewById(R.id.llSalon);
        llHair = view.findViewById(R.id.llHair);
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
                HistoryFragment nextFrag= new HistoryFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
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
        tvPhoneSettings.setText(getRootPhone());
        getUsername(getRootPhone());

        btnEditUserr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = View.inflate(getContext(), R.layout.edit_user_item, null);
                mBuilder.setView(mView);
                mBuilder.setCancelable(true);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                final EditText edtEditUsername;
                final Button btnEditUser;
                final Button btnCancelEdt;

                edtEditUsername = mView.findViewById(R.id.edtEditUsername);
                btnEditUser = mView.findViewById(R.id.btnEditUser);
                btnCancelEdt = mView.findViewById(R.id.btnCancelEdt);

                btnCancelEdt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnEditUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String username = edtEditUsername.getText().toString().trim();
                            if (username.isEmpty()) {
                                showMessegeWarning("Vui lòng nhập họ tên");
                            }else {
                                AndroidNetworking.post("https://barber123.herokuapp.com/updateUser")
                                        .addQueryParameter("name", username)
                                        .addQueryParameter("phoneUser", getRootPhone())
                                        .setTag("test")
                                        .setPriority(Priority.MEDIUM)
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                            }
                                            @Override
                                            public void onError(ANError error) {
                                                Log.d("Lỗi",""+error);
                                            }
                                        });
                                showMessegeSuccess("Sửa thành công");
                                dialog.dismiss();
                                SettingsFragment nextFrag = new SettingsFragment();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_layout, nextFrag, "findThisFragment")
                                        .addToBackStack(null)
                                        .commit();
                            }

                        } catch (Exception e) {

                        }
                    }
                });

            }
        });
    }

    private String getRootPhone() {
        String name;
        name = getContext().getSharedPreferences("USER", MODE_PRIVATE).getString("PHONE", "");
        return name;
    }

    private String getRootName() {
        String name;
        name = getContext().getSharedPreferences("USER", MODE_PRIVATE).getString("USERNAME", "");
        return name;
    }

    private void getUsername(String phoneNumber) {
        AndroidNetworking.post("https://barber123.herokuapp.com/findNameUser")
                .addQueryParameter("phoneUser", getRootPhone())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        tvNameUser.setText(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

}

