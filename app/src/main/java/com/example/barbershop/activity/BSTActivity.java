package com.example.barbershop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.authenticationsms.R;
import com.squareup.picasso.Picasso;

public class BSTActivity extends AppCompatActivity {
    private ImageView image81;
    private ImageView image82;
    private ImageView image83;
    private ImageView image84;
    private ImageView image85;
    private ImageView image86;
    private ImageView image87;
    private ImageView image88;
    private ImageView image89;

    String style1 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F11.jpg?alt=media&token=33b4c939-7808-439c-a773-a2981eded34c";
    String style2 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F12.jpg?alt=media&token=681d3380-d390-4471-995c-a2b80befb7f4";
    String style3 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F14.jpg?alt=media&token=6e1f0937-57df-478e-becb-c632b913832e";
    String style4 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F21.jpg?alt=media&token=6df21f08-71a2-4c6f-a8ce-618db1a2f38e";
    String style5 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F22.jpg?alt=media&token=55df5027-df3f-4510-97b7-413f820887b9";
    String style6 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F23.jpg?alt=media&token=54130f66-5240-4e4e-a198-c4f811b08484";
    String style7 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F31.jpg?alt=media&token=ef5f9bd5-dad6-4db0-8da8-b492dfb0cbed";
    String style8 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F32.jpg?alt=media&token=68c630c4-4087-4cd9-87b3-5ca14f009ab3";
    String style9 = "https://firebasestorage.googleapis.com/v0/b/authenticationsms.appspot.com/o/Style%2F33.jpg?alt=media&token=a10af095-89c2-4c0b-be02-d85588dbbbe3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bst);
        image81 = findViewById(R.id.image81);
        image82 = findViewById(R.id.image82);
        image83 = findViewById(R.id.image83);
        image84 = findViewById(R.id.image84);
        image85 = findViewById(R.id.image85);
        image86 = findViewById(R.id.image86);
        image87 = findViewById(R.id.image87);
        image88 = findViewById(R.id.image88);
        image89 = findViewById(R.id.image89);

        Picasso.get().load(style1).into(image81);
        Picasso.get().load(style2).into(image82);
        Picasso.get().load(style3).into(image83);
        Picasso.get().load(style4).into(image84);
        Picasso.get().load(style5).into(image85);
        Picasso.get().load(style6).into(image86);
        Picasso.get().load(style7).into(image87);
        Picasso.get().load(style8).into(image88);
        Picasso.get().load(style9).into(image89);

    }
}
