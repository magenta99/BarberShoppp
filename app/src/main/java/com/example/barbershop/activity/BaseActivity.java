package com.example.barbershop.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authenticationsms.R;
import com.pusher.pushnotifications.PushNotifications;

import es.dmoral.toasty.Toasty;

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        PushNotifications.start(getApplicationContext(), "f8f723dd-a9e2-4e4a-bbf2-287f01019905");
        PushNotifications.addDeviceInterest("hello");
    }

    public void startNewActivity(Class target){
        Intent intent = new Intent(this,target);
        startActivity(intent);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showMessegeSuccess(String message1) {
        Toasty.success(this, message1, Toast.LENGTH_SHORT).show();
    }

    public void showMessegeWarning(String message2) {
        Toasty.warning(this, message2, Toast.LENGTH_SHORT).show();
    }

    public void showMessegeError(String message3) {
        Toasty.error(this, message3, Toast.LENGTH_SHORT).show();
    }

//    public static class ProgressDialogUtils {
//
//        public ProgressDialogUtils() {}
//
//        public static ProgressDialog showLoadingDialog(Context context) {
//            ProgressDialog progressDialog = new ProgressDialog(context);
//            progressDialog.setTitle(context.getResources().getString(R.string.progress_dialog_title));
//            progressDialog.setMessage(context.getResources().getString(R.string.progress_dialog_message));
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            if(progressDialog.getWindow() != null) {
//                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//            }
//            progressDialog.setContentView(R.layout.layout_progress);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setCancelable(true);
//            progressDialog.setCanceledOnTouchOutside(false);
//            //progressDialog.setMax(100);
//            //progressDialog.setProgress(0);
//            progressDialog.show();
//            return progressDialog;
//        }
//
//    }
}
