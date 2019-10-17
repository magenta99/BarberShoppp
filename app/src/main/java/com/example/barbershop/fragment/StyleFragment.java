package com.example.barbershop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.authenticationsms.R;
import com.squareup.picasso.Picasso;

public class StyleFragment extends Fragment {
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView image8;
    private ImageView image9;
    private ImageView image10;
    private ImageView image11;
    private ImageView image12;
    String style1 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F11.jpg?alt=media&token=33b4c939-7808-439c-a773-a2981eded34c";
    String style2 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F12.jpg?alt=media&token=681d3380-d390-4471-995c-a2b80befb7f4";
    String style3 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F14.jpg?alt=media&token=6e1f0937-57df-478e-becb-c632b913832e";
    String style4 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F21.jpg?alt=media&token=6df21f08-71a2-4c6f-a8ce-618db1a2f38e";
    String style5 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F22.jpg?alt=media&token=55df5027-df3f-4510-97b7-413f820887b9";
    String style6 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F23.jpg?alt=media&token=54130f66-5240-4e4e-a198-c4f811b08484";
    String style7 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F31.jpg?alt=media&token=ef5f9bd5-dad6-4db0-8da8-b492dfb0cbed";
    String style8 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F32.jpg?alt=media&token=68c630c4-4087-4cd9-87b3-5ca14f009ab3";
    String style9 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F33.jpg?alt=media&token=a10af095-89c2-4c0b-be02-d85588dbbbe3";
    String style10 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F41.jpg?alt=media&token=336b5ffb-cbd0-48e1-a894-3a6c09114883";
    String style11 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F42.jpg?alt=media&token=eaf52429-283e-4d93-a4ac-94e83e87b5d4";
    String style12 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F43.jpg?alt=media&token=9e1ac2b6-1258-4d73-ad48-7342419505b3";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_style, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        image4 = view.findViewById(R.id.image4);
        image5 = view.findViewById(R.id.image5);
        image6 = view.findViewById(R.id.image6);
        image7 = view.findViewById(R.id.image7);
        image8 = view.findViewById(R.id.image8);
        image9 = view.findViewById(R.id.image9);
        image10 = view.findViewById(R.id.image10);
        image11 = view.findViewById(R.id.image11);
        image12 = view.findViewById(R.id.image12);

        Picasso.get().load(style1).into(image1);
        Picasso.get().load(style2).into(image2);
        Picasso.get().load(style3).into(image3);
        Picasso.get().load(style4).into(image4);
        Picasso.get().load(style5).into(image5);
        Picasso.get().load(style6).into(image6);
        Picasso.get().load(style7).into(image7);
        Picasso.get().load(style8).into(image8);
        Picasso.get().load(style9).into(image9);
        Picasso.get().load(style10).into(image10);
        Picasso.get().load(style11).into(image11);
        Picasso.get().load(style12).into(image12);

    }
}
