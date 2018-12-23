package com.example.ribeshmaharjan.ivy;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailActivity extends AppCompatActivity {

    CircleImageView mprofile_image_userdetail;
    TextView musername_userdetail;
    TextView muser_email__userdetail;
    Button mlogout___userdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        mprofile_image_userdetail=findViewById(R.id.profile_image_userdetail);
        musername_userdetail=findViewById(R.id.username_userdetail);
        muser_email__userdetail=findViewById(R.id.user_email__userdetail);
        mlogout___userdetail=findViewById(R.id.logout___userdetail);
        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        String id  = prefs.getString("name", "1");
        final String name = prefs.getString("name", "");
        final String email = prefs.getString("email","");
        final String avatar=prefs.getString("image","");
        musername_userdetail.setText(name);
        muser_email__userdetail.setText(email);
        Picasso.get().load(avatar).into(mprofile_image_userdetail);
        mlogout___userdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("mypref", MODE_PRIVATE).edit();
                editor.putBoolean("Islogin",false);
                editor.apply();

                UserDetailActivity.super.onBackPressed();

            }
        });

    }
}
