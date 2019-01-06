package com.creatudevelopers.ribeshmaharjan.ivy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailActivity extends AppCompatActivity {

    CircleImageView mprofile_image_userdetail;
    TextView musername_userdetail;
    TextView muser_email__userdetail;
    Button mlogout___userdetail;
    ImageButton mback_btn;
    TextView muser_id__userdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        mprofile_image_userdetail=findViewById(R.id.profile_image_userdetail);
        musername_userdetail=findViewById(R.id.username_userdetail);
        muser_email__userdetail=findViewById(R.id.user_email__userdetail);
        mlogout___userdetail=findViewById(R.id.logout___userdetail);
        mback_btn=findViewById(R.id.back_btn_userdetail);
        muser_id__userdetail=findViewById(R.id.user_id__userdetail);
        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        Integer id  = prefs.getInt("id", 0);
        final String name = prefs.getString("name", "");
        final String email = prefs.getString("email","");
        final String avatar=prefs.getString("image","");
        final boolean SignIngoogle=prefs.getBoolean("SignIngoogle",false);
        final boolean signinwithfb=prefs.getBoolean("signinwithfb",false);
        boolean FormLogin=prefs.getBoolean("FormLogin",false);

        musername_userdetail.setText(name);
        muser_email__userdetail.setText(email);
        /*if(FormLogin || SignIngoogle ) {
            Picasso.get().load(avatar).into(mprofile_image_userdetail);

        }
        else if (signinwithfb)
        {
            Picasso.get().load(RegisterActivity.profile_pic.toString()).into(mprofile_image_userdetail);
        }
        else
        {
            mprofile_image_userdetail.setImageResource(R.drawable.icons_userpng);
        }*/
        if(avatar!="") {
            Picasso.get().load(avatar).into(mprofile_image_userdetail);
        }
        else
        {
            mprofile_image_userdetail.setImageResource(R.drawable.icons_userpng);
        }
        muser_id__userdetail.setText(String.format("UserID: %s", id.toString()));
        mlogout___userdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("mypref", MODE_PRIVATE).edit();
                editor.putBoolean("Islogin",false);
                editor.putString("image","");
                editor.putString("phone","");
                editor.putBoolean("FormLogin",false);
                editor.putBoolean("SignIngoogle",false);
                editor.putBoolean("signinwithfb",false);
                editor.apply();
                //RegisterActivity.account=null;
                RegisterActivity.isLoggedIn=false;
                RegisterActivity.accessToken=null;
                if(SignIngoogle)
                {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .build();
                  GoogleSignInClient mgoogleSignInClient=GoogleSignIn.getClient(UserDetailActivity.this,gso);
                  mgoogleSignInClient.signOut();
                }
                if(signinwithfb)
                {
                   RegisterActivity.loginManager.getInstance().logOut();
                }
                //RegisterActivity.accessToken=null;
                Intent intent=new Intent(UserDetailActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
                //UserDetailActivity.super.onBackPressed();
            }
        });
        mback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetailActivity.super.onBackPressed();
            }
        });

    }
}
