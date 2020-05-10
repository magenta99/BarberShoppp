package com.example.barbershop.activity;

import android.content.Intent;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.authenticationsms.R;
import com.example.barbershop.fragment.WaxFragment;

import es.dmoral.toasty.Toasty;

public abstract class BaseFragment extends Fragment  {

    public void startNewActivity(Class target){
        Intent intent = new Intent(getContext(),target);
        startActivity(intent);
    }



    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showMessegeSuccess(String message1) {
        Toasty.success(getContext(), message1, Toast.LENGTH_SHORT).show();
    }

    public void showMessegeWarning(String message2) {
        Toasty.warning(getContext(), message2, Toast.LENGTH_SHORT).show();
    }

    public void showMessegeError(String message3) {
        Toasty.error(getContext(), message3, Toast.LENGTH_SHORT).show();
    }

}
